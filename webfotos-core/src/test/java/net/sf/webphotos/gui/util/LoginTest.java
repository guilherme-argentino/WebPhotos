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
package net.sf.webphotos.gui.util;

import java.awt.Component;
import java.awt.Rectangle;
import net.sf.webphotos.util.Util;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.UISpec4J;
import org.junit.Ignore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.finder.ComponentMatcher;
import org.uispec4j.interception.WindowInterceptor;
import webfotos.gui.util.Login;
import static org.junit.Assert.*;

/**
 * Waiting for changes on CloudBees
 * @author Guilherme
 */
public class LoginTest {

    static {
        UISpec4J.init();
    }

    public LoginTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Basic Test.
     */
    @Test
    public void testLogin_Basic() {
        System.out.println("Basic Login");

        final Login login = new Login();
        final WindowHandler windowHandler = new WindowHandler() {

            public Trigger process(Window window) {
                // return a trigger that will close it
                return window.getButton("OK").triggerClick();
            }
        };
        interceptDialog(login, windowHandler);

        assertEquals(Util.getConfig().getString("autoPreencher.Login"), login.getUser());
        assertEquals(Util.getConfig().getString("autoPreencher.Pass"), new String(login.getPassword()));
    }

    /**
     * Writing info Test.
     */
    @Test
    public void testLogin_WritingInfo() {
        System.out.println("Writing info Login");

        final String testeLogin = "TesteLogin";
        final String testePass = "TestePass";

        final Login login = new Login();
        final WindowHandler windowHandler = new WindowHandler() {

            public Trigger process(Window window) {
                window.getTextBox(new ComponentMatcher() {

                    public boolean matches(Component cmpnt) {
                        return cmpnt.getBounds().equals(new Rectangle(55,8,129,20));
                    }
                }).setText(testeLogin);
                window.getPasswordField(new ComponentMatcher() {

                    public boolean matches(Component cmpnt) {
                        return cmpnt.getBounds().equals(new Rectangle(55,34,129,20));
                    }
                }).setPassword(testePass);

                // return a trigger that will close it
                return window.getButton("OK").triggerClick();
            }
        };
        interceptDialog(login, windowHandler);

        assertEquals(testeLogin, login.getUser());
        assertEquals(testePass, new String(login.getPassword()));
    }

    /**
     * Cancel Test.
     */
    @Test
    public void testLogin_Cancel() {
        System.out.println("Cancel Login");

        final Login login = new Login();
        final WindowHandler windowHandler = new WindowHandler() {

            public Trigger process(Window window) {
                // return a trigger that will close it
                return window.getButton("Cancel").triggerClick();
            }
        };
        interceptDialog(login, windowHandler);

        assertNull(login.getUser());
        assertNull(login.getPassword());
    }

    private void interceptDialog(final Login login, final WindowHandler windowHandler) {
        WindowInterceptor.init(new Trigger() {

            public void run() throws Exception {
                login.show();
            }
        }).process(windowHandler).run();
    }
}
