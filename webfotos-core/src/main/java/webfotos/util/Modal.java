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
 * Modal.java
 *
 * Created on 31 de Maio de 2006, 11:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.util;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

/**
 * Ajusta o modo de aparição da janela quando ganha ou perde o foco.
 * Utilizada na classe FtpClient.
 * @author guilherme
 */
public class Modal implements WindowFocusListener {
    private JFrame frame;

    /**
     * Recebe um frame e seta o frame da classe.
     * @param f Frame.
     */
    public Modal(JFrame f) { frame=f; }
    /**
     * Implementa o método windowGainedFocus(WindowEvent e) da interface {@link java.awt.event.WindowFocusListener WindowFocusListener}.
     * @param e Evento de janela.
     */
    public void windowGainedFocus(WindowEvent e) {}
    /**
     * Implementa o método windowLostFocus(WindowEvent e) da interface {@link java.awt.event.WindowFocusListener WindowFocusListener}.
     * @param e Evento de janela.
     */
    public void windowLostFocus(WindowEvent e) {
        frame.toFront();
    }
}
