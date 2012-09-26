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

import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uispec4j.Trigger;
import org.uispec4j.UISpec4J;
import org.uispec4j.Window;
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
     *  Creating a Category.
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
        WindowInterceptor.init(mainWindow.getButton("buttonAddCredits").triggerClick()).process(new ModalWindowHandler("Cred", "Divulgacao")).run();
        
        mainWindow.getComboBox("lstCreditos").contains("Divulgacao").check();
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
