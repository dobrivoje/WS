package Views.SELLS;

import Main.MyUI;
import org.superb.apps.utilities.vaadin.Views.View_Dashboard;
import static org.dobrivoje.auth.roles.RolesPermissions.R_FUELSALES_MANAGER;

public class View_SELLS_Dynamic extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(R_FUELSALES_MANAGER);

    public View_SELLS_Dynamic() {
        super("Information System Sells Dynamics");
    }
}
