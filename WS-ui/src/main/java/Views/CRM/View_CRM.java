package Views.CRM;

import Main.MyUI;
import static Main.MyUI.DS;
import Trees.CRM.SALES.Tree_MD_SalesmanSales;
import db.ent.custom.CustomSearchData;
import Uni.Dialogs.Form_CustomSearch;
import Uni.Dialogs.SelectorDialog;
import Trees.Tree_MD_CrmCaseProcesses;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.Panel;
import org.superbapps.utils.vaadin.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.RelSALE;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.superbapps.auth.roles.Roles;
import org.superbapps.utils.vaadin.Trees.IUpdateData;
import org.superbapps.utils.vaadin.Views.View_Dashboard;

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().hasRole(Roles.R_WS_CRM_MAINTENANCE);

    public View_CRM() {
        super("Customer Relationship Management");

        dynamicPanels.add(0, realizedSalesPanel());
        dynamicPanels.add(1, casesNonSignedPanel());

        buildContentWithComponents(
                activeSalesrepCRMCasesPanel(),
                activeCRMCasesByCustomersPanel(),
                dynamicPanels.get(0),
                dynamicPanels.get(1)
        );
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component activeSalesrepCRMCasesPanel() {
        try {
            CustomSearchData csd = new CustomSearchData();
            csd.setCaseFinished(false);
            csd.setSaleAgreeded(false);

            for (Salesman S : DS.getSalesmanController().getAll()) {
                csd.setSalesman(S);

                List<CrmCase> activeCases = DS.getSearchController().getCRMCases(csd);

                if (!activeCases.isEmpty()) {
                    Map<Object, List> M = new HashMap<>();

                    for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd)) {
                        M.put(cc, cc.getCrmProcessList());
                    }

                    Tree_MD_CrmCaseProcesses csct = new Tree_MD_CrmCaseProcesses(M, false, formAllowed, false);
                    subPanels.add(new Panel(S.toString(), csct));
                }

            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Salesrep CRM Cases", subPanels, formAllowed);
    }

    private Component activeCRMCasesByCustomersPanel() {
        CustomSearchData csd = new CustomSearchData();
        csd.setCaseFinished(false);
        csd.setSaleAgreeded(false);

        try {
            for (Customer C : DS.getSearchController().getCustomers(csd)) {
                csd.setCustomer(C);

                Map<Object, List> M = new HashMap<>();
                for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd)) {
                    M.put(cc, cc.getCrmProcessList());
                }

                Tree_MD_CrmCaseProcesses ccct = new Tree_MD_CrmCaseProcesses(M, false, formAllowed, false);
                subPanels.add(new Panel(C.toString(), ccct));

            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Customers CRM Cases", subPanels, formAllowed);
    }

    private Component realizedSalesPanel() {
        String panelHeader = "REALIZED SALES";
        Map<String, MenuBar.Command> panelCommands = new LinkedHashMap<>();
        List<Panel> panels = new ArrayList();

        //<editor-fold defaultstate="collapsed" desc="Last Months Sales">
        panelCommands.put("Last Month Period", (MenuBar.Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(-1);

            CustomSearchData csd = new CustomSearchData();
            csd.setStartDate(dateInterval.getFrom());
            csd.setEndDate(dateInterval.getTo());

            panels.addAll(getAdvancedSearchSales(csd));

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
            dateInterval.setMonthsBackForth(-2);

            CustomSearchData csd = new CustomSearchData();
            csd.setStartDate(dateInterval.getFrom());
            csd.setEndDate(dateInterval.getTo());

            panels.addAll(getAdvancedSearchSales(csd));

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
                dateInterval.setMonthsBackForth(-3);

                CustomSearchData csd = new CustomSearchData();
                csd.setStartDate(dateInterval.getFrom());
                csd.setEndDate(dateInterval.getTo());

                panels.addAll(getAdvancedSearchSales(csd));

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

    private Component casesNonSignedPanel() {
        String panelHeader = "CASES NOT SIGNED !";
        Map<String, MenuBar.Command> panelCommands = new LinkedHashMap<>();
        List<Panel> panels = new ArrayList();

        //<editor-fold defaultstate="collapsed" desc="3 months">
        panelCommands.put("Last Three Months Period", (Command) (MenuBar.MenuItem selectedItem) -> {
            dateInterval.setMonthsBackForth(-3);

            CustomSearchData csd = new CustomSearchData();
            csd.setCaseFinished(true);
            csd.setSaleAgreeded(false);
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
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="6 months">
        panelCommands.put("Last Six Months Period",
                (Command) (MenuBar.MenuItem selectedItem) -> {
                    dateInterval.setMonthsBackForth(-6);

                    CustomSearchData csd = new CustomSearchData();
                    csd.setCaseFinished(true);
                    csd.setSaleAgreeded(false);
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
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="last year">
        panelCommands.put("Last Year Period",
                (Command) (MenuBar.MenuItem selectedItem) -> {
                    dateInterval.setMonthsBackForth(-12);

                    CustomSearchData csd = new CustomSearchData();
                    csd.setCaseFinished(true);
                    csd.setSaleAgreeded(false);
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
        //</editor-fold>

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

        return createPanelComponent(
                panelHeader,
                panels,
                formAllowed, panelCommands
        );
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Advanced Sell Search">
    private List<Panel> getAdvancedSearchSales(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        try {
            for (Salesman s : DS.getSearchController().getSalesreps(csd)) {
                Tree_MD_SalesmanSales tss = new Tree_MD_SalesmanSales(csd, formAllowed, false);

                float as = 0;
                for (RelSALE sale : (List<RelSALE>) DS.getSearchController().getSalesrepSales(csd).get(s)) {
                    as += sale.getAmmount();
                }

                LP.add(new Panel("All Sells Amount - " + as + " L", tss));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getNotSignedCases">
    private List<Panel> getNotSignedCases(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        try {
            for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd)) {
                if (cc != null) {
                    csd.setCrmCase(cc);
                    Map<Object, List> M = new HashMap<>();

                    M.put(cc, cc.getCrmProcessList());

                    Tree_MD_CrmCaseProcesses ccct = new Tree_MD_CrmCaseProcesses(M, false, formAllowed, false);
                    LP.add(new Panel(cc.getFK_IDRSC().getFK_IDC().toString(), ccct));
                }

            }
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>
}
