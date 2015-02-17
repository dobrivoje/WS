package Views.MainMenu;

import com.vaadin.event.ItemClickEvent;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Tree;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.utilities.Enums.CrudOperations;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import Forms.CDM.CBTForm;
import Forms.CDM.CustomerForm;
import Menu.Menu;
import Menu.MenuDefinitions;
import static Menu.MenuDefinitions.CUST_CRM_MANAG;
import static Menu.MenuDefinitions.CUST_DATA_MANAG;
import static Menu.MenuDefinitions.CUST_DATA_MANAG_BROWSER;
import static Menu.MenuDefinitions.CUST_DATA_MANAG_CBT_LIST;
import static Menu.MenuDefinitions.CUST_DATA_MANAG_CUST_DOCS;
import static Menu.MenuDefinitions.CUST_DATA_MANAG_NEW_CBT;
import static Menu.MenuDefinitions.CUST_DATA_MANAG_NEW_CUST;
import static Menu.MenuDefinitions.FS_DATA_MANAG;
import static Menu.MenuDefinitions.FS_DATA_MANAG_BROWSER;
import static Menu.MenuDefinitions.FS_DATA_MANAG_DOCS;
import static Menu.MenuDefinitions.FS_DATA_MANAG_IMAGES;
import static Menu.MenuDefinitions.FS_DATA_MANAG_NEW_FS;
import static Menu.MenuDefinitions.SYS_NOTIF_BOARD;
import static Menu.MenuDefinitions.SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST;
import static Menu.MenuDefinitions.SYS_NOTIF_BOARD_LICENCES_OVERDUE;
import db.ent.CustomerBussinesType;
import Views.MainMenu.CDM.CustomersView;
import Forms.FSM.FSForm;
import Forms.FSM.FSOWNER_Form;
import static Menu.MenuDefinitions.FS_DATA_MANAG_NEW_FS_OWNER;
import Views.MainMenu.FSDM.FSView;

/**
 * Responsive navigation menu presenting a list of available views to the user.
 */
public class MainMenu extends CssLayout {

    private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
    private final Navigator navigator;
    private final Map<String, Button> viewButtons = new HashMap<>();
    private final Map<String, Tree> viewTrees = new HashMap<>();

    private final CssLayout menuItemsLayout;
    private final CssLayout menuPart;

    public MainMenu(Navigator navigator) {
        this.navigator = navigator;
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);

        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
        top.setSpacing(true);
        Label title = new Label("App Management");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();
        Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        image.setStyleName("logo");
        top.addComponent(image);
        top.addComponent(title);
        menuPart.addComponent(top);

        // logout menu item
        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem("Logout", FontAwesome.SIGN_OUT, new Command() {
            @Override
            public void menuSelected(MenuItem selectedItem) {
                VaadinSession.getCurrent().getSession().invalidate();
                Page.getCurrent().reload();
            }
        });

        logoutMenu.addStyleName("user-menu");
        menuPart.addComponent(logoutMenu);

        // container for the navigation buttons, which are added by addView()
        menuItemsLayout = new CssLayout();
        menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
        menuPart.addComponent(menuItemsLayout);

