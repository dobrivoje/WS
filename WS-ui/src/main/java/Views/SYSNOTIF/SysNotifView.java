package Views.SYSNOTIF;

import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import enums.ISUserType;
import ws.MyUI;

public class SysNotifView extends DashboardView {

    public SysNotifView() {
        super("System Notification Board");

        ISUserType IST = MyUI.get().getLoggedISUserType();

        switch (IST) {
            case SALESMAN:
                buildContentWithComponents(panel("Active Cases"), panel("Current Month Sales"), panel("BLACK LIST CUSTOMERS !"), notesPanel(IST.toString() + " Notifications"));
                break;
            case ADMIN:
                buildContentWithComponents(panel("ADMIN PANEL"), panel("users who don't work often"), panel("logged users in the period"), notesPanel(IST.toString() + " notes"));
                break;
            case SECTOR_MANAGER:
                buildContentWithComponents(panel("NEW CRM CASES"), panel("NEW SALES THIS MONTH"));
                break;
            case TOP_MANAGER:
                buildContentWithComponents(panel("CARDS"), panel("FUEL"), panel("LPG"), panel("LUB"), notesPanel("NOTES"));
                break;
            default:
                buildContentWithComponents(panel("UNKNOWN USER TYPE."));
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component panel(String caption) {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption(caption);
        Component contentWrapper = createContentWrapper(VL);

        return contentWrapper;
    }

    private Component notesPanel(String caption) {
        TextArea notes = new TextArea(caption);
        notes.setValue("Remember to:\n· Zoom in and out in the Sales view\n· Filter the transactions and drag a set of them to the Reports tab\n· Create a new report\n· Change the schedule");
        notes.setSizeFull();
        notes.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
        Component panel = createContentWrapper(notes);
        panel.addStyleName("notes");

        return panel;
    }
    //</editor-fold>
}
