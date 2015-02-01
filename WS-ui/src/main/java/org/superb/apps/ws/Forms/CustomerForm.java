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
import org.superb.apps.utilities.Enums.CrudOperations;
import org.superb.apps.ws.controllers.Customer_Controller;
import org.superb.apps.ws.db.entities.Customer;

public class CustomerForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Customer.class);
    private Item BICCustomer;
    private Button crudButton;
    private BeanItem<Customer> beanItemCustomer;

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
    }

    public CustomerForm(Item customerItem, final Table callingTable, CrudOperations crudOperation) {
        setSizeFull();
        setMargin(true);

        fieldGroup.bindMemberFields(this);

        this.BICCustomer = customerItem;
        setBICCustomer(BICCustomer);

        if (crudOperation.equals(CrudOperations.UPDATE)) {
            crudButton = new Button("Save modifications", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        beanItemCustomer = (BeanItem<Customer>) fieldGroup.getItemDataSource();
                        Customer customerToUpdate = beanItemCustomer.getBean();
                        getValuesFromFields(customerToUpdate);
                        new Customer_Controller().updateCustomer(customerToUpdate);

                        // oveži tabelu posle izmene podatka !
                        callingTable.markAsDirtyRecursive();

                        Notification.show("Customer updated.", Notification.Type.HUMANIZED_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            });
        }
        if (crudOperation.equals(CrudOperations.CREATE)) {
            crudButton = new Button("New Customer", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        beanItemCustomer = (BeanItem<Customer>) fieldGroup.getItemDataSource();
                        Customer newCustomer = new Customer();
                        getValuesFromFields(newCustomer);
                        new Customer_Controller().addNewCustomer(newCustomer);

                        // oveži tabelu posle izmene podatka !
                        callingTable.markAsDirtyRecursive();

                        Notification.show("Customer updated.", Notification.Type.HUMANIZED_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            });
        }

        crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponents(name, address, city, zip, region, pib, crudButton);
    }

    private void getValuesFromFields(Customer customerBean) {
        customerBean.setName(name.getValue());
        customerBean.setAddress(address.getValue());
        customerBean.setCity(city.getValue());
        customerBean.setZip(zip.getValue());
        customerBean.setRegion(region.getValue());
        customerBean.setPib(pib.getValue());
    }

    public Item getBICCustomer() {
        return BICCustomer;
    }

    public final void setBICCustomer(Item BICCustomer) {
        this.BICCustomer = BICCustomer;
        fieldGroup.setItemDataSource(BICCustomer);
    }
}
