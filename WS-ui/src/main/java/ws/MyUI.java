package ws;

import org.dobrivoje.auth.IAccessAuthControl;
import Views.General.LoginScreen;
import Views.General.LoginScreen.LoginListener;
import Views.General.MainScreen;
import Views.SYSNOTIF.SysNotifView;
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
import org.dobrivoje.auth.IntermolADAccessControl;
import org.dobrivoje.utils.date.formats.DateFormat;

/**
 *
 */
@Theme("mytheme")
@Widgetset("ws.MyAppWidgetset")
public class MyUI extends UI {

    public final IAccessAuthControl accessControl = new IntermolADAccessControl();
    private final int un = accessControl.getLoggedUsers();

    public static final DataService DS = DataService.getDefault();
    public static final String APP_DATE_FORMAT = DateFormat.DATE_FORMAT_SRB.toString();

    private InfSysUser loggedISUser;
    private ISUserType loggedISUserType;
    private Salesman loggedSalesman;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
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

        if (!accessControl.authenticated()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void doAfterLogin() {
                    loggedISUser = DS.getINFSYSUSERController().getByID(accessControl.getPrincipal());
                    loggedISUserType = DS.getINFSYSUSERController().getInfSysUserType(loggedISUser);
                    loggedSalesman = DS.getINFSYSUSERController().getSalesman(loggedISUser);

                    showMainView();
                }
            }));
        }
    }

    protected void showMainView() {
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(SysNotifView.class.getSimpleName());
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

    public InfSysUser getLoggedISUser() {
        return loggedISUser;
    }

    public ISUserType getLoggedISUserType() {
        return loggedISUserType;
    }

    public Salesman getLoggedSalesman() throws Exception {
        if (loggedSalesman == null) {
            throw new Exception("User " + loggedISUser.getUserName() + ", is not defined as the salesman.");
        }

        return loggedSalesman;
    }
    //</editor-fold>

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
