/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.auth.roles;

import java.io.Serializable;

/**
 *
 * @author dobri
 */
public class RolesPermissions implements Serializable {

    public static final String R_CARDS_USER = "CardsUser";
    public static final String R_CARDS_MANAGER = "CardsManager";
    public static final String R_FUELSALES_USER = "FuelsalesUser";
    public static final String R_FUELSALES_MANAGER = "FuelsalesManager";
    public static final String R_USER_LOGIN = "UserLogin";
    public static final String R_ADMIN_PRIVILEGES = "adminPrivileges";
    public static final String R_EXCEL_ALL_IMPORT_PRIVILEGES = "ExcelAllImportPrivileges";
    public static final String R_EXCEL_ALL_EXPORT_PRIVILEGES = "ExcelAllExportPrivileges";
    public static final String R_EXCEL_CUSTOMERS_IMPORT_PRIVILEGES = "ExcelImportCustomers";
    public static final String R_EXCEL_CUSTOMERS_EXPORT_PRIVILEGES = "ExcelExportCustomers";
    public static final String R_EXCEL_SELLS_IMPORT_PRIVILEGES = "ExcelImportSells";
    public static final String R_EXCEL_SELLS_EXPORT_PRIVILEGES = "ExcelExportSells";

    public static final String R_ROOT_PRIVILEGES = "rootPrivileges";
    public static final String R_FUELSTATIONS_MANAGEMENT = "FuelstationsManagement";

    //<editor-fold defaultstate="collapsed" desc="CARD SECTOR ">
    //CARD SECTOR
    public static final String P_CARDS_USER_CUSTOMERS_SEARCH_ALL = "cardsuser:customers:search:all";
    public static final String P_CARDS_USER_CUSTOMERS_SEARCH_OWN = "cardsuser:customers:search:own";
    public static final String P_CARDS_USER_CUSTOMERS_EDIT_ALL = "cardsuser:customers:edit:all";
    public static final String P_CARDS_USER_CUSTOMERS_EDIT_OWN = "cardsuser:customers:edit:own";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FUELSALES SECTOR ">
    //FUELSALES SECTOR 
    public static final String P_FUELSALES_USER_CUSTOMERS_SEARCH_ALL = "fuelsalesuser:customers:search:all";
    public static final String P_FUELSALES_USER_CUSTOMERS_SEARCH_OWN = "fuelsalesuser:customers:search:own";
    public static final String P_FUELSALES_USER_CUSTOMERS_EDIT_ALL = "fuelsalesuser:customers:edit:all";
    public static final String P_FUELSALES_USER_CUSTOMERS_EDIT_OWN = "fuelsalesuser:customers:edit:own";
    public static final String P_FUELSALES_USER_FS_NEW_STATION = "fuelsalesuser:fs:newStation";
    public static final String P_FUELSALES_USER_FS_NEW_OWNER = "fuelsalesuser:fs:newOwner";
    public static final String P_FUELSALES_USER_FS_NEW_PROPERTY = "fuelsalesuser:fs:newProperty";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GENERAL - CUSTOMERS">
    public static final String P_CUSTOMERS_SEARCH_ALL = "customers:search:all";
    public static final String P_CUSTOMERS_SEARCH_OWN = "customers:search:own";
    public static final String P_CUSTOMERS_EDIT_ALL = "customers:edit:all";
    public static final String P_CUSTOMERS_EDIT_OWN = "customers:edit:own";
    public static final String P_CUSTOMERS_NEW_CUSTOMER = "customers:newCustomer";
    public static final String P_CUSTOMERS_NEW_CBT = "customers:newCBT";
    public static final String P_CUSTOMERS_DOCUMENTS = "customers:documents";
    public static final String P_CUSTOMERS_ALL = "customers:*";
    public static final String P_CUSTOMERS_EXCEL_IMPORT = "excel:import:customers";
    public static final String P_CUSTOMERS_EXCEL_EXPORT = "excel:export:customers";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GENERAL - CRM">
    public static final String P_CRM_NEW_CRM_PROCESS = "crm:process:new";
    public static final String P_CRM_NEW_SC_REL = "crm:sc_rel:new";
    public static final String P_CRM_ALL = "crm:*";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SELLS">
    public static final String P_SELLS_EXCEL_IMPORT = "excel:import:sells";
    public static final String P_SELLS_EXCEL_EXPORT = "excel:export:sells";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GENERAL - FUELSTATIONS">
    public static final String P_FUELSTATIONS_SEARCH_ALL = "fuelstations:search:all";
    public static final String P_FUELSTATIONS_SEARCH_OWN = "fuelstations:search:own";
    public static final String P_FUELSTATIONS_EDIT_ALL = "fuelstations:edit:all";
    public static final String P_FUELSTATIONS_EDIT_OWN = "fuelstations:edit:own";
    public static final String P_FUELSTATIONS_ALL = "fuelstations:*";
    public static final String P_FUELSTATIONS_MANAGEMENT = "fuelstations:edit:*";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GENERAL - APP">
    public static final String P_USER_LOGIN = "user:login";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="IT">
    public static final String P_ADMIN_ALL = "adminPrivileges = admin:*";
    //</editor-fold>

