/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.dobrivoje.auth.ShiroAccessControl;

/**
 *
 * @author root
 */
public class testKlasa {

    public static void main(String[] args) {
        ShiroAccessControl iac = new ShiroAccessControl();
        boolean login = iac.login("ws", "");
        System.err.println("logged in ? " + login);
        System.err.println("principal : " + iac.getPrincipal());
        System.err.println("principal : " + iac.getPrincipal());
        System.err.println("role 'appTestUser:basic:login': " + iac.hasRole("appTestUser:basic:login"));
        System.err.println("role 'appSuperUser': " + iac.hasRole("appSuperUser"));
        System.err.println("role 'appTestUser': " + iac.hasRole("appTestUser"));
        System.err.println("is permmited 'login': " + iac.getSubject().isPermitted("appTestUser:basic:login"));
        System.err.println("is permmited 'userManagement': " + iac.getSubject().isPermitted("appTestUser:basic:userManagement"));
        System.err.println("is permmited 'app:*': " + iac.getSubject().isPermitted("app"));

    }
}
