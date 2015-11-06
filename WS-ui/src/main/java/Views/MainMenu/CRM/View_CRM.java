package Views.MainMenu.CRM;

import db.ent.custom.CustomSearchData;
import Dialogs.Form_CustomSearch;
import Dialogs.SelectorDialog;
import Trees.CRM.SALES.Tree_SalesmanSales;
import Trees.CRM.SALES.Tree_SalesrepAdvSales;
import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.Tree_SalesmanCRMCases;
import Views.View_Dashboard;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;
import ws.MyUI;
import static ws.MyUI.DS;

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public View_CRM() {
        super("Customer Relationship Management");

        dynamicPanels.add(0, salesCasesPanel());
        dynamicPanels.add(1, notRelizedCasesPanel());

        buildContentWithComponents(
                activeCasesBySalesmanPanel(),
                activeCasesByCustomerPanel(),
                dynamicPanels.get(0),
                dynamicPanels.get(1)
        );
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component activeCasesBySalesmanPanel() {
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                // listom ispod, kontrolišemo not null vrednosti
                // da bi se stablo kreiralo, 
                // inače ako postoji null, stablo se neće kreirati.
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
        final String panelHeader = "REALIZED SALES";
        final Map<String, MenuBar.Command> panelCommands = new LinkedHashMap<>();

        //<editor-fold defaultstate="collapsed" desc="Last Months Sales">
        panelCommands.put("Last Month Period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(0);

            updateUIPanel(0,
                    createPanelComponent(
                            panelHeader,
                            getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()),
                            formAllowed,
                            panelCommands)
            );
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Last Two Months Sales">
        panelCommands.put("Last Two Months Period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(-1);

            updateUIPanel(0,
                    createPanelComponent(
                            panelHeader,
                            getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()),
                            formAllowed,
                            panelCommands)
            );
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Last Three Months Sales">
        panelCommands.put("Last Three Months Period", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                dateInterval.setMonthsBackForth(-2);

                updateUIPanel(0,
                        createPanelComponent(
                                panelHeader,
                                getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()),
                                formAllowed,
                                panelCommands)
                );
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Advanced Search Dialog">
        panelCommands.put("Advanced Search Features", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            Form_CustomSearch FCS = new Form_CustomSearch(0b111110000);
            SelectorDialog SD = new SelectorDialog(FCS);

            FCS.setUpdateDataListener((IUpdateData<CustomSearchData>) (CustomSearchData CSD) -> {
                updateUIPanel(0,
                        createPanelComponent(
                                panelHeader,
                                getAdvancedSearchSales(CSD),
                                formAllowed,
                                panelCommands)
                );
            });

            getUI().addWindow(SD);
        });
        //</editor-fold>

        return createPanelComponent(
                panelHeader,
                getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()),
                formAllowed, panelCommands
        );
    }

    private Component notRelizedCasesPanel() {
        final String panelHeader = "CASES NOT SIGNED !";
        final Map<String, MenuBar.Command> panelCommands = new LinkedHashMap<>();

        panelCommands.put("Last Three Months Period", getCommand(panelHeader, 1, panelCommands, -2));
        panelCommands.put("Last Six Months Period", getCommand(panelHeader, 1, panelCommands, -5));
        panelCommands.put("Last Year Period", getCommand(panelHeader, 1, panelCommands, -11));

        //<editor-fold defaultstate="collapsed" desc="Advanced Search Dialog">
        panelCommands.put("Advanced Search Features", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            Form_CustomSearch FCS = new Form_CustomSearch(0b111110011);
            SelectorDialog SD = new SelectorDialog(FCS);

            FCS.setUpdateDataListener((IUpdateData<CustomSearchData>) (CustomSearchData CSD) -> {
                updateUIPanel(1,
                        createPanelComponent(
                                panelHeader,
                                getNotSignedCases(CSD.getStartDate(), CSD.getEndDate()),
                                formAllowed,
                                panelCommands)
                );
            });

            getUI().addWindow(SD);
        });
        //</editor-fold>

        return createPanelComponent(panelHeader,
                getNotSignedCases(dateInterval.getFrom(), dateInterval.getTo()),
                formAllowed, panelCommands
        );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getSalesForPeriod">
    private List<Panel> getSalesForPeriod(Date from, Date to) {
        List<Panel> LP = new ArrayList();

        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                List<RelSALE> L = DS.getCRMController().getCRM_Sales(S, from, to);

                if (!L.isEmpty()) {
                    Tree_SalesmanSales tss = new Tree_SalesmanSales("", S, from, to, formAllowed);
                    LP.add(new Panel(S.toString(), tss));
                }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Advanced Sell Search">
    private List<Panel> getAdvancedSearchSales(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        try {
            for (Map.Entry<Salesman, List<RelSALE>> RS : DS.getSearchController().getAllSalesrepSales(csd).entrySet()) {

                if (!RS.getValue().isEmpty()) {
                    Tree_SalesrepAdvSales tss = new Tree_SalesrepAdvSales("", RS.getKey(), RS.getValue(), formAllowed);

                    double as = 0;

                    for (RelSALE a : RS.getValue()) {
                        as += a.getAmmount();
                    }

                    LP.add(new Panel("All Sells Amount - " + as + " L", tss));
                }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getNotSignedCases">
    private List<Panel> getNotSignedCases(Date from, Date to) {
        List<Panel> LP = new ArrayList();

        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                List<CrmCase> L = DS.getCRMController().getCRM_CasesStats(S, true, false, from, to);

                if (!L.isEmpty()) {
                    Tree_SalesmanCRMCases tss = new Tree_SalesmanCRMCases("", S, true, false, formAllowed, from, to);
                    LP.add(new Panel(S.toString(), tss));
                }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Panel Command">
    private Command getCommand(String panelHeader, int panelIndex, Map<String, MenuBar.Command> panelCommands, int monthsBack) {
        return (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(monthsBack);

            updateUIPanel(panelIndex,
                    createPanelComponent(
                            panelHeader,
                            getNotSignedCases(dateInterval.getFrom(), dateInterval.getTo()),
                            formAllowed,
                            panelCommands)
            );
        };
    }
    //</editor-fold>
}
