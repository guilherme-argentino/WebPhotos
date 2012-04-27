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
 * SyncListener.java
 *
 * Created on 19 de Maio de 2006, 15:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.webphotos.sync;

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
