package webfotos;

import net.sf.webphotos.BancoImagem;
import java.io.File;


import org.apache.log4j.Logger;

import webfotos.gui.FrameWebFotos;
import webfotos.util.Util;

/**
 * Classe responsável pelo inicio do sistema WebPhotos.
 * Realiza algumas rotinas para início do sistema.
 * Os dados são trabalhados somente no método principal.
 */
public class WebPhotos {

    private static Logger log = Logger.getLogger(WebPhotos.class);

    /**
     * Contêm uma série de rotinas para inicialização do programa.
     * No primeiro momento, o cache do FTP é apagado.
     * Logo após é feita uma busca do arquivo de configuração.
     * é obtido o driver do DB, e são preparadas as conexões para usar Socks Proxy.
     * Por último é exibida a tela de login ao usuário.
     * @param args Argumentos do método main.
     */
    public static void main(String args[]) {

        try {

            // apaga o cache ftp
            File cacheftp = new File("CacheFTP.txt");
            if (cacheftp.isFile()) {
                cacheftp.delete();
            }

            Util.loadSocksProxy();
            BancoImagem.loadUIManager();
            BancoImagem.loadDBDriver();

            // mostra tela de login
            BancoImagem.login();

            // Eficiente para produção - Ruim para depuração
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    FrameWebFotos.getInstance().setVisible(true);
                }
            });

        } catch (Exception e) {
            log.error("Não foi possível iniciar o WebFotos", e);
        }

    }
}
