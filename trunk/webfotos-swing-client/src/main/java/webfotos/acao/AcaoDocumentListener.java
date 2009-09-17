package webfotos.acao;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import webfotos.gui.PainelWebFotos;

/**
 * Notifica altera��es no documento.
 * Checa se ocorreram modifica��es e atrav�s do m�todo da classe chamado altera, alcan�a outro m�todo da classe PainelWebFotos chamado {@link webfotos.gui.PainelWebFotos#alteracaoDetectada(DocumentEvent) alteracaoDetectada}(DocumentEvent e) que tem funcionalidade de uma flag.
 */
public class AcaoDocumentListener implements DocumentListener {
    /**
     * Construtor da classe.
     * Inicialmente vazio pois a classe n�o possui dados atributos.
     */
    public AcaoDocumentListener() {}
    /**
     * Implementa o m�todo insertUptade da interface {@link javax.swing.event.DocumentListener DocumentListener}.
     * Faz uso da fun��o {@link webfotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent e) da pr�pria classe.
     * N�o possui utiliza��es.
     * @param e Notifica mudan�as no documento.
     */
    public void insertUpdate(DocumentEvent e) { altera(e); }
    /**
     * Implementa o m�todo removeUptade da interface {@link javax.swing.event.DocumentListener DocumentListener}.
     * Faz uso da fun��o {@link webfotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent e) da pr�pria classe.
     * N�o possui utiliza��es.
     * @param e Notifica mudan�as no documento.
     */
    public void removeUpdate(DocumentEvent e) { altera(e); }
    /**
     * Implementa o m�todo changeUptade da interface {@link javax.swing.event.DocumentListener DocumentListener}.
     * Faz uso da fun��o {@link webfotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent e) da pr�pria classe.
     * N�o possui utiliza��es.
     * @param e Notifica mudan�as no documento.
     */
    public void changedUpdate(DocumentEvent e) { altera(e); }
	
    /**
     * Faz uso da fun��o {@link webfotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}() da classe PainelWebFotos, para checar se ocorreu alguma altera��o.
     * Uma esp�cie de flag.
     * @param e Notifica mudan�as no documento.
     */
    public void altera(DocumentEvent e) {
	PainelWebFotos.alteracaoDetectada();
    }
}
