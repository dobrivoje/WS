package Forms.CDM;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import db.ent.City;
import db.ent.Customer;
import db.interfaces.ICustomerController;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

public class CustomerForm extends FormLayout {

    private final ICustomerController customerController = DS.getCustomerController();

    private final FieldGroup fieldGroup = new BeanFieldGroup(Customer.class);
    private Button crudButton;
    private BeanItem<Customer> beanItem;

    private Button.ClickListener clickListener;
    private String btnCaption;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("name")
    private final TextField name = new TextField("Customer Name");

    @PropertyId("address")
    private final TextField address = new TextField("Customer Address");

    @PropertyId("fKIDCity")
    private final ComboBox city = new ComboBox("Customer City",
            new BeanItemContainer(City.class, DS.getCityController().getAll()));

    @PropertyId("pib")
    private final TextField pib = new TextField("Customer PIB");

    @PropertyId("matBr")
    private final TextField matBr = new TextField("Customer MATBR");

    @PropertyId("navCode")
    private final TextField navCode = new TextField("Customer Nav ID");

    @PropertyId("tel1")
    private final TextField tel1 = new TextField("Customer Tel1");

    @PropertyId("tel2")
    private final TextField tel2 = new TextField("Customer Tel2");

    @PropertyId("fax")
    private final TextField fax = new TextField("Customer Fax");

    @PropertyId("mob")
    private final TextField mob = new TextField("Customer Mob");

    @PropertyId("email1")
    private final TextField email1 = new TextField("Customer Email1");

    @PropertyId("email2")
    private final TextField email2 = new TextField("Customer Email2");
    //</editor-fold>
    
    public CustomerForm() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);
        addStyleName(ValoTheme.FORMLAYOUT_LIGHT);

        fieldGroup.bindMemberFields(this);

        setTFWidth(250, Unit.PIXELS);

        city.setNullSelectionAllowed(false);

        name.focus();
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
                        customerController.addNew(newCustomer);
                        Notification n = new Notification("Customer Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);

            addBeansToForm();
        }
    }

    public CustomerForm(Item existingCustomer, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(existingCustomer);
        beanItem = (BeanItem<Customer>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Customer customerToUpdate = beanItem.getBean();
                bindFieldsToBean(customerToUpdate);

                try {
                    customerController.updateExisting(customerToUpdate);

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }
                    Notification n = new Notification("Customer Updated.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);
        crudButton.setWidth(150, Unit.PIXELS);

        addBeansToForm();
    }

    private void bindFieldsToBean(Customer customerBean) {
        customerBean.setName(name.getValue());
        customerBean.setAddress(address.getValue());
        customerBean.setFKIDCity((City) city.getValue());
        customerBean.setPib(pib.getValue());
        customerBean.setMatBr(matBr.getValue());
        customerBean.setNavCode(navCode.getValue());
        customerBean.setTel1(tel1.getValue());
        customerBean.setTel2(tel2.getValue());
        customerBean.setFax(fax.getValue());
        customerBean.setMob(mob.getValue());
        customerBean.setEmail1(email1.getValue());
        customerBean.setEmail2(email2.getValue());
    }

    private void addBeansToForm() {
        for (Component c : fieldGroup.getFields()) {
            if (c instanceof TextField) {
                TextField tf = (TextField) c;
                tf.setNullRepresentation("");
            }
            addComponent(c);
        }

        addComponents(crudButton);
    }

    private void setTFWidth(float width, Sizeable.Unit unit) {
        for (Component c : fieldGroup.getFields()) {
            if (c instanceof TextField) {
                ((TextField) c).setWidth(width, unit);
            }
        }
    }
}
