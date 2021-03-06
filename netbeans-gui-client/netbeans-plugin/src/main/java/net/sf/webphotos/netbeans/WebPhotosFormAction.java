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
package net.sf.webphotos.netbeans;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Action which shows WebPhotosForm component.
 */
@ActionID(id = "net.sf.webphotos.netbeans.WebPhotosFormAction", category = "Window")
@ActionRegistration(lazy = false, displayName = "#CTL_WebPhotosFormAction")
@ActionReference(path = "Menu/Window", name = "WebPhotosFormAction")
@Messages("CTL_WebPhotosFormAction=WebPhotosForm")
public class WebPhotosFormAction extends AbstractAction {

    public WebPhotosFormAction() {
        super(NbBundle.getMessage(WebPhotosFormAction.class, "CTL_WebPhotosFormAction"));
        putValue(SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(WebPhotosFormTopComponent.ICON_PATH, true)));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        TopComponent win = WebPhotosFormTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
