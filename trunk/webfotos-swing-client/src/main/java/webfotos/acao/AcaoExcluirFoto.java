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
 * Seu construtor seta esses dados para serem utilizados posteriormente pelo método que implementa a ação.
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
     * Seta os valores da tabela de fotos por um parâmetro recebido e através da tabela seta o valor da largura da coluna.
     * @param tabela Tabela de fotos.
     */
    public AcaoExcluirFoto(JTable tabela) {
        tbFotos=tabela;
        larguraColunasFotos=Util.getConfig().getString("colunas2");
    }

    /**
     * Método responsável pela exclusão de fotos.
     * Identifica os IDs e nomes das fotos selecionadas. Armazena quais e quantas linhas foram selecionadas.
     * Checa se existe apenas uma foto no albúm e mostra ao usuário que se a foto for excluída, o albúm também será. Pede confirmação e efetua a ação.
     * Faz um controle de exclusão de no máximo 20 fotos por vez.
     * Lista os albúns selecionados ao usuário e pede uma confirmação de exclusão.
     * Caso o usuário confirme, exclui as fotos selecionadas com o método {@link webfotos.Album#excluirFotos(int[]) excluirFotos(albunsID)} da classe Album.
     * Ao ser iniciado o método que implementa a ação, checa se a foto não é recente, se já possui registro no banco. Então cria um array com os IDs das fotos. Cria um arquivo javascript. E por último atualiza a lista e área das fotos no programa.
     * @param e Evento de ação de exclusão de fotos.
     */
    public void actionPerformed(ActionEvent e) {
        HashSet<String> fotosID=new HashSet<String>();
        HashSet<String> nomesArquivos=new HashSet<String>();
        int[] linhasSelecionadas=tbFotos.getSelectedRows();
        int numeroLinhasSelecionadas=tbFotos.getSelectedRowCount();
        String msg="";

        if(tbFotos.getRowCount()==1) {
            int retorno=JOptionPane.showConfirmDialog(null,
                "Esta é a última foto deste álbum.\nExcluir essa foto irá excluir seu álbum.",
                "Excluir álbum ?", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);       
            if(retorno==0) {
                // exclui o álbum e retorna
                Util.out.println ("remove album: " + Album.getAlbum().getAlbumID());
                return;
            }
            // usuário preferiu não excluir a última foto (e o álbum também)
            return;                 
        }
        // permite somente a exclusão de 20 fotos de cada vez
        if(numeroLinhasSelecionadas > 20 || numeroLinhasSelecionadas==0) {
            JOptionPane.showMessageDialog(null,
                "Você deve selecionar entre 1 e 20 fotos\npara serem excluídas","Informação",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // pede confirmacao
        for(int i=0; i < numeroLinhasSelecionadas; i++)
            msg=msg + "\n" + tbFotos.getModel().getValueAt(linhasSelecionadas[i],0) + " - " + tbFotos.getModel().getValueAt(linhasSelecionadas[i],1);

        if(numeroLinhasSelecionadas==1) {
            msg="Confirma a exclusão da foto ?\n" + msg;
        } else {
            msg="Confirma a exclusão de " + numeroLinhasSelecionadas + " fotos ?\n" + msg;
        }
        int confirmacao=JOptionPane.showConfirmDialog(null,msg, "Confirmação de exclusão", JOptionPane.WARNING_MESSAGE);

        // apaga a foto
        if(confirmacao==0) {
            // primeiro checa se o usuário não está excluindo fotos que
            // acabou de adicionar (nesse caso não tem entrada em db)
            String indice="";

            for(int i=0; i < numeroLinhasSelecionadas; i++) {
                indice=tbFotos.getModel().getValueAt(linhasSelecionadas[i],0).toString();

                // se acaba com jpg então não é numero
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
