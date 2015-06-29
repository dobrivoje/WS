package Views.SYSNOTIF;

import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import db.ent.InfSysUser;
import db.ent.Salesman;
import enums.ISUserType;
import ws.MyUI;
import static ws.MyUI.DS;

public class SysNotifView extends DashboardView {

    public SysNotifView() {
        super("System Notification Board");

        ISUserType IST = MyUI.get().getIS_USER_TYPE();
        InfSysUser ISU = MyUI.get().getGetLoggedInUser();
        Salesman S = DS.getINFSYSUSERController().getSalesman(ISU);

        String caption;

        switch (IST) {
            case SALESMAN:
                caption = S.getFkIdbl().getName().concat(" PANEL");
                buildContentWithComponents(panel(caption), panel("BLACK LIST CUSTOMERS !"), panel(caption), notesPanel(caption));
                break;
            case ADMIN:
                buildContentWithComponents(panel("ADMIN PANEL"), panel("users who don't work often"), panel("logged users in the period"), notesPanel("admin notes"));
                break;
            case SECTOR_MANAGER:
                buildContentWithComponents(panel("NEW CRM CASES"), panel("NEW SALES !"));
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
