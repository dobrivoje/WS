/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;

/**
 * Dialog for custom filtering options.
 * <p>
 * Primary use of this dialog is in the CRM panels.
 */
public class SelectorDialog extends WindowForm3 {

    /**
     *
     * @param caption Form caption
     * @param imageLocation Left image in the frame
     * @param actionButtonCaption Caption of the action button
     * @param formLayout Form with defined fields, to put inside this frame
     * @param imageDefaultSize True for default image size
     * @param externalButtonClickListener Listener to run upon calling action
     * button
     */
    public SelectorDialog(String caption, Layout formLayout, String imageLocation, String actionButtonCaption, Button.ClickListener externalButtonClickListener, boolean imageDefaultSize) {
        super(caption, formLayout, imageLocation, actionButtonCaption, externalButtonClickListener, imageDefaultSize);
    }

}
