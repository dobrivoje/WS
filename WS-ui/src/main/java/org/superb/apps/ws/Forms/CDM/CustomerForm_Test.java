package org.superb.apps.ws.Forms.CDM;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.TextField;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.ws.Forms.CRUDForm;
import org.superb.apps.ws.controllers.Customer_Controller;
import org.superb.apps.ws.db.entities.Customer;

public class CustomerForm_Test extends CRUDForm<Customer> {

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

    public CustomerForm_Test(final Customer customer) {
        super(customer);
        
        fieldGroup = new BeanFieldGroup(Customer.class);
        fieldGroup.bindMemberFields(this);
        
        addComponents(name, address, city, zip, region, pib, formButton);
    }

    public CustomerForm_Test(final Customer customer, final IRefreshVisualContainer visualContainer) {
        super(customer, visualContainer);
        
        fieldGroup = new BeanFieldGroup(Customer.class);
        fieldGroup.bindMemberFields(this);
        
        fieldGroup.setItemDataSource(new BeanItem(customer));
        beanItem = (BeanItem<Customer>) fieldGroup.getItemDataSource();
        
        addComponents(name, address, city, zip, region, pib, formButton);
    }

    @Override
    protected void bindFieldsToBean(Customer bean) {
        bean.setName(name.getValue());
        bean.setAddress(address.getValue());
        bean.setCity(city.getValue());
        bean.setZip(zip.getValue());
        bean.setRegion(region.getValue());
        bean.setPib(pib.getValue());
    }

    @Override
    public void addNewBean(Customer bean) throws Exception {
        new Customer_Controller().addNewCustomer(bean);
    }

    @Override
    public void updateExistingBean(Customer bean) throws Exception {
        new Customer_Controller().updateCustomer(bean);
    }
}
