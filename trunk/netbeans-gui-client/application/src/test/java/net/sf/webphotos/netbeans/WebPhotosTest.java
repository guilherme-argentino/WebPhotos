/**
 * Copyright 2008 WebPhotos
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sf.webphotos.netbeans;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.swing.JDialog;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import junit.framework.Test;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.OptionsOperator;
import org.netbeans.jellytools.TopComponentOperator;
import org.netbeans.jellytools.actions.Action;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.netbeans.jemmy.operators.TextComponentOperator;
import org.netbeans.jemmy.util.Dumper;
import org.netbeans.jemmy.util.NameComponentChooser;
import org.netbeans.jemmy.util.PNGEncoder;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;

/**
 *
 * @author Guilherme
 */
public class WebPhotosTest extends JellyTestCase {

    /**
     * Test List
     */
    private static final List<String> tests = new ArrayList<String>() {
        {
            add("testNewProjectAction");
            add("testNewProjectButton");
        }
    };

    /**
     * Mainframe Field
     */
    JFrameOperator mainFrame;

    /**
     * Constructor required by JUnit
     *
     * @param name
     */
    public WebPhotosTest(String name) {
        super(name);
    }

    /**
     * Creates suite from particular test cases. You can define order of test
     * cases here.
     *
     * @return
     */
    public static Test suite() {
        Configuration testConfig = NbModuleSuite.createConfiguration(WebPhotosTest.class);
        testConfig = testConfig.addTest(tests.toArray(new String[tests.size()]));
        testConfig = testConfig.clusters(".*").enableModules(".*");
        testConfig = testConfig.failOnException(Level.INFO).failOnMessage(Level.SEVERE);
        return testConfig.suite();
    }

    /**
     * Called before every test case.
     */
    @Override
    public void setUp() {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();

        System.out.println("########  " + getName() + "  #######");

        System.out.println("########  " + language + ":" + country + "  #######");

        mainFrame = new JFrameOperator("WebPhotos");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown(); //To change body of generated methods, choose Tools | Templates.

        //grab image
        PNGEncoder.captureScreen(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "screen." + getName() + ".png");

        //grab component state
        Dumper.dumpAll(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + "dump." + getName() + ".xml");
    }

    /**
     * Test brush size setting.
     */
    public void testNewProjectAction() {
        new Action("File|New Project", null).perform();
    }

    /**
     * Create an Empty Project and validate it.
     *
     * @throws java.io.FileNotFoundException
     */
    public void testNewProjectButton() throws Exception {

        // Click on "New Project" Button
        new JMenuBarOperator(mainFrame).pushMenuNoBlock("New Project");

        JDialog dialog = JDialogOperator.findJDialog("New Project", false, false);
        JDialogOperator newProjectDialogOperator = new JDialogOperator(dialog);

        // Some assertions
        assertEquals(dialog.getTitle(), "New Project");

        // Click on Category "WebPhotos" and "Empty WebPhotos Project"
        JTreeOperator treeNewProjectCategories = new JTreeOperator(newProjectDialogOperator);
        treeNewProjectCategories.selectPath(treeNewProjectCategories.findPath("WebPhotos"));

        JListOperator listOperator = new JListOperator(newProjectDialogOperator);
        listOperator.selectItem(0);

        // Click on Next
        new JButtonOperator(newProjectDialogOperator, "Next >").push();

        /**
         * Verifies if the project already exists XXX: I don't know how to solve
         * this for now
         */
        if (JTextPaneOperator.findJTextPane(dialog, "<html>\n"
                + "  <head>\n"
                + "    \n"
                + "  </head>\n"
                + "  <body>\n"
                + "    Project&#160;Folder&#160;already&#160;exists&#160;and&#160;is&#160;not&#160;empty.\n"
                + "  </body>\n"
                + "</html>", false, false) != null) {
            new JTextFieldOperator(newProjectDialogOperator, "EmptyWebPhotosProject").setText("EmptyWebPhotosProject." + new Date().getTime());
        }

        // Click on Finish
        new JButtonOperator(newProjectDialogOperator, "Finish").push();

        // Find Projects Tab and its main tree
        TopComponentOperator projectsComponentOperator = new TopComponentOperator("Projects");
        projectsComponentOperator.transferFocus();
        JTreeOperator tree = new JTreeOperator(projectsComponentOperator);

        //collapse node	    
        tree.collapsePath(tree.findPath("", "|"));

        //expand node	    
        tree.expandPath(tree.findPath("", "|"));

        //select node
        tree.selectPath(tree.findPath("EmptyWebPhotosProject", "|"));
        
        //Assertions
        //XXX: Assertions not working
        //assertEquals(1, tree.getChildCount("EmptyWebPhotosProject"));

        //TreePath selectedPath = tree.findPath("EmptyWebPhotosProject|webphotos.properties", "|");
        //DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) selectedPath.getLastPathComponent()).getUserObject();

        //assertEquals("webphotos.properties", selectedNode);
    }
}
