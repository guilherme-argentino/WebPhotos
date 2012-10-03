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
package net.sf.webphotos.netbeans;

import junit.framework.Test;
import org.junit.Ignore;
import org.netbeans.jellytools.JellyTestCase;
import org.netbeans.jellytools.TopComponentOperator;
import org.netbeans.jellytools.actions.Action;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbModuleSuite.Configuration;

/**
 *
 * @author Guilherme
 */
@Ignore
public class WebPhotosTest extends JellyTestCase {

    /**
     * Constructor required by JUnit
     */
    public WebPhotosTest(String name) {
        super(name);
    }

    /**
     * Creates suite from particular test cases. You can define order of test
     * cases here.
     */
    public static Test suite() {
        Configuration testConfig = NbModuleSuite.createConfiguration(WebPhotosTest.class);
        testConfig = testConfig.addTest("testNewProjectButton", "testNewProjectAction");
        testConfig = testConfig.clusters(".*").enableModules(".*");
        return testConfig.suite();
    }

    /**
     * Called before every test case.
     */
    @Override
    public void setUp() {
        System.out.println("########  " + getName() + "  #######");
    }

    /**
     * Test brush size setting.
     */
    public void testNewProjectAction() {
        new Action("File|New Project", null).perform();
    }

    /**
     * Test new button.
     */
    public void testNewProjectButton() {
        new JButtonOperator(new TopComponentOperator("Image"), "New Project").push();
    }
}
