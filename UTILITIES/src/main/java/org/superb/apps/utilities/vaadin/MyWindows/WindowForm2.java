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
public class WindowForm2 extends Window {

    protected TabSheet detailsWrapper = new TabSheet();
    protected VerticalLayout content = new VerticalLayout();

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public WindowForm2(String caption, Layout formLayout) {
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);

        setModal(true);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(60, Unit.PERCENTAGE);
        setWidth(60, Unit.PERCENTAGE);

        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildFormTab(caption, formLayout));

        content.addComponent(buildFooter());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="buildFormTab">
    private Component buildFormTab(String caption, Layout formLayout) {
        formLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        
        HorizontalLayout centralLayout = new HorizontalLayout();
        centralLayout.setCaption(caption);
        centralLayout.setIcon(FontAwesome.USER);
        centralLayout.setWidth(100.0f, Unit.PERCENTAGE);
        centralLayout.setSpacing(true);
        centralLayout.setMargin(true);
        centralLayout.addStyleName("profile-form");

        VerticalLayout picLayout = new VerticalLayout();
        picLayout.setSizeUndefined();
        picLayout.setSpacing(true);
        Image profilePic = new Image(null, new ThemeResource("img/profile-pic-300px.jpg"));
        profilePic.setWidth(100.0f, Sizeable.Unit.PIXELS);
        picLayout.addComponent(profilePic);

        centralLayout.addComponent(picLayout);
        centralLayout.addComponent(formLayout);
        centralLayout.setExpandRatio(formLayout, 1);

        return centralLayout;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Footer">
    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("Close");
        ok.addStyleName(ValoTheme.BUTTON_DANGER);
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });

        ok.focus();
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }
    //</editor-fold>

}
