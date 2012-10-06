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
import net.sf.webphotos.util.Util;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.uispec4j.ComponentAmbiguityException;
import org.uispec4j.ItemNotFoundException;
import org.uispec4j.Table;
import org.uispec4j.Trigger;
import org.uispec4j.UISpec4J;
import org.uispec4j.Window;
import org.uispec4j.interception.FileChooserHandler;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;
import org.uispec4j.utils.MainClassTrigger;

/**
 *
 * @author Guilherme
 */
public class WebPhotosTest {

    static {
        UISpec4J.init();
        UISpec4J.setWindowInterceptionTimeLimit(30000);
    }
    private static Trigger initialTrigger;
    private static Window mainWindow;

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        initialTrigger = WindowInterceptor.getModalDialog(new MainClassTrigger(WebPhotos.class, "")).getButton("OK").triggerClick();
        mainWindow = WindowInterceptor.run(initialTrigger);
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     * Creating a Category.
     */
    @Test
    public void testAddCategory() {
        System.out.println("Add Category");

        mainWindow.isVisible().check();
        mainWindow.isEnabled().check();
        WindowInterceptor.init(mainWindow.getButton("buttonAddCategory").triggerClick()).process(new ModalWindowHandler("Cat", "Geral")).run();

        mainWindow.getComboBox("lstCategoriasPesquisa").contains("Geral").check();
    }

    /**
     * Creating a Credit.
     */
    @Test
    public void testAddCredits() {
        System.out.println("Add Credits");

        mainWindow.isVisible().check();
        mainWindow.isEnabled().check();
        WindowInterceptor.init(mainWindow.getButton("buttonAddCredits").triggerClick())
                .process(new ModalWindowHandler("Cred", "Divulgacao")).run();

        mainWindow.getComboBox("lstCreditos").contains("Divulgacao").check();
    }

    @Test
    public void testCreateAlbumWithPhotos() {
        System.out.println("Create Album With Photos");

        mainWindow.isVisible().check();
        mainWindow.isEnabled().check();

        final String[] fileNames = new String[2];
        fileNames[0] = Util.getProperty("user.dir") + File.separator + ".." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Originals" + File.separator + "GPL.jpg";
        fileNames[1] = Util.getProperty("user.dir") + File.separator + ".." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Originals" + File.separator + "ZS-GPL.jpg";

        /**
         * Primeira tentativa - Comportamento esperado: duas fotos na lista
         */
        System.out.println("First try");
        Table tbFotos = addPhotosToAlbum(fileNames, "btNovo", Util.getFolder("diretorioAdicionarFotos"), "Criando novo \u00e1lbum - Selecione os arquivos");
        checkPhotosTable(tbFotos, fileNames);
        checkNewAlbumButton();

        /**
         * Segunda tentativa - com as mesmas fotos - Comportamento esperado:
         * duas fotos ignoradas
         */
        System.out.println("Second try");
        tbFotos = addPhotosToAlbum(fileNames, "buttonAddPhotos", new File(fileNames[0]).getParentFile(), "Adicionar fotos no \u00e1lbum");
        checkPhotosTable(tbFotos, fileNames);
        checkNewAlbumButton();
    }

    @Ignore
    public void testAddPhotos() {
        System.out.println("Add Photos");
    }

    private Table addPhotosToAlbum(final String[] fileNames, final String targetButton, final File startUpFolder, final String criando_novo_álbum__Selecione_os_arquivos) throws ComponentAmbiguityException, ItemNotFoundException {
        FileChooserHandler openDialog = FileChooserHandler.init()
                .titleEquals(criando_novo_álbum__Selecione_os_arquivos)
                .assertAcceptsFilesOnly()
                .assertMultiSelectionEnabled(true)
                .assertCurrentDirEquals(startUpFolder);
        WindowInterceptor.init(mainWindow.getButton(targetButton).triggerClick()).process(openDialog.select(fileNames)).run();
        final Table tbFotos = mainWindow.getTable("tbFotos");
        return tbFotos;
    }

    private void checkPhotosTable(Table tbFotos, final String[] fileNames) {
        tbFotos.rowCountEquals(fileNames.length).check();
        tbFotos.hasHeader().check();
        tbFotos.columnCountEquals(3).check();
        for (int i = 0; i < fileNames.length; i++) {
            System.out.printf("fileNames[%d] = %s", i, fileNames[i]);
            System.out.println();
            System.out.printf("tbFotos.getContentAt(%d, 0).toString() = %s", i, tbFotos.getContentAt(i, 0).toString());
            System.out.println();
            tbFotos.cellEquals(i + 1, 1, fileNames[i]);
//            final File expectedFile = new File(fileNames[i]);
//            final File chosenFile = new File(tbFotos.getContentAt(i, 0).toString());
//            System.out.printf("expectedFile = %s", expectedFile.getAbsolutePath());
//            System.out.println();
//            System.out.printf("chosenFile = %s", chosenFile.getAbsolutePath());
//            System.out.println();
//            Assert.assertEquals(expectedFile, chosenFile);
        }
    }

    private void checkNewAlbumButton() throws ItemNotFoundException, ComponentAmbiguityException {
        mainWindow.getButton("btNovo").isEnabled().check();
        mainWindow.getButton("btNovo").textEquals("Finalizar").check();
    }

    private static class ModalWindowHandler extends WindowHandler {

        private String titleName;
        private String modalText;

        public ModalWindowHandler(String titleName, String modalText) {
            this.modalText = modalText;
            this.titleName = titleName;
        }

        @Override
        public Trigger process(Window window) throws Exception {
            assertTrue("Tittle: " + window.getTitle(), window.titleContains(titleName).isTrue());
            assertTrue("Modal: " + window.getTitle(), window.isModal().isTrue());

            window.getInputTextBox().setText(modalText);
            return window.getButton("OK").triggerClick();
        }
    }
}
