package webfotos.acao;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import webfotos.Album;
import webfotos.gui.PainelWebFotos;
import webfotos.gui.util.TableModelFoto;
import webfotos.util.Util;
import webfotos.gui.util.TableSorter;
import java.io.*;


/**
 * Exclui fotos.
 * Possui os dados tabela de fotos e largura da coluna de fotos.
 * Seu construtor seta esses dados para serem utilizados posteriormente pelo m�todo que implementa a a��o.
 */
public class AcaoExcluirFoto extends AbstractAction {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6690995860578985531L;
	
	private JTable tbFotos;
    private String larguraColunasFotos;

    /**
     * Construtor da classe.
     * Seta os valores da tabela de fotos por um par�metro recebido e atrav�s da tabela seta o valor da largura da coluna.
     * @param tabela Tabela de fotos.
     */
    public AcaoExcluirFoto(JTable tabela) {
        tbFotos=tabela;
        larguraColunasFotos=Util.getConfig().getString("colunas2");
    }

    /**
     * M�todo respons�vel pela exclus�o de fotos.
     * Identifica os IDs e nomes das fotos selecionadas. Armazena quais e quantas linhas foram selecionadas.
     * Checa se existe apenas uma foto no alb�m e mostra ao usu�rio que se a foto for exclu�da, o alb�m tamb�m ser�. Pede confirma��o e efetua a a��o.
     * Faz um controle de exclus�o de no m�ximo 20 fotos por vez.
     * Lista os alb�ns selecionados ao usu�rio e pede uma confirma��o de exclus�o.
     * Caso o usu�rio confirme, exclui as fotos selecionadas com o m�todo {@link webfotos.Album#excluirFotos(int[]) excluirFotos(albunsID)} da classe Album.
     * Ao ser iniciado o m�todo que implementa a a��o, checa se a foto n�o � recente, se j� possui registro no banco. Ent�o cria um array com os IDs das fotos. Cria um arquivo javascript. E por �ltimo atualiza a lista e �rea das fotos no programa.
     * @param e Evento de a��o de exclus�o de fotos.
     */
    public void actionPerformed(ActionEvent e) {
        HashSet<String> fotosID=new HashSet<String>();
        HashSet<String> nomesArquivos=new HashSet<String>();
        int[] linhasSelecionadas=tbFotos.getSelectedRows();
        int numeroLinhasSelecionadas=tbFotos.getSelectedRowCount();
        String msg="";

        if(tbFotos.getRowCount()==1) {
            int retorno=JOptionPane.showConfirmDialog(null,
                "Esta � a �ltima foto deste �lbum.\nExcluir essa foto ir� excluir seu �lbum.",
                "Excluir �lbum ?", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);       
            if(retorno==0) {
                // exclui o �lbum e retorna
                Util.out.println ("remove album: " + Album.getAlbum().getAlbumID());
                return;
            }
            // usu�rio preferiu n�o excluir a �ltima foto (e o �lbum tamb�m)
            return;                 
        }
        // permite somente a exclus�o de 20 fotos de cada vez
        if(numeroLinhasSelecionadas > 20 || numeroLinhasSelecionadas==0) {
            JOptionPane.showMessageDialog(null,
                "Voc� deve selecionar entre 1 e 20 fotos\npara serem exclu�das","Informa��o",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // pede confirmacao
        for(int i=0; i < numeroLinhasSelecionadas; i++)
            msg=msg + "\n" + tbFotos.getModel().getValueAt(linhasSelecionadas[i],0) + " - " + tbFotos.getModel().getValueAt(linhasSelecionadas[i],1);

        if(numeroLinhasSelecionadas==1) {
            msg="Confirma a exclus�o da foto ?\n" + msg;
        } else {
            msg="Confirma a exclus�o de " + numeroLinhasSelecionadas + " fotos ?\n" + msg;
        }
        int confirmacao=JOptionPane.showConfirmDialog(null,msg, "Confirma��o de exclus�o", JOptionPane.WARNING_MESSAGE);

        // apaga a foto
        if(confirmacao==0) {
            // primeiro checa se o usu�rio n�o est� excluindo fotos que
            // acabou de adicionar (nesse caso n�o tem entrada em db)
            String indice="";

            for(int i=0; i < numeroLinhasSelecionadas; i++) {
                indice=tbFotos.getModel().getValueAt(linhasSelecionadas[i],0).toString();

                // se acaba com jpg ent�o n�o � numero
                if(indice.toLowerCase().endsWith(".jpg")) {
                    nomesArquivos.add(indice);
                } else {
                    fotosID.add(indice);
                }
            }

            Album album=Album.getAlbum();

            // monta os arrays para passar ao album
            if(nomesArquivos.size() > 0) {
                // passa na forma de um array de strings
                Util.out.println("nomesArquivos: " + nomesArquivos.toString());
                album.excluirFotos((String[]) nomesArquivos.toArray(new String[0]));
            }

            if(fotosID.size() > 0) {
                // passa na forma de um array de int
                Iterator<String> iter=fotosID.iterator();
                int[] fotoID=new int[fotosID.size()];
                int ct = 0;
                while(iter.hasNext()) {
                    fotoID[ct]=Integer.parseInt(iter.next().toString());
                    ct++;
                }

                album.excluirFotos(fotoID);

                // escreve o arquivo javaScript
                String caminhoAlbum=Util.getFolder("albunsRoot").getPath() + File.separator + album.getAlbumID();
                try {
                    FileWriter out=new FileWriter(caminhoAlbum + File.separator + album.getAlbumID() + ".js");
                    out.write(album.toJavaScript());
                    out.flush();
                    Util.out.println ("escrevendo: " + album.toJavaScript());

                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }

            // atualiza o modelo
            // aqui o codigo que atualiza a tabela
            TableModelFoto.getModel().update();
            TableModelFoto.getModel().fireTableDataChanged();
            //TableModelFoto.getModel().addMouseListener(tbFotos);
            //tbFotos.setModel(TableModelFoto.getModel());
            tbFotos.setModel(new TableSorter(TableModelFoto.getModel(),tbFotos.getTableHeader()));

            // ajusta colunas
            Util.ajustaLargura(tbFotos,larguraColunasFotos);
            tbFotos.repaint();

            // limpa controle de foto
            PainelWebFotos.resetFoto();
        }
    }
}
