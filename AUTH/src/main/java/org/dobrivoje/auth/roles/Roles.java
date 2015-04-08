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
    public static final String APP_OFFICE_MANAGER = "appOfficeManager";
    public static final String APP_FS_USER = "appFSUser";

    public static final String PERMISSION_APP_FS_USER_LOGIN = "appFSUser:login";
    public static final String PERMISSION_APP_FS_USER_SEARCH_ALL_WORKPLANS = "appFSUser:search:AllWorkplans";
    public static final String PERMISSION_APP_FS_USER_SEARCH_OWN_WORKPLAN = "appFSUser:search:OwnWorkplan";
    public static final String PERMISSION_APP_FS_USER_EDIT_ALL_WORKPLANS = "appFSUser:edit:AllWorkplans";
    public static final String PERMISSION_APP_FS_USER_EDIT_OWN_WORKPLANS = "appFSUser:edit:OwnWorkplans";

    public static String[] getAllRoles() {
        return new String[]{ROOT_PRIVILEGES, APP_OFFICE_MANAGER, APP_FS_USER};
    }

    public static String[] getAllPermissions() {
        return new String[]{
            PERMISSION_APP_FS_USER_LOGIN, PERMISSION_APP_FS_USER_SEARCH_ALL_WORKPLANS, PERMISSION_APP_FS_USER_SEARCH_OWN_WORKPLAN,
            PERMISSION_APP_FS_USER_EDIT_ALL_WORKPLANS, PERMISSION_APP_FS_USER_EDIT_OWN_WORKPLANS
        };
    }
}
