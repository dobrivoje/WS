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

    public ShiroAccessControl() {
        factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public boolean login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
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
        return subject.hasRole(role);
    }

    @Override
    public String getPrincipal() {
        return (String) subject.getPrincipal();
    }

}
