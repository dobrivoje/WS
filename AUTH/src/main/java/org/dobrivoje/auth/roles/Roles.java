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
    public static final String APP_SUPERUSER = "appSuperUser";
    public static final String APP_TESTUSER = "appTestUser";

    public static final String PERMISSION_APP_TESTUSER_BASIC_LOGIN = "appTestUser:basic:login";
    public static final String PERMISSION_APP_TESTUSER_BASIC_USERMANAGEMENT = "appTestUser:basic:userManagement";
    public static final String PERMISSION_APP_TESTUSER_BASIC_FSVIEW = "appTestUser:basic:FSView";

}
