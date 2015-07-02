package Views.SYSNOTIF;

import Trees.CRM.SALES.Salesman_Sales_Tree;
import Trees.CRM.Salesman_CRMCases_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Salesman;
import enums.ISUserType;
import java.util.ArrayList;
import java.util.List;
import static org.dobrivoje.auth.roles.RolesPermissions.R_CARDS_MANAGER;
import static org.dobrivoje.auth.roles.RolesPermissions.R_CARDS_USER;
import static org.dobrivoje.auth.roles.RolesPermissions.R_FUELSALES_MANAGER;
import static org.dobrivoje.auth.roles.RolesPermissions.R_FUELSALES_USER;
import static org.dobrivoje.auth.roles.RolesPermissions.R_ROOT_PRIVILEGES;
import ws.MyUI;

public class SysNotifView extends DashboardView {

    ISUserType IST = MyUI.get().getLoggedISUserType();
    Salesman S = MyUI.get().getLoggedSalesman();
    boolean FS_allowed = MyUI.get().isPermitted(R_FUELSALES_USER);
    boolean CARD_allowed = MyUI.get().isPermitted(R_CARDS_USER);

    public SysNotifView() {
        super("System Notification Board");

        switch (IST) {
            case SALESMAN:
                createSalesmanView(FS_allowed || CARD_allowed);
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
            Salesman_CRMCases_Tree csct = new Salesman_CRMCases_Tree("", S, FS_allowed || CARD_allowed);
            subPanels.add(new Panel(S.toString(), csct));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("Active CRM Cases by Salesman", subPanels, formEditAllowed));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            Salesman_Sales_Tree csct = new Salesman_Sales_Tree(R_CARDS_USER, S, null, null, FS_allowed || CARD_allowed);
            subPanels.add(new Panel(S.toString(), csct));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
        }

        C.add(createPanelComponent("Current/Previous Month Sales", subPanels, formEditAllowed));

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

        for (String s : new String[]{"NEW CRM CASES", "NEW SALES THIS MONTH"}) {
            C.add(createPanelComponent(s, subPanels,
                    MyUI.get().isPermitted(R_CARDS_MANAGER) || MyUI.get().isPermitted(R_FUELSALES_MANAGER)
            ));
        }

        buildContentWithComponents(C);
    }

    private void createGeneralManagerPanel() {
        List<Component> C = new ArrayList();

        for (String s : new String[]{"CARDS", "FUEL", "LPG", "LUB"}) {
            C.add(createPanelComponent(s, subPanels, MyUI.get().isPermitted(R_ROOT_PRIVILEGES)));
        }

        C.add(createNotesPanel(""));

        buildContentWithComponents(C);
    }
    //</editor-fold>
}
