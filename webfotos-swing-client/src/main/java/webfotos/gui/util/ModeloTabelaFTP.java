/*
 * ModeloTabelaFTP.java
 *
 * Created on 31 de Maio de 2006, 11:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.gui.util;

import webfotos.util.*;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;

/**
 * Modelo da tabela de dados do frame FtpClient.
 * TODO: Modelo Antigo (Eliminar).
 * @author guilherme
 */
public class ModeloTabelaFTP extends DefaultTableModel {
	
	private static final long serialVersionUID = -4674468107065732418L;

	final String[] nomesColuna={"Status","Ação","Álbum","Foto","Arquivo","Bytes"};
    private Object[][] modelo;
    
    /**
     * Cria uma instancia do Modelo usando um array de dados.
     * @param listaArquivos Dados a serem preenchidos
     */
    public ModeloTabelaFTP(ArrayList<Arquivo> listaArquivos) {
        super();
        // transforma a colecao em um modelo para a tabela
        refresh(listaArquivos);
    }

    /**
     * Realiza a atualização dos dados da tabela.
     * @param listaArquivos Dados usados para atualizar a tabela
     */
    public void refresh(ArrayList<Arquivo> listaArquivos) {
        modelo=null;
        modelo=new Object[listaArquivos.size()][6];
        Iterator<Arquivo> iter = listaArquivos.iterator();
        Arquivo a; int ct=0;
        while(iter.hasNext()) {
            a=(Arquivo) iter.next();
            modelo[ct][0]=a.getStatus();
            modelo[ct][1]=a.getNomeAcao();
            modelo[ct][2]=Integer.toString(a.getAlbumID());
            modelo[ct][3]=Integer.toString(a.getFotoID());
            modelo[ct][4]=a.getNomeArquivo();
            modelo[ct][5]=(Object) Long.toString(a.getTamanho());
            ct++;
        }
        setDataVector(modelo, nomesColuna);
    }

}
