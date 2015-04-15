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
import org.dobrivoje.auth.roles.Roles;

/**
 *
 * @author root
 */
public class testKlasa {

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

    public static void main(String[] args) {
        IAccessAuthControl intermolAD = new IntermolADAccessControl();

        try {
            intermolAD.login("intermol\\dprtenjak", "dedaMocika2002");
            //intermolAD.login("root", "root");
            //intermolAD.login("dobri", "dobri");

            System.err.println(intermolAD.getSubject().getPrincipal() + " isAuthenticated ? " + intermolAD.authenticated());

            for (String r : Roles.getAllRoles()) {
                try {
                    intermolAD.getSubject().checkRoles(r);
                    System.err.println("Role : " + r + ", " + intermolAD.getSubject().getPrincipal() + " IS permitted");
                } catch (Exception ae) {
                    System.err.println("Role : " + r + ", " + intermolAD.getSubject().getPrincipal() + " is NOT permitted");
                }
            }

            for (String p : Roles.getAllPermissions()) {
                try {
                    intermolAD.getSubject().checkPermission(p);
                    System.err.println("Permission : " + p + ", " + intermolAD.getSubject().getPrincipal() + " IS permitted");
                } catch (Exception ae) {
                    System.err.println("Permission : " + p + ", " + intermolAD.getSubject().getPrincipal() + " is NOT permitted");
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

        for (String s : Roles.getAllPermissions()) {
            System.err.println(intermolAD.getPrincipal() + ", " + s + " -> " + intermolAD.isPermitted(s));
        }
        for (String s : Roles.getAllRoles()) {
            System.err.println(intermolAD.getPrincipal() + ", " + s + " -> " + intermolAD.hasRole(s));
        }

        Sha256Hash sha256Hash = new Sha256Hash("");
        System.out.println(sha256Hash.toHex());

    }
}
