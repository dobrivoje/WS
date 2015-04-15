/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.auth.DZoneExample;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 *
 * @author dprtenjak
 */
public class CustomRealm extends AuthenticatingRealm {

    private CredentialsMatcher credentialsMatcher;

    @Override
    public String getName() {
        return CustomRealm.class.getCanonicalName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        this.credentialsMatcher = credentialsMatcher;
    }

    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        return this.credentialsMatcher;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        return new SimpleAuthenticationInfo(at.getPrincipal(), at.getCredentials(), getName());
    }

}
