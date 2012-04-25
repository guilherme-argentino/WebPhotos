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
package webfotos.acao;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.sf.webphotos.Album;
import webfotos.gui.PainelWebFotos;
import net.sf.webphotos.gui.util.TableModelAlbum;
import net.sf.webphotos.util.Util;
import net.sf.webphotos.gui.util.TableSorter;

/**
 * Exclui alb�ns.
 * N�o possui dados pr�prios, por isso seu construtor � vazio.
 * O m�todo que realiza a a��o, instancia dados apenas para verifica��o e chama o m�todo de exclus�o da classe Album no pacote webfotos.
 */
public class AcaoExcluirAlbum extends AbstractAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6469549565678592107L;

	/**
     * Construtor da classe.
     * Incialmente vazio, pois a classe n�o possui dados.
     */
    public AcaoExcluirAlbum() {
    }
	
    /**
     * M�todo respons�vel pela a��o de exclus�o de alb�ns.
     * Inicia uma tabela de alb�ns para buscar as linhas selecionadas e checa quais e quantos foram selecionados.
     * Faz um controle de exclus�o de no m�ximo 20 alb�ns por vez.
     * Lista os alb�ns selecionados ao usu�rio e pede uma confirma��o de exclus�o.
     * Caso o usu�rio confirme, exclui os alb�ns selecionados atrav�s da fun��o {@link webfotos.Album#excluirAlbuns(int[]) excluirAlbuns(albunsID)} em webfotos.Album e atualiza a lista e a ar�a dos alb�ns no programa.
     * @param e Evento de a��o de exclus�o de alb�ns.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        JTable tbAlbuns=PainelWebFotos.getTbAlbuns();
        String larguraColunas=Util.getConfig().getString("colunas1");

        // descobre os �lbuns selecionados
        int[] linhasSelecionadas=tbAlbuns.getSelectedRows();
        int numeroLinhasSelecionadas=tbAlbuns.getSelectedRowCount();
        String msg="";

        // permite somente a exclus�o de 20 �lbuns de cada vez
        if(numeroLinhasSelecionadas > 20 || numeroLinhasSelecionadas==0) {
            JOptionPane.showMessageDialog(null,
                    "Voc� deve selecionar entre 1 e 20 �lbuns\npara serem exclu�dos","Informa��o",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // solicita a confirma��o da exclus�o, listando os �lbuns selecionados
        for(int i=0; i < numeroLinhasSelecionadas; i++)
            msg=msg + "\n" + TableModelAlbum.getModel().getValueAt(linhasSelecionadas[i],0) + " - " + TableModelAlbum.getModel().getValueAt(linhasSelecionadas[i],2);

        if(numeroLinhasSelecionadas==1) {
            msg="Confirma a exclus�o do �lbum ?\n" + msg;
        } else {
            msg="Confirma a exclus�o de " + numeroLinhasSelecionadas + " �lbuns ?\n" + msg;
        }

        int confirmacao=JOptionPane.showConfirmDialog(null,msg, "Confirma��o de exclus�o", JOptionPane.WARNING_MESSAGE);

        // caso o usu�rio confirme, executa a exclus�o (a cargo de Album)
        if(confirmacao==0) {
            int[] albunsID=new int[numeroLinhasSelecionadas];
            for(int i=0; i < numeroLinhasSelecionadas; i++)
                albunsID[i]=Integer.parseInt(TableModelAlbum.getModel().getValueAt(linhasSelecionadas[i],0).toString());							

            // executa a exclus�o
            Album.getAlbum().excluirAlbuns(albunsID);

            if(Util.getConfig().getBoolean("autoTransferir")) {
                Thread t=new Thread(new net.sf.webphotos.gui.util.FtpClient());
                t.start();
            }

            TableModelAlbum.getModel().update();
            TableModelAlbum.getModel().fireTableDataChanged();
            //TableModelAlbum.getModel().addMouseListener(tbAlbuns);

            if(TableModelAlbum.getModel().getRowCount() > 1)
                tbAlbuns.removeRowSelectionInterval(0,TableModelAlbum.getModel().getRowCount()-1);

            tbAlbuns.setModel(new TableSorter(TableModelAlbum.getModel(), tbAlbuns.getTableHeader()));
            Util.ajustaLargura(tbAlbuns,larguraColunas);
            tbAlbuns.repaint();
            PainelWebFotos.apresentaNumReg();

            // reseta �rea de �lbum
            PainelWebFotos.resetAlbum();
            PainelWebFotos.alteracaoFinalizada();
        }
    }
}
