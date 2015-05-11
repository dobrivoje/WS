package Views.MainMenu.CRM;

import Layouts.InlineCSSLayout;
import Trees.CRM_SingleCase_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Salesman;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static ws.MyUI.DS;

public class CRMView extends DashboardView {

    public static final String VIEW_NAME = "CRMView";

    public CRMView() {
        super("Customer Relationship Management");
        buildContentWithComponents(
                overduePanel(), activeCasesBySalesmanPanel(), activeCasesByCustomerPanel(), notesPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component overduePanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Overdue Processes");
        Component contentWrapper = createContentWrapper(VL);

        return contentWrapper;
    }

    private Component activeCasesBySalesmanPanel() {
        VerticalLayout rootPanel = new VerticalLayout();

        rootPanel.setCaption("Active CRM Cases by Salesman");
        InlineCSSLayout ICL = new InlineCSSLayout();
        ICL.setSizeFull();

        rootPanel.addComponent(ICL);

        try {
            for (CrmCase cc : DS.getCrmController().getCRM_AllActiveCases(false)) {
                CRM_SingleCase_Tree csct = new CRM_SingleCase_Tree("", cc);

                Panel p = new Panel(cc.getFK_IDRSC().getFK_IDS().toString(), csct);
                p.setWidth(275, Unit.PIXELS);

                HorizontalLayout hl = new HorizontalLayout(p);
                hl.setMargin(true);
                hl.setSpacing(true);

                ICL.addComponent(hl);
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        Component contentWrapper = createContentWrapper(rootPanel);
        return contentWrapper;
    }

    private Component activeCasesByCustomerPanel() {
        VerticalLayout rootPanel = new VerticalLayout();

        rootPanel.setCaption("Active CRM Cases by Customers");
        InlineCSSLayout ICL = new InlineCSSLayout();
        ICL.setSizeFull();

        rootPanel.addComponent(ICL);

        Map<Salesman, List<CrmCase>> SC = new HashMap<>();

        try {
            for (CrmCase ccase : DS.getCrmController().getCRM_AllActiveCases(false)) {
                if (SC.containsKey(ccase.getFK_IDRSC().getFK_IDS())) {
                    Salesman S = ccase.getFK_IDRSC().getFK_IDS();
                    List<CrmCase> salesmanCasesList = SC.remove(S);

                    salesmanCasesList.add(ccase);
                    SC.put(S, salesmanCasesList);
                }

                CRM_SingleCase_Tree csct = new CRM_SingleCase_Tree("", ccase);

                Panel p = new Panel(ccase.getFK_IDRSC().getFK_IDS().toString(), csct);
                p.setWidth(275, Unit.PIXELS);

                HorizontalLayout hl = new HorizontalLayout(p);
                hl.setMargin(true);
                hl.setSpacing(true);

                ICL.addComponent(hl);
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        Component contentWrapper = createContentWrapper(rootPanel);
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