    public static String[] getAllRoles() {
        return new String[]{
            R_CARDS_USER,
            R_CARDS_MANAGER,
            R_FUELSALES_USER,
            R_FUELSALES_MANAGER,
            R_USER_LOGIN,
            R_ADMIN_PRIVILEGES,
            R_EXCEL_ALL_IMPORT_PRIVILEGES,
            R_EXCEL_ALL_EXPORT_PRIVILEGES,
            R_EXCEL_CUSTOMERS_IMPORT_PRIVILEGES,
            R_EXCEL_CUSTOMERS_EXPORT_PRIVILEGES,
            R_EXCEL_SELLS_IMPORT_PRIVILEGES,
            R_EXCEL_SELLS_EXPORT_PRIVILEGES,
            R_ROOT_PRIVILEGES,
            R_FUELSTATIONS_MANAGEMENT
        };
    }

    public static String[] getAllPermissions() {
        return new String[]{
            P_CARDS_USER_CUSTOMERS_SEARCH_ALL,
            P_CARDS_USER_CUSTOMERS_SEARCH_OWN,
            P_CARDS_USER_CUSTOMERS_EDIT_ALL,
            P_CARDS_USER_CUSTOMERS_EDIT_OWN,
            //
            P_FUELSALES_USER_CUSTOMERS_SEARCH_ALL,
            P_FUELSALES_USER_CUSTOMERS_SEARCH_OWN,
            P_FUELSALES_USER_CUSTOMERS_EDIT_ALL,
            P_FUELSALES_USER_CUSTOMERS_EDIT_OWN,
            P_FUELSALES_USER_FS_NEW_STATION,
            P_FUELSALES_USER_FS_NEW_OWNER,
            P_FUELSALES_USER_FS_NEW_PROPERTY,
            //    
            P_CUSTOMERS_SEARCH_ALL,
            P_CUSTOMERS_SEARCH_OWN,
            P_CUSTOMERS_EDIT_ALL,
            P_CUSTOMERS_EDIT_OWN,
            P_CUSTOMERS_NEW_CUSTOMER,
            P_CUSTOMERS_NEW_CBT,
            P_CUSTOMERS_DOCUMENTS,
            P_CUSTOMERS_ALL,
            P_CUSTOMERS_EXCEL_IMPORT,
            P_CUSTOMERS_EXCEL_EXPORT,
            //
            P_CRM_NEW_CRM_PROCESS,
            P_CRM_NEW_SC_REL,
            P_CRM_ALL,
            //
            P_SELLS_EXCEL_IMPORT,
            P_SELLS_EXCEL_EXPORT,
            //
            P_FUELSTATIONS_SEARCH_ALL,
            P_FUELSTATIONS_SEARCH_OWN,
            P_FUELSTATIONS_EDIT_ALL,
            P_FUELSTATIONS_EDIT_OWN,
            P_FUELSTATIONS_ALL,
            P_FUELSTATIONS_MANAGEMENT,
            //
            P_USER_LOGIN,
            //
            P_ADMIN_ALL
        };
    }
}
