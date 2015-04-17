package Views.MainMenu.CRM;

import Trees.SC_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ws.MyUI.DS;

public class CRMSCView extends DashboardView {

    public static final String VIEW_NAME = "CRM_SCView";
    private final Panel propPanel;

    public CRMSCView() {
        super("Existing Salesman Customer Relationship");

        propPanel = new Panel("Salesman/Customers");
        propPanel.setHeight(70, Unit.PERCENTAGE);
        propPanel.setWidth(70, Unit.PERCENTAGE);

        buildContentWithComponents(salesmanPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component salesmanPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setSizeFull();

        try {
            propPanel.setContent(new SC_Tree("", DS.getSalesmanController().getAll()));
        } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
            Logger.getLogger(CRMSCView.class.getName()).log(Level.WARNING, null, ex);
        }
        
        VL.addComponent(propPanel);

        return createContentWrapper(VL);
    }
    //</editor-fold>
}
