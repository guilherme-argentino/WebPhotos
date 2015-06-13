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

import org.netbeans.api.annotations.common.StaticResource;

/**
 *
 * @author WebPhotos
 */
public interface Constants {

    @StaticResource
    public static final String PROJECT_ICON = "net/sf/webphotos/netbeans/project/projectIcon.png";

    public interface ProjectFactory {

        public static final String CONFIG_WEBPHOTOS_PROPERTIES = "config/webphotos.properties";
        public static final String ALBUNS_WEBPHOTOS_XML = "albuns/webphotos.xml";
        public static final String EMPTY_WEBPHOTOS_PROJECT_WIZARD_PANEL = "net.sf.webphotos.netbeans.project.empty.EmptyWebPhotosProjectWizardPanel";
        
        public interface EmptyProject {
            
            public static final String PROJECT_DISPLAY_MANE = "net.sf.webphotos.netbeans.project.Bundle#Templates/Project/WebPhotos/EmptyWebPhotosProjectProject.zip";
            @StaticResource
            public static final String PROJECT_ICON_BASE = "net/sf/webphotos/netbeans/project/projectIcon.png";
            public static final String PROJECT_FOLDER = "Project/WebPhotos";
            public static final String PROJECT_DESCRIPTION = "/net/sf/webphotos/netbeans/project/empty/EmptyWebPhotosProjectDescription.html";
            public static final String PROJECT_CONTENT = "/net/sf/webphotos/netbeans/project/EmptyWebPhotosProjectProject.zip";
            
        }
 
    }
}
