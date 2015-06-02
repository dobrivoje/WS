/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyWindows;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author root
 */
public class WindowForm extends Window {

    protected final VerticalLayout content = new VerticalLayout();

    public WindowForm(String caption, boolean bigForm, Layout formLayout) {
        this(caption, bigForm, formLayout, null);
    }

    public WindowForm(String caption, boolean bigForm, Layout formLayout, Button.ClickListener externalButtonClickListener) {
        setCaption(caption);
        setModal(true);

        setHeight(70, Unit.PERCENTAGE);
        setWidth(60, Unit.PERCENTAGE);

        if (bigForm) {
            content.setSizeUndefined();
            setHeight(60, Unit.PERCENTAGE);
        } else {
            content.setSizeFull();
        }

        content.setMargin(new MarginInfo(true, false, true, false));

        content.addComponent(formLayout);
        content.addComponent(buildFooter(externalButtonClickListener));
        content.setExpandRatio(formLayout, 1f);

        setContent(content);
    }

    private Component buildFooter(ClickListener externalButtonClickListener) {
        HorizontalLayout footerLayout = new HorizontalLayout();

        footerLayout.setSpacing(true);
        footerLayout.setMargin(true);
        footerLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footerLayout.setWidth(100, Unit.PERCENTAGE);

        Button closeBtn = new Button("Close");
        closeBtn.setWidth(150, Unit.PIXELS);
        closeBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        closeBtn.addClickListener((Button.ClickEvent event) -> {
            close();
        });
        closeBtn.focus();

        Button saveBtn = new Button("Save");
        saveBtn.setWidth(150, Unit.PIXELS);

        if (externalButtonClickListener != null) {
            saveBtn.addClickListener(externalButtonClickListener);
        }
        footerLayout.addComponent(saveBtn);
        footerLayout.addComponent(closeBtn);

        footerLayout.setExpandRatio(saveBtn, 1.0f);

        footerLayout.setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);
        footerLayout.setComponentAlignment(closeBtn, Alignment.TOP_RIGHT);

        return footerLayout;
    }
}
