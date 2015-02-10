package org.superb.apps.ws.Forms.CDM;

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
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.ws.controllers.Customer_Controller;
import org.superb.apps.ws.db.entities.Customer;

public class CustomerForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Customer.class);
    private Button crudButton;
    private BeanItem<Customer> beanItemCustomer;

    private Button.ClickListener clickListener;
    private String btnCaption;

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

        fieldGroup.bindMemberFields(this);
    }

    public CustomerForm(final CrudOperations crudOperation) {
        this();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Customer newCustomer = new Customer();
                    bindFieldsToBean(newCustomer);

                    try {
                        new Customer_Controller().addNewCustomer(newCustomer);
                        Notification.show("Customer Added.", Notification.Type.HUMANIZED_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);

            crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
            addComponents(name, address, city, zip, region, pib, crudButton);
        }
    }

    public CustomerForm(Item existingCustomer, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(existingCustomer);
        beanItemCustomer = (BeanItem<Customer>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Customer customerToUpdate = beanItemCustomer.getBean();
                bindFieldsToBean(customerToUpdate);

                try {
                    new Customer_Controller().updateCustomer(customerToUpdate);
                    visualContainer.refreshVisualContainer();
                    Notification.show("Customer Updated.", Notification.Type.HUMANIZED_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);

        crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponents(name, address, city, zip, region, pib, crudButton);
    }

    private void bindFieldsToBean(Customer customerBean) {
        customerBean.setName(name.getValue());
        customerBean.setAddress(address.getValue());
        customerBean.setCity(city.getValue());
        customerBean.setZip(zip.getValue());
        customerBean.setRegion(region.getValue());
        customerBean.setPib(pib.getValue());
    }
}
