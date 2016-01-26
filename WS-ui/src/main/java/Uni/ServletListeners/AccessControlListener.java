/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uni.ServletListeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.dobrivoje.auth.IAccessAuthControl;
import org.dobrivoje.auth.IntermolADAccessControl;
import org.superb.apps.utilities.Enums.ServletOperations;

public class AccessControlListener implements HttpSessionListener {

    private IAccessAuthControl shiroAuthObject;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        shiroAuthObject = new IntermolADAccessControl();
        se.getSession().setAttribute(ServletOperations.SERVLET_CREATION.toString(), shiroAuthObject);

        Logger.getLogger("test").log(Level.INFO, "WS App - Http Session {0} - Created.",
                se.getSession().getId());
        Logger.getLogger("test").log(Level.INFO, "WS App - Shiro Sessions : {0}", shiroAuthObject.getUsersSessions().toString());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Logger.getLogger("test").log(Level.INFO, "WS App - Session  - Destroyed.");
        Logger.getLogger("test").log(Level.INFO, "WS App - Shiro Sessions : {0}", shiroAuthObject.getUsersSessions().toString());

        shiroAuthObject.logout();
    }

}
