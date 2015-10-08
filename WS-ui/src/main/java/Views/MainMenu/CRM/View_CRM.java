package Views.MainMenu.CRM;

import Dialogs.SelectorDialog;
import Forms.CRM.Form_CRMCase;
import Trees.CRM.SALES.Tree_SalesmanSales;
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
import java.util.Date;
import java.util.List;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import ws.MyUI;
import static ws.MyUI.DS;

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public View_CRM() {
        super("Customer Relationship Management");

        salesCasesPanelComponent.add(salesCasesPanel());

        buildContentWithComponents(
                activeCasesBySalesmanPanel(),
                activeCasesByCustomerPanel(),
                salesCasesPanelComponent.get(0),
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

        return createPanelComponent("Active Salesrep CRM Cases", subPanels, formAllowed);
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
        panelCommands.put("Last Two Months Sales", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(-1);

            updateUIPanel(dashboardPanels,
                    createPanelComponent(
                            "Realized Sales",
                            getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()),
                            formAllowed,
                            panelCommands)
            );
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Last Three Months Sales">
        panelCommands.put("Last Three Months Sales", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                dateInterval.setMonthsBackForth(-2);

                updateUIPanel(dashboardPanels,
                        createPanelComponent(
                                "Realized Sales",
                                getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()),
                                formAllowed,
                                panelCommands)
                );
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Sales In the June 2015">
        panelCommands.put("Sales In the June 2015", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                // subPanels.clear();
                dateInterval.setFrom(1, 6, 2015);
                dateInterval.setTo(30, 6, 2015);

                updateUIPanel(dashboardPanels,
                        createPanelComponent(
                                "Realized Sales",
                                getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()),
                                formAllowed,
                                panelCommands)
                );
            }
        });

        panelCommands.put("Test - Custom Dialog", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            getUI().addWindow(
                    new SelectorDialog(
                            "Selector Dialog",
                            new Form_CRMCase(),
                            "img/crm/crmCovekILaptop.png",
                            "Find", null, true)
            );
        });
        //</editor-fold>

        getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo());

        return createPanelComponent("Realized Sales", subPanels, formAllowed, panelCommands);
    }
    //</editor-fold>

    private List<Panel> getSalesForPeriod(Date from, Date to) {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                List<RelSALE> L = DS.getCRMController().getCRM_Sales(S, from, to);

                if (!L.isEmpty()) {
                    Tree_SalesmanSales tss = new Tree_SalesmanSales("", S, from, to, formAllowed);
                    subPanels.add(new Panel(S.toString(), tss));
                }
            }
            return subPanels;

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
            return null;
        }
    }

}
