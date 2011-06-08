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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.webphotos.netbeans.project;

import java.io.IOException;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Guilhe
 */
public class WebPhotosProjectFactory implements ProjectFactory {

    public static final String[] PROJECT_ARCHIVES;

    static {
        PROJECT_ARCHIVES = new String[]{
                    "config/webphotos.properties",
                    "albuns/webphotos.xml"
                };
    }

    /**
     * Determine if this directory is a WebPhotos Project looking up for files
     * that must be present.
     * @param projectDirectory chosen directory
     * @return
     */
    public boolean isProject(FileObject projectDirectory) {
        boolean isProject = true;
        for (String file : PROJECT_ARCHIVES) {
            isProject = (isProject && projectDirectory.getFileObject(file) != null);
        }
        return isProject;
    }

    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject(dir) ? new WebPhotosProject(dir, state) : null;
    }

    public void saveProject(Project project) throws IOException, ClassCastException {
        if (!isProject(project.getProjectDirectory())) {
            throw new IOException("Project dir " + project.getProjectDirectory().getPath() +
                    " deleted," +
                    " cannot save project");
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
