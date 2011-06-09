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
/*
 * AcaoPopup.java
 *
 * Created on 29 de Maio de 2006, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.acao;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/**
 * Trabalha o evento do clique do mouse (mousePressed) e apresenta uma janela com menu popup.
 * Possui um dado atributo, objeto do tipo {@link javax.swing.JPopupMenu JPopupMenu} que é a janela popup.
 * Após acionado o método {@link java.awt.event.MouseAdapter#mousePressed(MouseEvent) mousePressed}(MouseEvent evt), exibe a janela popup com o valor armazenado no objeto.
 * @author guilherme
 */
public class AcaoPopup extends MouseAdapter {
    private JPopupMenu menu;
    /**
     * Construtor da classe.
     * Recebe um objeto {@link javax.swing.JPopupMenu JPopupMenu} e seta esse valor a variável existente na classe.
     * @param m Menu popup.
     */
    public AcaoPopup(JPopupMenu m) { menu=m; }

    /**
     * Esse método gera uma janela popup.
     * Ao clicar do mouse, abre uma janela Popup com o conteúdo do objeto menu, e com dimensão já especificada nos dados de envio dos parâmetros.
     * @param evt Evento de ação do mouse.
     */
    @Override
    public void mousePressed(MouseEvent evt) {
    	menu.show(evt.getComponent(), (menu.getWidth()-24) * -1, 24);
    }
}	
