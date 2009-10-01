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
 * Seu construtor seta todas as vari�veis da classe exceto o �ndice da foto.
 * No m�todo que implementa a a��o, o �ndice � setado atrav�s dos valores da sele��o obtidos no programa e a partir da posi��o � encontrada a foto e feita a atualiza��o da legenda.
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
     * Recebe tr�s par�metros, uma tabela de fotos, um texto de legenda e uma lista de cr�ditos.
     * Seta esses 3 valores para vari�veis da classe para posteriormente manipul�-las.
     * @param tabela Tabela de fotos.
     * @param legenda Texto de legenda.
     * @param creditos Lista de cr�ditos.
     */
    public AcaoAtualizaLegenda(JTable tabela, JTextArea legenda, JComboBox creditos) {
        tbFotos=tabela;
        txtLegenda=legenda;
        lstCreditos=creditos;
    }

    /**
     * M�todo respons�vel pela a��o de atualiza��o da legenda.
     * Faz uma busca pelo �ndice da foto e seta a vari�vel fID.
     * Logo ap�s, instancia um objeto Foto e indica a foto para atualiza��o atrav�s de fID.
     * Seta o valor da legenda da foto, pelo valor armazenado em txtLegenda e ao final atualiza os valores.
     * @param e Evento de a��o de atualiza��o de legenda.
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