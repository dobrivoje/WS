package Views.SYSNOTIF;

import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SysNotifView extends DashboardView {

    public SysNotifView() {
        super("System Notification Board");
        buildContentWithComponents(
                cardsPanel(), blackListCustomersPanel(), fuelPanel(), notesPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component cardsPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("CARDS SECTOR");
        Component contentWrapper = createContentWrapper(VL);

        return contentWrapper;
    }

    private Component notesPanel() {
        TextArea notes = new TextArea("NOTES");
        notes.setValue("Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule");
        notes.setSizeFull();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        Component panel = createContentWrapper(notes);
        panel.addStyleName("notes");

        return panel;
    }

    private Component fuelPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("FUEL SECTOR");
        Component contentWrapper = createContentWrapper(VL);

        return contentWrapper;
    }

    private Component blackListCustomersPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("BLACK LIST CUSTOMERS !");

        Component contentWrapper = createContentWrapper(VL);

        return contentWrapper;
    }
    //</editor-fold>
}
