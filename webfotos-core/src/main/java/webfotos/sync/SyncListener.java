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
 * Interface ouvinte da sincronização.
 * Herda a classe EventListener.
 * Cria três métodos públicos que recebem eventos de sincronização, da classe
 * {@link webfotos.sync.SyncEvent SyncEvent}.
 * @author guilherme
 */
public interface SyncListener extends EventListener {
    
    /**
     * Trabalha o evento para apresentar a conexão ao FTP.
     * @param event Evento de sincronização.
     */
    public void connected(SyncEvent event);
    
    /**
     * Trabalha o evento para apresentar o início do logon.
     * @param event Evento de sincronização.
     */
    public void logonStarted(SyncEvent event);
    
    /**
     * Trabalha o evento para apresentar a desconexão do FTP.
     * @param event Evento de sincronização.
     */
    public void disconnected(SyncEvent event);
}
