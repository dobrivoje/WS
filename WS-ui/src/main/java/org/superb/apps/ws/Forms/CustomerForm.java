package org.superb.apps.ws.Forms;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.superb.apps.ws.db.entities.Customer;

public class CustomerForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Customer.class);
    private Item bicCustomer;

    //<editor-fold defaultstate="collapsed" desc="fields">
    @PropertyId("Name")
    private final TextField name = new TextField("customer Name");

    @PropertyId("Address")
    private final TextField address = new TextField("customer Address");

    @PropertyId("City")
    private final TextField city = new TextField("customer City");

    @PropertyId("ZIP")
    private final TextField zip = new TextField("customer ZIP");

    @PropertyId("Region")
    private final TextField region = new TextField("customer Region");

    @PropertyId("PIB")
    private final TextField pib = new TextField("customer PIB");
    //</editor-fold>

    public CustomerForm() {
        setSizeFull();
        setMargin(true);

        addComponents(name, address, city, zip, region, pib);
        fieldGroup.bindMemberFields(this);
    }

    public Item getBICCustomer() {
        return bicCustomer;
    }

    public void setBICCustomer(Item BICCustomer) {
        this.bicCustomer = BICCustomer;
        fieldGroup.setItemDataSource(BICCustomer);
    }
}
