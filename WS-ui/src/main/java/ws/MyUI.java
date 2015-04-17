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
import org.dobrivoje.auth.IntermolADAccessControl;
import org.dobrivoje.utils.date.formats.DateFormat;

/**
 *
 */
@Theme("mytheme")
@Widgetset("ws.MyAppWidgetset")
public class MyUI extends UI {

    private final IAccessAuthControl accessControl = new IntermolADAccessControl();
    public static final DataService DS = DataService.getDefault();
    public static final String APP_DATE_FORMAT = DateFormat.DATE_FORMAT_SRB.toString();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("WS App");

        Notification notification = new Notification("Welcome to WS App");
        notification.setDescription(
                "<span>This application covers wholesale bussines process.</span>"
                + "<span>This is a <b>test phase release</b></span>"
                + "<span>Please, use your Windows username, with password.</span>"
                + "<span>Just click the <b>Login</b> button to continue.</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(8000);
        notification.show(Page.getCurrent());

        if (!accessControl.authenticated()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void doAfterLogin() {
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

    public IAccessAuthControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}
