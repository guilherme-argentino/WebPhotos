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
package webfotos;

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

/**
 *
 * @author Guilherme
 */
public class WebPhotosTest {
    
    static {
        UISpec4J.init();
    }

    public WebPhotosTest() {
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

    /**
     * Test of main method, of class WebPhotos.
     */
    @Test
    public void testMain() {
        System.out.println("main");

        WindowInterceptor.init(new Trigger() {

            @Override
            public void run() {
                WebPhotos.main(new String[0]);
            }
        }).processWithButtonClick("OK").process(new WindowHandler("Web") {

            @Override
            public Trigger process(Window window) throws Exception {
                assertTrue(window.titleContains("Web").isTrue());
                System.out.println("Tittle: " + window.getTitle());
                return Trigger.DO_NOTHING;
            }
        }).run();
    }
}
