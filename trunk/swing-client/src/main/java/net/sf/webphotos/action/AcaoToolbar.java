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
package net.sf.webphotos.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import org.apache.log4j.Logger;
import net.sf.webphotos.gui.util.FtpClient;
import net.sf.webphotos.gui.PainelWebFotos;

/**
 * Identifica uma ação obtida ao clique de um dos botões da barra de ferramentas localizada no canto inferior direito do painel principal.
 * Na classe {@link net.sf.webphotos.gui.BotaoIcone BotaoIcone}, onde será realizada uma instância de AcaoToolbar, será transmitido o evento para posteriormente implementar a ação do botão nesta classe.
 */
public class AcaoToolbar extends AbstractAction {

    private static final long serialVersionUID = 5232557211589840659L;
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Construtor da classe.
     * Inicialmente vazio, pois a classe não possui atributos.
     */
    public AcaoToolbar() {
    }

    /**
     * Recupera o nome setado em ActionCommand numa String.
     * Executa testes para checar qual o comando desejado.
     * Caso clique no primeiro botão, inicia uma thread de FtpClient.
     * Se for um dos botões do meio, executa o método {@link net.sf.webphotos.gui.PainelWebFotos#marcaAlbunsFTP(int) marcaAlbunsFTP(int comando)} e marca para download ou upload.
     * E caso seja o último botão, adiciona fotos.
     * @param e Evento da ação
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("ftp")) {
            Thread t = new Thread(new FtpClient());
            t.start();
        } else if (comando.equals("down")) {
            PainelWebFotos.marcaAlbunsFTP(2);
        } else if (comando.equals("up")) {
            PainelWebFotos.marcaAlbunsFTP(1);
        } else if (comando.equals("camera")) {
            log.debug("adicionar fotos");
        }
    }
}
