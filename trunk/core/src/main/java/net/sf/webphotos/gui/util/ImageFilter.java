/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.webphotos.gui.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filtro da seleção de imagens.
 * 
 * Created on 11 de Maio de 2007, 12:44
 *
 * @author felipe
 */
public class ImageFilter extends FileFilter {

    String ext;

    /**
     * Retorna uma variável lógica que indica se o arquivo é válido. Aceita
     * todos os diretorios e formatos de imagem .jpeg e .jpg.
     *
     * @param f Arquivo ou diretório.
     * @return Retorna um valor lógico.
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String nmArquivo = f.getName();
        int pos = nmArquivo.lastIndexOf('.');
        if (pos > 0 && pos < nmArquivo.length() - 1) {
            ext = nmArquivo.substring(pos + 1).toLowerCase();
        }

        return ext.equals("jpg") || ext.equals("jpeg");
    }

    /**
     * Retorna a descrição "Imagens jpeg e jpg".
     *
     * @return Retorna uma descrição.
     */
    @Override
    public String getDescription() {
        return "Imagens jpeg e jpg";
    }
}
