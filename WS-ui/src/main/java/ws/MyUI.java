package ws;

import authentication.AccessControl;
import authentication.BasicAccessControl;
import Views.General.LoginScreen;
import Views.General.LoginScreen.LoginListener;
import Views.General.EmptyView;
import Views.General.MainScreen;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@Widgetset("ws.MyAppWidgetset")
public class MyUI extends UI {

    private final AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("WS App");

        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(EmptyView.class.getSimpleName());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}