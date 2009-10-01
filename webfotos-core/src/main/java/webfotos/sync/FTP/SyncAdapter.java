/*
 * SyncAdapter.java
 *
 * Created on 19 de Maio de 2006, 16:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.sync.FTP;

import org.apache.commons.net.util.ListenerList;

import webfotos.sync.SyncEvent;
import webfotos.sync.SyncListener;

/**
 * Adapta o listener para sincronização.
 * Implementa a classe SyncListener.
 * @author guilherme
 */
public class SyncAdapter implements SyncListener {
    
    private ListenerList internalListeners;
    
    /**
     * Construtor da classe.
     * Cria uma nova instância de SyncAdapter.
     */
    public SyncAdapter() {
        internalListeners = new ListenerList();
    }

    /**
     * Registra a FTPSyncListener para receber FTPSyncEvents.
     * Embora este método não esteja declarado para ser sincronizado, é
     * executado em uma maneira segura de thread.
     * @param listener O FTPSyncListener para registrar.
     */
    public void addSyncListener(SyncListener listener) {
        internalListeners.addListener(listener);
    }

    /**
     * Remove a FTPSyncListener.
     * Embora este método não esteja sincronizado,
     * é executado em uma maneira segura de thread.
     * @param listener  O FTPSyncListener para remover.
     */
    public void removeSyncListener(SyncListener listener) {
        internalListeners.removeListener(listener);
    }
    
    /**
     * Implementa o método disconnected da interface
     * {@link webfotos.sync.SyncListener SyncListener}.
     * Esse método será manipulado e trabalhado na classe
     * {@link webfotos.gui.FtpClient FtpClient}.
     * @param event Evento de SyncEvent.
     */
    @Override
    public void disconnected(SyncEvent event) { }
    
    /**
     * Implementa o método connected da interface
     * {@link webfotos.sync.SyncListener SyncListener}.
     * Esse método será manipulado e trabalhado na classe
     * {@link webfotos.gui.FtpClient FtpClient}.
     * @param event Evento de SyncEvent.
     */
    @Override
    public void connected(SyncEvent event) { }

    /**
     * Implementa o método logonStarted da interface
     * {@link webfotos.sync.SyncListener SyncListener}.
     * Esse método será manipulado e trabalhado na classe
     * {@link webfotos.gui.FtpClient FtpClient}.
     * @param event Evento de SyncEvent.
     */
    @Override
    public void logonStarted(SyncEvent event) {
    }
    
}
