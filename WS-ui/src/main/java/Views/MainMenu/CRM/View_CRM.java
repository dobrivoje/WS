package Views.MainMenu.CRM;

import Trees.CRM.SALES.Tree_SalesmanSales;
import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.Tree_SalesmanCRMCases;
import Views.View_Dashboard;
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

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public View_CRM() {
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
                    Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases("", S, formAllowed);
                    subPanels.add(new Panel(S.toString(), csct));
                }
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Salesmen CRM Cases", subPanels, formAllowed);
    }

    private Component activeCasesByCustomerPanel() {
        try {
            for (Customer C : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                Tree_CustomerCRMCases ccct = new Tree_CustomerCRMCases("", C, formAllowed);
                subPanels.add(new Panel(C.toString(), ccct));
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Customers CRM Cases", subPanels, formAllowed);
    }

    private Component salesCasesPanel() {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {
                Tree_SalesmanSales sst = new Tree_SalesmanSales("", S, null, null, formAllowed);
                subPanels.add(new Panel(S.toString(), sst));
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Curent/Last Month Sales", subPanels, formAllowed);
    }
    //</editor-fold>
}
