package org.dobrivoje.auth;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface IAccessAuthControl {

    public boolean login(String username, String password);

    public boolean authenticated();

    public boolean hasRole(String role);

    public String getPrincipal();
}
