package Main;

import Uni.Views.LoginScreen;
import Uni.Views.MainScreen;
import Views.SYSNOTIF.View_SysNotif;
import com.vaadin.annotations.PreserveOnRefresh;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import dataservice.DataService;
import db.ent.InfSysUser;
import db.ent.Salesman;
import enums.ISUserType;
import org.superb.apps.utilities.datum.DateFormat;
import org.superbapps.auth.IAccessAuthControl;
import org.superbapps.utils.common.Enums.ServletOperations;

/**
 *
 */
@Theme("mytheme")
@Widgetset("ws.MyAppWidgetset")
@PreserveOnRefresh

public class MyUI extends UI {

    public IAccessAuthControl accessControl;

    public static final DataService DS = DataService.getDefault();
    public static final String APP_DATE_FORMAT = DateFormat.DATE_FORMAT_SRB.toString();

    private InfSysUser loggedISUser;
    private ISUserType loggedISUserType;
    private Salesman loggedSalesman;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        accessControl = (IAccessAuthControl) vaadinRequest.getWrappedSession().getAttribute(ServletOperations.SERVLET_CREATION.toString());
        int un = accessControl.getNoOfSessions();

        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("WS App");

        Notification notification = new Notification("Welcome to WS App");
        notification.setDescription(
                "<span>This application covers wholesale bussines process.</span>"
                + "<span>This is a <b><u>test phase release.</u></b></span>"
                + "<span>Please, use your corporate Windows username !</b></span>"
                + (un > 0 ? "<span><b><u>"
                        + (un > 1 ? un + " users are " : " One user is ")
                        + "working with the software."
                        + "</u></b></span>"
                        : "<span>Currently, <b><u>no user uses the software.</u></b></span>")
        );

        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(8000);
        notification.show(Page.getCurrent());

        try {
            if (!accessControl.authenticated()) {
                setContent(new LoginScreen(accessControl, () -> {
                    loggedISUser = DS.getINFSYSUSERController().getByID(accessControl.getPrincipal());
                    loggedISUserType = DS.getINFSYSUSERController().getInfSysUserType(loggedISUser);
                    loggedSalesman = DS.getINFSYSUSERController().getSalesman(loggedISUser);

                    showMainView();
                }));
            } else {
                showMainView();
            }
        } catch (NullPointerException lnpe) {
        }
    }

    protected void showMainView() {
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(View_SysNotif.class.getSimpleName());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    //<editor-fold defaultstate="collapsed" desc="Interfaces">
    public IAccessAuthControl getAccessControl() {
        return accessControl;
    }

    public boolean isPermitted(String permission) {
        return accessControl.isPermitted(permission);
    }

    public boolean hasRole(String role) {
        return accessControl.hasRole(role);
    }

    public boolean isPermitted(Enum permission) {
        return accessControl.isPermitted(permission);
    }

    public boolean hasRole(Enum role) {
        return accessControl.hasRole(role);
    }

    public InfSysUser getLoggedISUser() {
        return loggedISUser;
    }

    public ISUserType getLoggedISUserType() {
        return loggedISUserType;
    }

    public Salesman getLoggedSalesman() {
        return loggedSalesman == null ? new Salesman() : loggedSalesman;
    }
    //</editor-fold>

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
