/*
 * SyncListener.java
 *
 * Created on 19 de Maio de 2006, 15:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.sync;

import java.util.EventListener;

/**
 * Interface ouvinte da sincroniza��o.
 * Herda a classe EventListener.
 * Cria tr�s m�todos p�blicos que recebem eventos de sincroniza��o, da classe
 * {@link webfotos.sync.SyncEvent SyncEvent}.
 * @author guilherme
 */
public interface SyncListener extends EventListener {
    
    /**
     * Trabalha o evento para apresentar a conex�o ao FTP.
     * @param event Evento de sincroniza��o.
     */
    public void connected(SyncEvent event);
    
    /**
     * Trabalha o evento para apresentar o in�cio do logon.
     * @param event Evento de sincroniza��o.
     */
    public void logonStarted(SyncEvent event);
    
    /**
     * Trabalha o evento para apresentar a desconex�o do FTP.
     * @param event Evento de sincroniza��o.
     */
    public void disconnected(SyncEvent event);
}
