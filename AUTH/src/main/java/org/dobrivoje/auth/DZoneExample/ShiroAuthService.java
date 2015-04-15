/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.auth.DZoneExample;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author dprtenjak
 */
public class ShiroAuthService {

    public ShiroAuthService() {
        Factory factory = new IniSecurityManagerFactory("classpath:dzone.ini");
        SecurityManager securityManager = (SecurityManager) factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

    private void testAuth() {
        UsernamePasswordToken token = new UsernamePasswordToken("intermol\\dprtenjak", "dedaMocika2002654564");
        token.setRememberMe(true);
        boolean loggedIn = false;
        Session session;
        Subject currentUser = SecurityUtils.getSubject();

        try {
            currentUser.login(token);
            session = currentUser.getSession();
            System.err.println("Session ID : " + session.getId());
            loggedIn = true;

            Subject requestSubject = new Subject.Builder().sessionId(session.getId())
                    .buildSubject();
            System.out.println("Is Authenticated = "
                    + requestSubject.isAuthenticated());//Should return true
            System.out.println("Is Remembered = "
                    + requestSubject.isRemembered());

        } catch (Exception e) {
            loggedIn = false;
            System.err.println("ERROR ! " + e.getMessage());
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        new ShiroAuthService().testAuth();
    }

}
