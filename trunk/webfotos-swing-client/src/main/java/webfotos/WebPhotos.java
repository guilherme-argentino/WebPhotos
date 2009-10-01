package webfotos;

import net.sf.webphotos.BancoImagem;
import java.io.File;


import org.apache.log4j.Logger;

import webfotos.gui.FrameWebFotos;
import webfotos.util.Util;

/**
 * Classe respons�vel pelo inicio do sistema WebPhotos.
 * Realiza algumas rotinas para in�cio do sistema.
 * Os dados s�o trabalhados somente no m�todo principal.
 */
public class WebPhotos {

    private static Logger log = Logger.getLogger(WebPhotos.class);

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

            Util.loadSocksProxy();
            BancoImagem.loadUIManager();
            BancoImagem.loadDBDriver();

            // mostra tela de login
            BancoImagem.login();

            // Eficiente para produ��o - Ruim para depura��o
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    FrameWebFotos.getInstance().setVisible(true);
                }
            });

        } catch (Exception e) {
            log.error("N�o foi poss�vel iniciar o WebFotos", e);
        }

    }
}
