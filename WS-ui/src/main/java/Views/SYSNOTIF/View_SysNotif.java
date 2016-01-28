package Views.SYSNOTIF;

import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.SALES.Tree_SalesmanSales;
import Trees.CRM.Tree_SalesmanCRMCases;
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

public class View_SysNotif extends View_Dashboard {

    ISUserType IST = MyUI.get().getLoggedISUserType();
    Salesman S = MyUI.get().getLoggedSalesman();
    String SECTOR = S.getFkIdbl().getName();

    boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

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

        //<editor-fold defaultstate="collapsed" desc="My Active CRM Cases">
        try {
            CustomSearchData csd = new CustomSearchData();
            csd.setSalesman(S);
            csd.setCaseFinished(false);

            Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases(csd, formEditAllowed, false);

            subPanels.add(new Panel(S.toString(), csct));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("My Active CRM Cases", subPanels, formEditAllowed));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="My Realised Sales">
        try {
            CustomSearchData csd = new CustomSearchData();
            csd.setSalesman(S);

            Tree_SalesmanSales csct = new Tree_SalesmanSales(csd, formEditAllowed, false);
            subPanels.add(new Panel(S.toString(), csct));
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

            for (Salesman BLS : DS.getSalesmanController().getSalesman(S.getFkIdbl())) {
                csd1.setSalesman(BLS);

                Tree_SalesmanSales csct = new Tree_SalesmanSales(csd1, formAllowed, false);
                subPanels.add(new Panel(BLS.toString(), csct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        sectorManagerPanels.add(createPanelComponent("REALIZED SALES", subPanels, formAllowed));
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        try {
            CustomSearchData csd2 = new CustomSearchData();
            csd2.setCaseFinished(true);
            csd2.setSaleAgreeded(true);
            csd2.setMonthsBackForth(-1);
            csd2.setBussinesLine(S.getFkIdbl());

            for (Salesman s : DS.getSearchController().getSalesreps(csd2)) {
                csd2.setSalesman(s);
                Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases(csd2, formAllowed, false);
                subPanels.add(new Panel(s.toString(), csct));
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
                Tree_CustomerCRMCases ccct = new Tree_CustomerCRMCases("", DS.getSearchController().getCRMCases(csd3), formAllowed);
                subPanels.add(new Panel(c.toString(), ccct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        sectorManagerPanels.add(createPanelComponent("NEW CUSTOMER CASES", subPanels, formAllowed));

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            CustomSearchData csd4 = new CustomSearchData();
            csd4.setCaseFinished(false);
            csd4.setBussinesLine(S.getFkIdbl());

            for (Salesman s : DS.getSearchController().getSalesreps(csd4)) {
                csd4.setSalesman(s);
                Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases(csd4, formAllowed, false);
                subPanels.add(new Panel(s.toString(), csct));
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
