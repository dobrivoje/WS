/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyWindows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
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

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public WindowForm3(String caption, Layout formLayout, String imageLocation, Button.ClickListener externalButtonClickListener) {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setHeight(70, Unit.PERCENTAGE);
        setWidth(60, Unit.PERCENTAGE);

        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, true, false));
        setContent(content);

        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildFormTab(caption, formLayout, imageLocation));

        content.addComponent(buildFooter(externalButtonClickListener));
    }

    /**
     * Kreiraj formu sa slikom korisnika
     *
     * @param caption
     * @param formLayout
     */
    public WindowForm3(String caption, Layout formLayout) {
        this(caption, formLayout, "img/profile-pic-300px.jpg", null);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="buildFormTab">
    protected final Component buildFormTab(String caption, Layout formLayout) {
        return buildFormTab(caption, formLayout, "img/profile-pic-300px.jpg");
    }

    protected final Component buildFormTab(String caption, Layout formLayout, String imageLocation) {
        formLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        formLayout.setSizeUndefined();

        HorizontalLayout centralLayout = new HorizontalLayout();
        centralLayout.setCaption(caption);
        centralLayout.setIcon(FontAwesome.USER);
        centralLayout.setSpacing(true);
        centralLayout.setMargin(true);
        centralLayout.addStyleName("profile-form");

        VerticalLayout picLayout = new VerticalLayout();
        picLayout.setSizeUndefined();
        picLayout.setSpacing(true);

        if (imageLocation == null) {
            imageLocation = "img/profile-pic-300px.jpg";
        }

        Image profilePic = new Image(null, new ThemeResource(imageLocation));
        profilePic.setWidth(100, Sizeable.Unit.PIXELS);
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

        footerLayout.setComponentAlignment(saveBtn, Alignment.TOP_RIGHT);
        footerLayout.setComponentAlignment(closeBtn, Alignment.TOP_RIGHT);

        return footerLayout;
    }
    //</editor-fold>

}
