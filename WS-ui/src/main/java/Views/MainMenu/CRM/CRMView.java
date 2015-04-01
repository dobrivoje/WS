package Views.MainMenu.CRM;

import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CRMView extends DashboardView {

    public static final String VIEW_NAME = "CRMView";

    public CRMView() {
        super("Customer Relationship Management");
        buildContentWithComponents(
                overduePanel(), opportunitiesPanel(), leadsPanel(), notesPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component overduePanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Overdue Processes");
        Component contentWrapper = createContentWrapper(VL);

        return contentWrapper;
    }

    private Component leadsPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Leads");
        Component contentWrapper = createContentWrapper(VL);

        return contentWrapper;
    }

    private Component opportunitiesPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Opportunities");
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
    //</editor-fold>
}