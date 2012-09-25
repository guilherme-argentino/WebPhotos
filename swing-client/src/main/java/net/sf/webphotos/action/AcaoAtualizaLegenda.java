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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import net.sf.webphotos.Album;
import net.sf.webphotos.Photo;
import net.sf.webphotos.gui.util.TableModelFoto;
import net.sf.webphotos.util.Util;

/**
 * Atualiza legenda. Seu construtor seta todas as variáveis da classe exceto o
 * índice da foto. No método que implementa a ação, o índice é setado através
 * dos valores da seleção obtidos no programa e a partir da posição é encontrada
 * a foto e feita a atualização da legenda.
 */
public class AcaoAtualizaLegenda extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 3926799612502941998L;
    private JTable tbFotos;
    private JTextArea txtLegenda;
    private int indiceFoto;
    private JComboBox lstCreditos;

    /**
     * Construtor da classe. Recebe três parâmetros, uma tabela de fotos, um
     * texto de legenda e uma lista de créditos. Seta esses 3 valores para
     * variáveis da classe para posteriormente manipulá-las.
     *
     * @param tabela Tabela de fotos.
     * @param legenda Texto de legenda.
     * @param creditos Lista de créditos.
     */
    public AcaoAtualizaLegenda(JTable tabela, JTextArea legenda, JComboBox creditos) {
        tbFotos = tabela;
        txtLegenda = legenda;
        lstCreditos = creditos;
    }

    /**
     * Método responsável pela ação de atualização da legenda. Faz uma busca
     * pelo índice da foto e seta a variável fID. Logo após, instancia um objeto
     * Photo e indica a foto para atualização através de fID. Seta o valor da
     * legenda da foto, pelo valor armazenado em txtLegenda e ao final atualiza
     * os valores.
     *
     * @param e Evento de ação de atualização de legenda.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Util.out.println("indiceFoto:" + indiceFoto);
        int fID = Integer.parseInt(tbFotos.getModel().getValueAt(tbFotos.getSelectedRow(), 0).toString());
        Photo f = Album.getAlbum().getFoto(fID);
        f.setLegenda(txtLegenda.getText());
        TableModelFoto.getModel().update();
        TableModelFoto.getModel().fireTableCellUpdated(tbFotos.getSelectedRow(), 1);
        lstCreditos.requestFocus();
    }
}