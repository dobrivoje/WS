/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.vaadin.utils;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 *
 * @author root
 */
public class WindowForm extends Window {

    public WindowForm(String caption, FormLayout formLayout) {
        setCaption(caption);
        setModal(true);

        setStyleName(Reindeer.LAYOUT_BLACK);

        setHeight(66, Unit.PERCENTAGE);
        setWidth(48, Unit.PERCENTAGE);
        center();
        setContent(formLayout);
    }
}
