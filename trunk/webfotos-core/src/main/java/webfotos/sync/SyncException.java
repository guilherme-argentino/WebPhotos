/*
 * SyncException.java
 *
 * Created on 23 de Maio de 2006, 11:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.sync;

/**
 * Exce��o de sincroniza��o.
 * Herda a classe {@link java.lang.Exception Exception} e trabalha as exce��es
 * atrav�s dela.
 * @author guilherme
 */
public class SyncException extends java.lang.Exception {
    
    /**
     * Cria uma nova inst�ncia de <code>SyncException</code> fazendo uma
     * chamada a classe base.
     */
    public SyncException() {
        super();
    }
    
    
    /**
     * Cria uma nova inst�ncia de <code>SyncException</code>
     * empilhando outra exce��o.
     * @param e Uma exce��o.
     */
    public SyncException(Exception e) {
        super(e.getMessage());
        initCause(e.getCause());
        setStackTrace(e.getStackTrace());        
    }
    
    
    /**
     * Cria uma nova inst�ncia de <code>SyncException</code> fazendo uma
     * chamada a classe base e especificando uma mensagem.
     * @param msg Mensagem detalhada.
     */
    public SyncException(String msg) {
        super(msg);
    }
}
