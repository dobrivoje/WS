package Views.CDM;

import Main.MyUI;
import static org.dobrivoje.auth.roles.RolesPermissions.P_CUSTOMERS_EXCEL_IMPORT;
import org.superb.apps.utilities.vaadin.Views.View_Dashboard;

public class View_CDM_NAV extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_CUSTOMERS_EXCEL_IMPORT);

    public View_CDM_NAV() {
        super("Information System Customers Synchronization");
    }
}
