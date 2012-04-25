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
 * Exclui albúns.
 * Não possui dados próprios, por isso seu construtor é vazio.
 * O método que realiza a ação, instancia dados apenas para verificação e chama o método de exclusão da classe Album no pacote webfotos.
 */
public class AcaoExcluirAlbum extends AbstractAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6469549565678592107L;

	/**
     * Construtor da classe.
     * Incialmente vazio, pois a classe não possui dados.
     */
    public AcaoExcluirAlbum() {
    }
	
    /**
     * Método responsável pela ação de exclusão de albúns.
     * Inicia uma tabela de albúns para buscar as linhas selecionadas e checa quais e quantos foram selecionados.
     * Faz um controle de exclusão de no máximo 20 albúns por vez.
     * Lista os albúns selecionados ao usuário e pede uma confirmação de exclusão.
     * Caso o usuário confirme, exclui os albúns selecionados através da função {@link webfotos.Album#excluirAlbuns(int[]) excluirAlbuns(albunsID)} em webfotos.Album e atualiza a lista e a aréa dos albúns no programa.
     * @param e Evento de ação de exclusão de albúns.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        JTable tbAlbuns=PainelWebFotos.getTbAlbuns();
        String larguraColunas=Util.getConfig().getString("colunas1");

        // descobre os álbuns selecionados
        int[] linhasSelecionadas=tbAlbuns.getSelectedRows();
        int numeroLinhasSelecionadas=tbAlbuns.getSelectedRowCount();
        String msg="";

        // permite somente a exclusão de 20 álbuns de cada vez
        if(numeroLinhasSelecionadas > 20 || numeroLinhasSelecionadas==0) {
            JOptionPane.showMessageDialog(null,
                    "Você deve selecionar entre 1 e 20 álbuns\npara serem excluídos","Informação",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // solicita a confirmação da exclusão, listando os álbuns selecionados
        for(int i=0; i < numeroLinhasSelecionadas; i++)
            msg=msg + "\n" + TableModelAlbum.getModel().getValueAt(linhasSelecionadas[i],0) + " - " + TableModelAlbum.getModel().getValueAt(linhasSelecionadas[i],2);

        if(numeroLinhasSelecionadas==1) {
            msg="Confirma a exclusão do álbum ?\n" + msg;
        } else {
            msg="Confirma a exclusão de " + numeroLinhasSelecionadas + " álbuns ?\n" + msg;
        }

        int confirmacao=JOptionPane.showConfirmDialog(null,msg, "Confirmação de exclusão", JOptionPane.WARNING_MESSAGE);

        // caso o usuário confirme, executa a exclusão (a cargo de Album)
        if(confirmacao==0) {
            int[] albunsID=new int[numeroLinhasSelecionadas];
            for(int i=0; i < numeroLinhasSelecionadas; i++)
                albunsID[i]=Integer.parseInt(TableModelAlbum.getModel().getValueAt(linhasSelecionadas[i],0).toString());							

            // executa a exclusão
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

            // reseta área de álbum
            PainelWebFotos.resetAlbum();
            PainelWebFotos.alteracaoFinalizada();
        }
    }
}
