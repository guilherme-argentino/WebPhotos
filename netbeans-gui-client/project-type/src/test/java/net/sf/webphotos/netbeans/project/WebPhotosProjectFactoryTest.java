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
package net.sf.webphotos.netbeans.project;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author Guilherme
 */
public class WebPhotosProjectFactoryTest {

    private FileObject baseProjectTest;
    private FileSystem memoryFileSystem;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        memoryFileSystem = FileUtil.createMemoryFileSystem();
        baseProjectTest = memoryFileSystem.getRoot();
        for (String node : WebPhotosProjectFactory.PROJECT_ARCHIVES) {
            FileUtil.createData(baseProjectTest, node);
        }
        assertNotNull(baseProjectTest);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isProject method, of class WebPhotosProjectFactory.
     */
    @Test
    public void testIsProject() {
        System.out.println("isProject");
        FileObject projectDirectory = baseProjectTest;
        WebPhotosProjectFactory instance = new WebPhotosProjectFactory();
        boolean expResult = true;
        boolean result = instance.isProject(projectDirectory);
        assertEquals(expResult, result);
    }

    /**
     * Test of loadProject method, of class WebPhotosProjectFactory.
     */
    @Test
    public void testLoadProject() throws Exception {
        System.out.println("loadProject");
        FileObject dir = baseProjectTest;
        ProjectState state = new ProjectState() {

            @Override
            public void markModified() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void notifyDeleted() throws IllegalStateException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        WebPhotosProjectFactory instance = new WebPhotosProjectFactory();
        Project result = instance.loadProject(dir, state);
        assertNotNull(result);
    }

    /**
     * Test of saveProject method, of class WebPhotosProjectFactory.
     */
    public void testSaveProject() throws Exception {
        System.out.println("saveProject");
        Project project = null;
        WebPhotosProjectFactory instance = new WebPhotosProjectFactory();
        instance.saveProject(project);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
