package org.superb.apps.ws;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.superb.apps.ws.controllers.CustomerController;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.functionalities.ICustomer;

/**
 *
 */
@Theme("mytheme")
@Widgetset("org.superb.apps.ws.MyAppWidgetset")
public class MyUI extends UI {

    private static final ICustomer CUSTOMER_CONTROLLER = new CustomerController();
    private final BeanItemContainer<Customer> BEAN_ITEM_CONTAINER = new BeanItemContainer<>(Customer.class);
    private final Table customerTable = new Table();
    //
    private final FormLayout layout = new FormLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        BEAN_ITEM_CONTAINER.addAll(CUSTOMER_CONTROLLER.getAllCustomers());

        /*
         for (int i = 1 + CUSTOMER_CONTROLLER.getAllCustomers().size(); i < 1000; i++) {
         Customer c = new Customer();
        
         c.setIdc((long) i);
         c.setName("Kompanija-" + i);
         c.setAddress("Kompanija-" + i + ", Adress-" + i);
         c.setCity("Kompanija-" + i + ", City-" + i);
         c.setZip("Kompanija-" + i + ", ZIP-" + i);
         c.setRegion("Kompanija-" + i + ", Region-" + i);
        
         BEAN_ITEM_CONTAINER.addBean(c);
         }
         */
        customerTable.setContainerDataSource(BEAN_ITEM_CONTAINER);

        Button button = new Button("All customers table");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                customerTable.refreshRowCache();
            }
        });

        final TextField customerName_TextField = new TextField("Name");
        final TextField customerAddress_TextField = new TextField("Address");
        final TextField customerCity_TextField = new TextField("City");
        final TextField customerZIP_TextField = new TextField("ZIP");
        final TextField customerRegion_TextField = new TextField("Region");

        final Label errorLabel = new Label("");

        Button saveNewCustomer_Button = new Button("Save New Customer", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                String errorMessage = null;
                try {
                    Customer newCustomer = new Customer();

                    newCustomer.setName(customerName_TextField.getValue());
                    newCustomer.setAddress(customerAddress_TextField.getValue());
                    newCustomer.setCity(customerCity_TextField.getValue());
                    newCustomer.setZip(customerZIP_TextField.getValue());
                    newCustomer.setRegion(customerRegion_TextField.getValue());
                    newCustomer.setPib("12313");

                    CUSTOMER_CONTROLLER.addNewCustomer(newCustomer);
                } catch (Exception e) {
                    if (e.toString().contains("java.sql.SQLException: Cannot insert the value NULL into column")) {
                        errorMessage = "NULL Value detected! ";
                    }

                    if (e.toString().contains("Violation of UNIQUE KEY constraint \'CUSTOMER_PIB\'")) {
                        errorMessage += "PIB MUST BE UNIQUE ! ";
                    }

                    if (e.toString().contains("java.sql.SQLException: Violation of UNIQUE KEY constraint \'CUSTOMER_Unique_Data\'")) {
                        errorMessage += "CUSTOMER PROBABLY ALREADY EXIST !!!";
                    }
                    errorLabel.setCaption("ERROR ! " + errorMessage);
                }
            }
        });

        layout.addComponent(customerName_TextField);
        layout.addComponent(customerAddress_TextField);
        layout.addComponent(customerCity_TextField);
        layout.addComponent(customerZIP_TextField);
        layout.addComponent(customerRegion_TextField);
        layout.addComponent(saveNewCustomer_Button);

        layout.addComponent(errorLabel);

        layout.addComponent(button);
        layout.addComponent(customerTable);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
