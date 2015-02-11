/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyWindows;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 *
 * @author root
 */
public class MyWindow extends Window {

    private final Label label = new Label();

    public MyWindow(String caption) {
        setCaption(caption);
        setStyleName(Reindeer.LAYOUT_BLACK);

        VerticalSplitPanel vSP = new VerticalSplitPanel();
        vSP.setSizeFull();

        VerticalLayout vL = new VerticalLayout();

        Button exitButton = new Button("Exit", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });

        vL.addComponent(exitButton);
        vL.setComponentAlignment(exitButton, Alignment.MIDDLE_CENTER);

        vSP.setSplitPosition(90, Unit.PERCENTAGE);
        vSP.setFirstComponent(label);
        vSP.setSecondComponent(vL);

        setHeight(66, Unit.PERCENTAGE);
        setWidth(48, Unit.PERCENTAGE);
        center();
        setContent(vSP);
    }

    public MyWindow(Layout layout) {
        super();

        layout.setSizeFull();
        setHeight(66, Unit.PERCENTAGE);
        setWidth(48, Unit.PERCENTAGE);
        center();
        setContent(layout);
    }

    public void setText(String text) {
        label.setValue(text);
    }
}
