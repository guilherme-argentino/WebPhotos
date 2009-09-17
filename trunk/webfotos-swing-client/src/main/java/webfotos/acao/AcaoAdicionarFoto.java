package webfotos.acao;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JComboBox;

import org.apache.commons.configuration.CompositeConfiguration;

import webfotos.Album;
import webfotos.gui.PainelWebFotos;
import webfotos.gui.util.ImageFilter;
import webfotos.gui.util.TableModelFoto;
import webfotos.gui.util.TableSorter;
import webfotos.util.Util;


/**
 * Adiciona novas fotos.
 * Mantém variáveis do diretório, tabela, largura da coluna, título da caixa de diálogo e lista de créditos.
 */
public class AcaoAdicionarFoto extends AbstractAction {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8331561928830049243L;
	
	private static File diretorioInicial;
    private JFileChooser fc;
    private JTable tbFotos;
    private String larguraColunasFotos;
    private String titulo;
    private JComboBox lstCreditosTabelaFotos;

    /**
     * Construtor da classe.
     * Recebe 3 parâmetros. A tabela de fotos, um combobox e um título para a caixa de diálogo.
     * Inicia um objeto de {@link webfotos.util.Config Config} para receber os dados do Folder.
     * Seta os valores do diretório inicial, tabela de fotos, creditos, largura da coluna e título.
     * @param tabela Tabela das fotos.
     * @param combo Lista.
     * @param tituloDialogo Título da caixa de diálogo.
     */
    public AcaoAdicionarFoto(JTable tabela, JComboBox combo, String tituloDialogo) {
        CompositeConfiguration c=Util.getConfig();
        diretorioInicial=Util.getFolder("diretorioAdicionarFotos");
        tbFotos=tabela;
        lstCreditosTabelaFotos = combo;
        larguraColunasFotos=c.getString("colunas2");
        titulo=tituloDialogo;
    }

    /**
     * Método responsável pela ação de inserção das fotos.
     * Inicia um objeto JFileChooser para a escolha do arquivo e faz a configuração.
     * Testa se o diretório inicial é válido e depois faz a implantação da foto.
     * Atualiza a tabela, ajusta as colunas, aciona o flag de {@link webfotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}() e armazena o último diretório lido.
     * @param e Evento da ação de adição de foto.
     */
    public void actionPerformed(ActionEvent e) {
        fc=new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new ImageFilter());
        fc.setDialogTitle(titulo);
        fc.setApproveButtonText("Ok");
        fc.setApproveButtonToolTipText("Adiciona as fotos selecionadas ao álbum");
        fc.setMultiSelectionEnabled(true);

        if(diretorioInicial!=null && diretorioInicial.isDirectory()) {
            fc.setCurrentDirectory(diretorioInicial);
        }

        int retornoFc=fc.showOpenDialog(null);

        if(retornoFc==JFileChooser.APPROVE_OPTION && fc.getSelectedFiles().length > 0) {
            Album.getAlbum().adicionarFotos(fc.getSelectedFiles());

            /** aqui o codigo que atualiza a tabela */
            TableModelFoto.getModel().update();
            TableModelFoto.getModel().fireTableDataChanged();
            tbFotos.setModel(new TableSorter(TableModelFoto.getModel(),tbFotos.getTableHeader()));
            tbFotos.getColumnModel().getColumn(2).setCellEditor(new javax.swing.DefaultCellEditor(lstCreditosTabelaFotos));
            
            /** ajusta colunas */
            Util.ajustaLargura(tbFotos,larguraColunasFotos);
            tbFotos.repaint();

            /** liga o flag de alteração realiza */
            PainelWebFotos.alteracaoDetectada();

            /** armazena o último diretório lido */
            diretorioInicial=null;
            diretorioInicial=new File(fc.getSelectedFiles()[0].getParent());
        }
    }
}