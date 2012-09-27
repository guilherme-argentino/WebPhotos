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
package net.sf.webphotos.gui;

import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import net.sf.webphotos.gui.util.ImageFilter;

/**
 * Implementa um menu para sele��o de arquivos de imagem.
 * N�o possui utiliza��es.
 */
public class AdicionarFotos {
    private static JFileChooser fc;
    private static int retornoFc;

    private AdicionarFotos() {
    }
	
    /**
     * Retorna um vetor com os arquivos selecionados para inclus�o.
     * Configura o objeto JFileChooser para escolher os arquivos, testa se as imagens s�o v�lidas atrav�s da classe ImageFilter.
     * Seta um diret�rio com par�metro caminho e retorna os arquivos selecionados.
     * N�o possui utiliza��es.
     * @param caminho Caminho do diret�rio de imagens.
     * @param pai Componente para busca no opendialog.
     * @return Retorna uma lista de imagens.
     */
    public static File[] getFiles(String caminho, JComponent pai) {
        fc=new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new ImageFilter());
        fc.setDialogTitle("Adicionar fotos ao �lbum");
        fc.setApproveButtonText("Ok");
        fc.setApproveButtonToolTipText("Adiciona as fotos selecionadas ao �lbum");
        fc.setMultiSelectionEnabled(true);

        File f=new File(caminho);
        if(f.isDirectory()) {
            fc.setCurrentDirectory(f);
        }

        retornoFc=fc.showOpenDialog(pai);

        if(retornoFc==JFileChooser.APPROVE_OPTION) {
                return fc.getSelectedFiles();
        } else { return null; }
    }
}
