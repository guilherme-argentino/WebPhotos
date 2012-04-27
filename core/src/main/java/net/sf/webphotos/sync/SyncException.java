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
 * SyncException.java
 *
 * Created on 23 de Maio de 2006, 11:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.webphotos.sync;

/**
 * Exceção de sincronização.
 * Herda a classe {@link java.lang.Exception Exception} e trabalha as exceções
 * através dela.
 * @author guilherme
 */
public class SyncException extends java.lang.Exception {
    
    /**
     * Cria uma nova instância de <code>SyncException</code> fazendo uma
     * chamada a classe base.
     */
    public SyncException() {
        super();
    }
    
    
    /**
     * Cria uma nova instância de <code>SyncException</code>
     * empilhando outra exceção.
     * @param e Uma exceção.
     */
    public SyncException(Exception e) {
        super(e.getMessage());
        initCause(e.getCause());
        setStackTrace(e.getStackTrace());        
    }
    
    
    /**
     * Cria uma nova instância de <code>SyncException</code> fazendo uma
     * chamada a classe base e especificando uma mensagem.
     * @param msg Mensagem detalhada.
     */
    public SyncException(String msg) {
        super(msg);
    }
}
