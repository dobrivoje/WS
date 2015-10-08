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
import com.vaadin.ui.HorizontalLayout;
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

    protected final VerticalLayout content = new VerticalLayout();

    protected Button.ClickListener externalButtonClickListener;

    private final HorizontalSplitPanel HSP = new HorizontalSplitPanel();
    private final VerticalLayout leftVL = new VerticalLayout();
    private final VerticalLayout rightVL = new VerticalLayout();

    private final Button closeButton;
    private final Button actionButton;

    /**
     * Typical Action-Close form
     *
     * @param caption Form caption
     * @param bigForm True for big form
     * @param actionButtonCaption Action button caption
     * @param externalButtonClickListener external action button interface
     * @param layout Layout to inject into this frame
     * @param components Right layout (form) part components
     */
    public WindowFormProp(String caption, boolean bigForm, String actionButtonCaption, Button.ClickListener externalButtonClickListener, Layout layout, Component... components) {
        setStyleName(Reindeer.LAYOUT_BLACK);
        setCaption(caption);
        setModal(true);

        if (bigForm) {
            content.setSizeUndefined();
        } else {
            content.setSizeFull();
        }

        content.setMargin(true);
        content.setSpacing(true);

        this.externalButtonClickListener = externalButtonClickListener;
        closeButton = new Button("Close Window", (Button.ClickEvent event) -> {
            close();
        });
        closeButton.setStyleName(ValoTheme.BUTTON_DANGER);
        closeButton.setWidth(150, Unit.PIXELS);

        actionButton = new Button(actionButtonCaption);
        actionButton.setWidth(150, Unit.PIXELS);
        if (externalButtonClickListener != null) {
            actionButton.addClickListener(externalButtonClickListener);
        }

        HSP.setSizeFull();
        HSP.setSplitPosition(60, Unit.PERCENTAGE);
        leftVL.addComponent(layout);
        rightVL.addComponents(components);
        leftVL.setSizeFull();
        // pošto je na levom panelu forma, ne treba space, da ne bi uzimao dva puta prostor...
        // leftVL.setMargin(true);
        // leftVL.setSpacing(true);
        rightVL.setSizeFull();
        rightVL.setMargin(true);
        rightVL.setSpacing(true);
        HSP.addComponent(leftVL);
        HSP.addComponent(rightVL);
        leftVL.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
        for (Component c : components) {
            rightVL.setComponentAlignment(c, Alignment.TOP_CENTER);
        }

        content.addComponent(HSP);

        HorizontalLayout footerLayout = new HorizontalLayout(actionButton, closeButton);
        footerLayout.setMargin(true);
        footerLayout.setSpacing(true);
        footerLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footerLayout.setWidth(100, Unit.PERCENTAGE);
        footerLayout.setExpandRatio(actionButton, 1.0f);

        content.addComponent(footerLayout);
        footerLayout.setComponentAlignment(actionButton, Alignment.MIDDLE_RIGHT);
        footerLayout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);

        content.setExpandRatio(HSP, 1);

        setWindowSize();
        center();
        setContent(content);
    }

    /**
     * Typical Save-Close form
     *
     * @param caption Form caption
     * @param bigForm True for big form
     * @param externalButtonClickListener external action button interface
     * @param layout Layout to inject into this frame
     * @param components Right layout (form) part components
     */
    public WindowFormProp(String caption, boolean bigForm, Button.ClickListener externalButtonClickListener, Layout layout, Component... components) {
        this(caption, bigForm, "Save", externalButtonClickListener, layout, components);
    }

    /**
     * Typical Action-Close form
     *
     * @param caption Form caption
     * @param bigForm True for big form
     * @param readOnly True for non-editable form
     * @param layout Layout to inject into this frame
     * @param components Right layout (form) part components
     */
    public WindowFormProp(String caption, boolean bigForm, boolean readOnly, Layout layout, Component... components) {
        this(caption, bigForm, null, layout, components);

        actionButton.setVisible(!readOnly);
    }

    private void setWindowSize() {
        setHeight(73, Unit.PERCENTAGE);
        setWidth(64, Unit.PERCENTAGE);
    }
}
