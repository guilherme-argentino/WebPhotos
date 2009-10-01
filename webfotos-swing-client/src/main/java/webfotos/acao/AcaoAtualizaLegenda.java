package webfotos.acao;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

import net.sf.webphotos.Album;
import net.sf.webphotos.Foto;
import webfotos.gui.util.TableModelFoto;
import webfotos.util.Util;

/**
 * Atualiza legenda.
 * Seu construtor seta todas as variáveis da classe exceto o índice da foto.
 * No método que implementa a ação, o índice é setado através dos valores da seleção obtidos no programa e a partir da posição é encontrada a foto e feita a atualização da legenda.
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
     * Construtor da classe.
     * Recebe três parâmetros, uma tabela de fotos, um texto de legenda e uma lista de créditos.
     * Seta esses 3 valores para variáveis da classe para posteriormente manipulá-las.
     * @param tabela Tabela de fotos.
     * @param legenda Texto de legenda.
     * @param creditos Lista de créditos.
     */
    public AcaoAtualizaLegenda(JTable tabela, JTextArea legenda, JComboBox creditos) {
        tbFotos=tabela;
        txtLegenda=legenda;
        lstCreditos=creditos;
    }

    /**
     * Método responsável pela ação de atualização da legenda.
     * Faz uma busca pelo índice da foto e seta a variável fID.
     * Logo após, instancia um objeto Foto e indica a foto para atualização através de fID.
     * Seta o valor da legenda da foto, pelo valor armazenado em txtLegenda e ao final atualiza os valores.
     * @param e Evento de ação de atualização de legenda.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Util.out.println ("indiceFoto:" + indiceFoto);
        int fID=Integer.parseInt(tbFotos.getModel().getValueAt(tbFotos.getSelectedRow(),0).toString());
        Foto f=Album.getAlbum().getFoto(fID);
        f.setLegenda(txtLegenda.getText());
        TableModelFoto.getModel().update();
        TableModelFoto.getModel().fireTableCellUpdated(tbFotos.getSelectedRow(),1);
        lstCreditos.requestFocus();
    }
}