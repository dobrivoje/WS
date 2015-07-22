package Views.SYSNOTIF;

import Trees.CRM.Tree_CustomerCRMCases;
import Trees.CRM.SALES.Tree_SalesmanSales;
import Trees.CRM.Tree_SalesmanCRMCases;
import Views.View_Dashboard;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.BussinesLine;
import db.ent.Customer;
import db.ent.Salesman;
import enums.ISUserType;
import java.util.ArrayList;
import java.util.List;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CRM_NEW_CRM_PROCESS;
import static org.dobrivoje.auth.roles.RolesPermissions.R_ROOT_PRIVILEGES;
import ws.MyUI;
import static ws.MyUI.DS;

public class View_SysNotif extends View_Dashboard {

    ISUserType IST = MyUI.get().getLoggedISUserType();
    Salesman S = MyUI.get().getLoggedSalesman();
    String SECTOR = S.getFkIdbl().getName();

    boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public View_SysNotif() throws Exception {
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

        try {
            Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases("", S, formEditAllowed);
            subPanels.add(new Panel(S.toString(), csct));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("Active CRM Cases by Salesman", subPanels, formEditAllowed));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            Tree_SalesmanSales csct = new Tree_SalesmanSales("", S, null, null, formEditAllowed);
            subPanels.add(new Panel(S.toString(), csct));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("last Two Month Sales", subPanels, formEditAllowed));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        List<Component> C = new ArrayList();

        try {

            for (Salesman BLS : DS.getSalesmanController().getSalesman(S.getFkIdbl())) {
                Tree_SalesmanSales csct = new Tree_SalesmanSales("", BLS, null, null, formAllowed);
                subPanels.add(new Panel(BLS.toString(), csct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("NEW SALES FOR THE LAST TWO MONTHS", subPanels, formAllowed));

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        try {

            for (Customer S : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                Tree_CustomerCRMCases ccct = new Tree_CustomerCRMCases("", S, formAllowed);
                subPanels.add(new Panel(S.toString(), ccct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("NEW CUSTOMER CASES", subPanels, formAllowed));

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        try {

            for (Salesman BLS : DS.getSalesmanController().getSalesman(S.getFkIdbl())) {
                Tree_SalesmanCRMCases csct = new Tree_SalesmanCRMCases("", BLS, formAllowed);
                subPanels.add(new Panel(BLS.toString(), csct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("NEW CRM CASES", subPanels, formAllowed));

        buildContentWithComponents(C);
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
