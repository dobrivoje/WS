package Views.CRM;

import Main.MyUI;
import static Main.MyUI.DS;
import Trees.CRM.SALES.Tree_MD_SalesmanSales;
import db.ent.custom.CustomSearchData;
import Uni.Dialogs.Form_CustomSearch;
import Uni.Dialogs.SelectorDialog;
import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.Tree_SalesmanCRMCases;
import Trees.Tree_MD_CrmCaseProcesses;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;

public class View_CRM extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

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
                List<CrmCase> L = DS.getSearchController().getCRMCases(csd);

                if (!L.isEmpty()) {
                    for (CrmCase cc : L) {
                        Map<Object, List> SR_Cases = new HashMap<>();
                        SR_Cases.put(cc, DS.getCRMController().getCRM_Processes(cc));

                        Tree_MD_CrmCaseProcesses csct = new Tree_MD_CrmCaseProcesses(SR_Cases, formAllowed);
                        subPanels.add(new Panel(S.toString(), csct));
                    }
                }

            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return createPanelComponent("Active Salesrep CRM Cases", subPanels, formAllowed);
    }

    private Component activeCRMCasesByCustomersPanel() {
        try {
            for (Customer C : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                Tree_CustomerCRMCases ccct = new Tree_CustomerCRMCases("", C, formAllowed);
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
            dateInterval.setMonthsBackForth(0);

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

        //<editor-fold defaultstate="collapsed" desc="Last Three Months Sales">
        panelCommands.put("Last Three Months Period", (MenuBar.Command) new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
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

    //<editor-fold defaultstate="collapsed" desc="Advanced Sell Search">
    private List<Panel> getAdvancedSearchSales(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        try {
            for (Map.Entry<Salesman, List<RelSALE>> RS : DS.getSearchController().getSalesrepSales(csd).entrySet()) {

                Map<Object, List> M = new HashMap<>();
                M.put(RS.getKey(), RS.getValue());

                // if (!RS.getValue().isEmpty()) {
                    Tree_MD_SalesmanSales tss = new Tree_MD_SalesmanSales(M, formAllowed, true);

                    double as = 0;

                    for (RelSALE a : RS.getValue()) {
                        as += a.getAmmount();
                    }

                    LP.add(new Panel("All Sells Amount - " + as + " L", tss));
                // }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getNotSignedCases">
    private List<Panel> getNotSignedCases(CustomSearchData csd) {
        List<Panel> LP = new ArrayList();

        // Date f = csd.getStartDate() == null ? new Date(0) : csd.getStartDate();
        // Date t = csd.getEndDate() == null ? new Date() : csd.getEndDate();
        try {
            for (Salesman S : DS.getSalesmanController().getAll()) {
                csd.setSalesman(S);

                // List<CrmCase> L = DS.getCRMController().getCRM_CasesStats(S, true, false, f, t);
                //         if (!L.isEmpty()) {
                Tree_SalesmanCRMCases tss = new Tree_SalesmanCRMCases(csd, formAllowed, true);
                LP.add(new Panel(S.toString(), tss));
                //         }
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        return LP;
    }
    //</editor-fold>
}
