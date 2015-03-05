/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyWindows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author root
 */
public class WindowForm extends Window {

    protected final Button closeButton;
    protected final VerticalLayout VL = new VerticalLayout();

    public WindowForm(String caption, boolean bigForm, Layout formLayout) {
        setStyleName(Reindeer.LAYOUT_BLACK);

        setCaption(caption);
        setModal(true);
        setHeight(90, Unit.PERCENTAGE);
        setWidth(60, Unit.PERCENTAGE);

        if (bigForm) {
            VL.setSizeUndefined();
            setHeight(90, Unit.PERCENTAGE);
        } else {
            VL.setSizeFull();
        }

        VL.setMargin(true);
        VL.setSpacing(true);

        closeButton = new Button("Close Window", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });
        closeButton.setStyleName(ValoTheme.BUTTON_DANGER);
        closeButton.setWidth(150, Unit.PIXELS);

        VL.addComponent(formLayout);
        VL.addComponent(closeButton);
        VL.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        VL.setExpandRatio(formLayout, 1);

        center();
        setContent(VL);
    }
}
