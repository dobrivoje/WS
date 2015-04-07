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
    public static final String APP_MANAGER = "appManager";
    public static final String APP_USER = "appUser";

    public static final String PERMISSION_APP_USER_LOGIN = "appUser:login";
    public static final String PERMISSION_APP_TESTUSER_BASIC_LOGIN = "appTestUser:login";
    public static final String PERMISSION_APP_TESTUSER_SEARCH_OWN = "appUser:search:Own";
    public static final String PERMISSION_APP_TESTUSER_BASIC_FSVIEW = "appTestUser:FSView";

    public static String[] getAllRoles() {
        return new String[]{ROOT_PRIVILEGES, APP_MANAGER, APP_USER};
    }

    public static String[] getAllPermissions() {
        return new String[]{
            PERMISSION_APP_USER_LOGIN,
            PERMISSION_APP_TESTUSER_BASIC_LOGIN,
            PERMISSION_APP_TESTUSER_SEARCH_OWN,
            PERMISSION_APP_TESTUSER_BASIC_FSVIEW
        };
    }
}
