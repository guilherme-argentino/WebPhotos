package webfotos.acao;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JTable;

import webfotos.Album;
import webfotos.Foto;
import webfotos.gui.PainelWebFotos;
import webfotos.gui.util.TableModelFoto;

/**
 * Informa se o item foi ou n�o selecionado.
 * Possui dois construtores, um geral que seta um valor para a tabela de fotos e o segundo que n�o recebe par�metros, serve apenas para mudar o flag de altera��o detectada.
 * Possui um m�todo que identifica o elemento selecionado e faz as altera��es necess�rias.
 */
public class AcaoItemListener implements ItemListener {
    private JTable tbFotos=null;

    /**
     * Construtor da classe.
     * Recebe uma tabela como par�metro e seta esse valor para a vari�vel tabela de fotos da classe.
     * @param tabela Tabela de fotos.
     */
    public AcaoItemListener(JTable tabela) {
        tbFotos=tabela;	
    }

    /**
     * Construtor da classe.
     * Inicialmente vazio.
     * Utilizado pelo combo lstCategoriasAlbum em {@link webfotos.gui.PainelWebFotos PainelWebFotos}, muda somente o flag {@link webfotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}.
     */
    public AcaoItemListener() { }

    /**
     * Checa quando for evento de lstCategoriasAlbum, significa que o album esta vazio, muda somente a flag de altera��o detectada.
     * Caso contr�rio busca qual item, no caso foto, foi selecionado pelo usu�rio, seta creditoNome com o item afetado pelo evento, atualiza o modelo do alb�m e muda o flag de altera��o detectada.
     * @param e Evento de item (selecionado ou n�o selecionado).
     */
    public void itemStateChanged(ItemEvent e) {
        Foto f;
        // quando for evento de lstCategoriasAlbum, somente muda a flag
        if(tbFotos==null) {
            PainelWebFotos.alteracaoDetectada();
            //return;
        } else if(e.getStateChange()==ItemEvent.SELECTED) {
            if(e.getItem().toString().length() > 0) {
                Object fotoID=tbFotos.getModel().getValueAt(tbFotos.getSelectedRow(),0);
                try {
                    f=(Foto) Album.getAlbum().getFoto(Integer.parseInt(fotoID.toString()));
                } catch (Exception ex) {
                    f=(Foto) Album.getAlbum().getFoto((String) fotoID);
                }
                f.setCreditoNome((String) e.getItem().toString());
                TableModelFoto.getModel().update();
                TableModelFoto.getModel().fireTableCellUpdated(tbFotos.getSelectedRow(),2);
                PainelWebFotos.alteracaoDetectada();
            }
        }
    }
}