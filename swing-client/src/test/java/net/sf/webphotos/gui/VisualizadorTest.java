/*
 * Copyright 2015 Guilherme.
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
package net.sf.webphotos.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Guilherme
 */
public class VisualizadorTest {
    
    public VisualizadorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calculaReducao method, of class Visualizador.
     */
    @Test
    public void testCalculaReducao() {
        System.out.println("calculaReducao");
        Dimension original = new Dimension(1154, 1772);
        int tamMaximo = 550;
        Dimension expResult = new Dimension(358, tamMaximo);
        Dimension result = Visualizador.calculaReducao(original, tamMaximo);
        assertEquals(expResult, result);
    }

    /**
     * Test of paint method, of class Visualizador.
     */
    @Test
    @Ignore
    public void testPaint() {
        System.out.println("paint");
        Graphics g = null;
        Visualizador instance = null;
        instance.paint(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionPerformed method, of class Visualizador.
     */
    @Test
    @Ignore
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent e = null;
        Visualizador instance = null;
        instance.actionPerformed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Visualizador.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] a = null;
        Visualizador.main(a);
    }
    
}
