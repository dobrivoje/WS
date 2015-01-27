package org.superb.apps.ws.Forms;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.ws.controllers.CustomerController;
import org.superb.apps.ws.db.entities.Customer;

public class CustomerForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Customer.class);
    private Item BICCustomer;
    private Button saveButton;
    private BeanItem<Customer> beanItemCustomer;
    private Table callingTable;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("name")
    private final TextField name = new TextField("customer Name");

    @PropertyId("address")
    private final TextField address = new TextField("customer Address");

    @PropertyId("city")
    private final TextField city = new TextField("customer City");

    @PropertyId("zip")
    private final TextField zip = new TextField("customer ZIP");

    @PropertyId("region")
    private final TextField region = new TextField("customer Region");

    @PropertyId("pib")
    private final TextField pib = new TextField("customer PIB");
    //</editor-fold>

    public CustomerForm() {
        setSizeFull();
        setMargin(true);
        setMargin(true);

        this.callingTable = null;

        fieldGroup.bindMemberFields(this);

        saveButton = new Button("Save modifications", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    beanItemCustomer = (BeanItem<Customer>) fieldGroup.getItemDataSource();
                    Customer customerToUpdate = beanItemCustomer.getBean();

                    customerToUpdate.setName(name.getValue());
                    customerToUpdate.setAddress(address.getValue());
                    customerToUpdate.setCity(city.getValue());
                    customerToUpdate.setZip(zip.getValue());
                    customerToUpdate.setRegion(region.getValue());
                    customerToUpdate.setPib(pib.getValue());

                    new CustomerController().updateCustomer(customerToUpdate);
                    // oveži tabelu posle izmene podatka !
                    callingTable.markAsDirtyRecursive();

                    Notification.show("Customer updated.", Notification.Type.HUMANIZED_MESSAGE);
                    
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        saveButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        addComponents(name, address, city, zip, region, pib, saveButton);
    }

    public CustomerForm(Item customerItem, Table callingTable) {
        this();
        this.callingTable = callingTable;
        this.BICCustomer = customerItem;
        setBICCustomer(BICCustomer);
    }

    public Item getBICCustomer() {
        return BICCustomer;
    }

    public final void setBICCustomer(Item BICCustomer) {
        this.BICCustomer = BICCustomer;
        fieldGroup.setItemDataSource(BICCustomer);
    }
}
