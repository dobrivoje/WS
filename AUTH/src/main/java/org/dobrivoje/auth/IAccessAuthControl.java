package org.dobrivoje.auth;

import java.io.Serializable;
import java.util.Set;
import org.apache.shiro.session.Session;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface IAccessAuthControl {

    boolean login(String username, String password);

    void logout();

    boolean authenticated();

    boolean hasRole(String role);

    boolean isPermitted(String permission);

    String getPrincipal();

    String getInfSysUserSession();

    int getNoOfSessions();

    Set<Serializable> getUsersSessions();

    void removeUserSession();

    Session getSubjectSession();

}
