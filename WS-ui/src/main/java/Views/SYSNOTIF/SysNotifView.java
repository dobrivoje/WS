package Views.SYSNOTIF;

import Trees.CRM.CustomerCRMCases_Tree;
import Trees.CRM.SALES.SalesmanSales_Tree;
import Trees.CRM.SalesmanCRMCases_Tree;
import Views.DashboardView;
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

public class SysNotifView extends DashboardView {

    ISUserType IST = MyUI.get().getLoggedISUserType();
    Salesman S = MyUI.get().getLoggedSalesman();
    String SECTOR = S.getFkIdbl().getName();

    boolean formAllowed = MyUI.get().isPermitted(P_CRM_NEW_CRM_PROCESS);

    public SysNotifView() throws Exception {
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
            SalesmanCRMCases_Tree csct = new SalesmanCRMCases_Tree("", S, formEditAllowed);
            subPanels.add(new Panel(S.toString(), csct));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("Active CRM Cases by Salesman", subPanels, formEditAllowed));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            SalesmanSales_Tree csct = new SalesmanSales_Tree("", S, null, null, formEditAllowed);
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
                SalesmanSales_Tree csct = new SalesmanSales_Tree("", BLS, null, null, formAllowed);
                subPanels.add(new Panel(BLS.toString(), csct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("NEW SALES FOR THE LAST TWO MONTHS", subPanels, formAllowed));

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        try {

            for (Customer S : DS.getCRMController().getCRM_CustomerActiveCases(false)) {
                CustomerCRMCases_Tree ccct = new CustomerCRMCases_Tree("", S, formAllowed);
                subPanels.add(new Panel(S.toString(), ccct));
            }

        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("NEW CUSTOMER CASES", subPanels, formAllowed));

        ///////////////////////////////////////////////////////////////////////////////////////////////////
        try {

            for (Salesman BLS : DS.getSalesmanController().getSalesman(S.getFkIdbl())) {
                SalesmanCRMCases_Tree csct = new SalesmanCRMCases_Tree("", BLS, formAllowed);
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
