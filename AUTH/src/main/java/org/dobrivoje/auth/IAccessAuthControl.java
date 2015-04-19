package org.dobrivoje.auth;

import java.io.Serializable;
import org.apache.shiro.session.Session;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface IAccessAuthControl {

    // SecurityManager getSecurityManager();
    boolean login(String username, String password);

    void logout();

    boolean authenticated();

    boolean hasRole(String role);

    boolean isPermitted(String permission);

    String getPrincipal();

    String getInfSysUserSession();

    int getLoggedUsers();

    void incLoggedUsers();

    void decLoggedUsers();

    Session getSubjectSession();

    Serializable getSubjectSessionID();

}
