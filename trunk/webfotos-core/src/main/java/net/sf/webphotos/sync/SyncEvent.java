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
 * SyncEvent.java
 *
 * Created on 19 de Maio de 2006, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.webphotos.sync;

import java.util.EventObject;

/**
 * Trabalha os eventos de sincroniza��o atrav�s da classe
 * {@link java.util.EventObject EventObject}, utilizada nas fun��es connect e
 * disconnect de {@link webfotos.sync.FTP.SyncObject SyncObject}.
 * @author guilherme
 */
public class SyncEvent extends EventObject {
    
    private boolean retrying;
    
    /**
     * Construtor da classse.
     * Cria uma nova inst�ncia de SyncEvent, recebe um Object e o envia para
     * a classe base (no caso {@link java.util.EventObject EventObject}).
     * Tamb�m seta o valor <i>false</i> para a vari�vel retrying.
     * @param o Object para envio a classe base.
     */
    public SyncEvent(Object o) {
        super(o);
        retrying = false;
    }
    
    /**
     * Construtor da classse.
     * Cria uma nova instancia de SyncEvent, recebe um Object e o envia para
     * a classe base (no caso {@link  java.util.EventObject EventObject}).
     * Recebe o valor para a vari�vel retrying e o implementa.
     * @param o Object para envio a classe base.
     * @param retrying Valor l�gico da vari�vel retrying.
     */
    public SyncEvent(Object o, boolean retrying) {
        super(o);
        this.retrying = retrying;
    }

    /**
     * Retorna o valor da vari�vel retrying.
     * @return Retorna a vari�vel retrying.
     */
    public boolean isRetrying() {
        return retrying;
    }

    /**
     * Seta o valor da vari�vel retrying.
     * @param retrying Vari�vel l�gica.
     */
    public void setRetrying(boolean retrying) {
        this.retrying = retrying;
    }
    
}
