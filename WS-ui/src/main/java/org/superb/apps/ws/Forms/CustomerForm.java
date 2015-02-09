package org.superb.apps.ws.Forms;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import static org.superb.apps.utilities.Enums.CrudOperations.UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.ws.controllers.Customer_Controller;
import org.superb.apps.ws.db.entities.Customer;

public class CustomerForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Customer.class);
    private Item newUpdate_Customer;
    private Button crudButton;
    private BeanItem<Customer> beanItemCustomer;

    private boolean modificationSuccesfull;

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
        setStyleName(Reindeer.LAYOUT_BLACK);

        modificationSuccesfull = false;
    }

    public CustomerForm(Item newUpdate_Customer, final CrudOperations crudOperation,
            final IRefreshVisualContainer visualContainer) {

        this();

        fieldGroup.bindMemberFields(this);

        this.newUpdate_Customer = newUpdate_Customer;
        setNewUpdate_Customer(newUpdate_Customer);
        beanItemCustomer = (BeanItem<Customer>) fieldGroup.getItemDataSource();

        final Button.ClickListener clickListener;
        final String btnCaption;

        switch (crudOperation) {
            case UPDATE: {
                btnCaption = BUTTON_CAPTION_UPDATE.toString();

                clickListener = new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer customerToUpdate = beanItemCustomer.getBean();
                        getValuesFromFields(customerToUpdate);

                        try {
                            new Customer_Controller().updateCustomer(customerToUpdate);
                            modificationSuccesfull = true;
                            visualContainer.refreshVisualContainer();
                            Notification.show("Customer updated.", Notification.Type.HUMANIZED_MESSAGE);
                        } catch (Exception ex) {
                            modificationSuccesfull = false;
                            Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                        }
                    }
                };

                crudButton = new Button(btnCaption, clickListener);
            }
            ;
            break;

            case CREATE: {
                btnCaption = BUTTON_CAPTION_NEW.toString();

                clickListener = new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer newCustomer = new Customer();
                        getValuesFromFields(newCustomer);

                        try {
                            new Customer_Controller().addNewCustomer(newCustomer);
                            modificationSuccesfull = true;
                            visualContainer.refreshVisualContainer();
                            Notification.show("Customer updated.", Notification.Type.HUMANIZED_MESSAGE);
                        } catch (Exception ex) {
                            modificationSuccesfull = false;
                            Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                        }
                    }
                };

                crudButton = new Button(btnCaption, clickListener);
            }
            ;
            break;
        };

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

    public Item getNewUpdate_Customer() {
        return newUpdate_Customer;
    }

    public final void setNewUpdate_Customer(Item newUpdate_Customer) {
        this.newUpdate_Customer = newUpdate_Customer;
        fieldGroup.setItemDataSource(newUpdate_Customer);
    }

    public boolean isModificationSuccesfull() {
        return modificationSuccesfull;
    }

    public void setModificationSuccesfull(boolean modificationSuccesfull) {
        this.modificationSuccesfull = modificationSuccesfull;
    }
}
