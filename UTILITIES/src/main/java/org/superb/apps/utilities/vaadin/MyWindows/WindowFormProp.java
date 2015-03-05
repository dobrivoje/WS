/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyWindows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author root
 */
public class WindowFormProp extends Window {

    protected final Button closeButton;
    protected final VerticalLayout VL = new VerticalLayout();

    private final HorizontalSplitPanel HSP = new HorizontalSplitPanel();
    private final VerticalLayout leftVL = new VerticalLayout();
    private final VerticalLayout rightVL = new VerticalLayout();

    public WindowFormProp(String caption, boolean bigForm, Layout layout, Component... components) {
        setStyleName(Reindeer.LAYOUT_BLACK);
        setCaption(caption);
        setModal(true);

        if (bigForm) {
            VL.setSizeUndefined();
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

        HSP.setSizeFull();
        HSP.setSplitPosition(60, Unit.PERCENTAGE);
        leftVL.addComponent(layout);
        rightVL.addComponents(components);
        leftVL.setSizeFull();
        leftVL.setMargin(true);
        leftVL.setSpacing(true);
        rightVL.setSizeFull();
        rightVL.setMargin(true);
        rightVL.setSpacing(true);
        HSP.addComponent(leftVL);
        HSP.addComponent(rightVL);
        leftVL.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
        for (Component c : components) {
            rightVL.setComponentAlignment(c, Alignment.TOP_CENTER);
        }

        VL.addComponent(HSP);
        VL.addComponent(closeButton);
        VL.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        VL.setExpandRatio(HSP, 1);

        setWindowSize();
        center();
        setContent(VL);
    }

    private void setWindowSize() {
        setHeight(73, Unit.PERCENTAGE);
        setWidth(64, Unit.PERCENTAGE);
    }
}
