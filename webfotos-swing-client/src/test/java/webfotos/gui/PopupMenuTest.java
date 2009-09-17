/*
 * PopupMenuTest.java
 * JUnit based test
 *
 * Created on 26 de Maio de 2006, 19:03
 */

package webfotos.gui;

import junit.framework.*;
import webfotos.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author 60410501
 */
public class PopupMenuTest extends TestCase {
    
    public PopupMenuTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(PopupMenuTest.class);
        
        return suite;
    }

    /**
     * Test of getPreSQL method, of class webfotos.gui.PopupMenu.
     */
    public void testGetPreSQL() {
        System.out.println("getPreSQL");
        
        PopupMenu instance = new PopupMenu();
        
        String expResult = "select albumID as ID, categorias.nmcategoria as Categoria, nmalbum as Pauta, DATE_FORMAT(DtInsercao, '%d/%m/%y') as Data from albuns left join categorias using(categoriaID) ";
        String result = instance.getPreSQL();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPreSQL method, of class webfotos.gui.PopupMenu.
     */
    public void testSetPreSQL() {
        System.out.println("setPreSQL");
        
        String expResult = "select * from albuns left join categorias using(categoriaID) ";
        PopupMenu instance = new PopupMenu();
        
        instance.setPreSQL(expResult);
        String result = instance.getPreSQL();
        assertEquals(expResult, result);

    }
    
}
