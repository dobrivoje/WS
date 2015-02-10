/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyWindows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author root
 */
public class WindowForm extends Window {

    private final Button closeButton;
    private final VerticalLayout VL = new VerticalLayout();

    public WindowForm(String caption, FormLayout formLayout) {
        setStyleName(Reindeer.LAYOUT_BLACK);

        setCaption(caption);
        setModal(true);
        
        VL.setSizeFull();

        closeButton = new Button("Close Window", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });
        closeButton.setStyleName(ValoTheme.BUTTON_DANGER);

        VL.addComponent(formLayout);
        VL.addComponent(closeButton);
        VL.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);

        setHeight(66, Unit.PERCENTAGE);
        setWidth(60, Unit.PERCENTAGE);
        center();
        setContent(VL);
    }
}
