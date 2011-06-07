/**
 * Copyright 2011 Guilherme.
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

import webfotos.util.Util;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.UISpec4J;
import org.junit.Ignore;
import javax.swing.JDialog;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowInterceptor;
import webfotos.gui.util.Login;
import static org.junit.Assert.*;

/**
 *
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
     * Test of startLogin method, of class Login.
     */
    @Test
    public void testLogin_Basic() {
        System.out.println("startLogin");
        final Login login = new Login();

        WindowInterceptor.init(new Trigger() {

            public void run() throws Exception {
                login.show();
            }
        }).process(new WindowHandler() {

            public Trigger process(Window window) {
                // ... perform some operations on the shown window ...

                // return a trigger that will close it
                return window.getButton("OK").triggerClick();
            }
        }).run();

        assertEquals(Util.getConfig().getString("autoPreencher.Login"), login.getUser());
        assertEquals(Util.getConfig().getString("autoPreencher.Pass"), new String(login.getPassword()));
    }

    /**
     * Test of getLogin method, of class Login.
     */
    @Ignore
    public void testGetLogin_String() {
        System.out.println("getLogin");
        String titulo = "";
        Login expResult = null;
        Login result = Login.getLogin(titulo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLogin method, of class Login.
     */
    @Ignore
    public void testGetLogin_0args() {
        System.out.println("getLogin");
        Login expResult = null;
        Login result = Login.getLogin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelado method, of class Login.
     */
    @Ignore
    public void testCancelado() {
        System.out.println("cancelado");
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.cancelado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class Login.
     */
    @Ignore
    public void testGetUser() {
        System.out.println("getUser");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class Login.
     */
    @Ignore
    public void testGetPassword() {
        System.out.println("getPassword");
        Login instance = new Login();
        char[] expResult = null;
        char[] result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of show method, of class Login.
     */
    @Ignore
    public void testShow_0args() {
        System.out.println("show");
        Login instance = new Login();
        instance.show();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of show method, of class Login.
     */
    @Ignore
    public void testShow_String() {
        System.out.println("show");
        String msgErro = "";
        Login instance = new Login();
        instance.show(msgErro);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Login.
     */
    @Ignore
    public void testMain() {
        System.out.println("main");
        String[] a = null;
        Login.main(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTelaLogin method, of class Login.
     */
    @Ignore
    public void testGetTelaLogin() {
        System.out.println("getTelaLogin");
        JDialog expResult = null;
        JDialog result = Login.getTelaLogin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
