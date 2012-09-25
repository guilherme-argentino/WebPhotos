/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.webphotos.action;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.sf.webphotos.gui.PainelWebFotos;

/**
 * Notifica alterações no documento. Checa se ocorreram modificações e através
 * do método da classe chamado altera, alcança outro método da classe
 * PainelWebFotos chamado
 * {@link net.sf.webphotos.gui.PainelWebFotos#alteracaoDetectada(DocumentEvent) alteracaoDetectada}(DocumentEvent
 * e) que tem funcionalidade de uma flag.
 */
public class AcaoDocumentListener implements DocumentListener {

    /**
     * Construtor da classe. Inicialmente vazio pois a classe não possui dados
     * atributos.
     */
    public AcaoDocumentListener() {
    }

    /**
     * Implementa o método insertUptade da interface
     * {@link javax.swing.event.DocumentListener DocumentListener}. Faz uso da
     * função
     * {@link net.sf.webphotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent
     * e) da própria classe. Não possui utilizações.
     *
     * @param e Notifica mudanças no documento.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        altera(e);
    }

    /**
     * Implementa o método removeUptade da interface
     * {@link javax.swing.event.DocumentListener DocumentListener}. Faz uso da
     * função
     * {@link net.sf.webphotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent
     * e) da própria classe. Não possui utilizações.
     *
     * @param e Notifica mudanças no documento.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        altera(e);
    }

    /**
     * Implementa o método changeUptade da interface
     * {@link javax.swing.event.DocumentListener DocumentListener}. Faz uso da
     * função
     * {@link net.sf.webphotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent
     * e) da própria classe. Não possui utilizações.
     *
     * @param e Notifica mudanças no documento.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        altera(e);
    }

    /**
     * Faz uso da função
     * {@link net.sf.webphotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}()
     * da classe PainelWebFotos, para checar se ocorreu alguma alteração. Uma
     * espécie de flag.
     *
     * @param e Notifica mudanças no documento.
     */
    public void altera(DocumentEvent e) {
        PainelWebFotos.alteracaoDetectada();
    }
}
