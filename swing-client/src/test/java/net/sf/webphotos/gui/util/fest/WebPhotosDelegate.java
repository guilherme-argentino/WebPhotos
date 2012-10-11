/*
 * Copyright 2012 Guilherme.
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
package net.sf.webphotos.gui.util.fest;

import java.awt.Dialog;
import java.awt.Frame;
import java.io.File;
import java.util.Arrays;
import javax.swing.JButton;
import net.sf.webphotos.WebPhotos;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.ComponentLookupScope;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import static org.fest.swing.finder.WindowFinder.*;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import static org.fest.swing.launcher.ApplicationLauncher.*;

import static org.fest.assertions.Assertions.assertThat;
import org.fest.assertions.Condition;

/**
 *
 * @author Guilherme
 */
public class WebPhotosDelegate implements net.sf.webphotos.gui.util.WebPhotosDelegate {

    private static final WebPhotosDelegate WEB_PHOTOS_DELEGATE = new WebPhotosDelegate();
    private static final Robot robot;
    private static FrameFixture mainFrame;

    static {
        robot = BasicRobot.robotWithCurrentAwtHierarchy();
        robot.settings().delayBetweenEvents(50);
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
    }

    @Override
    public void setUpClass() throws Exception {

//        FailOnThreadViolationRepaintManager.install();

//        ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();
//        JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane().withTimeout(10000).using(robot);

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                application(WebPhotos.class).start();
            }
        });

        DialogFixture dialog = findDialog(new LoginDialogTypeMatcherImpl()).withTimeout(10, java.util.concurrent.TimeUnit.SECONDS).using(robot);

        dialog.button(new GenericTypeMatcher<JButton>(JButton.class){

            @Override
            protected boolean isMatching(JButton okButton) {
                return "OK".equalsIgnoreCase(okButton.getText()) && okButton.isShowing();
            }
        
        }).click(MouseButton.LEFT_BUTTON);

        mainFrame =
                findFrame(new MainFrameTypeMatcherImpl()).withTimeout(20, java.util.concurrent.TimeUnit.SECONDS).using(robot);
        
    }

    @Override
    public void addPhotosToAlbumAndCheck(String[] fileNames, String buttonName, File folderNameToVerify, String fileDialogName) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkButtonHasText(String button, String textShown) throws RuntimeException {
        assertThat(mainFrame.button(button).text().contains(textShown)).isTrue();
    }

    @Override
    public void checkComboBoxHasText(String comboBoxName, String textShown) throws RuntimeException {
        assertThat(Arrays.binarySearch(mainFrame.comboBox(comboBoxName).contents(), textShown) > 0).isTrue();
    }

    @Override
    public void checkIsButtonEnabled(String button) throws RuntimeException {
        assertThat(mainFrame.button(button).component().isVisible()).isTrue();
        assertThat(mainFrame.button(button).component().isEnabled()).isTrue();
    }

    @Override
    public void fillModalWithText(String buttonName, String modalTittle, String data) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void tearDownClass() throws Exception {
        mainFrame.close();
    }

    @Override
    public void validateMainWindowIsPresent() {
        assertThat(mainFrame.component().isVisible()).isTrue();
        assertThat(mainFrame.component().isEnabled()).isTrue();
    }

    /**
     * Setup for One Test
     */
    @Override
    public void setUp() {
        
    }

    /**
     * Returns the Delegate
     *
     * @return
     */
    public static WebPhotosDelegate getWebPhotosDelegate() {
        return WEB_PHOTOS_DELEGATE;
    }

    private static class LoginDialogTypeMatcherImpl extends GenericTypeMatcher<Dialog> {

        public LoginDialogTypeMatcherImpl() {
            super(Dialog.class);
        }

        @Override
        protected boolean isMatching(Dialog frame) {
            return frame.getTitle().contains("Web") && frame.isShowing();
        }
    }

    private static class MainFrameTypeMatcherImpl extends GenericTypeMatcher<Frame> {

        public MainFrameTypeMatcherImpl() {
            super(Frame.class);
        }

        @Override
        protected boolean isMatching(Frame frame) {
            return frame.getTitle().contains("Web") && frame.isShowing();
        }
    }
}
