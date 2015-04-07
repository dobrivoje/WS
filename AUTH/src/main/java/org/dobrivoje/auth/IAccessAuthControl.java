package org.dobrivoje.auth;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface IAccessAuthControl {

    public SecurityManager getSecurityManager();

    public Subject getSubject();

    public boolean login(String username, String password);

    public void logout();

    public boolean authenticated();

    public boolean hasRole(String role);

    public String getPrincipal();
}
