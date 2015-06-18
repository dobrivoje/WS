package Views.MainMenu.CRM;

import Layouts.InlineCSSLayout;
import Trees.CRM.Customer_CRMCases_Tree2;
import Trees.CRM.Salesman_CRMCases_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;
import db.ent.Salesman;
import org.dobrivoje.auth.roles.RolesPermissions;
import ws.MyUI;
import static ws.MyUI.DS;

public class CRMView extends DashboardView {

    private final boolean formAllowed = MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_CRM_NEW_CRM_PROCESS);

    public CRMView() {
        super("Customer Relationship Management");
        buildContentWithComponents(
                activeCasesBySalesmanPanel(), activeCasesByCustomerPanel(), overduePanel(), notesPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component activeCasesBySalesmanPanel() {
        VerticalLayout rootPanel = new VerticalLayout();

        rootPanel.setCaption("Active CRM Cases by Salesman");
        InlineCSSLayout ICL = new InlineCSSLayout();
        ICL.setSizeFull();

        rootPanel.addComponent(ICL);

        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {
                Salesman_CRMCases_Tree csct = new Salesman_CRMCases_Tree("", S, formAllowed);

                Panel p = new Panel(S.toString(), csct);
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

        try {
            for (Customer C : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                Customer_CRMCases_Tree2 ccct = new Customer_CRMCases_Tree2("", C, formAllowed);

                Panel p = new Panel(C.toString(), ccct);
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

    private Component overduePanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Overdue Processes");
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
