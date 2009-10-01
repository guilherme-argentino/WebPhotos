/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.webphotos.netbeans;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Action which shows WebPhotosForm component.
 */
public class WebPhotosFormAction extends AbstractAction {

    public WebPhotosFormAction() {
        super(NbBundle.getMessage(WebPhotosFormAction.class, "CTL_WebPhotosFormAction"));
        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(WebPhotosFormTopComponent.ICON_PATH, true)));
    }

    public void actionPerformed(ActionEvent evt) {
        TopComponent win = WebPhotosFormTopComponent.findInstance();
        win.open();
        win.requestActive();
    }
}
