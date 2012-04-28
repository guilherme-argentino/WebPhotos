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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.sf.webphotos.Album;
import net.sf.webphotos.gui.PainelWebFotos;
import net.sf.webphotos.gui.util.TableModelAlbum;
import net.sf.webphotos.util.Util;
import org.apache.commons.configuration.Configuration;

/**
 * Pesquisa elementos no programa.
 * Possui um método que organiza buscas através da quantidade de parâmetros passados ao construtor, com o campo de texto ou com o ID da categoria.
 * Ao terminar a pesquisa, apresenta a quantidade de registros encontrados.
 */
public class AcaoPesquisa extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = -3050139194212291060L;
    private JComboBox lstCategoriasPesquisa;
    private JTextField txtPesquisa;
    private Configuration c = Util.getConfig();

    /**
     * Construtro da classe.
     * Recebe três parâmetros. Uma lista inicial de categorias, um campo de texto com um valor a ser pesquisado e uma tabela de albúm.
     * Seta esses três valores em variáveis da classe que serão utilizadas posteriormente.
     * @param lst Lista de categorias.
     * @param txt Campo de texto com valor a ser pesquisado.
     * @param tb Tabela de albúm.
     */
    public AcaoPesquisa(JComboBox lst, JTextField txt, JTable tb) {
        lstCategoriasPesquisa = lst;
        txtPesquisa = txt;
    }

    /**
     * Faz as pesquisas ao banco utilizando os valores de ID ou com o campo de texto de pesquisa.
     * Faz a busca com essas variáveis de quatro maneiras diferentes. Com categoria sem pesquisa, sem categoria e pesquisa, com categoria e pesquisa, e sem categoria com pesquisa.
     * Após definir o tipo de busca, trata possíveis exceções e atualiza o modelo do albúm.
     * Apresenta o número de registros encontrados e tira o cursor do modo de espera.
     * @param e Evento da ação de pesquisa.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String sql;
        // ação pesquisar (clique em botao pesquisar ou tecla ENTER
        PainelWebFotos.setCursorWait(true);
        int categoriaID = Album.getAlbum().getLstCategoriasID(lstCategoriasPesquisa.getSelectedItem().toString());
        String strCategoriaID = Integer.toString(categoriaID);
        String pesquisa = txtPesquisa.getText();

        // com categoria sem pesquisa
        // apresenta todos os registros dessa categoria
        if (pesquisa.length() == 0 && categoriaID != -1) {
            sql = c.getString("sql5");
            sql = sql.replaceFirst("\\?2", strCategoriaID);

            // sem categoria sem pesquisa
            // apresenta os registros conforme sql1
        } else if (pesquisa.length() == 0 && categoriaID == -1) {
            sql = c.getString("sql1");

            // com categoria e com pesquisa
        } else if (pesquisa.length() > 0 && categoriaID != -1) {
            sql = c.getString("sql4");
            // substitui ?1 pela chave de pesquisa
            sql = sql.replaceFirst("\\?1", pesquisa);
            // substitui ?2 pela categoriaID
            sql = sql.replaceFirst("\\?2", strCategoriaID);

            // sem categoria com pesquisa
        } else {
            sql = c.getString("sql3");
            sql = sql.replaceFirst("\\?1", pesquisa);
        }

        TableModelAlbum.getModel().update(sql);
        TableModelAlbum.getModel().fireTableDataChanged();

        PainelWebFotos.apresentaNumReg();
        PainelWebFotos.setCursorWait(false);
    }
}
