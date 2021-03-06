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
package net.sf.webphotos.gui.util.uispec4j;

import static org.junit.Assert.assertTrue;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;

/**
 *
 * @author Guilherme
 */
public class ModalWindowHandler extends WindowHandler {
    
    private String titleName;
    private String modalText;

    /**
     *
     * @param titleName
     * @param modalText
     */
    public ModalWindowHandler(String titleName, String modalText) {
        this.modalText = modalText;
        this.titleName = titleName;
    }

    /**
     *
     * @param window
     * @return
     * @throws Exception
     */
    @Override
    public Trigger process(Window window) throws Exception {
        assertTrue("Tittle: " + window.getTitle(), window.titleContains(titleName).isTrue());
        assertTrue("Modal: " + window.getTitle(), window.isModal().isTrue());
        window.getInputTextBox().setText(modalText);
        return window.getButton("OK").triggerClick();
    }
    
}
