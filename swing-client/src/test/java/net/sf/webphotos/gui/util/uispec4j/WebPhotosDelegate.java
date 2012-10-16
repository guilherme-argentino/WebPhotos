/*
 * Copyright 2012 Guilherme.
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
package net.sf.webphotos.gui.util.uispec4j;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import net.sf.webphotos.WebPhotos;
import org.uispec4j.Button;
import org.uispec4j.ComponentAmbiguityException;
import org.uispec4j.ItemNotFoundException;
import org.uispec4j.Table;
import org.uispec4j.Trigger;
import org.uispec4j.UISpec4J;
import org.uispec4j.Window;
import org.uispec4j.interception.FileChooserHandler;
import org.uispec4j.interception.WindowInterceptor;
import org.uispec4j.utils.MainClassTrigger;

/**
 *
 * @author Guilherme
 */
public class WebPhotosDelegate implements net.sf.webphotos.gui.util.WebPhotosDelegate {

    private static final WebPhotosDelegate WEB_PHOTOS_DELEGATE = new WebPhotosDelegate();

    static {
        UISpec4J.init();
        UISpec4J.setWindowInterceptionTimeLimit(30000);
    }
    private static Window mainWindow;
    private static Trigger initialTrigger;

    /**
     * Tests Button
     *
     * @param button
     * @throws RuntimeException no errors allowed
     */
    @Override
    public void checkIsButtonEnabled(final String button) throws RuntimeException {
        getButton(button).isEnabled().check();
    }

    /**
     * Tests Button Text
     *
     * @param button
     * @param textShown
     * @throws RuntimeException no errors allowed
     */
    @Override
    public void checkButtonHasText(final String button, final String textShown) throws RuntimeException {
        getButton(button).textEquals(textShown).check();
    }

    /**
     * Tests Combox Text
     *
     * @param comboBoxName
     * @param textShown
     * @throws RuntimeException no errors allowed
     */
    @Override
    public void checkComboBoxHasText(final String comboBoxName, final String textShown) throws RuntimeException {
        WebPhotosDelegate.mainWindow.getComboBox(comboBoxName).contains(textShown).check();
    }

    /**
     * Tests mainWindow
     */
    @Override
    public void validateMainWindowIsPresent() throws RuntimeException {
        WebPhotosDelegate.mainWindow.isVisible().check();
        WebPhotosDelegate.mainWindow.isEnabled().check();
    }

    /**
     * Dispose all tests
     *
     * @throws Exception no errors allowed
     */
    @Override
    public void tearDownClass() throws Exception {
        mainWindow.dispose();
    }

    /**
     * Prepare all tests
     *
     * @throws Exception no errors allowed
     */
    @Override
    public void setUpClass() throws Exception {
        WebPhotosDelegate.initialTrigger = WindowInterceptor.getModalDialog(new MainClassTrigger(WebPhotos.class, "")).getButton("OK").triggerClick();
        WebPhotosDelegate.mainWindow = WindowInterceptor.run(WebPhotosDelegate.initialTrigger);
    }

    /**
     *
     * @param fileNames
     * @param buttonName
     * @param folderNameToVerify
     * @param fileDialogName
     * @throws RuntimeException no errors allowed
     */
    @Override
    public void addPhotosToAlbumAndCheck(final String[] fileNames, final String buttonName, final File folderNameToVerify, final String fileDialogName) throws RuntimeException {
        Table tbFotos = addPhotosToAlbum(fileNames, buttonName, folderNameToVerify, fileDialogName);
        checkPhotosTable(tbFotos, fileNames);
    }

    /**
     * Writes text to a Modal Dialog
     *
     * @param buttonName
     * @param modalTittle
     * @param data
     * @throws RuntimeException no errors allowed
     */
    @Override
    public void fillModalWithText(final String buttonName, final String modalTittle, final String data) throws RuntimeException {
        WindowInterceptor.init(mainWindowTriggerClick(buttonName)).process(new ModalWindowHandler(modalTittle, data)).run();
    }

    /**
     * Returns the Delegate
     *
     * @return
     */
    public static WebPhotosDelegate getWebPhotosDelegate() {
        return WEB_PHOTOS_DELEGATE;
    }

    /**
     * Setup one Test
     */
    @Override
    public void setUp() {
    }

    @Override
    public void fillAlbumForm(String albumTitle, String albumDescription, String categoryName, Map<String, String[]> photoData) {
        mainWindow.getTextBox("txtTitulo").setText(albumTitle);
        mainWindow.getTextBox("txtDescricao").setText(albumDescription);
        mainWindow.getComboBox("lstCategoriasAlbum").select(categoryName);

        final Table tbFotos = WebPhotosDelegate.mainWindow.getTable("tbFotos");
        for (String photoName : photoData.keySet()) {
            tbFotos.selectRowsWithText(0, photoName);
            System.out.println("Line Selected (tbFotos.getAwtComponent().getSelectedRow()) : " + tbFotos.getAwtComponent().getSelectedRow());
            tbFotos.getAwtComponent().setValueAt(photoData.get(photoName)[0], tbFotos.getAwtComponent().getSelectedRow(), 1);
            //tbFotos.editCell(tbFotos.getAwtComponent().getSelectedRow(), 1, photoData.get(photoName)[0], true);
            //mainWindow.getTextBox("txtLegenda").setText(photoData.get(photoName)[0]);
            //mainWindow.findSwingComponent(JTextArea.class, "txtLegenda").setText(photoData.get(photoName)[0]);
            mainWindow.getComboBox("lstCreditos").select(photoData.get(photoName)[1]);
            tbFotos.selectRowsWithText(0, photoName);
        }

        WindowInterceptor.run(getButton("btNovo").triggerClick());
        //getButton("btNovo").click();
    }

    @Override
    public void checkNewAlbum() {
        Table tbAlbuns = mainWindow.getTable("tbAlbuns");
        tbAlbuns.rowCountEquals(1).check();
    }

    private Table addPhotosToAlbum(final String[] fileNames, final String targetButton, final File startUpFolder, final String fileChooserName) throws RuntimeException {
        FileChooserHandler openDialog = FileChooserHandler.init().titleEquals(fileChooserName).assertAcceptsFilesOnly().assertMultiSelectionEnabled(true).assertCurrentDirEquals(startUpFolder);
        WindowInterceptor.init(getButton(targetButton).triggerClick()).process(openDialog.select(fileNames)).run();
        final Table tbFotos = WebPhotosDelegate.mainWindow.getTable("tbFotos");
        return tbFotos;
    }

    private Trigger mainWindowTriggerClick(final String buttonName) throws RuntimeException {
        return getButton(buttonName).triggerClick();
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
        }
    }

    private Button getButton(final String button) throws ItemNotFoundException, ComponentAmbiguityException {
        return mainWindow.getButton(button);
    }

    /**
     * Private Constructor: Singleton Pattern
     */
    private WebPhotosDelegate() {
    }
}
