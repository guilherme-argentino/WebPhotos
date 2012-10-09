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
package net.sf.webphotos.gui.util;

import java.io.File;

/**
 *
 * @author Guilherme
 */
public interface WebPhotosDelegate {

    /**
     *
     * @param fileNames
     * @param buttonName
     * @param folderNameToVerify
     * @param fileDialogName
     * @throws RuntimeException no errors allowed
     */
    void addPhotosToAlbumAndCheck(final String[] fileNames, final String buttonName, final File folderNameToVerify, final String fileDialogName) throws RuntimeException;

    /**
     * Tests Button Text
     * @param button
     * @param textShown
     * @throws RuntimeException no errors allowed
     */
    void checkButtonHasText(final String button, final String textShown) throws RuntimeException;

    /**
     * Tests Combox Text
     * @param comboBoxName
     * @param textShown
     * @throws RuntimeException no errors allowed
     */
    void checkComboBoxHasText(final String comboBoxName, final String textShown) throws RuntimeException;

    /**
     * Tests Button
     * @param button
     * @throws RuntimeException no errors allowed
     */
    void checkIsButtonEnabled(final String button) throws RuntimeException;

    /**
     * Writes text to a Modal Dialog
     * @param buttonName
     * @param modalTittle
     * @param data
     * @throws RuntimeException no errors allowed
     */
    void fillModalWithText(final String buttonName, final String modalTittle, final String data) throws RuntimeException;

    /**
     * Prepare all tests
     * @throws Exception no errors allowed
     */
    void setUpClass() throws Exception;

    /**
     * Dispose all tests
     * @throws Exception no errors allowed
     */
    void tearDownClass() throws Exception;

    /**
     * Tests mainWindow
     */
    void validateMainWindowIsPresent();

    /**
     *
     */
    void setUp();
    
}
