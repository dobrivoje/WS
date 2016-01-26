package Views.CRM;

import Main.MyUI;
import static Main.MyUI.DS;
import Trees.CRM.SALES.Tree_MD_SalesmanSales;
import db.ent.custom.CustomSearchData;
import Uni.Dialogs.Form_CustomSearch;
import Uni.Dialogs.SelectorDialog;
import Trees.CRM.SALES.Tree_SalesrepAdvSales;
import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.Tree_SalesmanCRMCases;
import Trees.Tree_CRM_CP;
import org.superb.apps.utilities.vaadin.Views.View_Dashboard;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import org.superb.apps.utilities.datum.Dates;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public View_CRM() {
        super("Customer Relationship Management");

        dynamicPanels.add(0, salesCasesPanel());
        dynamicPanels.add(1, notRelizedCasesPanel());
        // dynamicPanels.add(2, activeCasesBySalesmanPanel1());

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
            CustomSearchData csd = new CustomSearchData();
            csd.setCaseFinished(false);
            csd.setSaleAgreeded(false);

            for (Salesman S : DS.getSalesmanController().getAll()) {
                csd.setSalesman(S);
                List<CrmCase> L = DS.getSearchController().getCRMCases(csd);

                if (!L.isEmpty()) {
                    for (CrmCase cc : L) {
                        Map<Object, List> SR_Cases = new HashMap<>();
                        SR_Cases.put(cc, DS.getCRMController().getCRM_Processes(cc));

                        Tree_CRM_CP csct = new Tree_CRM_CP(SR_Cases, formAllowed);
                        subPanels.add(new Panel(S.toString(), csct));
                    }
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

            CustomSearchData csd = new CustomSearchData();
            csd.setStartDate(dateInterval.getFrom());
            csd.setEndDate(dateInterval.getTo());

            panels.addAll(getSalesForPeriod(csd));

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

            CustomSearchData csd = new CustomSearchData();
            csd.setStartDate(dateInterval.getFrom());
            csd.setEndDate(dateInterval.getTo());

            panels.addAll(getSalesForPeriod(csd));

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

                CustomSearchData csd = new CustomSearchData();
                csd.setStartDate(dateInterval.getFrom());
                csd.setEndDate(dateInterval.getTo());

                panels.addAll(getSalesForPeriod(csd));

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
            Form_CustomSearch FCS = new Form_CustomSearch(0b1111110000, csd);

            SelectorDialog SD = new SelectorDialog(FCS);

            FCS.setUpdateDataListener((IUpdateData<CustomSearchData>) (CustomSearchData CSD) -> {

                panels.addAll(getSalesForPeriod(CSD));

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

            CustomSearchData csd = new CustomSearchData();
            csd.setStartDate(dateInterval.getFrom());
            csd.setEndDate(dateInterval.getTo());

            panels.addAll(getNotSignedCases(csd));
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

                    CustomSearchData csd = new CustomSearchData();
                    csd.setStartDate(dateInterval.getFrom());
                    csd.setEndDate(dateInterval.getTo());

                    panels.addAll(getNotSignedCases(csd));
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

                    CustomSearchData csd = new CustomSearchData();
                    csd.setStartDate(dateInterval.getFrom());
                    csd.setEndDate(dateInterval.getTo());

                    panels.addAll(getNotSignedCases(csd));
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

            Form_CustomSearch FCS = new Form_CustomSearch(0b1111110000, csd);
            SelectorDialog SD = new SelectorDialog(FCS);

            FCS.setUpdateDataListener((IUpdateData<CustomSearchData>) (CustomSearchData CSD) -> {

                panels.addAll(getNotSignedCases(CSD));

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
    private List<Panel> getSalesForPeriod(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        try {
            for (Salesman s : DS.getSalesmanController().getAll()) {

                // if (!DS.getCRMController().getCRM_MD_CRM_Sales(s, from, to).isEmpty()) {
                Tree_MD_SalesmanSales tss = new Tree_MD_SalesmanSales(csd, formAllowed);
                LP.add(new Panel(s.toString(), tss));
                //}

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
    private List<Panel> getNotSignedCases(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        Date f = csd.getStartDate() == null ? new Date(0) : csd.getStartDate();
        Date t = csd.getEndDate() == null ? new Date() : csd.getEndDate();

        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {

                List<CrmCase> L = DS.getCRMController().getCRM_CasesStats(S, true, false, f, t);

                if (!L.isEmpty()) {
                    Tree_SalesmanCRMCases tss = new Tree_SalesmanCRMCases("", L, formAllowed);
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
