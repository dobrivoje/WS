package org.superb.apps.ws.Views.General;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import org.superb.apps.ws.MyUI;
import org.superb.apps.ws.Views.ConsoleView;
import org.superb.apps.ws.Views.Menu;

/**
 * Content of the UI when the user is logged in.
 *
 *
 */
public class MainScreen extends HorizontalLayout {

    private Menu menu;

    public MainScreen(MyUI ui) {

        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new Menu(navigator);
        menu.addViewTree(new ConsoleView(), ConsoleView.class.getSimpleName(), ConsoleView.VIEW_NAME);
        menu.addViewButton(new AboutView(), AboutView.class.getSimpleName(), AboutView.VIEW_NAME, FontAwesome.INFO_CIRCLE);

        navigator.addView(EmptyView.class.getSimpleName(), EmptyView.class);

        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }

    // notify the view menu about view changes so that it can display which view
    // is currently active
    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveViewButton(event.getViewName());
        }

    };
}
