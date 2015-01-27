/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.vaadin.utils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author root
 */
public class WindowForm extends Window implements Button.ClickListener {

    private final Button closeButton;
    private final VerticalSplitPanel VSP = new VerticalSplitPanel();
    private final VerticalLayout exitlayout = new VerticalLayout();

    public WindowForm(String caption, FormLayout formLayout) {
        setStyleName(Reindeer.LAYOUT_BLACK);
        
        setCaption(caption);
        setModal(true);

        this.VSP.setSizeFull();
        this.VSP.setSplitPosition(90, Unit.PERCENTAGE);

        this.closeButton = new Button("Close Window", this);
        this.closeButton.setStyleName(ValoTheme.BUTTON_DANGER);

        this.exitlayout.addComponent(closeButton);
        this.exitlayout.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);

        this.VSP.setFirstComponent(formLayout);
        this.VSP.setSecondComponent(exitlayout);
        
        setHeight(60, Unit.PERCENTAGE);
        setWidth(50, Unit.PERCENTAGE);
        center();
        setContent(VSP);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        close();
    }
}
