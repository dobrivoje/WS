package org.dobrivoje.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class ShiroAccessControl implements IAccessAuthControl {

    private final Factory<SecurityManager> factory;
    private final SecurityManager securityManager;
    private final Subject subject;

    public ShiroAccessControl(String initFile) {
        factory = new IniSecurityManagerFactory(initFile);
        securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
    }

    @Override
    public boolean login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);

            // atribut pod navodnicima je id sesije koja se odnosi na username ulogovanog korisnika
            subject.getSession().setAttribute("UR8450-XC88xoiuf-iow889s", getPrincipal());

            return true;
        } catch (AuthenticationException ae) {
            return false;
        }
    }

    @Override
    public boolean authenticated() {
        return subject.isAuthenticated();
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
        String s = (String) subject.getPrincipal();
        return s == null ? "n/a" : s;
    }

    @Override
    public String getSessionISUser() {
        // atribut pod navodnicima je id sesije koja se odnosi na username ulogovanog korisnika
        return (String) subject.getSession().getAttribute("UR8450-XC88xoiuf-iow889s");
    }

}
