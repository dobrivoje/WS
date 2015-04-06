/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import org.apache.shiro.mgt.SecurityManager;

/**
 *
 * @author root
 */
public class NewEmptyJUnitTest {

    private final Factory<SecurityManager> factory;
    private final SecurityManager securityManager;
    private final Subject subject;

    public NewEmptyJUnitTest() {
        factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
    }

    @Test
    public void testiraj() {
        UsernamePasswordToken token = new UsernamePasswordToken("juzer", "pass");
        Assert.assertEquals("poruka", "juzer", token.getUsername());
        Assert.assertEquals("poruka2", "pass", String.valueOf(token.getPassword()));
    }
}
