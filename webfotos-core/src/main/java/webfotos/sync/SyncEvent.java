/*
 * SyncEvent.java
 *
 * Created on 19 de Maio de 2006, 15:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.sync;

import java.util.EventObject;

/**
 * Trabalha os eventos de sincronização através da classe
 * {@link java.util.EventObject EventObject}, utilizada nas funções connect e
 * disconnect de {@link webfotos.sync.FTP.SyncObject SyncObject}.
 * @author guilherme
 */
public class SyncEvent extends EventObject {
    
    private boolean retrying;
    
    /**
     * Construtor da classse.
     * Cria uma nova instância de SyncEvent, recebe um Object e o envia para
     * a classe base (no caso {@link java.util.EventObject EventObject}).
     * Também seta o valor <i>false</i> para a variável retrying.
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
     * Recebe o valor para a variável retrying e o implementa.
     * @param o Object para envio a classe base.
     * @param retrying Valor lógico da variável retrying.
     */
    public SyncEvent(Object o, boolean retrying) {
        super(o);
        this.retrying = retrying;
    }

    /**
     * Retorna o valor da variável retrying.
     * @return Retorna a variável retrying.
     */
    public boolean isRetrying() {
        return retrying;
    }

    /**
     * Seta o valor da variável retrying.
     * @param retrying Variável lógica.
     */
    public void setRetrying(boolean retrying) {
        this.retrying = retrying;
    }
    
}