        addComponent(menuPart);
    }

    // Register a pre-created view instance in the navigation menu and in the
    // Navigator}.
    public void addView(View view, final String name, String caption, Resource icon) {
        navigator.addView(name, view);
        createViewButton(name, caption, icon);
    }

    public void addViewButton(View view, final String name, String caption, Resource icon) {
        navigator.addView(name, view);
        createViewButton(name, caption, icon);
    }

    public void addViewTree(View view, final String name, String caption) {
        navigator.addView(name, view);
        createViewTree(name);
    }

    private void createViewButton(final String name, String caption, Resource icon) {
        Button button = new Button(caption, new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                navigator.navigateTo(name);
            }
        });
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        menuItemsLayout.addComponent(button);
        viewButtons.put(name, button);
    }

    private void createViewTree(final String name) {
        Tree customersTree = new Tree();
        //<editor-fold defaultstate="collapsed" desc="Menu UI Defs">
        customersTree.addItems(Menu.getDefault().getAllMenuItems());

        customersTree.expandItemsRecursively(CUST_DATA_MANAG);
        customersTree.expandItemsRecursively(FS_DATA_MANAG);

        customersTree.setChildrenAllowed(SYS_NOTIF_BOARD, true);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG, true);
        customersTree.setChildrenAllowed(CUST_CRM_MANAG, true);
        customersTree.setChildrenAllowed(FS_DATA_MANAG, true);

        customersTree.setParent(SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST, SYS_NOTIF_BOARD);
        customersTree.setParent(SYS_NOTIF_BOARD_LICENCES_OVERDUE, SYS_NOTIF_BOARD);
        customersTree.setChildrenAllowed(SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST, false);
        customersTree.setChildrenAllowed(SYS_NOTIF_BOARD_LICENCES_OVERDUE, false);

        customersTree.setParent(CUST_DATA_MANAG_BROWSER, CUST_DATA_MANAG);
        customersTree.setParent(CUST_DATA_MANAG_NEW_CUST, CUST_DATA_MANAG);
        customersTree.setParent(CUST_DATA_MANAG_CUST_DOCS, CUST_DATA_MANAG);

        customersTree.setChildrenAllowed(CUST_DATA_MANAG_BROWSER, false);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG_NEW_CUST, false);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG_NEW_CBT, false);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG_CBT_LIST, false);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG_CUST_DOCS, true);

        customersTree.setParent(FS_DATA_MANAG_BROWSER, FS_DATA_MANAG);
        customersTree.setParent(FS_DATA_MANAG_NEW_FS, FS_DATA_MANAG);
        customersTree.setParent(FS_DATA_MANAG_NEW_FS_OWNER, FS_DATA_MANAG);
        customersTree.setParent(FS_DATA_MANAG_IMAGES, FS_DATA_MANAG);
        customersTree.setParent(FS_DATA_MANAG_DOCS, FS_DATA_MANAG);

        customersTree.setChildrenAllowed(FS_DATA_MANAG_BROWSER, false);
        customersTree.setChildrenAllowed(FS_DATA_MANAG_NEW_FS, false);
        customersTree.setChildrenAllowed(FS_DATA_MANAG_NEW_FS_OWNER, false);
        customersTree.setChildrenAllowed(FS_DATA_MANAG_IMAGES, false);
        customersTree.setChildrenAllowed(FS_DATA_MANAG_DOCS, false);
        //</editor-fold>

        customersTree.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                switch ((MenuDefinitions) (event.getItemId())) {
                    case CUST_DATA_MANAG_BROWSER:
                        navigator.navigateTo(CustomersView.class.getSimpleName());
                        break;
                    case CUST_DATA_MANAG_NEW_CUST:
                        navigator.navigateTo(CustomersView.class.getSimpleName());
                        getUI().addWindow(new WindowForm(CUST_DATA_MANAG_NEW_CUST.toString(), new CustomerForm(CrudOperations.CREATE)));
                        ;
                        break;
                    case CUST_DATA_MANAG_NEW_CBT:
                        getUI().addWindow(new WindowForm(CUST_DATA_MANAG_CBT_LIST.toString(), new CBTForm(new CustomerBussinesType())));
                        ;
                        break;
                    case FS_DATA_MANAG_NEW_FS:
                        navigator.navigateTo(FSView.class.getSimpleName());
                        getUI().addWindow(new WindowForm(FS_DATA_MANAG_NEW_FS.toString(), new FSForm(CrudOperations.CREATE)));
                        break;
                    case FS_DATA_MANAG_NEW_FS_OWNER:
                        getUI().addWindow(new WindowForm(FS_DATA_MANAG_NEW_FS_OWNER.toString(), new FSOWNER_Form(CrudOperations.CREATE)));
                        break;
                    case FS_DATA_MANAG_BROWSER:
                        navigator.navigateTo(FSView.class.getSimpleName());
                        ;
                        break;

                }
            }
        });

        customersTree.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        menuItemsLayout.addComponent(customersTree);
        viewTrees.put(name, customersTree);
    }

    /**
     * Highlights a view navigation button as the currently active view in the
     * menu. This method does not perform the actual navigation.
     *
     * @param viewName the name of the view to show as active
     */
    public void setActiveViewButton(String viewName) {
        for (Button button : viewButtons.values()) {
            button.removeStyleName("selected");
        }
        Button selected = viewButtons.get(viewName);
        if (selected != null) {
            selected.addStyleName("selected");
        }
        menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }

    public void setActiveTreeView(String viewName) {
        for (Tree subTree : viewTrees.values()) {
            subTree.removeStyleName("selected");
        }
        Tree selected = viewTrees.get(viewName);
        if (selected != null) {
            selected.addStyleName("selected");
        }
        menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }
}