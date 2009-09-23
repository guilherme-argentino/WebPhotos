/*
 * ImageFilter.java
 *
 * Created on 11 de Maio de 2007, 12:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package webfotos.gui.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filtro da sele��o de imagens.
 * @author felipe
 */
public class ImageFilter extends FileFilter {
    String ext;
    
    /**
     * Retorna uma vari�vel l�gica que indica se o arquivo � v�lido.
     * Aceita todos os diretorios e formatos de imagem .jpeg e .jpg.
     * @param f Arquivo ou diret�rio.
     * @return Retorna um valor l�gico.
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
            String nmArquivo=f.getName();
            int pos=nmArquivo.lastIndexOf(".");
            if(pos > 0 && pos < nmArquivo.length()-1)
                    ext=nmArquivo.substring(pos+1).toLowerCase();

            if(ext.equals("jpg") || ext.equals("jpeg")) return true;

            return false;
    }

    /**
     * Retorna a descri��o "Imagens jpeg e jpg".
     * @return Retorna uma descri��o.
     */
    @Override
    public String getDescription() {
        return "Imagens jpeg e jpg";
    }
}
