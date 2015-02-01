package org.superb.apps.ws.Views.General;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import org.superb.apps.utilities.vaadin.MyMenus.AccordionMenu;

public class AccordionView extends VerticalLayout implements View {

    private final AccordionMenu menu = new AccordionMenu();

    private final HorizontalLayout HL = new HorizontalLayout();
    private final HorizontalSplitPanel HSP = new HorizontalSplitPanel(HL, null);

    public AccordionView() {
        setMargin(true);
        setSpacing(true);

        menu.setMainMenuOptions("New Customer", "Bussines Types", "Tasks");
        menu.setSubMenuButtons(0, new Button(), new Button(), new Button(), new Button());
        menu.setSubMenuButtons(1, new Button("d1"), new Button("d1"), new Button("d1"), new Button("d1"));
        menu.setSubMenuButtons(2, new Button("d1"), new Button("d1"), new Button("d1"), new Button("d1"));

        /*
         try {
         menu.createTabs();
         } catch (AccordionIndexException ex) {
         Notification.show("Error.", ex.toString(), Notification.Type.ERROR_MESSAGE);
         }
         */
        HSP.setSplitPosition(33, Unit.PERCENTAGE);
        HL.addComponent(menu);
        addComponent(HSP);

    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
