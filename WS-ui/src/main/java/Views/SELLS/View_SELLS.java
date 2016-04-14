package Views.SELLS;

import Main.MyUI;
import static org.dobrivoje.auth.roles.RolesPermissions.P_SELLS_EXCEL_IMPORT;
import org.superb.apps.utilities.vaadin.Views.View_Dashboard;

public class View_SELLS extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(P_SELLS_EXCEL_IMPORT);

    public View_SELLS() {
        super("Information System Sells Synchronization");
    }
}
