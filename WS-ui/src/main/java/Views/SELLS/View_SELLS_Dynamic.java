package Views.SELLS;

import Main.MyUI;
import static org.dobrivoje.auth.roles.RolesPermissions.R_FUELSALES_MANAGER;
import org.superbapps.utils.vaadin.Views.View_Dashboard;

public class View_SELLS_Dynamic extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(R_FUELSALES_MANAGER);

    public View_SELLS_Dynamic() {
        super("Information System Sells Dynamics");
    }
}
