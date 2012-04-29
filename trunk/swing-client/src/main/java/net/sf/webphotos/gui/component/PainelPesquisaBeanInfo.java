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
package net.sf.webphotos.gui.component;

import java.beans.*;

/**
 *
 * @author Guilherme
 */
public class PainelPesquisaBeanInfo extends SimpleBeanInfo {

    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( net.sf.webphotos.gui.component.PainelPesquisa.class , null ); // NOI18N
        beanDescriptor.setShortDescription ( "Controls to search on a datasource" );//GEN-HEADEREND:BeanDescriptor

        // Here you can add code for customizing the BeanDescriptor.

        return beanDescriptor;     }//GEN-LAST:BeanDescriptor
    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_acaoPesquisa = 0;
    private static final int PROPERTY_actionMap = 1;
    private static final int PROPERTY_alignmentX = 2;
    private static final int PROPERTY_alignmentY = 3;
    private static final int PROPERTY_ancestorListeners = 4;
    private static final int PROPERTY_autoscrolls = 5;
    private static final int PROPERTY_background = 6;
    private static final int PROPERTY_backgroundSet = 7;
    private static final int PROPERTY_baselineResizeBehavior = 8;
    private static final int PROPERTY_border = 9;
    private static final int PROPERTY_bounds = 10;
    private static final int PROPERTY_categoriasPesquisaComboBoxModel = 11;
    private static final int PROPERTY_colorModel = 12;
    private static final int PROPERTY_component = 13;
    private static final int PROPERTY_componentCount = 14;
    private static final int PROPERTY_componentListeners = 15;
    private static final int PROPERTY_componentOrientation = 16;
    private static final int PROPERTY_componentPopupMenu = 17;
    private static final int PROPERTY_components = 18;
    private static final int PROPERTY_containerListeners = 19;
    private static final int PROPERTY_cursor = 20;
    private static final int PROPERTY_cursorSet = 21;
    private static final int PROPERTY_debugGraphicsOptions = 22;
    private static final int PROPERTY_displayable = 23;
    private static final int PROPERTY_doubleBuffered = 24;
    private static final int PROPERTY_dropTarget = 25;
    private static final int PROPERTY_enabled = 26;
    private static final int PROPERTY_focusable = 27;
    private static final int PROPERTY_focusCycleRoot = 28;
    private static final int PROPERTY_focusCycleRootAncestor = 29;
    private static final int PROPERTY_focusListeners = 30;
    private static final int PROPERTY_focusOwner = 31;
    private static final int PROPERTY_focusTraversable = 32;
    private static final int PROPERTY_focusTraversalKeys = 33;
    private static final int PROPERTY_focusTraversalKeysEnabled = 34;
    private static final int PROPERTY_focusTraversalPolicy = 35;
    private static final int PROPERTY_focusTraversalPolicyProvider = 36;
    private static final int PROPERTY_focusTraversalPolicySet = 37;
    private static final int PROPERTY_font = 38;
    private static final int PROPERTY_fontSet = 39;
    private static final int PROPERTY_foreground = 40;
    private static final int PROPERTY_foregroundSet = 41;
    private static final int PROPERTY_graphics = 42;
    private static final int PROPERTY_graphicsConfiguration = 43;
    private static final int PROPERTY_height = 44;
    private static final int PROPERTY_hierarchyBoundsListeners = 45;
    private static final int PROPERTY_hierarchyListeners = 46;
    private static final int PROPERTY_ignoreRepaint = 47;
    private static final int PROPERTY_inheritsPopupMenu = 48;
    private static final int PROPERTY_inputContext = 49;
    private static final int PROPERTY_inputMap = 50;
    private static final int PROPERTY_inputMethodListeners = 51;
    private static final int PROPERTY_inputMethodRequests = 52;
    private static final int PROPERTY_inputVerifier = 53;
    private static final int PROPERTY_insets = 54;
    private static final int PROPERTY_keyListeners = 55;
    private static final int PROPERTY_layout = 56;
    private static final int PROPERTY_lightweight = 57;
    private static final int PROPERTY_locale = 58;
    private static final int PROPERTY_location = 59;
    private static final int PROPERTY_locationOnScreen = 60;
    private static final int PROPERTY_managingFocus = 61;
    private static final int PROPERTY_maximumSize = 62;
    private static final int PROPERTY_maximumSizeSet = 63;
    private static final int PROPERTY_minimumSize = 64;
    private static final int PROPERTY_minimumSizeSet = 65;
    private static final int PROPERTY_mouseListeners = 66;
    private static final int PROPERTY_mouseMotionListeners = 67;
    private static final int PROPERTY_mousePosition = 68;
    private static final int PROPERTY_mouseWheelListeners = 69;
    private static final int PROPERTY_name = 70;
    private static final int PROPERTY_nextFocusableComponent = 71;
    private static final int PROPERTY_opaque = 72;
    private static final int PROPERTY_optimizedDrawingEnabled = 73;
    private static final int PROPERTY_paintingForPrint = 74;
    private static final int PROPERTY_paintingTile = 75;
    private static final int PROPERTY_parent = 76;
    private static final int PROPERTY_peer = 77;
    private static final int PROPERTY_preferredSize = 78;
    private static final int PROPERTY_preferredSizeSet = 79;
    private static final int PROPERTY_propertyChangeListeners = 80;
    private static final int PROPERTY_registeredKeyStrokes = 81;
    private static final int PROPERTY_requestFocusEnabled = 82;
    private static final int PROPERTY_rootPane = 83;
    private static final int PROPERTY_showing = 84;
    private static final int PROPERTY_size = 85;
    private static final int PROPERTY_toolkit = 86;
    private static final int PROPERTY_toolTipText = 87;
    private static final int PROPERTY_topLevelAncestor = 88;
    private static final int PROPERTY_transferHandler = 89;
    private static final int PROPERTY_treeLock = 90;
    private static final int PROPERTY_UI = 91;
    private static final int PROPERTY_UIClassID = 92;
    private static final int PROPERTY_valid = 93;
    private static final int PROPERTY_validateRoot = 94;
    private static final int PROPERTY_verifyInputWhenFocusTarget = 95;
    private static final int PROPERTY_vetoableChangeListeners = 96;
    private static final int PROPERTY_visible = 97;
    private static final int PROPERTY_visibleRect = 98;
    private static final int PROPERTY_width = 99;
    private static final int PROPERTY_x = 100;
    private static final int PROPERTY_y = 101;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[102];
    
        try {
            properties[PROPERTY_acaoPesquisa] = new PropertyDescriptor ( "acaoPesquisa", net.sf.webphotos.gui.component.PainelPesquisa.class, "getAcaoPesquisa", "setAcaoPesquisa" ); // NOI18N
            properties[PROPERTY_acaoPesquisa].setPreferred ( true );
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", net.sf.webphotos.gui.component.PainelPesquisa.class, "getActionMap", "setActionMap" ); // NOI18N
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", net.sf.webphotos.gui.component.PainelPesquisa.class, "getAlignmentX", "setAlignmentX" ); // NOI18N
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", net.sf.webphotos.gui.component.PainelPesquisa.class, "getAlignmentY", "setAlignmentY" ); // NOI18N
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getAncestorListeners", null ); // NOI18N
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", net.sf.webphotos.gui.component.PainelPesquisa.class, "getAutoscrolls", "setAutoscrolls" ); // NOI18N
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", net.sf.webphotos.gui.component.PainelPesquisa.class, "getBackground", "setBackground" ); // NOI18N
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isBackgroundSet", null ); // NOI18N
            properties[PROPERTY_baselineResizeBehavior] = new PropertyDescriptor ( "baselineResizeBehavior", net.sf.webphotos.gui.component.PainelPesquisa.class, "getBaselineResizeBehavior", null ); // NOI18N
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", net.sf.webphotos.gui.component.PainelPesquisa.class, "getBorder", "setBorder" ); // NOI18N
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", net.sf.webphotos.gui.component.PainelPesquisa.class, "getBounds", "setBounds" ); // NOI18N
            properties[PROPERTY_categoriasPesquisaComboBoxModel] = new PropertyDescriptor ( "categoriasPesquisaComboBoxModel", net.sf.webphotos.gui.component.PainelPesquisa.class, "getCategoriasPesquisaComboBoxModel", "setCategoriasPesquisaComboBoxModel" ); // NOI18N
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", net.sf.webphotos.gui.component.PainelPesquisa.class, "getColorModel", null ); // NOI18N
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", net.sf.webphotos.gui.component.PainelPesquisa.class, null, null, "getComponent", null ); // NOI18N
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", net.sf.webphotos.gui.component.PainelPesquisa.class, "getComponentCount", null ); // NOI18N
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getComponentListeners", null ); // NOI18N
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", net.sf.webphotos.gui.component.PainelPesquisa.class, "getComponentOrientation", "setComponentOrientation" ); // NOI18N
            properties[PROPERTY_componentPopupMenu] = new PropertyDescriptor ( "componentPopupMenu", net.sf.webphotos.gui.component.PainelPesquisa.class, "getComponentPopupMenu", "setComponentPopupMenu" ); // NOI18N
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", net.sf.webphotos.gui.component.PainelPesquisa.class, "getComponents", null ); // NOI18N
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getContainerListeners", null ); // NOI18N
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", net.sf.webphotos.gui.component.PainelPesquisa.class, "getCursor", "setCursor" ); // NOI18N
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isCursorSet", null ); // NOI18N
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", net.sf.webphotos.gui.component.PainelPesquisa.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" ); // NOI18N
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", net.sf.webphotos.gui.component.PainelPesquisa.class, "isDisplayable", null ); // NOI18N
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", net.sf.webphotos.gui.component.PainelPesquisa.class, "isDoubleBuffered", "setDoubleBuffered" ); // NOI18N
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", net.sf.webphotos.gui.component.PainelPesquisa.class, "getDropTarget", "setDropTarget" ); // NOI18N
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", net.sf.webphotos.gui.component.PainelPesquisa.class, "isEnabled", "setEnabled" ); // NOI18N
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", net.sf.webphotos.gui.component.PainelPesquisa.class, "isFocusable", "setFocusable" ); // NOI18N
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", net.sf.webphotos.gui.component.PainelPesquisa.class, "isFocusCycleRoot", "setFocusCycleRoot" ); // NOI18N
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", net.sf.webphotos.gui.component.PainelPesquisa.class, "getFocusCycleRootAncestor", null ); // NOI18N
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getFocusListeners", null ); // NOI18N
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", net.sf.webphotos.gui.component.PainelPesquisa.class, "isFocusOwner", null ); // NOI18N
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", net.sf.webphotos.gui.component.PainelPesquisa.class, "isFocusTraversable", null ); // NOI18N
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", net.sf.webphotos.gui.component.PainelPesquisa.class, null, null, null, "setFocusTraversalKeys" ); // NOI18N
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", net.sf.webphotos.gui.component.PainelPesquisa.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", net.sf.webphotos.gui.component.PainelPesquisa.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicyProvider] = new PropertyDescriptor ( "focusTraversalPolicyProvider", net.sf.webphotos.gui.component.PainelPesquisa.class, "isFocusTraversalPolicyProvider", "setFocusTraversalPolicyProvider" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isFocusTraversalPolicySet", null ); // NOI18N
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", net.sf.webphotos.gui.component.PainelPesquisa.class, "getFont", "setFont" ); // NOI18N
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isFontSet", null ); // NOI18N
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", net.sf.webphotos.gui.component.PainelPesquisa.class, "getForeground", "setForeground" ); // NOI18N
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isForegroundSet", null ); // NOI18N
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", net.sf.webphotos.gui.component.PainelPesquisa.class, "getGraphics", null ); // NOI18N
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", net.sf.webphotos.gui.component.PainelPesquisa.class, "getGraphicsConfiguration", null ); // NOI18N
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", net.sf.webphotos.gui.component.PainelPesquisa.class, "getHeight", null ); // NOI18N
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getHierarchyBoundsListeners", null ); // NOI18N
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getHierarchyListeners", null ); // NOI18N
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", net.sf.webphotos.gui.component.PainelPesquisa.class, "getIgnoreRepaint", "setIgnoreRepaint" ); // NOI18N
            properties[PROPERTY_inheritsPopupMenu] = new PropertyDescriptor ( "inheritsPopupMenu", net.sf.webphotos.gui.component.PainelPesquisa.class, "getInheritsPopupMenu", "setInheritsPopupMenu" ); // NOI18N
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", net.sf.webphotos.gui.component.PainelPesquisa.class, "getInputContext", null ); // NOI18N
            properties[PROPERTY_inputMap] = new PropertyDescriptor ( "inputMap", net.sf.webphotos.gui.component.PainelPesquisa.class, "getInputMap", null ); // NOI18N
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getInputMethodListeners", null ); // NOI18N
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", net.sf.webphotos.gui.component.PainelPesquisa.class, "getInputMethodRequests", null ); // NOI18N
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", net.sf.webphotos.gui.component.PainelPesquisa.class, "getInputVerifier", "setInputVerifier" ); // NOI18N
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", net.sf.webphotos.gui.component.PainelPesquisa.class, "getInsets", null ); // NOI18N
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getKeyListeners", null ); // NOI18N
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", net.sf.webphotos.gui.component.PainelPesquisa.class, "getLayout", "setLayout" ); // NOI18N
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", net.sf.webphotos.gui.component.PainelPesquisa.class, "isLightweight", null ); // NOI18N
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", net.sf.webphotos.gui.component.PainelPesquisa.class, "getLocale", "setLocale" ); // NOI18N
            properties[PROPERTY_location] = new PropertyDescriptor ( "location", net.sf.webphotos.gui.component.PainelPesquisa.class, "getLocation", "setLocation" ); // NOI18N
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", net.sf.webphotos.gui.component.PainelPesquisa.class, "getLocationOnScreen", null ); // NOI18N
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", net.sf.webphotos.gui.component.PainelPesquisa.class, "isManagingFocus", null ); // NOI18N
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", net.sf.webphotos.gui.component.PainelPesquisa.class, "getMaximumSize", "setMaximumSize" ); // NOI18N
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isMaximumSizeSet", null ); // NOI18N
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", net.sf.webphotos.gui.component.PainelPesquisa.class, "getMinimumSize", "setMinimumSize" ); // NOI18N
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isMinimumSizeSet", null ); // NOI18N
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getMouseListeners", null ); // NOI18N
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getMouseMotionListeners", null ); // NOI18N
            properties[PROPERTY_mousePosition] = new PropertyDescriptor ( "mousePosition", net.sf.webphotos.gui.component.PainelPesquisa.class, "getMousePosition", null ); // NOI18N
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getMouseWheelListeners", null ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", net.sf.webphotos.gui.component.PainelPesquisa.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", net.sf.webphotos.gui.component.PainelPesquisa.class, "getNextFocusableComponent", "setNextFocusableComponent" ); // NOI18N
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", net.sf.webphotos.gui.component.PainelPesquisa.class, "isOpaque", "setOpaque" ); // NOI18N
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", net.sf.webphotos.gui.component.PainelPesquisa.class, "isOptimizedDrawingEnabled", null ); // NOI18N
            properties[PROPERTY_paintingForPrint] = new PropertyDescriptor ( "paintingForPrint", net.sf.webphotos.gui.component.PainelPesquisa.class, "isPaintingForPrint", null ); // NOI18N
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", net.sf.webphotos.gui.component.PainelPesquisa.class, "isPaintingTile", null ); // NOI18N
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", net.sf.webphotos.gui.component.PainelPesquisa.class, "getParent", null ); // NOI18N
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", net.sf.webphotos.gui.component.PainelPesquisa.class, "getPeer", null ); // NOI18N
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", net.sf.webphotos.gui.component.PainelPesquisa.class, "getPreferredSize", "setPreferredSize" ); // NOI18N
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", net.sf.webphotos.gui.component.PainelPesquisa.class, "isPreferredSizeSet", null ); // NOI18N
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getPropertyChangeListeners", null ); // NOI18N
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", net.sf.webphotos.gui.component.PainelPesquisa.class, "getRegisteredKeyStrokes", null ); // NOI18N
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", net.sf.webphotos.gui.component.PainelPesquisa.class, "isRequestFocusEnabled", "setRequestFocusEnabled" ); // NOI18N
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", net.sf.webphotos.gui.component.PainelPesquisa.class, "getRootPane", null ); // NOI18N
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", net.sf.webphotos.gui.component.PainelPesquisa.class, "isShowing", null ); // NOI18N
            properties[PROPERTY_size] = new PropertyDescriptor ( "size", net.sf.webphotos.gui.component.PainelPesquisa.class, "getSize", "setSize" ); // NOI18N
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", net.sf.webphotos.gui.component.PainelPesquisa.class, "getToolkit", null ); // NOI18N
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", net.sf.webphotos.gui.component.PainelPesquisa.class, "getToolTipText", "setToolTipText" ); // NOI18N
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", net.sf.webphotos.gui.component.PainelPesquisa.class, "getTopLevelAncestor", null ); // NOI18N
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", net.sf.webphotos.gui.component.PainelPesquisa.class, "getTransferHandler", "setTransferHandler" ); // NOI18N
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", net.sf.webphotos.gui.component.PainelPesquisa.class, "getTreeLock", null ); // NOI18N
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", net.sf.webphotos.gui.component.PainelPesquisa.class, "getUI", "setUI" ); // NOI18N
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", net.sf.webphotos.gui.component.PainelPesquisa.class, "getUIClassID", null ); // NOI18N
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", net.sf.webphotos.gui.component.PainelPesquisa.class, "isValid", null ); // NOI18N
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", net.sf.webphotos.gui.component.PainelPesquisa.class, "isValidateRoot", null ); // NOI18N
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", net.sf.webphotos.gui.component.PainelPesquisa.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" ); // NOI18N
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", net.sf.webphotos.gui.component.PainelPesquisa.class, "getVetoableChangeListeners", null ); // NOI18N
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", net.sf.webphotos.gui.component.PainelPesquisa.class, "isVisible", "setVisible" ); // NOI18N
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", net.sf.webphotos.gui.component.PainelPesquisa.class, "getVisibleRect", null ); // NOI18N
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", net.sf.webphotos.gui.component.PainelPesquisa.class, "getWidth", null ); // NOI18N
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", net.sf.webphotos.gui.component.PainelPesquisa.class, "getX", null ); // NOI18N
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", net.sf.webphotos.gui.component.PainelPesquisa.class, "getY", null ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties

        // Here you can add code for customizing the properties array.

        return properties;     }//GEN-LAST:Properties
    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_ancestorListener = 0;
    private static final int EVENT_componentListener = 1;
    private static final int EVENT_containerListener = 2;
    private static final int EVENT_focusListener = 3;
    private static final int EVENT_hierarchyBoundsListener = 4;
    private static final int EVENT_hierarchyListener = 5;
    private static final int EVENT_inputMethodListener = 6;
    private static final int EVENT_keyListener = 7;
    private static final int EVENT_mouseListener = 8;
    private static final int EVENT_mouseMotionListener = 9;
    private static final int EVENT_mouseWheelListener = 10;
    private static final int EVENT_propertyChangeListener = 11;
    private static final int EVENT_vetoableChangeListener = 12;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[13];
    
        try {
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorRemoved", "ancestorMoved"}, "addAncestorListener", "removeAncestorListener" ); // NOI18N
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentResized", "componentMoved", "componentShown", "componentHidden"}, "addComponentListener", "removeComponentListener" ); // NOI18N
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" ); // NOI18N
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" ); // NOI18N
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" ); // NOI18N
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" ); // NOI18N
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"inputMethodTextChanged", "caretPositionChanged"}, "addInputMethodListener", "removeInputMethodListener" ); // NOI18N
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyTyped", "keyPressed", "keyReleased"}, "addKeyListener", "removeKeyListener" ); // NOI18N
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited"}, "addMouseListener", "removeMouseListener" ); // NOI18N
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" ); // NOI18N
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" ); // NOI18N
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" ); // NOI18N
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( net.sf.webphotos.gui.component.PainelPesquisa.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Events

        // Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events
    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_action0 = 0;
    private static final int METHOD_add1 = 1;
    private static final int METHOD_addNotify2 = 2;
    private static final int METHOD_addPropertyChangeListener3 = 3;
    private static final int METHOD_applyComponentOrientation4 = 4;
    private static final int METHOD_areFocusTraversalKeysSet5 = 5;
    private static final int METHOD_bounds6 = 6;
    private static final int METHOD_checkImage7 = 7;
    private static final int METHOD_checkImage8 = 8;
    private static final int METHOD_computeVisibleRect9 = 9;
    private static final int METHOD_contains10 = 10;
    private static final int METHOD_contains11 = 11;
    private static final int METHOD_countComponents12 = 12;
    private static final int METHOD_createImage13 = 13;
    private static final int METHOD_createImage14 = 14;
    private static final int METHOD_createToolTip15 = 15;
    private static final int METHOD_createVolatileImage16 = 16;
    private static final int METHOD_createVolatileImage17 = 17;
    private static final int METHOD_deliverEvent18 = 18;
    private static final int METHOD_disable19 = 19;
    private static final int METHOD_dispatchEvent20 = 20;
    private static final int METHOD_doLayout21 = 21;
    private static final int METHOD_enable22 = 22;
    private static final int METHOD_enable23 = 23;
    private static final int METHOD_enableInputMethods24 = 24;
    private static final int METHOD_findComponentAt25 = 25;
    private static final int METHOD_findComponentAt26 = 26;
    private static final int METHOD_firePropertyChange27 = 27;
    private static final int METHOD_firePropertyChange28 = 28;
    private static final int METHOD_firePropertyChange29 = 29;
    private static final int METHOD_firePropertyChange30 = 30;
    private static final int METHOD_firePropertyChange31 = 31;
    private static final int METHOD_firePropertyChange32 = 32;
    private static final int METHOD_firePropertyChange33 = 33;
    private static final int METHOD_firePropertyChange34 = 34;
    private static final int METHOD_getActionForKeyStroke35 = 35;
    private static final int METHOD_getBaseline36 = 36;
    private static final int METHOD_getBounds37 = 37;
    private static final int METHOD_getClientProperty38 = 38;
    private static final int METHOD_getComponentAt39 = 39;
    private static final int METHOD_getComponentAt40 = 40;
    private static final int METHOD_getComponentZOrder41 = 41;
    private static final int METHOD_getConditionForKeyStroke42 = 42;
    private static final int METHOD_getDefaultLocale43 = 43;
    private static final int METHOD_getFocusTraversalKeys44 = 44;
    private static final int METHOD_getFontMetrics45 = 45;
    private static final int METHOD_getInsets46 = 46;
    private static final int METHOD_getListeners47 = 47;
    private static final int METHOD_getLocation48 = 48;
    private static final int METHOD_getMousePosition49 = 49;
    private static final int METHOD_getPopupLocation50 = 50;
    private static final int METHOD_getPropertyChangeListeners51 = 51;
    private static final int METHOD_getSize52 = 52;
    private static final int METHOD_getToolTipLocation53 = 53;
    private static final int METHOD_getToolTipText54 = 54;
    private static final int METHOD_gotFocus55 = 55;
    private static final int METHOD_grabFocus56 = 56;
    private static final int METHOD_handleEvent57 = 57;
    private static final int METHOD_hasFocus58 = 58;
    private static final int METHOD_hide59 = 59;
    private static final int METHOD_imageUpdate60 = 60;
    private static final int METHOD_insets61 = 61;
    private static final int METHOD_inside62 = 62;
    private static final int METHOD_invalidate63 = 63;
    private static final int METHOD_isAncestorOf64 = 64;
    private static final int METHOD_isFocusCycleRoot65 = 65;
    private static final int METHOD_isLightweightComponent66 = 66;
    private static final int METHOD_keyDown67 = 67;
    private static final int METHOD_keyUp68 = 68;
    private static final int METHOD_layout69 = 69;
    private static final int METHOD_list70 = 70;
    private static final int METHOD_list71 = 71;
    private static final int METHOD_list72 = 72;
    private static final int METHOD_list73 = 73;
    private static final int METHOD_list74 = 74;
    private static final int METHOD_locate75 = 75;
    private static final int METHOD_location76 = 76;
    private static final int METHOD_lostFocus77 = 77;
    private static final int METHOD_minimumSize78 = 78;
    private static final int METHOD_mouseDown79 = 79;
    private static final int METHOD_mouseDrag80 = 80;
    private static final int METHOD_mouseEnter81 = 81;
    private static final int METHOD_mouseExit82 = 82;
    private static final int METHOD_mouseMove83 = 83;
    private static final int METHOD_mouseUp84 = 84;
    private static final int METHOD_move85 = 85;
    private static final int METHOD_nextFocus86 = 86;
    private static final int METHOD_paint87 = 87;
    private static final int METHOD_paintAll88 = 88;
    private static final int METHOD_paintComponents89 = 89;
    private static final int METHOD_paintImmediately90 = 90;
    private static final int METHOD_paintImmediately91 = 91;
    private static final int METHOD_postEvent92 = 92;
    private static final int METHOD_preferredSize93 = 93;
    private static final int METHOD_prepareImage94 = 94;
    private static final int METHOD_prepareImage95 = 95;
    private static final int METHOD_print96 = 96;
    private static final int METHOD_printAll97 = 97;
    private static final int METHOD_printComponents98 = 98;
    private static final int METHOD_putClientProperty99 = 99;
    private static final int METHOD_registerKeyboardAction100 = 100;
    private static final int METHOD_registerKeyboardAction101 = 101;
    private static final int METHOD_remove102 = 102;
    private static final int METHOD_remove103 = 103;
    private static final int METHOD_remove104 = 104;
    private static final int METHOD_removeAll105 = 105;
    private static final int METHOD_removeNotify106 = 106;
    private static final int METHOD_removePropertyChangeListener107 = 107;
    private static final int METHOD_repaint108 = 108;
    private static final int METHOD_repaint109 = 109;
    private static final int METHOD_repaint110 = 110;
    private static final int METHOD_repaint111 = 111;
    private static final int METHOD_repaint112 = 112;
    private static final int METHOD_requestDefaultFocus113 = 113;
    private static final int METHOD_requestFocus114 = 114;
    private static final int METHOD_requestFocus115 = 115;
    private static final int METHOD_requestFocusInWindow116 = 116;
    private static final int METHOD_resetKeyboardActions117 = 117;
    private static final int METHOD_reshape118 = 118;
    private static final int METHOD_resize119 = 119;
    private static final int METHOD_resize120 = 120;
    private static final int METHOD_revalidate121 = 121;
    private static final int METHOD_scrollRectToVisible122 = 122;
    private static final int METHOD_setBounds123 = 123;
    private static final int METHOD_setComponentZOrder124 = 124;
    private static final int METHOD_setDefaultLocale125 = 125;
    private static final int METHOD_show126 = 126;
    private static final int METHOD_show127 = 127;
    private static final int METHOD_size128 = 128;
    private static final int METHOD_toString129 = 129;
    private static final int METHOD_transferFocus130 = 130;
    private static final int METHOD_transferFocusBackward131 = 131;
    private static final int METHOD_transferFocusDownCycle132 = 132;
    private static final int METHOD_transferFocusUpCycle133 = 133;
    private static final int METHOD_unregisterKeyboardAction134 = 134;
    private static final int METHOD_update135 = 135;
    private static final int METHOD_updateUI136 = 136;
    private static final int METHOD_validate137 = 137;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[138];
    
        try {
            methods[METHOD_action0] = new MethodDescriptor(java.awt.Component.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_action0].setDisplayName ( "" );
            methods[METHOD_add1] = new MethodDescriptor(java.awt.Component.class.getMethod("add", new Class[] {java.awt.PopupMenu.class})); // NOI18N
            methods[METHOD_add1].setDisplayName ( "" );
            methods[METHOD_addNotify2] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("addNotify", new Class[] {})); // NOI18N
            methods[METHOD_addNotify2].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener3] = new MethodDescriptor(java.awt.Container.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_addPropertyChangeListener3].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation4] = new MethodDescriptor(java.awt.Container.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class})); // NOI18N
            methods[METHOD_applyComponentOrientation4].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet5] = new MethodDescriptor(java.awt.Container.class.getMethod("areFocusTraversalKeysSet", new Class[] {int.class})); // NOI18N
            methods[METHOD_areFocusTraversalKeysSet5].setDisplayName ( "" );
            methods[METHOD_bounds6] = new MethodDescriptor(java.awt.Component.class.getMethod("bounds", new Class[] {})); // NOI18N
            methods[METHOD_bounds6].setDisplayName ( "" );
            methods[METHOD_checkImage7] = new MethodDescriptor(java.awt.Component.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage7].setDisplayName ( "" );
            methods[METHOD_checkImage8] = new MethodDescriptor(java.awt.Component.class.getMethod("checkImage", new Class[] {java.awt.Image.class, int.class, int.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage8].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect9] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_computeVisibleRect9].setDisplayName ( "" );
            methods[METHOD_contains10] = new MethodDescriptor(java.awt.Component.class.getMethod("contains", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_contains10].setDisplayName ( "" );
            methods[METHOD_contains11] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("contains", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_contains11].setDisplayName ( "" );
            methods[METHOD_countComponents12] = new MethodDescriptor(java.awt.Container.class.getMethod("countComponents", new Class[] {})); // NOI18N
            methods[METHOD_countComponents12].setDisplayName ( "" );
            methods[METHOD_createImage13] = new MethodDescriptor(java.awt.Component.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class})); // NOI18N
            methods[METHOD_createImage13].setDisplayName ( "" );
            methods[METHOD_createImage14] = new MethodDescriptor(java.awt.Component.class.getMethod("createImage", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_createImage14].setDisplayName ( "" );
            methods[METHOD_createToolTip15] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("createToolTip", new Class[] {})); // NOI18N
            methods[METHOD_createToolTip15].setDisplayName ( "" );
            methods[METHOD_createVolatileImage16] = new MethodDescriptor(java.awt.Component.class.getMethod("createVolatileImage", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_createVolatileImage16].setDisplayName ( "" );
            methods[METHOD_createVolatileImage17] = new MethodDescriptor(java.awt.Component.class.getMethod("createVolatileImage", new Class[] {int.class, int.class, java.awt.ImageCapabilities.class})); // NOI18N
            methods[METHOD_createVolatileImage17].setDisplayName ( "" );
            methods[METHOD_deliverEvent18] = new MethodDescriptor(java.awt.Container.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_deliverEvent18].setDisplayName ( "" );
            methods[METHOD_disable19] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("disable", new Class[] {})); // NOI18N
            methods[METHOD_disable19].setDisplayName ( "" );
            methods[METHOD_dispatchEvent20] = new MethodDescriptor(java.awt.Component.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class})); // NOI18N
            methods[METHOD_dispatchEvent20].setDisplayName ( "" );
            methods[METHOD_doLayout21] = new MethodDescriptor(java.awt.Container.class.getMethod("doLayout", new Class[] {})); // NOI18N
            methods[METHOD_doLayout21].setDisplayName ( "" );
            methods[METHOD_enable22] = new MethodDescriptor(java.awt.Component.class.getMethod("enable", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_enable22].setDisplayName ( "" );
            methods[METHOD_enable23] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("enable", new Class[] {})); // NOI18N
            methods[METHOD_enable23].setDisplayName ( "" );
            methods[METHOD_enableInputMethods24] = new MethodDescriptor(java.awt.Component.class.getMethod("enableInputMethods", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_enableInputMethods24].setDisplayName ( "" );
            methods[METHOD_findComponentAt25] = new MethodDescriptor(java.awt.Container.class.getMethod("findComponentAt", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_findComponentAt25].setDisplayName ( "" );
            methods[METHOD_findComponentAt26] = new MethodDescriptor(java.awt.Container.class.getMethod("findComponentAt", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_findComponentAt26].setDisplayName ( "" );
            methods[METHOD_firePropertyChange27] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, byte.class, byte.class})); // NOI18N
            methods[METHOD_firePropertyChange27].setDisplayName ( "" );
            methods[METHOD_firePropertyChange28] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, short.class, short.class})); // NOI18N
            methods[METHOD_firePropertyChange28].setDisplayName ( "" );
            methods[METHOD_firePropertyChange29] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, long.class, long.class})); // NOI18N
            methods[METHOD_firePropertyChange29].setDisplayName ( "" );
            methods[METHOD_firePropertyChange30] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, float.class, float.class})); // NOI18N
            methods[METHOD_firePropertyChange30].setDisplayName ( "" );
            methods[METHOD_firePropertyChange31] = new MethodDescriptor(java.awt.Component.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, double.class, double.class})); // NOI18N
            methods[METHOD_firePropertyChange31].setDisplayName ( "" );
            methods[METHOD_firePropertyChange32] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, boolean.class, boolean.class})); // NOI18N
            methods[METHOD_firePropertyChange32].setDisplayName ( "" );
            methods[METHOD_firePropertyChange33] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, int.class, int.class})); // NOI18N
            methods[METHOD_firePropertyChange33].setDisplayName ( "" );
            methods[METHOD_firePropertyChange34] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, char.class, char.class})); // NOI18N
            methods[METHOD_firePropertyChange34].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke35] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getActionForKeyStroke35].setDisplayName ( "" );
            methods[METHOD_getBaseline36] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getBaseline", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_getBaseline36].setDisplayName ( "" );
            methods[METHOD_getBounds37] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_getBounds37].setDisplayName ( "" );
            methods[METHOD_getClientProperty38] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class})); // NOI18N
            methods[METHOD_getClientProperty38].setDisplayName ( "" );
            methods[METHOD_getComponentAt39] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentAt", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_getComponentAt39].setDisplayName ( "" );
            methods[METHOD_getComponentAt40] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentAt", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getComponentAt40].setDisplayName ( "" );
            methods[METHOD_getComponentZOrder41] = new MethodDescriptor(java.awt.Container.class.getMethod("getComponentZOrder", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_getComponentZOrder41].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke42] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getConditionForKeyStroke42].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale43] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getDefaultLocale", new Class[] {})); // NOI18N
            methods[METHOD_getDefaultLocale43].setDisplayName ( "" );
            methods[METHOD_getFocusTraversalKeys44] = new MethodDescriptor(java.awt.Container.class.getMethod("getFocusTraversalKeys", new Class[] {int.class})); // NOI18N
            methods[METHOD_getFocusTraversalKeys44].setDisplayName ( "" );
            methods[METHOD_getFontMetrics45] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class})); // NOI18N
            methods[METHOD_getFontMetrics45].setDisplayName ( "" );
            methods[METHOD_getInsets46] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getInsets", new Class[] {java.awt.Insets.class})); // NOI18N
            methods[METHOD_getInsets46].setDisplayName ( "" );
            methods[METHOD_getListeners47] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getListeners", new Class[] {java.lang.Class.class})); // NOI18N
            methods[METHOD_getListeners47].setDisplayName ( "" );
            methods[METHOD_getLocation48] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getLocation", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getLocation48].setDisplayName ( "" );
            methods[METHOD_getMousePosition49] = new MethodDescriptor(java.awt.Container.class.getMethod("getMousePosition", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_getMousePosition49].setDisplayName ( "" );
            methods[METHOD_getPopupLocation50] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getPopupLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getPopupLocation50].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners51] = new MethodDescriptor(java.awt.Component.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getPropertyChangeListeners51].setDisplayName ( "" );
            methods[METHOD_getSize52] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getSize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_getSize52].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation53] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipLocation53].setDisplayName ( "" );
            methods[METHOD_getToolTipText54] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipText54].setDisplayName ( "" );
            methods[METHOD_gotFocus55] = new MethodDescriptor(java.awt.Component.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_gotFocus55].setDisplayName ( "" );
            methods[METHOD_grabFocus56] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("grabFocus", new Class[] {})); // NOI18N
            methods[METHOD_grabFocus56].setDisplayName ( "" );
            methods[METHOD_handleEvent57] = new MethodDescriptor(java.awt.Component.class.getMethod("handleEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_handleEvent57].setDisplayName ( "" );
            methods[METHOD_hasFocus58] = new MethodDescriptor(java.awt.Component.class.getMethod("hasFocus", new Class[] {})); // NOI18N
            methods[METHOD_hasFocus58].setDisplayName ( "" );
            methods[METHOD_hide59] = new MethodDescriptor(java.awt.Component.class.getMethod("hide", new Class[] {})); // NOI18N
            methods[METHOD_hide59].setDisplayName ( "" );
            methods[METHOD_imageUpdate60] = new MethodDescriptor(java.awt.Component.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, int.class, int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_imageUpdate60].setDisplayName ( "" );
            methods[METHOD_insets61] = new MethodDescriptor(java.awt.Container.class.getMethod("insets", new Class[] {})); // NOI18N
            methods[METHOD_insets61].setDisplayName ( "" );
            methods[METHOD_inside62] = new MethodDescriptor(java.awt.Component.class.getMethod("inside", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_inside62].setDisplayName ( "" );
            methods[METHOD_invalidate63] = new MethodDescriptor(java.awt.Container.class.getMethod("invalidate", new Class[] {})); // NOI18N
            methods[METHOD_invalidate63].setDisplayName ( "" );
            methods[METHOD_isAncestorOf64] = new MethodDescriptor(java.awt.Container.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isAncestorOf64].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot65] = new MethodDescriptor(java.awt.Container.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class})); // NOI18N
            methods[METHOD_isFocusCycleRoot65].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent66] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isLightweightComponent66].setDisplayName ( "" );
            methods[METHOD_keyDown67] = new MethodDescriptor(java.awt.Component.class.getMethod("keyDown", new Class[] {java.awt.Event.class, int.class})); // NOI18N
            methods[METHOD_keyDown67].setDisplayName ( "" );
            methods[METHOD_keyUp68] = new MethodDescriptor(java.awt.Component.class.getMethod("keyUp", new Class[] {java.awt.Event.class, int.class})); // NOI18N
            methods[METHOD_keyUp68].setDisplayName ( "" );
            methods[METHOD_layout69] = new MethodDescriptor(java.awt.Container.class.getMethod("layout", new Class[] {})); // NOI18N
            methods[METHOD_layout69].setDisplayName ( "" );
            methods[METHOD_list70] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {})); // NOI18N
            methods[METHOD_list70].setDisplayName ( "" );
            methods[METHOD_list71] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {java.io.PrintStream.class})); // NOI18N
            methods[METHOD_list71].setDisplayName ( "" );
            methods[METHOD_list72] = new MethodDescriptor(java.awt.Component.class.getMethod("list", new Class[] {java.io.PrintWriter.class})); // NOI18N
            methods[METHOD_list72].setDisplayName ( "" );
            methods[METHOD_list73] = new MethodDescriptor(java.awt.Container.class.getMethod("list", new Class[] {java.io.PrintStream.class, int.class})); // NOI18N
            methods[METHOD_list73].setDisplayName ( "" );
            methods[METHOD_list74] = new MethodDescriptor(java.awt.Container.class.getMethod("list", new Class[] {java.io.PrintWriter.class, int.class})); // NOI18N
            methods[METHOD_list74].setDisplayName ( "" );
            methods[METHOD_locate75] = new MethodDescriptor(java.awt.Container.class.getMethod("locate", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_locate75].setDisplayName ( "" );
            methods[METHOD_location76] = new MethodDescriptor(java.awt.Component.class.getMethod("location", new Class[] {})); // NOI18N
            methods[METHOD_location76].setDisplayName ( "" );
            methods[METHOD_lostFocus77] = new MethodDescriptor(java.awt.Component.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_lostFocus77].setDisplayName ( "" );
            methods[METHOD_minimumSize78] = new MethodDescriptor(java.awt.Container.class.getMethod("minimumSize", new Class[] {})); // NOI18N
            methods[METHOD_minimumSize78].setDisplayName ( "" );
            methods[METHOD_mouseDown79] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseDown79].setDisplayName ( "" );
            methods[METHOD_mouseDrag80] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseDrag80].setDisplayName ( "" );
            methods[METHOD_mouseEnter81] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseEnter81].setDisplayName ( "" );
            methods[METHOD_mouseExit82] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseExit82].setDisplayName ( "" );
            methods[METHOD_mouseMove83] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseMove83].setDisplayName ( "" );
            methods[METHOD_mouseUp84] = new MethodDescriptor(java.awt.Component.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, int.class, int.class})); // NOI18N
            methods[METHOD_mouseUp84].setDisplayName ( "" );
            methods[METHOD_move85] = new MethodDescriptor(java.awt.Component.class.getMethod("move", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_move85].setDisplayName ( "" );
            methods[METHOD_nextFocus86] = new MethodDescriptor(java.awt.Component.class.getMethod("nextFocus", new Class[] {})); // NOI18N
            methods[METHOD_nextFocus86].setDisplayName ( "" );
            methods[METHOD_paint87] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paint", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paint87].setDisplayName ( "" );
            methods[METHOD_paintAll88] = new MethodDescriptor(java.awt.Component.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintAll88].setDisplayName ( "" );
            methods[METHOD_paintComponents89] = new MethodDescriptor(java.awt.Container.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintComponents89].setDisplayName ( "" );
            methods[METHOD_paintImmediately90] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paintImmediately", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_paintImmediately90].setDisplayName ( "" );
            methods[METHOD_paintImmediately91] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("paintImmediately", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_paintImmediately91].setDisplayName ( "" );
            methods[METHOD_postEvent92] = new MethodDescriptor(java.awt.Component.class.getMethod("postEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_postEvent92].setDisplayName ( "" );
            methods[METHOD_preferredSize93] = new MethodDescriptor(java.awt.Container.class.getMethod("preferredSize", new Class[] {})); // NOI18N
            methods[METHOD_preferredSize93].setDisplayName ( "" );
            methods[METHOD_prepareImage94] = new MethodDescriptor(java.awt.Component.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage94].setDisplayName ( "" );
            methods[METHOD_prepareImage95] = new MethodDescriptor(java.awt.Component.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, int.class, int.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage95].setDisplayName ( "" );
            methods[METHOD_print96] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("print", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_print96].setDisplayName ( "" );
            methods[METHOD_printAll97] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("printAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printAll97].setDisplayName ( "" );
            methods[METHOD_printComponents98] = new MethodDescriptor(java.awt.Container.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printComponents98].setDisplayName ( "" );
            methods[METHOD_putClientProperty99] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_putClientProperty99].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction100] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, int.class})); // NOI18N
            methods[METHOD_registerKeyboardAction100].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction101] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, javax.swing.KeyStroke.class, int.class})); // NOI18N
            methods[METHOD_registerKeyboardAction101].setDisplayName ( "" );
            methods[METHOD_remove102] = new MethodDescriptor(java.awt.Component.class.getMethod("remove", new Class[] {java.awt.MenuComponent.class})); // NOI18N
            methods[METHOD_remove102].setDisplayName ( "" );
            methods[METHOD_remove103] = new MethodDescriptor(java.awt.Container.class.getMethod("remove", new Class[] {int.class})); // NOI18N
            methods[METHOD_remove103].setDisplayName ( "" );
            methods[METHOD_remove104] = new MethodDescriptor(java.awt.Container.class.getMethod("remove", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_remove104].setDisplayName ( "" );
            methods[METHOD_removeAll105] = new MethodDescriptor(java.awt.Container.class.getMethod("removeAll", new Class[] {})); // NOI18N
            methods[METHOD_removeAll105].setDisplayName ( "" );
            methods[METHOD_removeNotify106] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("removeNotify", new Class[] {})); // NOI18N
            methods[METHOD_removeNotify106].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener107] = new MethodDescriptor(java.awt.Component.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_removePropertyChangeListener107].setDisplayName ( "" );
            methods[METHOD_repaint108] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {})); // NOI18N
            methods[METHOD_repaint108].setDisplayName ( "" );
            methods[METHOD_repaint109] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {long.class})); // NOI18N
            methods[METHOD_repaint109].setDisplayName ( "" );
            methods[METHOD_repaint110] = new MethodDescriptor(java.awt.Component.class.getMethod("repaint", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_repaint110].setDisplayName ( "" );
            methods[METHOD_repaint111] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("repaint", new Class[] {long.class, int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_repaint111].setDisplayName ( "" );
            methods[METHOD_repaint112] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("repaint", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_repaint112].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus113] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestDefaultFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestDefaultFocus113].setDisplayName ( "" );
            methods[METHOD_requestFocus114] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestFocus114].setDisplayName ( "" );
            methods[METHOD_requestFocus115] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestFocus", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_requestFocus115].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow116] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("requestFocusInWindow", new Class[] {})); // NOI18N
            methods[METHOD_requestFocusInWindow116].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions117] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("resetKeyboardActions", new Class[] {})); // NOI18N
            methods[METHOD_resetKeyboardActions117].setDisplayName ( "" );
            methods[METHOD_reshape118] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("reshape", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_reshape118].setDisplayName ( "" );
            methods[METHOD_resize119] = new MethodDescriptor(java.awt.Component.class.getMethod("resize", new Class[] {int.class, int.class})); // NOI18N
            methods[METHOD_resize119].setDisplayName ( "" );
            methods[METHOD_resize120] = new MethodDescriptor(java.awt.Component.class.getMethod("resize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_resize120].setDisplayName ( "" );
            methods[METHOD_revalidate121] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("revalidate", new Class[] {})); // NOI18N
            methods[METHOD_revalidate121].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible122] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_scrollRectToVisible122].setDisplayName ( "" );
            methods[METHOD_setBounds123] = new MethodDescriptor(java.awt.Component.class.getMethod("setBounds", new Class[] {int.class, int.class, int.class, int.class})); // NOI18N
            methods[METHOD_setBounds123].setDisplayName ( "" );
            methods[METHOD_setComponentZOrder124] = new MethodDescriptor(java.awt.Container.class.getMethod("setComponentZOrder", new Class[] {java.awt.Component.class, int.class})); // NOI18N
            methods[METHOD_setComponentZOrder124].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale125] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class})); // NOI18N
            methods[METHOD_setDefaultLocale125].setDisplayName ( "" );
            methods[METHOD_show126] = new MethodDescriptor(java.awt.Component.class.getMethod("show", new Class[] {})); // NOI18N
            methods[METHOD_show126].setDisplayName ( "" );
            methods[METHOD_show127] = new MethodDescriptor(java.awt.Component.class.getMethod("show", new Class[] {boolean.class})); // NOI18N
            methods[METHOD_show127].setDisplayName ( "" );
            methods[METHOD_size128] = new MethodDescriptor(java.awt.Component.class.getMethod("size", new Class[] {})); // NOI18N
            methods[METHOD_size128].setDisplayName ( "" );
            methods[METHOD_toString129] = new MethodDescriptor(java.awt.Component.class.getMethod("toString", new Class[] {})); // NOI18N
            methods[METHOD_toString129].setDisplayName ( "" );
            methods[METHOD_transferFocus130] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocus", new Class[] {})); // NOI18N
            methods[METHOD_transferFocus130].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward131] = new MethodDescriptor(java.awt.Container.class.getMethod("transferFocusBackward", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusBackward131].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle132] = new MethodDescriptor(java.awt.Container.class.getMethod("transferFocusDownCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusDownCycle132].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle133] = new MethodDescriptor(java.awt.Component.class.getMethod("transferFocusUpCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusUpCycle133].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction134] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_unregisterKeyboardAction134].setDisplayName ( "" );
            methods[METHOD_update135] = new MethodDescriptor(javax.swing.JComponent.class.getMethod("update", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_update135].setDisplayName ( "" );
            methods[METHOD_updateUI136] = new MethodDescriptor(javax.swing.JPanel.class.getMethod("updateUI", new Class[] {})); // NOI18N
            methods[METHOD_updateUI136].setDisplayName ( "" );
            methods[METHOD_validate137] = new MethodDescriptor(java.awt.Container.class.getMethod("validate", new Class[] {})); // NOI18N
            methods[METHOD_validate137].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods

        // Here you can add code for customizing the methods array.

        return methods;     }//GEN-LAST:Methods
    private static java.awt.Image iconColor16 = null;//GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;//GEN-END:IconsDef
    private static String iconNameC16 = "/icons/zoom16.gif";//GEN-BEGIN:Icons
    private static String iconNameC32 = "/icons/zoom24.gif";
    private static String iconNameM16 = null;
    private static String iconNameM32 = null;//GEN-END:Icons
    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx

