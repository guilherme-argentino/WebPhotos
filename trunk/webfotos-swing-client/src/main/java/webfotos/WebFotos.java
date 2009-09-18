package webfotos;

import java.io.File;

import javax.swing.UIManager;

import org.apache.log4j.Logger;

import webfotos.gui.FrameWebFotos;
import webfotos.util.Util;

/**
 * Classe responsável pelo inicio do sistema WebFotos.
 * Realiza algumas rotinas para início do sistema.
 * Os dados são trabalhados somente no método principal.
 */
public class WebFotos {

    private static Logger log = Logger.getLogger(WebFotos.class);

    /**
     * Contêm uma série de rotinas para inicialização do programa.
     * No primeiro momento, o cache do FTP ï¿½ apagado.
     * Logo após ï¿½ feita uma busca do arquivo de configuração.
     * ï¿½ obtido o driver do DB, e são preparadas as conexões para usar Socks Proxy.
     * Por último ï¿½ exibida a tela de login ao usuário.
     * @param args Argumentos do método main.
     */
    public static void main(String args[]) {

        try {

            // apaga o cache ftp
            File cacheftp = new File("CacheFTP.txt");
            if (cacheftp.isFile()) {
                cacheftp.delete();
            }

            String socksHost = Util.getConfig().getString("socks.host");
            int socksPort = 0;
            if(Util.getConfig().containsKey("socks.port")) {
                socksPort = Util.getConfig().getInt("socks.port");
            }

            // obtém driver do db
            String url = Util.getConfig().getString("jdbc.url");
            String driver = Util.getConfig().getString("jdbc.driver");
            
            String lookAndFeel = Util.getConfig().getString("UIManager.lookAndFeel");

            //Prepara as conexões para usar Socks Proxy (se configurado)
            if (socksHost != null && ! socksHost.equals("")) {
                System.getProperties().put("socksProxyHost", socksHost);
                if (socksPort > 0 && socksPort < 65534) {
                    System.getProperties().put("socksProxyPort", socksPort);
                }
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch ( Exception e ) {
                log.warn("Caution: Theme not correctly configured");
                //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }

            // mostra tela de login
            BancoImagem.getBancoImagem().configure(url, driver);
            BancoImagem.login();

            //SplashScreenA2.getInstance().setVisible(true);

            // Eficiente para produção - Ruim para depuração
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    FrameWebFotos.getInstance().setVisible(true);
                }
            });

        } catch (Exception e) {
            log.error("Não foi possível iniciar o WebFotos", e);
        }

    }
}
