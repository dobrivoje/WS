package Forms.CDM;

import Forms.Form_CRUD2;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import db.ent.City;
import db.ent.Customer;
import db.interfaces.ICustomerController;
import java.util.ArrayList;
import java.util.Arrays;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

public class Form_Customer extends Form_CRUD2<Customer> {

    private final ICustomerController customerController = DS.getCustomerController();

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

    @PropertyId("licence")
    private final OptionGroup licence = new OptionGroup("Licence ? ");

    @PropertyId("zone")
    private final ComboBox zone = new ComboBox("Customer Zone",
            new BeanItemContainer(String.class, new ArrayList(
                            Arrays.asList("West", "Beograd", "Central", "East", "North", "South"))));

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

    @PropertyId("comment")
    private final TextArea comment = new TextArea("Comment");
    //</editor-fold>

    public Form_Customer() {
        super(new BeanFieldGroup(Customer.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        initFields();

        name.focus();
    }

    public Form_Customer(boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(new Customer()));
        beanItem = (BeanItem<Customer>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();
                    customerController.addNew(beanItem.getBean());

                    Notification n = new Notification("Customer Added.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (FieldGroup.CommitException ce) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();
    }

    public Form_Customer(Customer customer, final IRefreshVisualContainer visualContainer) {
        this(customer, visualContainer, true);
    }

    public Form_Customer(Customer customer, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(customer));
        beanItem = (BeanItem<Customer>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Customer customerToUpdate = beanItem.getBean();
                setBeanFromFields(customerToUpdate);

                try {
                    fieldGroup.commit();

                    customerController.updateExisting(customerToUpdate);

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("Customer Updated.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (FieldGroup.CommitException ce) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();
    }

    @Override
    protected void setBeanFromFields(Customer customerBean) {
        customerBean.setName(name.getValue());
        customerBean.setAddress(address.getValue());
        customerBean.setFKIDCity((City) city.getValue());
        customerBean.setPib(pib.getValue());
        customerBean.setMatBr(matBr.getValue());
        customerBean.setNavCode(navCode.getValue());

        if (licence.getValue() != null) {
            customerBean.setLicence((boolean) licence.getValue());
        }

        customerBean.setZone((String) zone.getValue());
        customerBean.setTel1(tel1.getValue());
        customerBean.setTel2(tel2.getValue());
        customerBean.setFax(fax.getValue());
        customerBean.setMob(mob.getValue());
        customerBean.setEmail1(email1.getValue());
        customerBean.setEmail2(email2.getValue());
        customerBean.setComment(comment.getValue());
    }

    @Override
    public void setFieldsFromBean(Customer c) {
        name.setValue(c.getName());
        address.setValue(c.getAddress());
        city.setValue(c.getFKIDCity());
        pib.setValue(c.getPib());
        matBr.setValue(c.getMatBr());
        navCode.setValue(c.getNavCode());
        licence.setValue(c.getLicence());
        zone.setValue(c.getZone());
        tel1.setValue(c.getTel1());
        tel2.setValue(c.getTel2());
        fax.setValue(c.getFax());
        mob.setValue(c.getMob());
        email1.setValue(c.getEmail1());
        email2.setValue(c.getEmail2());
        comment.setValue(c.getComment());
    }

    @Override
    protected void updateDynamicFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected final void initFields() {
        setRequiredFields();

        licence.addItem(false);
        licence.setItemCaption(false, "No licence");
        licence.addItem(true);
        licence.setItemCaption(true, "Yes, licensed");
        licence.addStyleName("horizontal");

        // postavi validatore
        email1.addValidator(new EmailValidator("Must be an email address !"));
        email2.addValidator(new EmailValidator("Must be an email address !"));
    }

    @Override
    protected void setRequiredFields() {
        name.setRequired(true);
        address.setRequired(true);
        city.setRequired(true);
        zone.setRequired(true);
    }
}
