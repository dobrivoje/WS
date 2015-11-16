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
import org.superb.apps.utilities.datum.Dates;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;
import ws.MyUI;
import static ws.MyUI.DS;

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public View_CRM() {
        super("Customer Relationship Management");

        dynamicPanels.add(0, salesCasesPanel());
        dynamicPanels.add(1, notRelizedCasesPanel());
        // dynamicPanels.add(2, activeCasesBySalesmanPanel1());

        buildContentWithComponents(
                activeCasesBySalesmanPanel(),
                // dynamicPanels.get(2),
                activeCasesByCustomerPanel(),
                dynamicPanels.get(0),
                dynamicPanels.get(1)
        );
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component activeCasesBySalesmanPanel() {
        try {
            CustomSearchData csd = new CustomSearchData();
            csd.setCaseFinished(false);
            csd.setSaleAgreeded(false);

            for (Salesman S : DS.getSalesmanController().getAll()) {
                csd.setSalesman(S);
                List<CrmCase> L = DS.getSearchController().getCRMCases(csd);

                if (!L.isEmpty()) {
                    Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases("", L, formAllowed);
                    subPanels.add(new Panel(S.toString(), csct));
                }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Salesrep CRM Cases", subPanels, formAllowed);
    }

    private Component activeCasesBySalesmanPanel1() {
        final String panelHeader = "Active Salesrep CRM Cases";
        final Map<String, MenuBar.Command> panelCommands = new LinkedHashMap<>();

        CustomSearchData csd = new CustomSearchData();
        Dates d = new Dates();

        //<editor-fold defaultstate="collapsed" desc="Last Month Salesrep Cases">
        panelCommands.put("Last Month Period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            csd.setStartDate(d.getFrom());
            csd.setEndDate(d.getTo());

            updateUIPanel(2,
                    createPanelComponent(
                            panelHeader,
                            getAdvancedSearchSalesrepCases(csd),
                            formAllowed,
                            panelCommands)
            );
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Last Two Months Salesrep Cases">
        panelCommands.put("Last Two Months Period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            d.setMonthsBackForth(-1);
            csd.setStartDate(d.getFrom());
            csd.setEndDate(d.getTo());

            updateUIPanel(2,
                    createPanelComponent(
                            panelHeader,
                            getAdvancedSearchSalesrepCases(csd),
                            formAllowed,
                            panelCommands)
            );
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Last Three Months Salesrep Cases">
        panelCommands.put("Last Three Months Period", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                d.setMonthsBackForth(-2);
                csd.setStartDate(d.getFrom());
                csd.setEndDate(d.getTo());

                updateUIPanel(2,
                        createPanelComponent(
                                panelHeader,
                                getAdvancedSearchSalesrepCases(csd),
                                formAllowed,
                                panelCommands)
                );
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Advanced Search Dialog">
        panelCommands.put("Advanced Search Features", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            CustomSearchData csd2 = new CustomSearchData();
            csd2.setCaseFinished(false);
            csd2.setSaleAgreeded(false);
            Form_CustomSearch FCS = new Form_CustomSearch(0b111110000, csd2);

            SelectorDialog SD = new SelectorDialog(FCS);

            FCS.setUpdateDataListener((IUpdateData<CustomSearchData>) (CustomSearchData CSD) -> {
                updateUIPanel(2,
                        createPanelComponent(
                                panelHeader,
                                getAdvancedSearchSalesrepCases(CSD),
                                formAllowed,
                                panelCommands)
                );
            });

            getUI().addWindow(SD);
        });
        //</editor-fold>

        return createPanelComponent(
                panelHeader,
                getAdvancedSearchSalesrepCases(csd),
                formAllowed, panelCommands
        );
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
        String panelHeader = "REALIZED SALES";
        Map<String, MenuBar.Command> panelCommands = new LinkedHashMap<>();
        List<Panel> panels = new ArrayList();

        //<editor-fold defaultstate="collapsed" desc="Last Months Sales">
        panelCommands.put("Last Month Period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(0);

            panels.addAll(getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()));

            updateUIPanel(0,
                    createPanelComponent(
                            panelHeader,
                            panels,
                            formAllowed,
                            panelCommands)
            );
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Last Two Months Sales">
        panelCommands.put("Last Two Months Period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(-1);

            panels.addAll(getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()));

            updateUIPanel(0,
                    createPanelComponent(
                            panelHeader,
                            panels,
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

                panels.addAll(getSalesForPeriod(dateInterval.getFrom(), dateInterval.getTo()));

                updateUIPanel(0,
                        createPanelComponent(
                                panelHeader,
                                panels,
                                formAllowed,
                                panelCommands)
                );
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Advanced Search Dialog">
        panelCommands.put("Advanced Search Features", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            CustomSearchData csd = new CustomSearchData();
            csd.setCaseFinished(true);
            csd.setSaleAgreeded(true);
            Form_CustomSearch FCS = new Form_CustomSearch(0b111110000, csd);

            SelectorDialog SD = new SelectorDialog(FCS);

            FCS.setUpdateDataListener((IUpdateData<CustomSearchData>) (CustomSearchData CSD) -> {

                panels.addAll(getAdvancedSearchSales(CSD));

                updateUIPanel(0,
                        createPanelComponent(
                                panelHeader,
                                panels,
                                formAllowed,
                                panelCommands)
                );
            });

            getUI().addWindow(SD);
        });
        //</editor-fold>

        return createPanelComponent(
                panelHeader,
                panels,
                formAllowed, panelCommands
        );
    }

    private Component notRelizedCasesPanel() {
        String panelHeader = "CASES NOT SIGNED !";
        Map<String, MenuBar.Command> panelCommands = new LinkedHashMap<>();
        List<Panel> panels = new ArrayList();

        panelCommands.put("Last Three Months Period", (Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(-2);
            panels.addAll(getNotSignedCases(dateInterval.getFrom(), dateInterval.getTo()));
            updateUIPanel(1,
                    createPanelComponent(
                            panelHeader,
                            panels,
                            formAllowed,
                            panelCommands)
            );
        });

        panelCommands.put("Last Six Months Period",
                (Command) (MenuBar.MenuItem selectedItem) -> {
                    dateInterval.setMonthsBackForth(-5);
                    panels.addAll(getNotSignedCases(dateInterval.getFrom(), dateInterval.getTo()));
                    updateUIPanel(1,
                            createPanelComponent(
                                    panelHeader,
                                    panels,
                                    formAllowed,
                                    panelCommands)
                    );
                }
        );
        panelCommands.put("Last Year Period",
                (Command) (MenuBar.MenuItem selectedItem) -> {
                    dateInterval.setMonthsBackForth(-11);
                    panels.addAll(getNotSignedCases(dateInterval.getFrom(), dateInterval.getTo()));
                    updateUIPanel(1,
                            createPanelComponent(
                                    panelHeader,
                                    panels,
                                    formAllowed,
                                    panelCommands)
                    );
                }
        );

        //<editor-fold defaultstate="collapsed" desc="Advanced Search Dialog">
        panelCommands.put("Advanced Search Features", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            CustomSearchData csd = new CustomSearchData();
            csd.setCaseFinished(true);
            csd.setSaleAgreeded(false);

            Form_CustomSearch FCS = new Form_CustomSearch(0b111110000, csd);
            SelectorDialog SD = new SelectorDialog(FCS);

            FCS.setUpdateDataListener((IUpdateData<CustomSearchData>) (CustomSearchData CSD) -> {

                panels.addAll(getNotSignedCases(CSD.getStartDate(), CSD.getEndDate()));

                updateUIPanel(1,
                        createPanelComponent(
                                panelHeader,
                                panels,
                                formAllowed,
                                panelCommands)
                );
            });

            getUI().addWindow(SD);
        });
        //</editor-fold>

        return createPanelComponent(panelHeader,
                panels,
                formAllowed, panelCommands
        );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getSalesForPeriod">
    private List<Panel> getSalesForPeriod(Date from, Date to) {
        List<Panel> LP = new ArrayList();

        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                List<CrmCase> L = DS.getCRMController().getCRM_Salesrep_Sales_Cases(S, from, to);

                if (!L.isEmpty()) {
                    Tree_SalesmanSales tss = new Tree_SalesmanSales("", L, from, to, formAllowed);
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
            for (Map.Entry<Salesman, List<RelSALE>> RS : DS.getSearchController().getSalesrepSales(csd).entrySet()) {

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
                    Tree_SalesmanCRMCases tss = new Tree_SalesmanCRMCases("", L, formAllowed, from, to);
                    LP.add(new Panel(S.toString(), tss));
                }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>

    private List<Panel> getAdvancedSearchSalesrepCases(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        try {

            for (Salesman S : DS.getSalesmanController().getAll()) {
                csd.setSalesman(S);
                List<CrmCase> L = DS.getSearchController().getCRMCases(csd);

                if (!L.isEmpty()) {
                    Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases("", L, formAllowed);
                    subPanels.add(new Panel(S.toString(), csct));
                }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
}
