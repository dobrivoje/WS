package org.dobrivoje.auth;

import java.io.Serializable;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;

public class ShiroAccessControl implements IAccessAuthControl {

    //<editor-fold defaultstate="collapsed" desc="Infrastructure">
    // atribut pod navodnicima je id sesije koja se odnosi na username ulogovanog korisnika
    private static final String UN_SESSION_KEY = "UR8450-XC88xoiuf-iow889s";
    private static int loggedInUsers = 0;

    private final Factory<SecurityManager> factory;
    private final SecurityManager securityManager;
    private final Subject subject;

    public ShiroAccessControl(String initFile) {
        factory = new IniSecurityManagerFactory(initFile);
        securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
    }
    //</editor-fold>

    @Override
    public synchronized boolean login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        if (subject.getSession().getAttribute(UN_SESSION_KEY) == null) {
            try {
                subject.login(token);

                subject.getSession().setAttribute(UN_SESSION_KEY, getPrincipal());
                incLoggedUsers();

                return true;

            } catch (AuthenticationException ae) {
                return false;
            }

        } else {
            return true;
        }
    }

    @Override
    public synchronized void logout() {
        decLoggedUsers();

        try {
            subject.logout();
        } catch (Exception e) {
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Permissions/Auths...">
    @Override
    public boolean authenticated() {
        return subject != null ? subject.isAuthenticated() : false;
    }

    @Override
    public boolean hasRole(String role) {
        boolean o = false;
        try {
            o = subject.hasRole(role);
        } catch (Exception e) {
        }
        return o;
    }

    @Override
    public boolean isPermitted(String permission) {
        boolean o = false;
        try {
            o = subject.isPermitted(permission);
        } catch (Exception e) {
        }
        return o;
    }

    @Override
    public String getPrincipal() {
        return subject != null ? (String) subject.getPrincipal() : "n/a";
    }

    @Override
    public Session getSubjectSession() {
        return subject != null ? subject.getSession() : new SimpleSession("");
    }

    @Override
    public Serializable getSubjectSessionID() {
        return subject != null ? subject.getSession().getId() : "";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inf Sys users,...">
    @Override
    public String getInfSysUserSession() {
        // atribut pod navodnicima je id sesije koja se odnosi na username ulogovanog korisnika
        return subject != null ? (String) subject.getSession().getAttribute(UN_SESSION_KEY) : "";
    }

    @Override
    public synchronized int getLoggedUsers() {
        return loggedInUsers;
    }

    @Override
    public synchronized void incLoggedUsers() {
        loggedInUsers++;
    }

    @Override
    public synchronized void decLoggedUsers() {
        --loggedInUsers;
    }
    //</editor-fold>
}
