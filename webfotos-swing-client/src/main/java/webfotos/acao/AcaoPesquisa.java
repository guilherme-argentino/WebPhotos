package webfotos.acao;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.configuration.CompositeConfiguration;

import webfotos.Album;
import webfotos.gui.PainelWebFotos;
import webfotos.gui.util.TableModelAlbum;
import webfotos.util.Util;

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
    private CompositeConfiguration c=Util.getConfig();

    /**
     * Construtro da classe.
     * Recebe três parâmetros. Uma lista inicial de categorias, um campo de texto com um valor a ser pesquisado e uma tabela de albúm.
     * Seta esses três valores em variáveis da classe que serão utilizadas posteriormente.
     * @param lst Lista de categorias.
     * @param txt Campo de texto com valor a ser pesquisado.
     * @param tb Tabela de albúm.
     */
    public AcaoPesquisa(JComboBox lst, JTextField txt, JTable tb) {
        lstCategoriasPesquisa=lst;
        txtPesquisa=txt;
    }

    /**
     * Faz as pesquisas ao banco utilizando os valores de ID ou com o campo de texto de pesquisa.
     * Faz a busca com essas variáveis de quatro maneiras diferentes. Com categoria sem pesquisa, sem categoria e pesquisa, com categoria e pesquisa, e sem categoria com pesquisa.
     * Após definir o tipo de busca, trata possíveis exceções e atualiza o modelo do albúm.
     * Apresenta o número de registros encontrados e tira o cursor do modo de espera.
     * @param e Evento da ação de pesquisa.
     */
    public void actionPerformed(ActionEvent e) {
        String sql;
        // ação pesquisar (clique em botao pesquisar ou tecla ENTER
        PainelWebFotos.setCursorWait(true);
        int categoriaID=Album.getAlbum().getLstCategoriasID(lstCategoriasPesquisa.getSelectedItem().toString());
        String strCategoriaID=Integer.toString(categoriaID);
        String pesquisa=txtPesquisa.getText();

        // com categoria sem pesquisa
        // apresenta todos os registros dessa categoria
        if(pesquisa.length()==0 && categoriaID != -1) {
            sql=c.getString("sql5");
            sql=sql.replaceFirst("\\?2", strCategoriaID);

        // sem categoria sem pesquisa
        // apresenta os registros conforme sql1
        } else if(pesquisa.length()==0 && categoriaID == -1) {
            sql=c.getString("sql1");

        // com categoria e com pesquisa
        } else if(pesquisa.length() > 0 && categoriaID != -1) {
            sql=c.getString("sql4");
            // substitui ?1 pela chave de pesquisa
            sql=sql.replaceFirst("\\?1", pesquisa);
            // substitui ?2 pela categoriaID
            sql=sql.replaceFirst("\\?2", strCategoriaID);

        // sem categoria com pesquisa
        } else {
            sql=c.getString("sql3");
            sql=sql.replaceFirst("\\?1", pesquisa);
        }
        
        TableModelAlbum.getModel().update(sql);
        TableModelAlbum.getModel().fireTableDataChanged();
        
        PainelWebFotos.apresentaNumReg();
        PainelWebFotos.setCursorWait(false);
    }
}