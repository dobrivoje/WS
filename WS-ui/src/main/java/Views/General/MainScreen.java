package Views.General;

import Views.ConsoleView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import Views.MainMenu.CDM.CustomersView;
import Views.MainMenu.FSDM.FSView;
import Views.MainMenu.MainMenu;
import Views.SYSNOTIF.SysNotifView;
import com.vaadin.server.FontAwesome;
import ws.MyUI;

/**
 * Content of the UI when the user is logged in.
 *
 *
 */
public class MainScreen extends HorizontalLayout {

    private MainMenu menu;

    public MainScreen(MyUI ui) {

        setStyleName("main-screen");

        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);
        menu = new MainMenu(navigator);
        menu.createViewTree("mainMenu");
        // menu.addViewTree(new ConsoleView(), ConsoleView.class.getSimpleName(), ConsoleView.VIEW_NAME);
        menu.addViewButton(new AboutView(), AboutView.class.getSimpleName(), AboutView.VIEW_NAME, FontAwesome.INFO_CIRCLE);

        // DODAVANJE VIEW-ova NOVIH AKCIJA I GL. MENIJA :
        navigator.addView(EmptyView.class.getSimpleName(), EmptyView.class);
        navigator.addView(CustomersView.class.getSimpleName(), CustomersView.class);
        navigator.addView(FSView.class.getSimpleName(), FSView.class);
        navigator.addView(SysNotifView.class.getSimpleName(), SysNotifView.class);

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
