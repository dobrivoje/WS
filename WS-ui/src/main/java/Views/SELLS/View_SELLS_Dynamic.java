package Views.SELLS;

import Main.MyUI;
import org.superbapps.auth.roles.Roles;
import org.superbapps.utils.vaadin.Views.View_Dashboard;

public class View_SELLS_Dynamic extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().hasRole(Roles.R_WS_FS_MANAGER);

    public View_SELLS_Dynamic() {
        super("Information System Sells Dynamics");
    }
}
