/*
 * AcaoPopMenu.java
 *
 * Created on 29 de Maio de 2006, 15:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import webfotos.gui.util.TableModelAlbum;

/**
 * Gera o menu de visualiza��o dos dados do alb�m.
 * Faz uma busca no banco de dados, e ao inicializar o construtor atualiza o valor da vari�vel sql, usada na busca ao banco.
 * Com seu m�todo de a��o, implementa a atualiza��o do modelo da tabela e apresenta os registros encontrados.
 * @author guilherme
 */
public class AcaoPopMenu extends AbstractAction {
    String sql="select albumID as ID, categorias.nmcategoria as Categoria, nmalbum as Pauta, DATE_FORMAT(DtInsercao, '%d/%m/%y') as Data from albuns left join categorias using(categoriaID) ";

    /**
     * Construtor da classe.
     * Recebe uma String como par�metro e testa se ela inicia com "SELECT", caso positivo, substitui a vari�vel sql da classe pela recebida no construtor.
     * Caso contr�rio, concatena o valor recebido � vari�vel da classe.
     * @param posSQL Query do sql ou continua��o de uma query.
     */
    public AcaoPopMenu(String posSQL) {
        if(posSQL.startsWith("select")) {
            sql=posSQL;
        } else {
            sql+=posSQL;
        }
    }

    /**
     * Esse m�todo tem como objetivo construir o menu de visualiza��o dos dados dos alb�ns.
     * Armazena uma tabela com os alb�ns em uma vari�vel chamada <I>tabela</I>, atualiza o modelo da tabela baseado na busca ao banco de dados.
     * Por �ltimo apresenta o n�mero de registros encontrados.
     * Apresenta utiliza��o apenas na classe AcaoPopMenuTest localizada nos pacotes de teste.
     * @param e Evento da a��o de constru��o do menu.
     */
    public void actionPerformed(ActionEvent e) {
        PainelWebFotos.setCursorWait(true);

        TableModelAlbum.getModel().update(sql);
        TableModelAlbum.getModel().fireTableStructureChanged();
        TableModelAlbum.getModel().fireTableDataChanged();

        PainelWebFotos.apresentaNumReg();
        PainelWebFotos.setCursorWait(false);
    }
	
}


