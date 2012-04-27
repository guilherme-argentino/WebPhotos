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
package net.sf.webphotos.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;

import org.apache.commons.configuration.Configuration;

import net.sf.webphotos.Album;
import net.sf.webphotos.gui.PainelWebFotos;
import net.sf.webphotos.gui.util.ImageFilter;
import net.sf.webphotos.gui.util.TableModelFoto;
import net.sf.webphotos.gui.util.TableSorter;
import net.sf.webphotos.util.Util;


/**
 * Adiciona novas fotos.
 * Mant�m vari�veis do diret�rio, tabela, largura da coluna, t�tulo da caixa de di�logo e lista de cr�ditos.
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
     * Recebe 3 par�metros. A tabela de fotos, um combobox e um t�tulo para a caixa de di�logo.
     * Inicia um objeto de {@link net.sf.webphotos.util.Config Config} para receber os dados do Folder.
     * Seta os valores do diret�rio inicial, tabela de fotos, creditos, largura da coluna e t�tulo.
     * @param tabela Tabela das fotos.
     * @param combo Lista.
     * @param tituloDialogo T�tulo da caixa de di�logo.
     */
    public AcaoAdicionarFoto(JTable tabela, JComboBox combo, String tituloDialogo) {
        Configuration c=Util.getConfig();
        diretorioInicial=Util.getFolder("diretorioAdicionarFotos");
        tbFotos=tabela;
        lstCreditosTabelaFotos = combo;
        larguraColunasFotos=c.getString("colunas2");
        titulo=tituloDialogo;
    }

    /**
     * M�todo respons�vel pela a��o de inser��o das fotos.
     * Inicia um objeto JFileChooser para a escolha do arquivo e faz a configura��o.
     * Testa se o diret�rio inicial � v�lido e depois faz a implanta��o da foto.
     * Atualiza a tabela, ajusta as colunas, aciona o flag de {@link net.sf.webphotos.gui.PainelWebFotos#alteracaoDetectada() alteracaoDetectada}() e armazena o �ltimo diret�rio lido.
     * @param e Evento da a��o de adi��o de foto.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        fc=new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new ImageFilter());
        fc.setDialogTitle(titulo);
        fc.setApproveButtonText("Ok");
        fc.setApproveButtonToolTipText("Adiciona as fotos selecionadas ao �lbum");
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

            /** liga o flag de altera��o realiza */
            PainelWebFotos.alteracaoDetectada();

            /** armazena o �ltimo diret�rio lido */
            diretorioInicial=null;
            diretorioInicial=new File(fc.getSelectedFiles()[0].getParent());
        }
    }
}