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
package net.sf.webphotos.gui.util.fest;

import java.io.File;

/**
 *
 * @author Guilherme
 */
public class WebPhotosDelegate implements net.sf.webphotos.gui.util.WebPhotosDelegate {

    @Override
    public void addPhotosToAlbumAndCheck(String[] fileNames, String buttonName, File folderNameToVerify, String fileDialogName) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkButtonHasText(String button, String textShown) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkComboBoxHasText(String comboBoxName, String textShown) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkIsButtonEnabled(String button) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fillModalWithText(String buttonName, String modalTittle, String data) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUpClass() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void tearDownClass() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void validateMainWindowIsPresent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
