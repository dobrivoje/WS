package Views.MainMenu.CRM;

import Trees.CRM.Customer_CRMCases_Tree;
import Trees.CRM.Salesman_CRMCases_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
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
        buildContentWithComponents(activeCasesBySalesmanPanel(), activeCasesByCustomerPanel(), overduePanel(), createNotesPanel(""));
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component activeCasesBySalesmanPanel() {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {
                Salesman_CRMCases_Tree csct = new Salesman_CRMCases_Tree("", S, formAllowed);
                subPanels.add(new Panel(S.toString(), csct));
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active CRM Cases by Salesman", subPanels, formAllowed);
    }

    private Component activeCasesByCustomerPanel() {
        try {
            for (Customer S : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                Customer_CRMCases_Tree ccct = new Customer_CRMCases_Tree("", S, formAllowed);
                subPanels.add(new Panel(S.toString(), ccct));
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active CRM Cases by Customers", subPanels, formAllowed);
    }

    private Component overduePanel() {
        subPanels.add(new Panel());
        return createPanelComponent("Overdue CRM Cases", subPanels, formAllowed);
    }
    //</editor-fold>
}
