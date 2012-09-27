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
 * AcaoPopMenu.java
 *
 * Created on 29 de Maio de 2006, 15:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.webphotos.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import net.sf.webphotos.gui.util.TableModelAlbum;

/**
 * Gera o menu de visualização dos dados do albúm.
 * Faz uma busca no banco de dados, e ao inicializar o construtor atualiza o valor da variável sql, usada na busca ao banco.
 * Com seu método de ação, implementa a atualização do modelo da tabela e apresenta os registros encontrados.
 * @author guilherme
 */
public class AcaoPopMenu extends AbstractAction {
    String sql="select albumID as ID, categorias.nmcategoria as Categoria, nmalbum as Pauta, DATE_FORMAT(DtInsercao, '%d/%m/%y') as Data from albuns left join categorias using(categoriaID) ";

    /**
     * Construtor da classe.
     * Recebe uma String como parâmetro e testa se ela inicia com "SELECT", caso positivo, substitui a variável sql da classe pela recebida no construtor.
     * Caso contrário, concatena o valor recebido à variável da classe.
     * @param posSQL Query do sql ou continuação de uma query.
     */
    public AcaoPopMenu(String posSQL) {
        if(posSQL.startsWith("select")) {
            sql=posSQL;
        } else {
            sql+=posSQL;
        }
    }

    /**
     * Esse método tem como objetivo construir o menu de visualização dos dados dos albúns.
     * Armazena uma tabela com os albúns em uma variável chamada <I>tabela</I>, atualiza o modelo da tabela baseado na busca ao banco de dados.
     * Por último apresenta o número de registros encontrados.
     * Apresenta utilização apenas na classe AcaoPopMenuTest localizada nos pacotes de teste.
     * @param e Evento da ação de construção do menu.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        PainelWebFotos.setCursorWait(true);

        TableModelAlbum.getModel().update(sql);
        TableModelAlbum.getModel().fireTableStructureChanged();
        TableModelAlbum.getModel().fireTableDataChanged();

        PainelWebFotos.apresentaNumReg();
        PainelWebFotos.setCursorWait(false);
    }
	
}


