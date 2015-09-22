package Views.MainMenu.CRM;

import Trees.CRM.SALES.Tree_SalesmanSalesInPeriod;
import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.Tree_SalesmanCRMCases;
import Views.View_Dashboard;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import org.superb.apps.utilities.datum.Dates;
import ws.MyUI;
import static ws.MyUI.DS;

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);
    private final Component salesCasesPanelComponent;

    private final List<Tree_SalesmanSalesInPeriod> treesSSP = new ArrayList<>();
    private final Dates dates = new Dates();

    public View_CRM() {
        super("Customer Relationship Management");

        buildContentWithComponents(
                activeCasesBySalesmanPanel(),
                activeCasesByCustomerPanel(),
                salesCasesPanelComponent = salesCasesPanel(),
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
        //<editor-fold defaultstate="collapsed" desc="Last Two Months Sales">
        panelCommands.put("Sales For Last Two Months", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dates.setFrom(-2);
            getSalesForPeriod(dates.getFrom(), dates.getTo());

            dashboardPanels.replaceComponent(
                    salesCasesPanelComponent,
                    createPanelComponent("Curent/Last Month Sales", subPanels, formAllowed, panelCommands)
            );
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Last Three Months Sales">
        panelCommands.put("Sales For Last Three Months", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                dates.setFrom(-3);
                getSalesForPeriod(dates.getFrom(), dates.getTo());

                dashboardPanels.replaceComponent(
                        salesCasesPanelComponent,
                        createPanelComponent("Curent/Last Month Sales", subPanels, formAllowed, panelCommands)
                );
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Sales In the June 2015">
        panelCommands.put("Sales In the June 2015", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                // subPanels.clear();
                dates.setFrom(1, 5, 2015);
                dates.setTo(31, 5, 2015);

                getSalesForPeriod(dates.getFrom(), dates.getTo());

                dashboardPanels.replaceComponent(
                        salesCasesPanelComponent,
                        createPanelComponent("Curent/Last Month Sales", subPanels, formAllowed, panelCommands)
                );
            }
        });
        //</editor-fold>

        getSalesForPeriod(dates.getFrom(), dates.getTo());

        return createPanelComponent("Curent/Last Month Sales", subPanels, formAllowed, panelCommands);
    }
    //</editor-fold>

    private void getSalesForPeriod(Date from, Date to) {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                List<RelSALE> L = DS.getCRMController().getCRM_Sales(S, from, to);

                if (!L.isEmpty()) {
                    Tree_SalesmanSalesInPeriod tss = new Tree_SalesmanSalesInPeriod("", S, from, to, formAllowed);
                    treesSSP.add(tss);
                    subPanels.add(new Panel(S.toString(), tss));
                }
            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }
    }
}
