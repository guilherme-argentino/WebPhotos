package webfotos.acao;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import webfotos.gui.PainelWebFotos;

/**
 * Notifica alterações no documento.
 * Checa se ocorreram modificações e através do método da classe chamado altera, alcança outro método da classe PainelWebFotos chamado {@link webfotos.gui.PainelWebFotos#alteracaoDetectada(DocumentEvent) alteracaoDetectada}(DocumentEvent e) que tem funcionalidade de uma flag.
 */
public class AcaoDocumentListener implements DocumentListener {
    /**
     * Construtor da classe.
     * Inicialmente vazio pois a classe não possui dados atributos.
     */
    public AcaoDocumentListener() {}
    /**
     * Implementa o método insertUptade da interface {@link javax.swing.event.DocumentListener DocumentListener}.
     * Faz uso da função {@link webfotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent e) da própria classe.
     * Não possui utilizações.
     * @param e Notifica mudanças no documento.
     */
    public void insertUpdate(DocumentEvent e) { altera(e); }
    /**
     * Implementa o método removeUptade da interface {@link javax.swing.event.DocumentListener DocumentListener}.
     * Faz uso da função {@link webfotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent e) da própria classe.
     * Não possui utilizações.
     * @param e Notifica mudanças no documento.
     */
    public void removeUpdate(DocumentEvent e) { altera(e); }
    /**
     * Implementa o método changeUptade da interface {@link javax.swing.event.DocumentListener DocumentListener}.
     * Faz uso da função {@link webfotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent e) da própria classe.
     * Não possui utilizações.
     * @param e Notifica mudanças no documento.
     */
    public void changedUpdate(DocumentEvent e) { altera(e); }
	
    /**
     * Faz uso da função {@link webfotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}() da classe PainelWebFotos, para checar se ocorreu alguma alteração.
     * Uma espécie de flag.
     * @param e Notifica mudanças no documento.
     */
    public void altera(DocumentEvent e) {
	PainelWebFotos.alteracaoDetectada();
    }
}
