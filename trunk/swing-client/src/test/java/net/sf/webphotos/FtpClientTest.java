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
package net.sf.webphotos;

import java.io.PrintWriter;
import java.net.InetAddress;
import net.sf.webphotos.gui.util.FtpClient;
import net.sf.webphotos.util.Util;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author Guilherme
 */
public class FtpClientTest {

    /**
     * Execução separada do sistema. Serve, principalmente, para sincronizar as
     * pastas entre o servidor e o cliente. Obtém o driver do db e url,
     * configura esses valores para o banco de imagens e mostra a tela de login.
     * Depois inicia uma thread de FtpClient.
     *
     * @param args args do método main.
     */
    public static void main(String[] args) {
        try {
            Util.loadSocksProxy();

            /**
             * FIXEME: change this code to derby.drda.startNetworkServer=true
             */
            NetworkServerControl server = new NetworkServerControl(InetAddress.getByName(Util.getConfig().getString("derby.drda.host")), Util.getConfig().getInt("derby.drda.portNumber"));
            server.start(new PrintWriter(Util.out));
            server.setMaxThreads(100);
            
            BancoImagem.loadUIManager();
            BancoImagem.loadDBDriver();
            BancoImagem.login();
            Thread cl = new Thread(new FtpClient());
            cl.start();
        } catch (Exception ex) {
            Util.err.println(ex);
        }
    }
    
}