//GEN-FIRST:Superclass
    // Here you can add code for customizing the Superclass BeanInfo.
//GEN-LAST:Superclass
    /**
     * Gets the bean's
     * <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable properties of this bean.
     * May return null if the information should be obtained by automatic
     * analysis.
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }

    /**
     * Gets the bean's
     * <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean. May return null if the information
     * should be obtained by automatic analysis. <p> If a property is indexed,
     * then its entry in the result array will belong to the
     * IndexedPropertyDescriptor subclass of PropertyDescriptor. A client of
     * getPropertyDescriptors can use "instanceof" to check if a given
     * PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }

    /**
     * Gets the bean's
     * <code>EventSetDescriptor</code>s.
     *
     * @return An array of EventSetDescriptors describing the kinds of events
     * fired by this bean. May return null if the information should be obtained
     * by automatic analysis.
     */
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEdescriptor();
    }

    /**
     * Gets the bean's
     * <code>MethodDescriptor</code>s.
     *
     * @return An array of MethodDescriptors describing the methods implemented
     * by this bean. May return null if the information should be obtained by
     * automatic analysis.
     */
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return getMdescriptor();
    }

    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are
     * customizing the bean.
     *
     * @return Index of default property in the PropertyDescriptor array
     * returned by getPropertyDescriptors. <P>	Returns -1 if there is no default
     * property.
     */
    @Override
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }

    /**
     * A bean may have a "default" event that is the event that will mostly
     * commonly be used by human's when using the bean.
     *
     * @return Index of default event in the EventSetDescriptor array returned
     * by getEventSetDescriptors. <P>	Returns -1 if there is no default event.
     */
    @Override
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }

    /**
     * This method returns an image object that can be used to represent the
     * bean in toolboxes, toolbars, etc. Icon images will typically be GIFs, but
     * may in future include other formats. <p> Beans aren't required to provide
     * icons and may return null from this method. <p> There are four possible
     * flavors of icons (16x16 color, 32x32 color, 16x16 mono, 32x32 mono). If a
     * bean choses to only support a single icon we recommend supporting 16x16
     * color. <p> We recommend that icons have a "transparent" background so
     * they can be rendered onto an existing background.
     *
     * @param iconKind The kind of icon requested. This should be one of the
     * constant values ICON_COLOR_16x16, ICON_COLOR_32x32, ICON_MONO_16x16, or
     * ICON_MONO_32x32.
     * @return An image object representing the requested icon. May return null
     * if no suitable icon is available.
     */
    @Override
    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
            case ICON_COLOR_16x16:
                if (iconNameC16 == null) {
                    return null;
                } else {
                    if (iconColor16 == null) {
                        iconColor16 = loadImage(iconNameC16);
                    }
                    return iconColor16;
                }
            case ICON_COLOR_32x32:
                if (iconNameC32 == null) {
                    return null;
                } else {
                    if (iconColor32 == null) {
                        iconColor32 = loadImage(iconNameC32);
                    }
                    return iconColor32;
                }
            case ICON_MONO_16x16:
                if (iconNameM16 == null) {
                    return null;
                } else {
                    if (iconMono16 == null) {
                        iconMono16 = loadImage(iconNameM16);
                    }
                    return iconMono16;
                }
            case ICON_MONO_32x32:
                if (iconNameM32 == null) {
                    return null;
                } else {
                    if (iconMono32 == null) {
                        iconMono32 = loadImage(iconNameM32);
                    }
                    return iconMono32;
                }
            default:
                return null;
        }
    }
}
