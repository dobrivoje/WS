package Views.MainMenu.CRM;

import Views.DashboardView;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class CRM_SCView extends DashboardView {

    public static final String VIEW_NAME = "CRM_SCView";

    public CRM_SCView() {
        super("Existing Salesman Customer Relationship");
        buildContentWithComponents(salesmanPanel(), customersPanel());
    }

    //<editor-fold defaultstate="collapsed" desc="Custom Panels,...">
    private Component salesmanPanel() {
        VerticalLayout VL = new VerticalLayout();
        VL.setCaption("Salesman");
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
