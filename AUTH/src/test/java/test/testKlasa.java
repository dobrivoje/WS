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
import org.apache.shiro.subject.Subject;
import org.dobrivoje.auth.IntermolADAccessControl;
import org.dobrivoje.auth.IAccessAuthControl;
import org.dobrivoje.auth.roles.Roles;

/**
 *
 * @author root
 */
public class testKlasa {

    //<editor-fold defaultstate="collapsed" desc="f">
    @RequiresAuthentication()
    private static void testAutentifikacije(Subject subject) {
        System.err.println("testAutentifikacije, Subjekat " + subject.getPrincipal() + ", autentifikovan !");
    }
    
    @RequiresPermissions(Roles.ROOT_PRIVILEGES)
    private static void testDozovle1(Subject subject) {
        System.err.println("testDozovle1 Subjekat " + subject.getPrincipal() + ", ima dozovlu za pravo : " + Roles.ROOT_PRIVILEGES);
    }
    
    @RequiresRoles(value = Roles.ROOT_PRIVILEGES)
    private static void testDozovle2(Subject subject) {
        System.err.println("testDozovle2 Subjekat " + subject.getPrincipal() + ", ima ROLE : " + Roles.ROOT_PRIVILEGES);
    }
    //</editor-fold>

    public static void main(String[] args) {
        IAccessAuthControl intermolAD = new IntermolADAccessControl();

        try {
            // intermolAD.login("intermol\\dprtenjak", "dedaMocika2002");
            // intermolAD.login("root", "dedaMocika2001");
            intermolAD.login("ws", "");

            System.err.println(intermolAD.getSubject().getPrincipal() + " isAuthenticated ? " + intermolAD.authenticated());

            for (String r : Roles.getAllRoles()) {
                try {
                    intermolAD.getSubject().checkRoles(r);
                    System.err.println(intermolAD.getSubject().getPrincipal() + " is permitted : " + r);
                } catch (Exception ae) {
                    System.err.println(intermolAD.getSubject().getPrincipal() + " is NOT permitted : " + r);
                }
            }

            for (String p : Roles.getAllPermissions()) {
                try {
                    intermolAD.getSubject().checkPermission(p);
                    System.err.println(intermolAD.getSubject().getPrincipal() + " is permitted : " + p);
                } catch (Exception ae) {
                    System.err.println(intermolAD.getSubject().getPrincipal() + " is NOT permitted : " + p);
                }
            }

            testAutentifikacije(intermolAD.getSubject());
            testDozovle1(intermolAD.getSubject());
            testDozovle2(intermolAD.getSubject());

        } catch (UnknownAccountException e) {
            System.err.println("Nepoznati nalog !");
        } catch (IncorrectCredentialsException e) {
            System.err.println("IncorrectCredentials !");
        } catch (ExcessiveAttemptsException e) {
            System.err.println("ExcessiveAttempts !");
        }

    }
}
