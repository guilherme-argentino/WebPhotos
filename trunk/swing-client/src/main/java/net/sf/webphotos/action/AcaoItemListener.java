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
package net.sf.webphotos.action;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JTable;
import net.sf.webphotos.Album;
import net.sf.webphotos.PhotoDTO;
import net.sf.webphotos.gui.PainelWebFotos;
import net.sf.webphotos.gui.util.TableModelFoto;

/**
 * Informa se o item foi ou n�o selecionado. Possui dois construtores, um geral
 * que seta um valor para a tabela de fotos e o segundo que n�o recebe
 * par�metros, serve apenas para mudar o flag de altera��o detectada. Possui um
 * m�todo que identifica o elemento selecionado e faz as altera��es necess�rias.
 */
public class AcaoItemListener implements ItemListener {

    private JTable tbFotos = null;

    /**
     * Construtor da classe. Recebe uma tabela como par�metro e seta esse valor
     * para a vari�vel tabela de fotos da classe.
     *
     * @param tabela Tabela de fotos.
     */
    public AcaoItemListener(JTable tabela) {
        tbFotos = tabela;
    }

    /**
     * Construtor da classe. Inicialmente vazio. Utilizado pelo combo
     * lstCategoriasAlbum em
     * {@link net.sf.webphotos.gui.PainelWebFotos PainelWebFotos}, muda somente
     * o flag
     * {@link net.sf.webphotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}.
     */
    public AcaoItemListener() {
    }

    /**
     * Checa quando for evento de lstCategoriasAlbum, significa que o album esta
     * vazio, muda somente a flag de altera��o detectada. Caso contr�rio busca
     * qual item, no caso foto, foi selecionado pelo usu�rio, seta creditoNome
     * com o item afetado pelo evento, atualiza o modelo do alb�m e muda o flag
     * de altera��o detectada.
     *
     * @param e Evento de item (selecionado ou n�o selecionado).
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        PhotoDTO f;
        // quando for evento de lstCategoriasAlbum, somente muda a flag
        if (tbFotos == null) {
            PainelWebFotos.alteracaoDetectada();
            //return;
        } else if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().toString().length() > 0) {
                Object fotoID = tbFotos.getModel().getValueAt(tbFotos.getSelectedRow(), 0);
                try {
                    f = (PhotoDTO) Album.getAlbum().getFoto(Integer.parseInt(fotoID.toString()));
                } catch (Exception ex) {
                    f = (PhotoDTO) Album.getAlbum().getFoto((String) fotoID);
                }
                f.setCreditoNome((String) e.getItem().toString());
                TableModelFoto.getModel().update();
                TableModelFoto.getModel().fireTableCellUpdated(tbFotos.getSelectedRow(), 2);
                PainelWebFotos.alteracaoDetectada();
            }
        }
    }
}