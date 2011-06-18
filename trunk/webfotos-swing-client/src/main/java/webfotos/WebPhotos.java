/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package webfotos;

import net.sf.webphotos.BancoImagem;
import java.io.File;
import java.io.PrintWriter;
import java.net.InetAddress;
import org.apache.derby.drda.NetworkServerControl;


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

            /**
             * FIXEME: change this code to derby.drda.startNetworkServer=true
             */
            NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(Util.getConfig().getString("derby.drda.host")), Util.getConfig().getInt("derby.drda.portNumber"));
            server.start(new PrintWriter(Util.out));
            server.setMaxThreads(100);

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
