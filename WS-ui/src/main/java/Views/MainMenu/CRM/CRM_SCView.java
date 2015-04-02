package Views.MainMenu.CRM;

import Trees.SC_Tree;
import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import static ws.MyUI.DS;

public class CRM_SCView extends DashboardView {
    
    public static final String VIEW_NAME = "CRM_SCView";
    private SC_Tree scTree;
    
    public CRM_SCView() {
        super("Existing Salesman Customer Relationship");
        buildContentWithComponents(salesmanPanel(), customersPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component salesmanPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Salesman");
        
        VL.addComponent(new SC_Tree("", DS.getSalesmanController().getByID(3L)));
        
        Component contentWrapper = createContentWrapper(VL);
        
        return contentWrapper;
    }
    
    private Component customersPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Salesman's Customers");
        Component contentWrapper = createContentWrapper(VL);
        
        return contentWrapper;
    }
    //</editor-fold>
}
