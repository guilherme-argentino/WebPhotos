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
package net.sf.webphotos;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.sf.webphotos.gui.util.WebPhotosDelegate;
import net.sf.webphotos.util.Util;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Guilherme
 */
public class WebPhotosTest {
    
    public static final WebPhotosDelegate WEB_PHOTOS_DELEGATE = net.sf.webphotos.gui.util.uispec4j.WebPhotosDelegate.getWebPhotosDelegate();
    //public static final WebPhotosDelegate WEB_PHOTOS_DELEGATE = net.sf.webphotos.gui.util.fest.WebPhotosDelegate.getWebPhotosDelegate();

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        WEB_PHOTOS_DELEGATE.setUpClass();
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        WEB_PHOTOS_DELEGATE.tearDownClass();
    }

    /**
     *
     */
    @Before
    public void setUp() {
        WEB_PHOTOS_DELEGATE.setUp();
    }

    /**
     * Creating a Category.
     */
    @Test
    public void testAddCategory() {
        System.out.println("Add Category");
        WEB_PHOTOS_DELEGATE.validateMainWindowIsPresent();
        WEB_PHOTOS_DELEGATE.fillModalWithText("buttonAddCategory", "Cat", "Geral");

        WEB_PHOTOS_DELEGATE.checkComboBoxHasText("lstCategoriasPesquisa", "Geral");
    }

    /**
     * Creating a Credit.
     */
    @Test
    public void testAddCredits() {
        System.out.println("Add Credits");
        WEB_PHOTOS_DELEGATE.validateMainWindowIsPresent();
        WEB_PHOTOS_DELEGATE.fillModalWithText("buttonAddCredits", "Cred", "Divulgacao");

        WEB_PHOTOS_DELEGATE.checkComboBoxHasText("lstCreditos", "Divulgacao");
    }

    @Test
    @Ignore
    public void testCreateAlbumWithPhotos() {
        System.out.println("Create Album With Photos");
        WEB_PHOTOS_DELEGATE.validateMainWindowIsPresent();

        final String[] fileNames = new String[2];
        fileNames[0] = Util.getProperty("user.dir") + File.separator + ".." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Originals" + File.separator + "GPL.jpg";
        fileNames[1] = Util.getProperty("user.dir") + File.separator + ".." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Originals" + File.separator + "ZS-GPL.jpg";

        /**
         * Primeira tentativa - Comportamento esperado: duas fotos na lista
         */
        System.out.println("Adding Photos - First try");
        WEB_PHOTOS_DELEGATE.addPhotosToAlbumAndCheck(fileNames, "btNovo", Util.getFolder("diretorioAdicionarFotos"), "Criando novo \u00e1lbum - Selecione os arquivos");
        checkNewAlbumButton();

        /**
         * Segunda tentativa - com as mesmas fotos - Comportamento esperado:
         * duas fotos ignoradas
         */
        System.out.println("Adding Photos - Second try");
        WEB_PHOTOS_DELEGATE.addPhotosToAlbumAndCheck(fileNames, "buttonAddPhotos", new File(fileNames[0]).getParentFile(), "Adicionar fotos no \u00e1lbum");
        checkNewAlbumButton();
        
        final Map<String, String[]> photoData = new HashMap<String, String[]>();
        for (String fileName : fileNames) {
            photoData.put(fileName, new String[] { "Original Name: " + fileName, "Divulgacao" });
        }
        WEB_PHOTOS_DELEGATE.fillAlbumForm("First Test - Title", "First Test - Description", "Geral", photoData);
        
        WEB_PHOTOS_DELEGATE.validateMainWindowIsPresent();
        
        WEB_PHOTOS_DELEGATE.checkNewAlbum();
    }

    @Ignore
    public void testAddPhotos() {
        System.out.println("Add Photos");
    }

    private void checkNewAlbumButton() throws RuntimeException {
        WEB_PHOTOS_DELEGATE.checkIsButtonEnabled("btNovo");
        WEB_PHOTOS_DELEGATE.checkButtonHasText("btNovo", "Finalizar");
    }
}
