package Views.MainMenu.CRM;

import Trees.CRM.CustomerCRMCases_Tree;
import Trees.CRM.SALES.SalesmanSales_Tree;
import Trees.CRM.SalesmanCRMCases_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.List;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import ws.MyUI;
import static ws.MyUI.DS;

public class CRMView extends DashboardView {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public CRMView() {
        super("Customer Relationship Management");
        buildContentWithComponents(
                activeCasesBySalesmanPanel(),
                activeCasesByCustomerPanel(),
                salesCasesPanel(),
                createNotesPanel("")
        );
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component activeCasesBySalesmanPanel() {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                // listom ispod, kontrolišemo not null vrednosti,
                // da bi se stablo kreiralo, inače zbog null, stablo se neće kreirati.
                List<CrmCase> L = DS.getCRMController().getCRM_Cases(S, false);
                if (!L.isEmpty()) {
                    SalesmanCRMCases_Tree csct = new SalesmanCRMCases_Tree("", S, formAllowed);
                    subPanels.add(new Panel(S.toString(), csct));
                }
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Salesmen CRM Cases", subPanels, formAllowed);
    }

    private Component activeCasesByCustomerPanel() {
        try {
            for (Customer S : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                CustomerCRMCases_Tree ccct = new CustomerCRMCases_Tree("", S, formAllowed);
                subPanels.add(new Panel(S.toString(), ccct));
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Customers CRM Cases", subPanels, formAllowed);
    }

    private Component salesCasesPanel() {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {
                SalesmanSales_Tree sst = new SalesmanSales_Tree("", S, null, null, formAllowed);
                subPanels.add(new Panel(S.toString(), sst));
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Curent/Last Month Sales", subPanels, formAllowed);
    }
    //</editor-fold>
}
