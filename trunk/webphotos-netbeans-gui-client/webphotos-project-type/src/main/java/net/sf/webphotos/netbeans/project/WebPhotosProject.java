/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.webphotos.netbeans.project;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.CopyOperationImplementation;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Guilhe
 */
public class WebPhotosProject implements Project {

    private final FileObject projectDirectory;
    private final ProjectState state;
    private Lookup lkp;

    public WebPhotosProject(FileObject projectDir, ProjectState state) {
        this.projectDirectory = projectDir;
        this.state = state;
    }

    FileObject getFolder(int folder, boolean create) {
        FileObject result =
                projectDirectory.getFileObject(WebPhotosProjectFactory.PROJECT_ARCHIVES[folder]).getParent();
        if (result == null && create) {
            try {
                result = projectDirectory.createData(WebPhotosProjectFactory.PROJECT_ARCHIVES[folder]).getParent();
            } catch (IOException ioe) {
                Exceptions.printStackTrace(ioe);
            }
        }
        return result;
    }

    public FileObject getProjectDirectory() {
        return projectDirectory;
    }

    public Lookup getLookup() {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (lkp == null) {
            lkp = Lookups.fixed(new Object[]{
                        state, //allow outside code to mark the project as needing saving
                        new ActionProviderImpl(), //Provides standard actions like Build and Clean
                        new WebPhotosDeleteOperation(),
                        new WebPhotosCopyOperation(this),
                        new WebPhotosProjectInformation(), //Project information implementation
                        new WebPhotosProjectLogicalView(this), //Logical view of project implementation
                    });
        }
        return lkp;
    }

    private final class ActionProviderImpl implements ActionProvider {

        private String[] supported = new String[]{
            ActionProvider.COMMAND_DELETE,
            ActionProvider.COMMAND_COPY
        };

        public String[] getSupportedActions() {
            return supported;
        }

        public void invokeAction(String action, Lookup lookup) throws IllegalArgumentException {
            if (action.equalsIgnoreCase(ActionProvider.COMMAND_DELETE)) {
                DefaultProjectOperations.performDefaultDeleteOperation(WebPhotosProject.this);
            } else if (action.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(WebPhotosProject.this);
            }
        }

        public boolean isActionEnabled(String command, Lookup lookup) throws IllegalArgumentException {
            if ((command.equals(ActionProvider.COMMAND_DELETE))) {
                return true;
            } else if ((command.equals(ActionProvider.COMMAND_COPY))) {
                return true;
            } else {
                throw new IllegalArgumentException(command);
            }
        }
    }

    private final class WebPhotosDeleteOperation implements DeleteOperationImplementation {

        public void notifyDeleting() throws IOException {
        }

        public void notifyDeleted() throws IOException {
        }

        public List<FileObject> getMetadataFiles() {
            List<FileObject> dataFiles = new ArrayList<FileObject>();
            return dataFiles;
        }

        public List<FileObject> getDataFiles() {
            List<FileObject> dataFiles = new ArrayList<FileObject>();
            return dataFiles;
        }
    }

    private class WebPhotosCopyOperation implements CopyOperationImplementation {

        private final WebPhotosProject project;
        private final FileObject projectDir;

        public WebPhotosCopyOperation(WebPhotosProject project) {
            this.project = project;
            this.projectDir = project.getProjectDirectory();
        }

        public void notifyCopying() throws IOException {
        }

        public void notifyCopied(Project arg0, File arg1, String arg2) throws IOException {
        }

        public List<FileObject> getMetadataFiles() {
            return Collections.EMPTY_LIST;
        }

        public List<FileObject> getDataFiles() {
            return Collections.EMPTY_LIST;
        }
    }

    private final class WebPhotosProjectInformation implements ProjectInformation {

        public String getName() {
            return getProjectDirectory().getName();
        }

        public String getDisplayName() {
            return getName();
        }

        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(
                    Constants.PROJECT_ICON));
        }

        public Project getProject() {
            return WebPhotosProject.this;
        }

        public void addPropertyChangeListener(PropertyChangeListener arg0) {
            /** Do nothing */
            assert arg0 != null : arg0;
        }

        public void removePropertyChangeListener(PropertyChangeListener arg0) {
            /** Do nothing */
            assert arg0 != null : arg0;
        }
    }
}
