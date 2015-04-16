/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.dobrivoje.auth.IAccessAuthControl;
import org.dobrivoje.auth.IntermolADAccessControl;
import org.dobrivoje.auth.roles.RolesPermissions;

/**
 *
 * @author root
 */
public class testKlasa {

    //<editor-fold defaultstate="collapsed" desc="annotated methods,...">
    @RequiresAuthentication()
    private static void testAutentifikacije(Subject subject) {
        System.err.println("testAutentifikacije, Subjekat " + subject.getPrincipal() + ", autentifikovan !");
    }

    @RequiresPermissions(RolesPermissions.P_CARDS_USER_CUSTOMERS_EDIT_ALL)
    private static void testDozovla_CARDS_CUSTOMERS_EDIT_ALL(Subject subject) {
        System.err.println("testDozovla_CARDS_CUSTOMERS_EDIT_ALL, Subjekat " + subject.getPrincipal() + ", ima dozovlu za pravo : " + RolesPermissions.P_CARDS_USER_CUSTOMERS_EDIT_ALL);
    }

    @RequiresPermissions(RolesPermissions.P_CARDS_USER_CUSTOMERS_SEARCH_ALL)
    private static void testDozovla_CARDS_USER_CUSTOMERS_SEARCH_ALL(Subject subject) {
        System.err.println("testDozovla_CUST_EDIT_ALL, Subjekat " + subject.getPrincipal() + ", ima dozovlu za pravo : " + RolesPermissions.P_CARDS_USER_CUSTOMERS_SEARCH_ALL);
    }

    @RequiresRoles(RolesPermissions.R_ROOT_PRIVILEGES)
    private static void testDozovle_ROOT_PRIV(Subject subject) {
        System.err.println("testDozovle_ROOT_PRIV Subjekat " + subject.getPrincipal() + ", ima ROLE : " + RolesPermissions.R_ROOT_PRIVILEGES);
    }
    //</editor-fold>

    public static void main(String[] args) {
        IAccessAuthControl intermolAD = new IntermolADAccessControl();

        try {
            //intermolAD.login("intermol\\dprtenjak", "...");
            //intermolAD.login("root", "...");
            intermolAD.login("ws", "");

            System.err.println(intermolAD.getPrincipal() + " isAuthenticated ? " + intermolAD.authenticated());

            //<editor-fold defaultstate="collapsed" desc="try/catch funkcionalnosti">
            /*
             for (String r : RolesPermissions.getAllRoles()) {
             try {
             intermolAD.getSubject().checkRoles(r);
             System.err.println("Role : " + r + ", " + intermolAD.getSubject().getPrincipal() + " IS permitted");
             } catch (Exception ae) {
             System.err.println("Role : " + r + ", " + intermolAD.getSubject().getPrincipal() + " is NOT permitted");
             }
             }
            
             for (String p : RolesPermissions.getAllPermissions()) {
             try {
             intermolAD.getSubject().checkPermission(p);
             System.err.println("Permission : " + p + ", " + intermolAD.getSubject().getPrincipal() + " IS permitted");
             } catch (Exception ae) {
             System.err.println("Permission : " + p + ", " + intermolAD.getSubject().getPrincipal() + " is NOT permitted");
             }
             }
            
             testAutentifikacije(intermolAD.getSubject());
             testDozovla_CARDS_CUSTOMERS_EDIT_ALL(intermolAD.getSubject());
             testDozovla_CARDS_USER_CUSTOMERS_SEARCH_ALL(intermolAD.getSubject());
             testDozovle_ROOT_PRIV(intermolAD.getSubject());
             */
            //</editor-fold>
        } catch (UnknownAccountException e) {
            System.err.println("Nepoznati nalog !");
        } catch (IncorrectCredentialsException e) {
            System.err.println("IncorrectCredentials !");
        } catch (ExcessiveAttemptsException e) {
            System.err.println("ExcessiveAttempts !");
        }

        for (String s : RolesPermissions.getAllPermissions()) {
            System.err.println(intermolAD.getPrincipal() + ", " + s + " -> " + intermolAD.isPermitted(s));
        }
        for (String s : RolesPermissions.getAllRoles()) {
            System.err.println(intermolAD.getPrincipal() + ", " + s + " -> " + intermolAD.hasRole(s));
        }

        Sha256Hash sha256Hash = new Sha256Hash("dedaMocika2001");
        System.out.println(sha256Hash.toHex());

        System.err.println(intermolAD.getPrincipal() + " : " + RolesPermissions.P_CARDS_USER_CUSTOMERS_EDIT_OWN
                + " : "
                + intermolAD.isPermitted(RolesPermissions.P_CARDS_USER_CUSTOMERS_EDIT_OWN)
        );

        System.err.println(intermolAD.getPrincipal() + " : " + RolesPermissions.P_FUELSALES_USER_CUSTOMERS_EDIT_ALL
                + " : "
                + intermolAD.isPermitted(RolesPermissions.P_FUELSALES_USER_CUSTOMERS_EDIT_ALL)
        );

        System.err.println(intermolAD.getPrincipal() + " : " + RolesPermissions.P_FUELSALES_USER_FS_NEW_PROPERTY
                + " : "
                + intermolAD.isPermitted(RolesPermissions.P_FUELSALES_USER_FS_NEW_PROPERTY)
        );

        System.err.println("\nĐŠPĆČČLDJHNMLČĐŠ");
    }
}
