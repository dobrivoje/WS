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
public class Roles implements Serializable {

    public static final String ROOT_PRIVILEGES = "rootPrivileges";
    public static final String APP_CARDS_MANAGER = "appCardsManager";
    public static final String APP_CARDS_USER = "appCardsUser";
    public static final String APP_CARDS_SUPERUSER = "appCardsSuperUser";
    public static final String APP_FUELSALE_MANAGER = "appFuelsaleManager";
    public static final String APP_FUELSALE_USER = "appFuelsaleUser";

    public static final String PERMISSION_USER_LOGIN = "appUser:login";

    //CARD SECTOR 
    public static final String PERMISSION_CARDS_USER_CUSTOMERS_SEARCH_ALL = "appCardsUser:customers:search:all";
    public static final String PERMISSION_CARDS_CUSTOMERS_SEARCH_OWN = "appCardsUser:customers:search:own";
    public static final String PERMISSION_CARDS_CUSTOMERS_EDIT_ALL = "appCardsUser:customers:edit:all";
    public static final String PERMISSION_CARDS_CUSTOMERS_EDIT_OWN = "appCardsUser:customers:edit:own";

    public static String[] getAllRoles() {
        return new String[]{
            ROOT_PRIVILEGES,
            APP_CARDS_MANAGER,
            APP_CARDS_USER,
            APP_CARDS_SUPERUSER,
            APP_FUELSALE_MANAGER,
            APP_FUELSALE_USER
        };
    }

    public static String[] getAllPermissions() {
        return new String[]{
            PERMISSION_USER_LOGIN,
            PERMISSION_CARDS_USER_CUSTOMERS_SEARCH_ALL,
            PERMISSION_CARDS_CUSTOMERS_SEARCH_OWN,
            PERMISSION_CARDS_CUSTOMERS_EDIT_ALL,
            PERMISSION_CARDS_CUSTOMERS_EDIT_OWN
        };
    }
}
