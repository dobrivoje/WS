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
public class WindowForm extends Window implements Button.ClickListener {

    private final Button closeButton;
    private final VerticalLayout VL = new VerticalLayout();

    public WindowForm(String caption, FormLayout formLayout) {
        setStyleName(Reindeer.LAYOUT_BLACK);
        
        setCaption(caption);
        setModal(true);

        this.VL.setSizeFull();

        this.closeButton = new Button("Close Window", this);
        this.closeButton.setStyleName(ValoTheme.BUTTON_DANGER);

        this.VL.addComponent(formLayout);
        this.VL.addComponent(closeButton);
        this.VL.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);

        setHeight(66, Unit.PERCENTAGE);
        setWidth(60, Unit.PERCENTAGE);
        center();
        setContent(VL);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        close();
    }
}
