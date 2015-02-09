/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.MainMenu;

/**
 *
 * @author root
 */
public enum MenuDefinitions {

    // Main menu options
    SYS_NOTIF_BOARD("System Notifications Board"),
    CUST_DATA_MANAG("Customers Data Management"),
    CUST_CRM_MANAG("Customers CRM Management"),
    FS_DATA_MANAG("FS Data Management"),
    // Submenus
    SYS_NOTIF_BOARD_CUSTOMERS_BLACKLIST("Customer Blacklist"),
    SYS_NOTIF_BOARD_LICENCES_OVERDUE("Licences Overdue"),
    //
    CUST_DATA_MANAG_ALL_CUST("List of All Customers"),
    CUST_DATA_MANAG_NEW_CUST("New Customers"),
    CUST_DATA_MANAG_NEW_CBT("New Customer Bussines Type"),
    CUST_DATA_MANAG_CBT_LIST("Customer Bussines Type List"),
    CUST_DATA_MANAG_CUST_FS("Customer's FS"),
    CUST_DATA_MANAG_CUST_DOCS("Customer's Documents"),
    //
    FS_DATA_MANAG_IMAGES("FS's Images"),
    FS_DATA_MANAG_DOCS("FS's Documents");

    private final String menuItem;

    private MenuDefinitions(String menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public String toString() {
        return menuItem;
    }
}
