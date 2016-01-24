/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Random;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 *
 * @author root
 */
public class testMultiUserLogins {

    private static final String[] usernames = new String[]{"root", "ws", "test", "cu", "cum", "fs", "fsm"};

    private static Factory<org.apache.shiro.mgt.SecurityManager> factory;
    private static org.apache.shiro.mgt.SecurityManager securityManager;
    private static Subject subject;
    private static final String initFile = "classpath:IntermolAD.ini";

    public static void main(String[] args) {

        //<editor-fold defaultstate="collapsed" desc="preko shiro klasa,...">
        /*
         List<IAccessAuthControl> IAD = new ArrayList<>();
        
         try {
         for (int i = 0; i < 10; i++) {
         String u = usernames[new Random().nextInt(usernames.length)];
         System.err.println("adding user : " + u);
        
         IAccessAuthControl iac = new IntermolADAccessControl();
         iac.login(u, "");
        
         System.err.println("Loggedin user : " + iac.getPrincipal());
         System.err.println("Session id : " + iac.getSubjectSession().getId());
        
         IAD.add(iac);
         }
         } catch (UnknownAccountException e) {
         System.err.println("Nepoznati nalog !");
         } catch (IncorrectCredentialsException e) {
         System.err.println("IncorrectCredentials !");
         } catch (ExcessiveAttemptsException e) {
         System.err.println("ExcessiveAttempts !");
         } catch (Exception e) {
         System.err.println("Other exception. Error : " + e.getMessage());
         }
        
         System.err.println("------------------------------");
         System.err.println(" users logged in :");
         System.err.println("------------------------------");
        
         for (IAccessAuthControl ID : IAD) {
         System.err.println("getInfSysUserSession : " + ID.getInfSysUserSession());
         System.err.println("getPrincipal : " + ID.getPrincipal());
         System.err.println("getSubjectSession : " + ID.getSubjectSession().getId());
         System.err.println("getAttributeKeys : " + ID.getSubjectSession().getAttributeKeys().toString());
         System.err.println("getSubjectSessionID : " + ID.getSubjectSession().getId());
         }
        
         System.err.println("sessions : " + IAD.iterator().next().getUsersSessions().toString());
         */
        //</editor-fold>
        factory = new IniSecurityManagerFactory(initFile);
        securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();

        // login test
        for (int i = 0; i < 10; i++) {
            UsernamePasswordToken token = new UsernamePasswordToken(
                    usernames[new Random().nextInt(usernames.length)], "");

            Subject s = SecurityUtils.getSubject();
            System.err.println("subject - sesija pre logovanja : " + s.getSession().getId());
            s.login(token);
            System.err.println("subject - sesija posle logovanja : " + s.getSession().getId());

        }

    }
}
