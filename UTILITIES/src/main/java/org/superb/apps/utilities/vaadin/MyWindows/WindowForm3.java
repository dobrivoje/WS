/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyWindows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import static org.superb.apps.utilities.vaadin.MyWindows.ProfilePreferencesWindow.ID;

/**
 *
 * @author root
 */
public class WindowForm3 extends Window {

    protected TabSheet detailsWrapper = new TabSheet();
    protected VerticalLayout content = new VerticalLayout();

    protected Button actionButton;
    protected Button closeButton;

    //<editor-fold defaultstate="collapsed" desc="Konstruktori">
    private WindowForm3(String caption, Layout formLayout, String imageLocation, Button.ClickListener externalButtonClickListener) {
        this(caption, formLayout, imageLocation, "Save", externalButtonClickListener, false);
    }

    private WindowForm3(String caption, Layout formLayout, String imageLocation, String actionButtonCaption, Button.ClickListener externalButtonClickListener) {
        this(caption, formLayout, imageLocation, actionButtonCaption, externalButtonClickListener, false);
    }

    private WindowForm3(String caption, Layout formLayout, String imageLocation, String actionButtonCaption, Button.ClickListener externalButtonClickListener, boolean imageDefaultSize) {
        if (imageDefaultSize) {
            init(caption, formLayout, imageLocation, actionButtonCaption, externalButtonClickListener, 150, -1);
        } else {
            init(caption, formLayout, imageLocation, actionButtonCaption, externalButtonClickListener, -1, -1);
        }
    }

    /**
     * Implementacija Save-Close forme
     *
     * @param caption Form caption
     * @param formLayout Form to inject into this frame
     * @param imageLocation Left image on the frame
     * @param externalButtonClickListener Click listener to call upon action
     * button click
     * @param imgWidth Left image width
     * @param imgHeight Left image height
     */
    public WindowForm3(String caption, Layout formLayout, String imageLocation, Button.ClickListener externalButtonClickListener, int imgWidth, int imgHeight) {
        init(caption, formLayout, imageLocation, "Save", externalButtonClickListener, imgWidth, imgHeight);
    }

    /**
     * Implementacija Save-Close forme
     *
     * @param caption Form caption
     * @param formLayout Form to inject into this frame
     * @param imageLocation Left image on the frame
     * @param externalButtonClickListener Click listener to call upon action
     * button click
     * @param imgWidth Left image width
     * @param imgHeight Left image height
     * @param readOnly If true, prevent adding action button on the form
     */
    public WindowForm3(String caption, Layout formLayout, String imageLocation, Button.ClickListener externalButtonClickListener, int imgWidth, int imgHeight, boolean readOnly) {
        init(caption, formLayout, imageLocation, "Save", externalButtonClickListener, imgWidth, imgHeight);
        actionButton.setVisible(!readOnly);
    }

    /**
     * Implementacija Specifične Action-Close forme
     *
     * @param caption Form caption
     * @param formLayout Form to inject into this frame
     * @param imageLocation Left image on the frame
     * @param actionButtonCaption Caption for the action button
     * @param externalButtonClickListener Click listener to call upon action
     * button click
     * @param imgWidth Left image width
     * @param imgHeight Left image height
     * @param readOnly If true, prevent adding action button on the form
     */
    public WindowForm3(String caption, Layout formLayout, String imageLocation, String actionButtonCaption, Button.ClickListener externalButtonClickListener, int imgWidth, int imgHeight, boolean readOnly) {
        init(caption, formLayout, imageLocation, actionButtonCaption, externalButtonClickListener, imgWidth, imgHeight);
        actionButton.setVisible(!readOnly);
    }

    private void init(String caption, Layout formLayout, String imageLocation, String actionButtonCaption, Button.ClickListener externalButtonClickListener, int imgWidth, int imgHeight) {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setHeight(70, Unit.PERCENTAGE);
        setWidth(60, Unit.PERCENTAGE);

        actionButton = new Button(actionButtonCaption);
        closeButton = new Button("Close");

        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, true, false));
        setContent(content);

        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildFormTab(caption, formLayout, imageLocation, imgWidth, imgHeight));
        content.addComponent(buildFooter(externalButtonClickListener));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="buildFormTab">
    protected final Component buildFormTab(String caption, Layout formLayout, String imageLocation, int imageWidth, int imageHeight) {
        formLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        formLayout.setSizeUndefined();

        HorizontalLayout centralLayout = new HorizontalLayout();
        centralLayout.setCaption(caption);
        centralLayout.setIcon(FontAwesome.USER);
        centralLayout.setSpacing(true);
        centralLayout.setMargin(true);
        centralLayout.addStyleName("profile-form");

        VerticalLayout picLayout = new VerticalLayout();
        picLayout.setSizeFull();
        picLayout.setSpacing(true);

        if (imageLocation == null) {
            imageLocation = "img/profile-pic-300px.jpg";
        }

        Image profilePic = new Image(null, new ThemeResource(imageLocation));

        if (imageWidth < 0 && imageHeight < 0) {
            profilePic.setWidth(85, Unit.PERCENTAGE);
            profilePic.setHeight(85, Unit.PERCENTAGE);
        } else {
            if (imageWidth > 0) {
                profilePic.setWidth(imageWidth, Unit.PIXELS);
            }

            if (imageHeight > 0) {
                profilePic.setHeight(imageHeight, Unit.PIXELS);
            }
        }

        picLayout.addComponent(profilePic);

        centralLayout.addComponent(picLayout);
        centralLayout.addComponent(formLayout);
        centralLayout.setExpandRatio(formLayout, 1);

        return centralLayout;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Footer">
    private Component buildFooter(Button.ClickListener externalButtonClickListener) {
        HorizontalLayout footerLayout = new HorizontalLayout();

        footerLayout.setSpacing(true);
        footerLayout.setMargin(true);
        footerLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footerLayout.setWidth(100, Unit.PERCENTAGE);

        closeButton.setWidth(150, Unit.PIXELS);
        closeButton.addStyleName(ValoTheme.BUTTON_DANGER);
        closeButton.addClickListener((Button.ClickEvent event) -> {
            close();
        });
        closeButton.focus();

        actionButton.setWidth(150, Unit.PIXELS);

        if (externalButtonClickListener != null) {
            actionButton.addClickListener(externalButtonClickListener);
        }

        footerLayout.addComponent(actionButton);
        footerLayout.addComponent(closeButton);

        footerLayout.setExpandRatio(actionButton, 1.0f);

        footerLayout.setComponentAlignment(actionButton, Alignment.MIDDLE_RIGHT);
        footerLayout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);

        return footerLayout;
    }
    //</editor-fold>

}
