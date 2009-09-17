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
 * Ajusta o modo de apari��o da janela quando ganha ou perde o foco.
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
     * Implementa o m�todo windowGainedFocus(WindowEvent e) da interface {@link java.awt.event.WindowFocusListener WindowFocusListener}.
     * @param e Evento de janela.
     */
    public void windowGainedFocus(WindowEvent e) {}
    /**
     * Implementa o m�todo windowLostFocus(WindowEvent e) da interface {@link java.awt.event.WindowFocusListener WindowFocusListener}.
     * @param e Evento de janela.
     */
    public void windowLostFocus(WindowEvent e) {
        frame.toFront();
    }
}
