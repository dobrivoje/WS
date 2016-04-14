package Uni.MainMenu;

import com.vaadin.event.ItemClickEvent;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Tree;
import com.vaadin.ui.themes.ValoTheme;
import Forms.CDM.Form_Customer;
import Forms.CDM.Form_RELCBT;
import Forms.CRM.Form_CRMCase;
import Forms.CRM.Form_CRMProcess;
import Forms.CRM.Form_CRMSell;
import Forms.CRM.Form_SCR;
import Uni.Menu.Menu;
import static Uni.MainMenu.MenuDefinitions.CUST_CRM_MANAG;
import static Uni.MainMenu.MenuDefinitions.CUST_DATA_MANAG;
import static Uni.MainMenu.MenuDefinitions.CUST_DATA_MANAG_SEARCH_ENGINE;
import static Uni.MainMenu.MenuDefinitions.CUST_DATA_MANAG_CUST_DOCS;
import static Uni.MainMenu.MenuDefinitions.CUST_DATA_MANAG_NEW_CBT;
import static Uni.MainMenu.MenuDefinitions.CUST_DATA_MANAG_NEW_CUST;
import static Uni.MainMenu.MenuDefinitions.FS_DATA_MANAG;
import static Uni.MainMenu.MenuDefinitions.FS_DATA_MANAG_PROPERTIES;
import static Uni.MainMenu.MenuDefinitions.FS_DATA_MANAG_DOCS;
import static Uni.MainMenu.MenuDefinitions.FS_DATA_MANAG_NEW_FS;
import static Uni.MainMenu.MenuDefinitions.SYS_NOTIF_BOARD;
import static Uni.MainMenu.MenuDefinitions.SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST;
import static Uni.MainMenu.MenuDefinitions.SYS_NOTIF_BOARD_LICENCES_OVERDUE;
import Views.CDM.View_Customers;
import Forms.FSM.Form_FS;
import Forms.FSM.Form_FSOwner;
import static Uni.MainMenu.MenuDefinitions.CRM_MANAG_NEW_PROCESS;
import static Uni.MainMenu.MenuDefinitions.CRM_MANAG_NEW_SALESMAN_CUST_REL;
import static Uni.MainMenu.MenuDefinitions.CRM_MANAG_NEW_CASE;
import static Uni.MainMenu.MenuDefinitions.FS_DATA_MANAG_NEW_FS_OWNER;
import static Uni.MainMenu.MenuDefinitions.SELLS;
import static Uni.MainMenu.MenuDefinitions.CRM_NEW_SALE;
import Views.CRM.View_CRM;
import Views.FSDM.View_FS;
import Views.SYSNOTIF.View_SysNotif;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import static enums.REPORTS.REPORT1;
import gen.ReportGenerator1;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.dobrivoje.auth.roles.RolesPermissions;
import org.superb.apps.utilities.Enums.LOGS;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import Main.MyUI;
import static Main.MyUI.DS;
import static Uni.MainMenu.MenuDefinitions.CRM_EXISTING_SELL_CASES;
import static Uni.MainMenu.MenuDefinitions.CUST_DATA_MANAG_CUST_NAV_SYNC;
import static Uni.MainMenu.MenuDefinitions.DATAMAINTENANCE;
import static Uni.MainMenu.MenuDefinitions.DATA_MAINTENANCE_NAV_CUST_SYNC;
import static Uni.MainMenu.MenuDefinitions.DATA_MAINTENANCE_NAV_SELLS_SYNC;
import static Uni.MainMenu.MenuDefinitions.SELLS_DYNAMIC;
import Views.CDM.View_CDM_NAV;
import Views.SELLS.View_SELLS;
import Views.SELLS.View_SELLS_Dynamic;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CUSTOMERS_EXCEL_IMPORT;
import static org.dobrivoje.auth.roles.RolesPermissions.P_SELLS_EXCEL_IMPORT;
import static org.dobrivoje.auth.roles.RolesPermissions.R_FUELSALES_MANAGER;

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

    public MainMenu(final Navigator navigator) {
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
        // Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        // image.setStyleName("logo");
        // top.addComponent(image);
        top.addComponent(title);
        menuPart.addComponent(top);

        // logout menu item
        String ISU;
        String S;

        try {
            ISU = MyUI.get().getLoggedISUser().getUserName();
            Logger.getLogger("test1").log(
                    Level.INFO,
                    "WS APP - Uni.MainMenu.MainMenu() : MyUI.get().getLoggedISUser().getUserName() : {0}", MyUI.get().getLoggedISUser().getUserName());
        } catch (Exception e) {
            ISU = MyUI.get().accessControl.getPrincipal();
            Logger.getLogger("test2").log(
                    Level.INFO,
                    "WS APP - Uni.MainMenu.MainMenu() : MyUI.get().accessControl.getPrincipal() : {0}", MyUI.get().accessControl.getPrincipal());
        }

        S = MyUI.get().getLoggedSalesman().toString();

        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem("Logout " + (MyUI.get().getLoggedSalesman().getName().isEmpty() ? ISU : S),
                FontAwesome.SIGN_OUT, (MenuItem selectedItem) -> {
                    try {
                        DS.getLOGController().addNew(
                                new Date(),
                                LOGS.LOGOUT.toString(),
                                MyUI.get().getLoggedISUser().getUserName(),
                                MyUI.get().getLoggedISUser()
                        );
                    } catch (Exception ex) {
                        Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, "LOGOUT - ERROR : ".concat(ex.getMessage()), ex);
                    }

                    /* pre izmene inspirisano izmenama u MF-u 23.1.2016 !
                     MyUI.get().getAccessControl().logout();
                    
                     System.err.println("logout sessions : " + MyUI.get().accessControl.getUsersSessions());
                    
                     // VEOMA VAŽNO !!!
                     // ONEMOGUĆAVANJE KODA : VaadinSession.getCurrent().getSession().invalidate();,
                     // DOVODI DO TOGA DA SE NA ISTOM RAČUNARU
                     // NA KOME SE LOGUJE VIŠE PUTA ISTI KORISNIK POSLE LOGOUT-A, OSTALI
                     // KORISNICI I DALJE MOGU DA RADE !!!
                     VaadinSession.getCurrent().getSession().invalidate();
                     Page.getCurrent().reload();
                    
                     */
                    // izmena 23.1.2016
                    //
                    VaadinSession.getCurrent().getSession().invalidate();
                    MyUI.get().getAccessControl().logout();
                    Page.getCurrent().reload();
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
        Button button = new Button(caption, (ClickEvent event) -> {
            navigator.navigateTo(name);
        });
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        menuItemsLayout.addComponent(button);
        viewButtons.put(name, button);
    }

    public void createViewTree(final String name) {
        Tree customersTree = new Tree();

        //<editor-fold defaultstate="collapsed" desc="Menu UI Defs">
        customersTree.addItems(Menu.getDefault().getAllMenuItems());

        customersTree.setChildrenAllowed(SYS_NOTIF_BOARD, true);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG, true);
        customersTree.setChildrenAllowed(SELLS, true);
        customersTree.setChildrenAllowed(CUST_CRM_MANAG, true);
        customersTree.setChildrenAllowed(FS_DATA_MANAG, true);
        customersTree.setChildrenAllowed(DATAMAINTENANCE, true);

        // definicije gl. stavki čije su podstavke otvorene !
        customersTree.expandItemsRecursively(CUST_CRM_MANAG);
        customersTree.expandItemsRecursively(SELLS);
        customersTree.expandItemsRecursively(DATAMAINTENANCE);

        // SYS_NOTIF_BOARD
        customersTree.setParent(SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST, SYS_NOTIF_BOARD);
        customersTree.setParent(SYS_NOTIF_BOARD_LICENCES_OVERDUE, SYS_NOTIF_BOARD);
        customersTree.setChildrenAllowed(SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST, false);
        customersTree.setChildrenAllowed(SYS_NOTIF_BOARD_LICENCES_OVERDUE, false);

        // CUST_DATA_MANAG
        customersTree.setParent(CUST_DATA_MANAG_SEARCH_ENGINE, CUST_DATA_MANAG);
        customersTree.setParent(CUST_DATA_MANAG_NEW_CUST, CUST_DATA_MANAG);
        customersTree.setParent(CUST_DATA_MANAG_NEW_CBT, CUST_DATA_MANAG);
        customersTree.setParent(CUST_DATA_MANAG_CUST_DOCS, CUST_DATA_MANAG);

        customersTree.setChildrenAllowed(CUST_DATA_MANAG_SEARCH_ENGINE, false);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG_NEW_CUST, false);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG_NEW_CBT, false);
        customersTree.setChildrenAllowed(CUST_DATA_MANAG_CUST_DOCS, true);

        // CRM
        customersTree.setParent(CRM_MANAG_NEW_CASE, CUST_CRM_MANAG);
        customersTree.setParent(CRM_MANAG_NEW_PROCESS, CUST_CRM_MANAG);
        customersTree.setParent(CRM_MANAG_NEW_SALESMAN_CUST_REL, CUST_CRM_MANAG);
        customersTree.setParent(CRM_NEW_SALE, CUST_CRM_MANAG);
        customersTree.setParent(CRM_EXISTING_SELL_CASES, CUST_CRM_MANAG);

        customersTree.setChildrenAllowed(CRM_MANAG_NEW_SALESMAN_CUST_REL, false);
        customersTree.setChildrenAllowed(CRM_MANAG_NEW_CASE, false);
        customersTree.setChildrenAllowed(CRM_MANAG_NEW_PROCESS, false);
        customersTree.setChildrenAllowed(CRM_NEW_SALE, false);
        customersTree.setChildrenAllowed(CRM_EXISTING_SELL_CASES, false);

        //SELLS
        customersTree.setParent(SELLS_DYNAMIC, SELLS);
        customersTree.setChildrenAllowed(SELLS_DYNAMIC, false);

        // FS MANAGEMENT
        customersTree.setParent(FS_DATA_MANAG_NEW_FS, FS_DATA_MANAG);
        customersTree.setParent(FS_DATA_MANAG_NEW_FS_OWNER, FS_DATA_MANAG);
        customersTree.setParent(FS_DATA_MANAG_PROPERTIES, FS_DATA_MANAG);
        customersTree.setParent(FS_DATA_MANAG_DOCS, FS_DATA_MANAG);

        customersTree.setChildrenAllowed(FS_DATA_MANAG_PROPERTIES, false);
        customersTree.setChildrenAllowed(FS_DATA_MANAG_NEW_FS, false);
        customersTree.setChildrenAllowed(FS_DATA_MANAG_NEW_FS_OWNER, false);
        customersTree.setChildrenAllowed(FS_DATA_MANAG_DOCS, false);

        // DATA MAINTENANCE
        customersTree.setParent(DATA_MAINTENANCE_NAV_CUST_SYNC, DATAMAINTENANCE);
        customersTree.setParent(DATA_MAINTENANCE_NAV_SELLS_SYNC, DATAMAINTENANCE);

        customersTree.setChildrenAllowed(DATA_MAINTENANCE_NAV_CUST_SYNC, false);
        customersTree.setChildrenAllowed(DATA_MAINTENANCE_NAV_SELLS_SYNC, false);
        //</editor-fold>

        customersTree.addItemClickListener((ItemClickEvent event) -> {
            // proveri da li je sesija aktivna, pa ako nije, izbaci poruku
            try {
                // metod "getPrincipal" se ovde kao test da se utvrdi
                // da li je accessControl aktivan.
                MyUI.get().getAccessControl().getPrincipal();

                switch ((MenuDefinitions) (event.getItemId())) {
                    case SYS_NOTIF_BOARD_LICENCES_OVERDUE:

                        try {
                            // new ReportGenerator1().exportPDF(REPORT1.toString(), "\\\\d007012iml\\Users\\dprtenjak\\Desktop\\izvestaj.pdf");
                            new ReportGenerator1().generateReportWithPopUpDialog(REPORT1.toString(), "\\\\GLADIATOR\\Users\\Public\\Downloads\\izvestaj.pdf");

                            Logger.getLogger("Views.MainMenu.MainMenu.java")
                                    .log(Level.INFO, "Jasper uspe\u0161no izvr\u0161en : {0}", (new Date()).toString());
                        } catch (Exception ex) {
                            Logger.getLogger("Views.MainMenu.MainMenu.java / public void createViewTree(final String name)").log(Level.WARNING, ex.getMessage());
                        }
                        break;

                    case SYS_NOTIF_BOARD:
                        navigator.navigateTo(View_SysNotif.class.getSimpleName());
                        break;

                    case CUST_DATA_MANAG_SEARCH_ENGINE:
                        navigator.navigateTo(View_Customers.class.getSimpleName());
                        break;

                    case CUST_DATA_MANAG_NEW_CUST:
                        if (MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_NEW_CUSTOMER)) {
                            navigator.navigateTo(View_Customers.class.getSimpleName());

                            Form_Customer cf = new Form_Customer(false);

                            getUI().addWindow(new WindowForm3(
                                    CUST_DATA_MANAG_NEW_CUST.toString(),
                                    cf,
                                    495, 750, Unit.PIXELS,
                                    "img/crm/crm-user-3.png", "Save",
                                    cf.getClickListener(), 194, 236, false)
                            );

                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case CUST_DATA_MANAG_NEW_CBT:
                        if (MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_NEW_CBT)) {

                            Form_RELCBT rcbtf = new Form_RELCBT(false);

                            getUI().addWindow(new WindowForm3(
                                    "New Customer Business Type",
                                    rcbtf,
                                    495, 750, Unit.PIXELS,
                                    "img/crm/cbt.png", "Save",
                                    rcbtf.getClickListener(), 216, 236, false)
                            );

                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer bussines type !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case CUST_DATA_MANAG_CUST_NAV_SYNC:
                        if (MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EXCEL_IMPORT)) {
                            navigator.navigateTo(View_CDM_NAV.class.getSimpleName());
                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer relationship !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case CRM_NEW_SALE:
                        if (MyUI.get().isPermitted(RolesPermissions.P_CRM_NEW_CRM_PROCESS)) {
                            Form_CRMSell csf = new Form_CRMSell(MyUI.get().getLoggedSalesman());

                            getUI().addWindow(new WindowForm3(
                                    CRM_NEW_SALE.toString(),
                                    csf,
                                    495, 750, Unit.PIXELS,
                                    "img/crm/sell.png", "Save",
                                    csf.getClickListener(), 196, 236, false)
                            );

                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer bussines type !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case CUST_CRM_MANAG:
                        navigator.navigateTo(View_CRM.class.getSimpleName());
                        break;

                    case CRM_MANAG_NEW_CASE:
                        if (MyUI.get().isPermitted(RolesPermissions.P_CRM_NEW_CRM_PROCESS)) {

                            Form_CRMCase ccf = new Form_CRMCase(MyUI.get().getLoggedSalesman(), true);

                            getUI().addWindow(new WindowForm3(
                                    CRM_MANAG_NEW_CASE.toString(),
                                    ccf,
                                    495, 750, Unit.PIXELS,
                                    "img/crm/crm-case-new.png", "Save",
                                    ccf.getClickListener(), 253, 300, false)
                            );

                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer case !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case CRM_MANAG_NEW_PROCESS:
                        if (MyUI.get().isPermitted(RolesPermissions.P_CRM_NEW_CRM_PROCESS)) {
                            try {
                                Form_CRMProcess cpf = new Form_CRMProcess(MyUI.get().getLoggedSalesman());

                                getUI().addWindow(new WindowForm3(
                                        CRM_MANAG_NEW_PROCESS.toString(),
                                        cpf,
                                        495, 750, Unit.PIXELS,
                                        "img/crm/crm5.png", "Save",
                                        cpf.getClickListener(), 225, 300, false)
                                );

                            } catch (NullPointerException | IllegalArgumentException ex) {
                            }
                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer process !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case CRM_MANAG_NEW_SALESMAN_CUST_REL:
                        if (MyUI.get().isPermitted(RolesPermissions.P_CRM_NEW_SC_REL)) {
                            Form_SCR scf = new Form_SCR(false);

                            getUI().addWindow(new WindowForm3(
                                    CRM_MANAG_NEW_SALESMAN_CUST_REL.toString(),
                                    scf,
                                    495, 750, Unit.PIXELS,
                                    "img/crm/new-sc-rel2.jpg", "Save",
                                    scf.getClickListener(), 256, 230, false)
                            );

                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer relationship !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case SELLS_DYNAMIC:
                        if (MyUI.get().hasRole(R_FUELSALES_MANAGER)) {
                            navigator.navigateTo(View_SELLS_Dynamic.class.getSimpleName());
                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer relationship !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case FS_DATA_MANAG_NEW_FS:
                        if (MyUI.get().isPermitted(RolesPermissions.P_FUELSALES_USER_FS_NEW_STATION)) {
                            navigator.navigateTo(View_FS.class.getSimpleName());

                            Form_FS fof = new Form_FS(false);

                            getUI().addWindow(new WindowForm3(
                                    FS_DATA_MANAG_NEW_FS.toString(),
                                    fof,
                                    495, 750, Unit.PIXELS,
                                    "img/crm/cbt.png", "Save",
                                    fof.getClickListener(), 216, 236, false)
                            );

                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto add new fuelstation !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case FS_DATA_MANAG_NEW_FS_OWNER:
                        if (MyUI.get().isPermitted(RolesPermissions.P_FUELSALES_USER_FS_NEW_OWNER)) {

                            Form_FSOwner fof = new Form_FSOwner(false);

                            getUI().addWindow(new WindowForm3(
                                    FS_DATA_MANAG_NEW_FS_OWNER.toString(),
                                    fof,
                                    495, 750, Unit.PIXELS,
                                    "img/crm/cbt.png", "Save",
                                    fof.getClickListener(), 216, 236, false)
                            );

                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto appoint fuelstation to the customer !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case FS_DATA_MANAG_PROPERTIES:
                        navigator.navigateTo(View_FS.class.getSimpleName());
                        break;

                    case DATA_MAINTENANCE_NAV_CUST_SYNC:
                        if (MyUI.get().isPermitted(P_CUSTOMERS_EXCEL_IMPORT)) {
                            navigator.navigateTo(View_CDM_NAV.class.getSimpleName());
                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer relationship !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                    case DATA_MAINTENANCE_NAV_SELLS_SYNC:
                        if (MyUI.get().isPermitted(P_SELLS_EXCEL_IMPORT)) {
                            navigator.navigateTo(View_SELLS.class.getSimpleName());
                        } else {
                            Notification.show("User Rights Error", "You don't have rights \nto create new customer relationship !", Notification.Type.ERROR_MESSAGE);
                        }
                        break;

                }

            } catch (IllegalArgumentException | NullPointerException | ExpiredSessionException | UnknownSessionException e) {
                Notification.show(
                        "Session Expiration",
                        "Your current session has expired.\nPlease logout, and login again.",
                        Notification.Type.ERROR_MESSAGE
                );

            } catch (Exception e1) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, e1);
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
