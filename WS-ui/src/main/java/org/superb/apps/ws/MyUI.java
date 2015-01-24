package org.superb.apps.ws;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.superb.apps.ws.Views.AccordionView;
import org.superb.apps.ws.Views.ConsoleView;
import org.superb.apps.ws.Views.ErrorView;

/**
 *
 */
@Theme("mytheme")
@Widgetset("org.superb.apps.ws.MyAppWidgetset")
public class MyUI extends UI {

    private final Navigator navigator = new Navigator(this, this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator.addView(ConsoleView.class.getSimpleName(), ConsoleView.class);
        navigator.addView(AccordionView.class.getSimpleName(), AccordionView.class);
        navigator.setErrorView(ErrorView.class);

        navigator.navigateTo(ConsoleView.class.getSimpleName());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
