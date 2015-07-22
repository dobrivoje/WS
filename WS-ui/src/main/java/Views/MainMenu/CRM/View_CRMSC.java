package Views.MainMenu.CRM;

import Trees.CRM.Tree_SC;
import Views.View_Dashboard;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ws.MyUI.DS;

public class View_CRMSC extends View_Dashboard {

    public View_CRMSC() {
        super("Existing Salesman Customer Relationship");
        buildContentWithComponents(salesmanPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component salesmanPanel() {
        Panel P = new Panel("Salesman/Customers");
        P.setHeight(70, Unit.PERCENTAGE);
        P.setWidth(70, Unit.PERCENTAGE);

        try {
            P.setContent(new Tree_SC("", DS.getSalesmanController().getAll()));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
            Logger.getLogger(View_CRMSC.class.getName()).log(Level.WARNING, null, ex);
        }

        return createPanelComponent("", Arrays.asList(P), true);
    }
    //</editor-fold>
}
