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
 * Notifica altera��es no documento. Checa se ocorreram modifica��es e atrav�s
 * do m�todo da classe chamado altera, alcan�a outro m�todo da classe
 * PainelWebFotos chamado
 * {@link net.sf.webphotos.gui.PainelWebFotos#alteracaoDetectada(DocumentEvent) alteracaoDetectada}(DocumentEvent
 * e) que tem funcionalidade de uma flag.
 */
public class AcaoDocumentListener implements DocumentListener {

    /**
     * Construtor da classe. Inicialmente vazio pois a classe n�o possui dados
     * atributos.
     */
    public AcaoDocumentListener() {
    }

    /**
     * Implementa o m�todo insertUptade da interface
     * {@link javax.swing.event.DocumentListener DocumentListener}. Faz uso da
     * fun��o
     * {@link net.sf.webphotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent
     * e) da pr�pria classe. N�o possui utiliza��es.
     *
     * @param e Notifica mudan�as no documento.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        altera(e);
    }

    /**
     * Implementa o m�todo removeUptade da interface
     * {@link javax.swing.event.DocumentListener DocumentListener}. Faz uso da
     * fun��o
     * {@link net.sf.webphotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent
     * e) da pr�pria classe. N�o possui utiliza��es.
     *
     * @param e Notifica mudan�as no documento.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        altera(e);
    }

    /**
     * Implementa o m�todo changeUptade da interface
     * {@link javax.swing.event.DocumentListener DocumentListener}. Faz uso da
     * fun��o
     * {@link net.sf.webphotos.acao.AcaoDocumentListener#altera(DocumentEvent) altera}(DocumentEvent
     * e) da pr�pria classe. N�o possui utiliza��es.
     *
     * @param e Notifica mudan�as no documento.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        altera(e);
    }

    /**
     * Faz uso da fun��o
     * {@link net.sf.webphotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}()
     * da classe PainelWebFotos, para checar se ocorreu alguma altera��o. Uma
     * esp�cie de flag.
     *
     * @param e Notifica mudan�as no documento.
     */
    public void altera(DocumentEvent e) {
        PainelWebFotos.alteracaoDetectada();
    }
}
