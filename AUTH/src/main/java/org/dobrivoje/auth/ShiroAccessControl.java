package org.dobrivoje.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;

public class ShiroAccessControl implements IAccessAuthControl, SessionListener {

    //<editor-fold defaultstate="collapsed" desc="Infrastructure">
    // atribut pod navodnicima je id sesije koja se odnosi na username ulogovanog korisnika
    // private static final String UN_SESSION_KEY = "UR8450-XC88xoiuf-iow889s-HG786hjgghH11H50HH8911-mNNmn558wuuuw768x8c7";
    private String UN_SESSION_KEY;
    private static int loggedInUsers = 0;
    private static final Set<Serializable> usersSessions = new HashSet<>();

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

                subject.getSession().setAttribute(UN_SESSION_KEY = (String) getSubjectSessionID(), getPrincipal());
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
        try {
            return subject.isAuthenticated();
        } catch (Exception e) {
            return false;
        }
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
        try {
            return (String) subject.getPrincipal();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public Session getSubjectSession() {
        try {
            return subject.getSession();
        } catch (Exception e) {
            return new SimpleSession("");
        }
    }

    @Override
    public Serializable getSubjectSessionID() {
        try {
            return subject.getSession().getId();
        } catch (Exception e) {
            return "";
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inf Sys users,...">
    @Override
    public String getInfSysUserSession() {
        // atribut pod navodnicima je id sesije koja se odnosi na username ulogovanog korisnika
        try {
            return (String) subject.getSession().getAttribute(UN_SESSION_KEY);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public synchronized int getLoggedUsers() {
        return loggedInUsers;
    }

    @Override
    public synchronized void incLoggedUsers() {
        if (!usersSessions.contains(getSubjectSessionID())) {
            usersSessions.add(getSubjectSessionID());
            loggedInUsers++;
        }
    }

    @Override
    public synchronized void decLoggedUsers() {
        if (usersSessions.contains(getSubjectSessionID())) {
            usersSessions.remove(getSubjectSessionID());
        }

        if (--loggedInUsers < 0) {
            loggedInUsers = 0;
        }

    }
    //</editor-fold>

    public static Set<Serializable> getUsersSessions() {
        return usersSessions;
    }

    @Override
    public void onStart(Session sn) {
        System.err.println("start sesije.");
    }

    @Override
    public void onStop(Session sn) {
        System.err.println("stop sesije.");
    }

    @Override
    public void onExpiration(Session sn) {
        System.err.println("sesija istekla.");
    }
}
