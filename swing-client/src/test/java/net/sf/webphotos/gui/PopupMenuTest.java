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
package net.sf.webphotos.gui;

import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author 60410501
 */
public class PopupMenuTest extends TestCase {
    
    public PopupMenuTest(String testName) {
        super(testName);
    }

    /**
     * Test of getPreSQL method, of class webfotos.gui.PopupMenu.
     */
    @Test
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
    @Test
    public void testSetPreSQL() {
        System.out.println("setPreSQL");
        
        String expResult = "select * from albuns left join categorias using(categoriaID) ";
        PopupMenu instance = new PopupMenu();
        
        instance.setPreSQL(expResult);
        String result = instance.getPreSQL();
        assertEquals(expResult, result);

    }
    
}
