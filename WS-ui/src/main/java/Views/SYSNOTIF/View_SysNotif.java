package Views.SYSNOTIF;

import org.superb.apps.utilities.vaadin.Views.View_Dashboard;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.BussinesLine;
import db.ent.Customer;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import enums.ISUserType;
import java.util.ArrayList;
import java.util.List;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import static org.dobrivoje.auth.roles.RolesPermissions.R_ROOT_PRIVILEGES;
import Main.MyUI;
import static Main.MyUI.DS;
import Trees.CRM.SALES.Tree_MD_CrmCaseSales;
import Trees.CRM.SALES.Tree_MD_SalesmanSales;
import Trees.Tree_MD_CrmCaseProcesses;
import db.ent.CrmCase;
import db.ent.RelSALE;
import org.superb.apps.utilities.datum.Dates;

public class View_SysNotif extends View_Dashboard {

    ISUserType IST = MyUI.get().getLoggedISUserType();
    Salesman S = MyUI.get().getLoggedSalesman();
    String SECTOR = S.getFkIdbl().getName();

    boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);
    Dates d = new Dates();
    Panel datePanel;

    public View_SysNotif() {
        super(MyUI.get().getLoggedSalesman().getFkIdbl().getName() + " Sector Active Events");

        switch (IST) {
            case SALESMAN:
                createSalesmanView(formAllowed);
                break;
            case ADMIN:
                createAdminView();
                break;
            case SECTOR_MANAGER:
                createSectorManagerView();
                break;
            case TOP_MANAGER:
                createGeneralManagerPanel();
                break;
            default:
                buildContentWithComponents(new Panel("this user has no roles difined in database."));
        }

    }

    //<editor-fold defaultstate="collapsed" desc="MAIN PANELS CREATION FOR THE SPECIFIC KIND OF USER">
    private void createSalesmanView(boolean formEditAllowed) {
        List<Component> C = new ArrayList();

        CustomSearchData csd = new CustomSearchData();
        csd.setSalesman(S);

        //<editor-fold defaultstate="collapsed" desc="My Active CRM Cases">
        try {
            csd.setCaseFinished(false);
            csd.setSaleAgreeded(false);

            d.setFrom(csd.getStartDate());
            d.setTo(csd.getEndDate());

            String m = "";
            if (d.getFromStr() == null) {
                m = "For the period until the %s";
            }

            if (d.getToStr() == null) {
                m = "For the period from the %s";
            }

            if (d.getToStr() == null && d.getFromStr() == null) {
                m = "For all the time";
            }

            String msg = String.format(m, new Object[]{d.getFromStr(), d.getToStr()});
            datePanel = new Panel(msg);

            subPanels.add(new Panel(msg));

            // Map<Object, List> M = new HashMap<>();
            /*
             for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd)) {
             M.put(cc, cc.getCrmProcessList());
             }
             */
            Tree_MD_CrmCaseProcesses csct = new Tree_MD_CrmCaseProcesses(csd, false, formAllowed, false);
            subPanels.add(new Panel(S.toString(), csct));

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("My Active CRM Cases", subPanels, formEditAllowed));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="My Realised Sales">
        try {
            csd.setCaseFinished(true);
            csd.setSaleAgreeded(true);

            for (CrmCase cc1 : (List<CrmCase>) DS.getSearchController().getCRMCases(csd)) {
                Tree_MD_CrmCaseSales csct = new Tree_MD_CrmCaseSales(cc1, false, formAllowed, false);
                subPanels.add(new Panel("", csct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("My Realised Sales", subPanels, formEditAllowed));
        //</editor-fold>

        buildContentWithComponents(C);
    }

    private void createAdminView() {
        List<Component> C = new ArrayList();

        for (String s : new String[]{"admin panel", "users who don't work often", "logged users in the period"}) {
            C.add(createPanelComponent(s, subPanels, MyUI.get().isPermitted(R_ROOT_PRIVILEGES)));
        }

        buildContentWithComponents(C);
    }

    private void createSectorManagerView() {
        List<Component> sectorManagerPanels = new ArrayList();

        try {
            dateInterval.setMonthsBackForth(-1);

            CustomSearchData csd1 = new CustomSearchData();
            csd1.setStartDate(dateInterval.getFrom());
            csd1.setEndDate(dateInterval.getTo());
            csd1.setBussinesLine(S.getFkIdbl());

            Tree_MD_SalesmanSales tss = new Tree_MD_SalesmanSales(csd1, formAllowed, false);

            double as = 0;
            for (RelSALE a : (List<RelSALE>) DS.getSearchController().getSalesrepSales(csd1).get(S)) {
                as += a.getAmmount();
            }

            subPanels.add(new Panel("All Sells Amount - " + as + " L", tss));

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        sectorManagerPanels.add(createPanelComponent("REALIZED SALES", subPanels, formAllowed));
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        try {
            CustomSearchData csd2 = new CustomSearchData();
            csd2.setCaseFinished(true);
            csd2.setSaleAgreeded(false);
            csd2.setBussinesLine(S.getFkIdbl());

            for (Customer c : DS.getSearchController().getCustomers(csd2)) {
                csd2.setCustomer(c);

                //Map<Object, List> M = new HashMap<>();
                //for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd2)) {
                //    M.put(cc, cc.getCrmProcessList());
                // }
                Tree_MD_CrmCaseProcesses ccct = new Tree_MD_CrmCaseProcesses(csd2, false, formAllowed, false);
                subPanels.add(new Panel(c.toString(), ccct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        sectorManagerPanels.add(createPanelComponent("CASES NOT SIGNED !", subPanels, formAllowed));

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            CustomSearchData csd3 = new CustomSearchData();
            csd3.setCaseFinished(false);
            csd3.setBussinesLine(S.getFkIdbl());

            for (Customer c : DS.getSearchController().getCustomers(csd3)) {
                csd3.setCustomer(c);

                //Map<Object, List> M = new HashMap<>();
                //for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd3)) {
                //    M.put(cc, cc.getCrmProcessList());
                //}
                Tree_MD_CrmCaseProcesses ccct = new Tree_MD_CrmCaseProcesses(csd3, false, formAllowed, false);
                subPanels.add(new Panel(c.toString(), ccct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        sectorManagerPanels.add(createPanelComponent("NEW CUSTOMER CASES", subPanels, formAllowed));

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            CustomSearchData csd4 = new CustomSearchData();
            csd4.setCaseFinished(false);
            csd4.setSaleAgreeded(false);
            csd4.setBussinesLine(S.getFkIdbl());

            for (Salesman S : DS.getSalesmanController().getAll()) {
                csd4.setSalesman(S);

                //Map<Object, List> M = new HashMap<>();
                //for (CrmCase cc : (List<CrmCase>) DS.getSearchController().getCRMCases(csd4)) {
                //    M.put(cc, cc.getCrmProcessList());
                //}
                Tree_MD_CrmCaseProcesses csct = new Tree_MD_CrmCaseProcesses(csd4, false, formAllowed, false);
                subPanels.add(new Panel(S.toString(), csct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        sectorManagerPanels.add(createPanelComponent("NEW SALESREP CASES", subPanels, formAllowed));

        buildContentWithComponents(sectorManagerPanels);
    }

    private void createGeneralManagerPanel() {
        List<Component> C = new ArrayList();

        for (BussinesLine BL : DS.getBLController().getAll()) {
            C.add(createPanelComponent(BL.getName(), subPanels, MyUI.get().isPermitted(R_ROOT_PRIVILEGES)));
        }

        C.add(createNotesPanel(""));

        buildContentWithComponents(C);
    }
    //</editor-fold>
}
