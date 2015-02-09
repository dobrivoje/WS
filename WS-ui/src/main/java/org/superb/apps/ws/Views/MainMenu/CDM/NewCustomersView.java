package org.superb.apps.ws.Views.MainMenu.CDM;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;
import org.superb.apps.utilities.Enums.CrudOperations;
import org.superb.apps.ws.Forms.CDM.CustomerForm;

public class NewCustomersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "New customers";

    private final VerticalLayout VL = new VerticalLayout();
    private final CustomerForm customerForm = new CustomerForm(CrudOperations.CREATE);

    public NewCustomersView() {
        setSizeFull();

        VL.setSizeFull();
        VL.setMargin(true);
        VL.addComponent(customerForm);

        addComponent(VL);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
