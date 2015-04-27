/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author root
 */
public enum MenuDefinitions {

    //
    // Main menu options
    //
    SYS_NOTIF_BOARD("System Notifications Board"),
    CUST_DATA_MANAG("Customers Management"),
    CUST_CRM_MANAG("CRM Management"),
    FS_DATA_MANAG("Fuelstations Management"),
    //
    // Submenus
    //
    SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST("Customer Blacklist"),
    SYS_NOTIF_BOARD_LICENCES_OVERDUE("Licences Overdue"),
    //
    CUST_DATA_MANAG_SEARCH_ENGINE("Customer Search Engine"),
    CUST_DATA_MANAG_NEW_CUST("New Customer"),
    CUST_DATA_MANAG_NEW_CBT("New Customer Bussines Type"),
    CUST_DATA_MANAG_CBT_LIST("Customer Bussines Type List"),
    CUST_DATA_MANAG_CUST_DOCS("Customer's Documents"),
    //
    CRM_MANAG_ACTIVE_CASES("Active CRM Cases"),
    CRM_MANAG_NEW_CASE("New CRM Case"),
    CRM_MANAG_NEW_PROCESS("New CRM Process"),
    CRM_MANAG_NEW_SALESMAN_CUST_REL("New SC Relationship"),
    CRM_MANAG_EXISTING_SALESMAN_CUST_REL("Existing SC Relationship"),
    //
    FS_DATA_MANAG_SEARCH_ENGINE("Fuelstation Search Engine"),
    FS_DATA_MANAG_NEW_FS("New Fuelstation"),
    FS_DATA_MANAG_NEW_FS_OWNER("New Fuelstation Owner"),
    FS_DATA_MANAG_DOCS("Fuelstation's Documents");

    private final String menuItem;

    private MenuDefinitions(String menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public String toString() {
        return menuItem;
    }

    public static List<MenuDefinitions> get_MainMenuItems() {
        return new ArrayList<>(Arrays.asList(
                SYS_NOTIF_BOARD,
                CUST_DATA_MANAG,
                CUST_CRM_MANAG,
                FS_DATA_MANAG
        ));
    }

    public static List<MenuDefinitions> get_SYS_NOTIF_BOARD_SubItems() {
        return new ArrayList<>(Arrays.asList(
                SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST,
                SYS_NOTIF_BOARD_LICENCES_OVERDUE
        ));
    }

    public static List<MenuDefinitions> get_CUSTOMER_SubItems() {
        return new ArrayList<>(Arrays.asList(
                CUST_DATA_MANAG_SEARCH_ENGINE,
                CUST_DATA_MANAG_NEW_CUST,
                CUST_DATA_MANAG_CUST_DOCS
        ));
    }

    public static List<MenuDefinitions> get_CRM_SubItems() {
        return new ArrayList<>(Arrays.asList(CRM_MANAG_NEW_SALESMAN_CUST_REL,
                CRM_MANAG_NEW_PROCESS,
                CRM_MANAG_NEW_CASE,
                CRM_MANAG_ACTIVE_CASES,
                CRM_MANAG_EXISTING_SALESMAN_CUST_REL
        ));
    }

    public static List<MenuDefinitions> get_FS_SubItems() {
        return new ArrayList<>(Arrays.asList(
                FS_DATA_MANAG_SEARCH_ENGINE,
                FS_DATA_MANAG_NEW_FS,
                FS_DATA_MANAG_NEW_FS_OWNER,
                FS_DATA_MANAG_DOCS
        ));
    }
}
