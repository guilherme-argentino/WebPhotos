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
package net.sf.webphotos.gui.util.fest;

import java.awt.Dialog;
import java.awt.Frame;
import static java.awt.event.KeyEvent.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import javax.swing.JButton;
import net.sf.webphotos.WebPhotos;
import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.ComponentLookupScope;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.finder.JFileChooserFinder;
import static org.fest.swing.finder.WindowFinder.*;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JButtonFixture;
import org.fest.swing.fixture.JFileChooserFixture;
import static org.fest.swing.launcher.ApplicationLauncher.*;
import org.fest.swing.security.NoExitSecurityManagerInstaller;

/**
 *
 * @author Guilherme
 */
public class WebPhotosDelegate implements net.sf.webphotos.gui.util.WebPhotosDelegate {

    private static final WebPhotosDelegate WEB_PHOTOS_DELEGATE = new WebPhotosDelegate();
    private static final Robot robot;
    private static final int TIMEOUT_SECONDS = 10;
    

    private static final Comparator<String> IGNORE_CASE_COMPARATOR = new Comparator<String>() {

        @Override
        public int compare(String o1, String o2) {
            System.out.println(o1+".compareToIgnoreCase("+o2+") = " + o1.compareToIgnoreCase(o2));
            return o1.compareToIgnoreCase(o2);
        }
    };
    
    private FrameFixture mainFrame;
    private NoExitSecurityManagerInstaller noExitSecurityManagerInstaller;

    static {
        robot = BasicRobot.robotWithCurrentAwtHierarchy();
        robot.settings().delayBetweenEvents(50);
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
    }

    @Override
    public void setUpClass() throws Exception {
        
        noExitSecurityManagerInstaller = NoExitSecurityManagerInstaller.installNoExitSecurityManager();

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                application(WebPhotos.class).start();
            }
        });

        DialogFixture dialog = findDialog(new LoginDialogTypeMatcherImpl("Web")).withTimeout(TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS).using(robot);

        dialog.button(new GenericTypeMatcher<JButton>(JButton.class) {
            @Override
            protected boolean isMatching(JButton okButton) {
                return "OK".equalsIgnoreCase(okButton.getText()) && okButton.isShowing();
            }
        }).click(MouseButton.LEFT_BUTTON);

        mainFrame = findFrame(new MainFrameTypeMatcherImpl("Web")).withTimeout(TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS).using(robot);

    }

    @Override
    public void addPhotosToAlbumAndCheck(String[] fileNames, String buttonName, File folderNameToVerify, String fileDialogName) throws RuntimeException {
        JButtonFixture button = clickButton(buttonName);
        JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser().withTimeout(TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS).using(button.robot);

        fileChooser.component().getCurrentDirectory().equals(folderNameToVerify);
        
        File[] files = new File[fileNames.length];
        for (int i = 0; i < files.length; i++) {
            files[i] = new File(fileNames[i]);
        }
        fileChooser.selectFiles(files).pressKey(VK_ENTER);
        
    }

    @Override
    public void checkButtonHasText(String button, String textShown) throws RuntimeException {
        assertThat(mainFrame.button(button).text().contains(textShown)).isTrue();
    }

    @Override
    public void checkComboBoxHasText(String comboBoxName, String textShown) throws RuntimeException {
        System.out.println("mainFrame.comboBox("+comboBoxName+").contents()" + mainFrame.comboBox(comboBoxName).contents().toString());
        Arrays.sort(mainFrame.comboBox(comboBoxName).contents(), IGNORE_CASE_COMPARATOR);
        assertThat(Arrays.binarySearch(mainFrame.comboBox(comboBoxName).contents(), textShown, IGNORE_CASE_COMPARATOR) >= 0).isTrue();
    }

    @Override
    public void checkIsButtonEnabled(String button) throws RuntimeException {
        assertThat(mainFrame.button(button).component().isVisible()).isTrue();
        assertThat(mainFrame.button(button).component().isEnabled()).isTrue();
    }

    @Override
    public void fillModalWithText(final String buttonName, String modalTittle, String data) throws RuntimeException {
        JButtonFixture button = clickButton(buttonName);

        DialogFixture dialog = findDialog(new LoginDialogTypeMatcherImpl(modalTittle)).withTimeout(TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS).using(button.robot);

        dialog.textBox().setText(data);
        dialog.pressKey(VK_ENTER);
    }

    @Override
    public void tearDownClass() throws Exception {
        System.out.println("Shutting down FEST");
        noExitSecurityManagerInstaller.uninstall();
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

    private JButtonFixture clickButton(final String buttonName) {
        JButtonFixture button = mainFrame.button(new JButtonTypeMatcherImpl(JButton.class, buttonName)).click(MouseButton.LEFT_BUTTON);
        return button;
    }

    @Override
    public void fillAlbumForm(String albumTitle, String albumDescription, String categoryName, Map<String, String[]> photoData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void checkNewAlbum() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static class LoginDialogTypeMatcherImpl extends GenericTypeMatcher<Dialog> {

        private String dialogName;

        public LoginDialogTypeMatcherImpl(final String dialogName) {
            super(Dialog.class);
            this.dialogName = dialogName;
        }

        @Override
        protected boolean isMatching(Dialog frame) {
            return frame.getTitle().contains(dialogName) && frame.isShowing();
        }
    }

    private static class MainFrameTypeMatcherImpl extends GenericTypeMatcher<Frame> {

        private String frameName;

        public MainFrameTypeMatcherImpl(final String frameName) {
            super(Frame.class);
            this.frameName = frameName;
        }

        @Override
        protected boolean isMatching(Frame frame) {
            return frame.getTitle().contains(frameName) && frame.isShowing();
        }
    }

    private static class JButtonTypeMatcherImpl extends GenericTypeMatcher<JButton> {

        private final String buttonName;

        public JButtonTypeMatcherImpl(Class<JButton> supportedType, String buttonName) {
            super(supportedType);
            this.buttonName = buttonName;
        }

        @Override
        protected boolean isMatching(JButton chosenButtonName) {
            return buttonName.equalsIgnoreCase(chosenButtonName.getName());
        }
    }
}
