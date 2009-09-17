package webfotos;

import javax.swing.UIManager;
import java.io.*;
import javax.swing.*;
import org.apache.log4j.Logger;
//import webfotos.gui.SplashScreenA2;
import webfotos.util.*;

import webfotos.gui.FrameWebFotos;

/**
 * Classe respons�vel pelo inicio do sistema WebFotos.
 * Realiza algumas rotinas para in�cio do sistema.
 * Os dados s�o trabalhados somente no m�todo principal.
 */
public class WebFotos {

    private static Logger log = Logger.getLogger(WebFotos.class);

    /**
     * Cont�m uma s�rie de rotinas para inicializa��o do programa.
     * No primeiro momento, o cache do FTP � apagado.
     * Logo ap�s � feita uma busca do arquivo de configura��o.
     * � obtido o driver do DB, e s�o preparadas as conex�es para usar Socks Proxy.
     * Por �ltimo � exibida a tela de login ao usu�rio.
     * @param args Argumentos do m�todo main.
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

            // obt�m driver do db
            String url = Util.getConfig().getString("jdbc.url");
            String driver = Util.getConfig().getString("jdbc.driver");
            
            String lookAndFeel = Util.getConfig().getString("UIManager.lookAndFeel");

            //TODO: Rever
            //Util.getConfig().storeXML(new FileOutputStream("webfotos.xml"));

            //Prepara as conex�es para usar Socks Proxy (se configurado)
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

            // Eficiente para produ��o - Ruim para depura��o
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    FrameWebFotos.getInstance().setVisible(true);
                }
            });

        } catch (Exception e) {
            log.error("N�o foi poss�vel iniciar o WebFotos", e);
        }

    }
}
