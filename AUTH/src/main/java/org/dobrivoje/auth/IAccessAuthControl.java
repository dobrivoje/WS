package org.dobrivoje.auth;

import org.apache.shiro.mgt.SecurityManager;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface IAccessAuthControl {

    // SecurityManager getSecurityManager();

    boolean login(String username, String password);

    boolean authenticated();

    boolean hasRole(String role);

    boolean isPermitted(String permission);

    String getPrincipal();

    String getSessionISUser();
}
