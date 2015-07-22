package Views.General;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import Views.MainMenu.CDM.View_Customers;
import Views.MainMenu.CRM.View_CRM;
import Views.MainMenu.CRM.View_CRMSC;
import Views.MainMenu.FSDM.View_FS;
import Views.MainMenu.MainMenu;
import Views.SYSNOTIF.View_SysNotif;
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
        menu.addViewButton(new AboutView(), AboutView.class.getSimpleName(), AboutView.class.getSimpleName(), FontAwesome.INFO_CIRCLE);

        // DODAVANJE VIEW-ova NOVIH AKCIJA I GL. MENIJA :
        navigator.addView(EmptyView.class.getSimpleName(), EmptyView.class);
        navigator.addView(View_Customers.class.getSimpleName(), View_Customers.class);
        navigator.addView(View_CRM.class.getSimpleName(), View_CRM.class);
        navigator.addView(View_CRMSC.class.getSimpleName(), View_CRMSC.class);
        navigator.addView(View_FS.class.getSimpleName(), View_FS.class);
        navigator.addView(View_SysNotif.class.getSimpleName(), View_SysNotif.class);

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
