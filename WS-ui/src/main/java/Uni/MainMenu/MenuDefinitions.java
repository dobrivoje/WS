/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uni.MainMenu;

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
    SELLS("Sells"),
    FS_DATA_MANAG("Fuelstations Management"),
    DATAMAINTENANCE("Data Maintenance"),
    //
    // Submenus
    //
    SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST("Customer Blacklist"),
    SYS_NOTIF_BOARD_LICENCES_OVERDUE("Licences Overdue"),
    //
    CUST_DATA_MANAG_SEARCH_ENGINE("Existing Customers"),
    CUST_DATA_MANAG_NEW_CUST("New Customer"),
    CUST_DATA_MANAG_NEW_CBT("New Bussines Type"),
    CUST_DATA_MANAG_CBT_LIST("Bussines Type List"),
    CUST_DATA_MANAG_CUST_DOCS("Customer Documents"),
    CUST_DATA_MANAG_CUST_NAV_SYNC("NAV Customers Sync"),
    //
    CRM_MANAG_NEW_CASE("New CRM Case"),
    CRM_EXISTING_CASE("Existing CRM Case"),
    CRM_MANAG_NEW_PROCESS("New CRM Activity"),
    CRM_MANAG_EXISTING_PROCESS("Existing CRM Action"),
    CRM_MANAG_NEW_SALESMAN_CUST_REL("New CRM Relationship"),
    CRM_NEW_SALE("New Sell Case"),
    CRM_EXISTING_SELL_CASES("Existing Sell Cases"),
    //
    SELLS_DYNAMIC("Sells Dynamic"),
    //
    FS_DATA_MANAG_NEW_FS("New FS"),
    FS_DATA_MANAG_NEW_FS_OWNER("New FS Owner"),
    FS_DATA_MANAG_PROPERTIES("Existing FSs"),
    FS_DATA_MANAG_DOCS("FS Documents"),
    //
    DATA_MAINTENANCE_NAV_CUST_SYNC("NAV Customers Sync"),
    DATA_MAINTENANCE_NAV_SELLS_SYNC("NAV Sells Sync");

    private final String menuItem;

    private MenuDefinitions(String menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public String toString() {
        return menuItem;
    }

    public static List<MenuDefinitions> get_MainMenuItems() {
        return Arrays.asList(
                SYS_NOTIF_BOARD,
                CUST_DATA_MANAG,
                CUST_CRM_MANAG,
                SELLS,
                FS_DATA_MANAG,
                DATAMAINTENANCE
        );
    }

    public static List<MenuDefinitions> get_SYS_NOTIF_BOARD_SubItems() {
        return Arrays.asList(
                SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST,
                SYS_NOTIF_BOARD_LICENCES_OVERDUE
        );
    }

    public static List<MenuDefinitions> get_CUSTOMER_SubItems() {
        return Arrays.asList(CUST_DATA_MANAG_SEARCH_ENGINE,
                CUST_DATA_MANAG_NEW_CUST,
                CUST_DATA_MANAG_NEW_CBT,
                CUST_DATA_MANAG_CUST_DOCS,
                CUST_DATA_MANAG_CUST_NAV_SYNC
        );
    }

    public static List<MenuDefinitions> get_SALE_SubItems() {
        return Arrays.asList(SELLS_DYNAMIC);
    }

    public static List<MenuDefinitions> get_CRM_SubItems() {
        return Arrays.asList(
                CRM_MANAG_NEW_SALESMAN_CUST_REL,
                CRM_MANAG_NEW_PROCESS,
                CRM_MANAG_NEW_CASE,
                CRM_NEW_SALE,
                CRM_EXISTING_SELL_CASES
        );
    }

    public static List<MenuDefinitions> get_FS_SubItems() {
        return Arrays.asList(
                FS_DATA_MANAG_NEW_FS,
                FS_DATA_MANAG_NEW_FS_OWNER,
                FS_DATA_MANAG_PROPERTIES,
                FS_DATA_MANAG_DOCS
        );
    }

    public static List<MenuDefinitions> get_DATAMAINTENANCE_SubItems() {
        return Arrays.asList(
                DATA_MAINTENANCE_NAV_CUST_SYNC,
                DATA_MAINTENANCE_NAV_SELLS_SYNC
        );
    }
}
