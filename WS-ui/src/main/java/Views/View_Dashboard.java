package Views;

import Layouts.Layout_InlineCSS;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.datum.Dates;

@SuppressWarnings("serial")
public abstract class View_Dashboard extends Panel implements View {

    private static final String EDIT_ID = "dashboard-edit";
    private static final String TITLE_ID = "dashboard-title";

    private Label titleLabel;
    private NotificationsButton notificationsButton;
    protected CssLayout dashboardPanels;
    protected final VerticalLayout root = new VerticalLayout();

    protected boolean viewMaximized;

    /**
     * <b>subPanels</b> predstavlja listu svih Panel-a sa stablima, npr. paneli
     * sa prodajama, crm slučajevima, i slično.
     */
    protected final List<Panel> subPanels;

    protected final Dates dateInterval = new Dates();

    /**
     * <b>dynamicPanels</b> Lista komponenti (panela) koje se dinamičko
     * ažuriraju posle odgovarajuće komande panela.
     */
    protected final List<Component> dynamicPanels;

    private Window notificationsWindow;

    private MenuBar.MenuItem max;

    protected View_Dashboard(String dashBoardTitle) {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);

        viewMaximized = false;

        subPanels = new ArrayList<>();
        dynamicPanels = new ArrayList<>();

        root.addComponent(buildHeader(dashBoardTitle));
    }

    //<editor-fold defaultstate="collapsed" desc="DashBoard Header">
    protected final Component buildHeader(String dashBoardTitle) {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        titleLabel = new Label(dashBoardTitle);
        titleLabel.setId(TITLE_ID);
        titleLabel.setSizeUndefined();
        titleLabel.addStyleName(ValoTheme.LABEL_H1);
        titleLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(titleLabel);

        notificationsButton = buildNotificationsButton();
        Component edit = buildEditButton();
        HorizontalLayout tools = new HorizontalLayout(notificationsButton, edit);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }

    private NotificationsButton buildNotificationsButton() {
        NotificationsButton result = new NotificationsButton();
        result.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                openNotificationsPopup(event);
            }
        });
        return result;
    }

    private Component buildEditButton() {
        Button result = new Button();
        result.setId(EDIT_ID);
        result.setIcon(FontAwesome.EDIT);
        result.addStyleName("icon-edit");
        result.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        result.setDescription("Edit Dashboard");
        result.addClickListener((final Button.ClickEvent event) -> {
        });
        return result;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Wrappers, builders, popups windows">
    protected Component createContentWrapper(final Component content, Map<String, MenuBar.Command> panelCommands) {
        final CssLayout slot = new CssLayout();
        slot.setWidth(100, Unit.PERCENTAGE);
        slot.addStyleName("dashboard-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth(100, Unit.PERCENTAGE);
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth(100, Unit.PERCENTAGE);

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption("");

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        max = tools.addItem("", FontAwesome.EXPAND, new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setIcon(FontAwesome.COMPRESS);
                    toggleMaximized(slot, viewMaximized = true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setIcon(FontAwesome.EXPAND);
                    toggleMaximized(slot, viewMaximized = false);
                }
            }
        });
        max.setStyleName("icon-only");

        MenuBar.MenuItem panelOptions = tools.addItem("", FontAwesome.COG, null);

        // pomocna varijabla za iscrtavanje separator linije
        int separatorInd = 0;
        if (panelCommands == null) {
            panelCommands = new HashMap<>();

            panelCommands.put("Last two months period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
                dateInterval.setMonthsBackForth(-1);
            });

            panelCommands.put("Help", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
                Notification.show("Help", "Select appropriate date interval\nto narrow selection.", Notification.Type.ERROR_MESSAGE);
            });
        }
        for (Map.Entry<String, MenuBar.Command> ES : panelCommands.entrySet()) {
            if (separatorInd++ > 0) {
                panelOptions.addSeparator();
            }

            panelOptions.addItem(ES.getKey(), ES.getValue());
        }

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

        card.addComponents(toolbar, content);
        slot.addComponent(card);

        return slot;
    }

    protected Component createContentWrapper(final Component content) {
        Map<String, MenuBar.Command> PC = new HashMap<>();
        MenuBar.Command dcl = (MenuBar.MenuItem selectedItem) -> {
            Notification.show("Info", "Not yet implemented.\nAny suggestions are welcomed.", Notification.Type.ERROR_MESSAGE);
        };
        PC.put("Help", dcl);
        PC.put("Close", dcl);

        return createContentWrapper(content, PC);
    }

    protected Component createPanelComponent(String caption, List<Panel> panels, boolean formEditAllowed, Map<String, MenuBar.Command> PC) {
        VerticalLayout componentRootVL = new VerticalLayout();

        componentRootVL.setCaption(caption);
        Layout_InlineCSS ICL = new Layout_InlineCSS();
        ICL.setSizeFull();

        for (Panel p : panels) {
            p.setWidth(275, Unit.PIXELS);

            HorizontalLayout HL = new HorizontalLayout(p);
            HL.setMargin(true);
            ICL.addComponent(HL);
        }

        componentRootVL.addComponent(ICL);

        try {
            panels.clear();
        } catch (Exception e) {
        }

        return createContentWrapper(componentRootVL, PC);
    }

    protected Component createPanelComponent(String caption, List<Panel> panels, boolean formEditAllowed) {
        return createPanelComponent(caption, panels, formEditAllowed, null);
    }

    protected Component createNotesPanel(String notesContent) {
        TextArea textArea = new TextArea("NOTES");
        textArea.setValue(notesContent);
        textArea.setSizeFull();
        textArea.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        Component panel = createContentWrapper(textArea);
        panel.addStyleName("notes");

        return panel;
    }

    protected final void buildContentWithComponents(Component... components) {
        buildContentWithComponents(Arrays.asList(components));
    }

    protected final void buildContentWithComponents(List<Component> components) {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);

        for (Component c : components) {
            dashboardPanels.addComponent(c);
        }

        root.addComponent(dashboardPanels);
        root.setExpandRatio(dashboardPanels, 1);
    }

    /**
     *
     * @param panel Panel u kome kreiramo podpanele sa stablima koji
     * reprezentuju prodaje, slučajeve, i slično.
     * @param oldComponent Panel pre poziva komande panela
     * @param newComponent Panel posle poziva komande panela
     */
    protected void updateUIPanel(CssLayout panel, Component oldComponent, Component newComponent) {
        panel.replaceComponent(oldComponent, newComponent);

        toggleMaximized(newComponent, viewMaximized);
    }

    protected void updateUIPanel(int dynamicCasesPanelIndex, Component newComponent) {
        dashboardPanels.replaceComponent(dynamicPanels.get(dynamicCasesPanelIndex), newComponent);

        dynamicPanels.set(dynamicCasesPanelIndex, newComponent);

        toggleMaximized(newComponent, viewMaximized);
    }

    protected void openNotificationsPopup(final Button.ClickEvent event) {
        VerticalLayout notificationsLayout = new VerticalLayout();
        notificationsLayout.setMargin(true);
        notificationsLayout.setSpacing(true);

        Label title = new Label("Notifications");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        notificationsLayout.addComponent(title);

        List<String> notifications = new ArrayList(Arrays.asList(
                "Poruka1", "Poruka2", "Poruka3", "Poruka4", "Poruka5", "Poruka6",
                "Poruka7", "Poruka8", "Poruka9", "Poruka10", "Poruka11", "Poruka12"
        ));

        for (String n : notifications) {
            VerticalLayout notificationLayout = new VerticalLayout();
            notificationLayout.addStyleName("notification-item");

            Label titleLabel1 = new Label("Title..");
            titleLabel1.addStyleName("notification-title");

            Label timeLabel = new Label("timeLabel");
            timeLabel.addStyleName("notification-time");

            Label contentLabel = new Label("notification-content");
            contentLabel.addStyleName("notification-content");

            notificationLayout.addComponents(titleLabel1, timeLabel,
                    contentLabel);
            notificationsLayout.addComponent(notificationLayout);
        }

        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100, Unit.PERCENTAGE);
        Button showAll = new Button("View All Notifications", (final Button.ClickEvent event1) -> {
            Notification.show("Not implemented in this demo");
        });
        showAll.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
        showAll.addStyleName(ValoTheme.BUTTON_SMALL);
        footer.addComponent(showAll);
        footer.setComponentAlignment(showAll, Alignment.TOP_CENTER);
        notificationsLayout.addComponent(footer);

        if (notificationsWindow == null) {
            notificationsWindow = new Window();
            notificationsWindow.setWidth(300.0f, Sizeable.Unit.PIXELS);
            notificationsWindow.addStyleName("notifications");
            notificationsWindow.setClosable(false);
            notificationsWindow.setResizable(false);
            notificationsWindow.setDraggable(false);
            notificationsWindow.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
            notificationsWindow.setContent(notificationsLayout);
        }

        if (!notificationsWindow.isAttached()) {
            notificationsWindow.setPositionY(event.getClientY()
                    - event.getRelativeY() + 40);
            getUI().addWindow(notificationsWindow);
            notificationsWindow.focus();
        } else {
            notificationsWindow.close();
        }
    }

    protected void toggleMaximized(final Component panel, final boolean maximized) {
        for (Iterator<Component> it = root.iterator(); it.hasNext();) {
            it.next().setVisible(!maximized);
        }
        dashboardPanels.setVisible(true);

        for (Component c : dashboardPanels) {
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }

    public static final class NotificationsButton extends Button {

        private static final String STYLE_UNREAD = "unread";
        public static final String ID = "dashboard-notifications";

        public NotificationsButton() {
            setIcon(FontAwesome.BELL);
            setId(ID);
            addStyleName("notifications");
            addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        }

        public void setUnreadCount(final int count) {
            setCaption(String.valueOf(count));

            String description = "Notifications";
            if (count > 0) {
                addStyleName(STYLE_UNREAD);
                description += " (" + count + " unread)";
            } else {
                removeStyleName(STYLE_UNREAD);
            }
            setDescription(description);
        }
    }
    //</editor-fold>

    @Override
    public void enter(final ViewChangeEvent event) {
    }

}
