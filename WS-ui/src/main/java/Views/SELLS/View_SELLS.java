package Views.SELLS;

import Main.MyUI;
import org.superbapps.auth.roles.Roles;
import org.superbapps.utils.vaadin.Views.View_Dashboard;

public class View_SELLS extends View_Dashboard {

    private final boolean formAllowed = MyUI.get().isPermitted(Roles.P_WS_EXCEL_IMPORT);

    public View_SELLS() {
        super("Information System Sells Synchronization");
    }
}
