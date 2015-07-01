package Views.MainMenu.CRM;

import Trees.CRM.SC_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ws.MyUI.DS;

public class CRMSCView extends DashboardView {

    public CRMSCView() {
        super("Existing Salesman Customer Relationship");
        buildContentWithComponents(salesmanPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component salesmanPanel() {
        Panel P = new Panel("Salesman/Customers");
        P.setHeight(70, Unit.PERCENTAGE);
        P.setWidth(70, Unit.PERCENTAGE);

        try {
            P.setContent(new SC_Tree("", DS.getSalesmanController().getAll()));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
            Logger.getLogger(CRMSCView.class.getName()).log(Level.WARNING, null, ex);
        }

        return createPanelComponent("", Arrays.asList(P), true);
    }
    //</editor-fold>
}
