/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;

/**
 *
 * @author root
 */
public class SelectorDialog extends WindowForm {

    /**
     *
     * @param caption Form caption
     * @param bigForm Default is false
     * @param formLayout Form to put inside this frame
     * @param externalButtonClickListener listener to run upon calling action
     * button
     * @param actionButtonCaption Caption of the action button
     */
    public SelectorDialog(String caption, boolean bigForm, Layout formLayout, Button.ClickListener externalButtonClickListener, String actionButtonCaption) {
        super(caption, bigForm, formLayout, externalButtonClickListener, actionButtonCaption);
    }

}
