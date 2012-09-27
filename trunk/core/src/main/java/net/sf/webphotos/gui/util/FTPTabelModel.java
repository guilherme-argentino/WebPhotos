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
/*
 * FTPTabelModel.java
 *
 * Created on 31 de Maio de 2006, 11:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.webphotos.gui.util;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import net.sf.webphotos.util.legacy.Arquivo;

/**
 * Modelo da tabela de dados do frame FtpClient.
 * TODO: Modelo Antigo (Eliminar).
 * @author guilherme
 */
public class FTPTabelModel extends DefaultTableModel {
	
	private static final long serialVersionUID = -4674468107065732418L;

	final String[] nomesColuna={"Status","Ação","Álbum","Foto","Arquivo","Bytes"};
    private Object[][] modelo;
    
    /**
     * Cria uma instancia do Modelo usando um array de dados.
     * @param listaArquivos Dados a serem preenchidos
     */
    public FTPTabelModel(ArrayList<Arquivo> listaArquivos) {
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
