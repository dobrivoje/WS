package Forms.FSM;

import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import dataservice.exceptions.MyDBNullException;
import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.Date;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static Main.MyUI.DS;

public class Form_FSOwner extends Form_CRUD2<Owner> {

    private final BeanItemContainer<Customer> bicc = new BeanItemContainer(Customer.class, DS.getCustomerController().getAll());
    private final BeanItemContainer<Fuelstation> bicf = new BeanItemContainer(Fuelstation.class, DS.getFSController().getAll());

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox customer = new ComboBox("Customer", bicc);

    @PropertyId("fkIdFs")
    private final ComboBox fs = new ComboBox("Fuel station", bicf);

    @PropertyId("dateFrom")
    private final DateField dateFrom = new DateField("Date From");

    @PropertyId("dateTo")
    private final DateField dateTo = new DateField("Date To");

    @PropertyId("active")
    private final CheckBox active = new CheckBox("Active ?");
    //</editor-fold>

    public Form_FSOwner() {
        super(new BeanFieldGroup(Owner.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);
        initFields();
    }

    public Form_FSOwner(boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(new Owner()));
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();
                    DS.getFSOController().addNew(beanItem.getBean());

                    Notification n = new Notification("New FS Owner Added.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                    
                } catch (FieldGroup.CommitException ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponent(customer);
        addBeansToForm();
    }

    public Form_FSOwner(final Fuelstation fuelstation, final IRefreshVisualContainer visualContainer) {
        this(fuelstation, visualContainer, true);
    }

    public Form_FSOwner(final Fuelstation fs, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        final Owner currentOwner = DS.getFSOController().getCurrentFSOwner(fs);
        boolean noOwner = (currentOwner == null);
        Owner O = (noOwner ? new Owner(null, fs, new Date(), null, defaultCRUDButtonOnForm) : currentOwner);

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;
        btnCaption = BUTTON_CAPTION_SAVE.toString();

        setFieldsFromBean(O);
        fieldGroup.setItemDataSource(new BeanItem(O));
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    // Ovo sam stavio pod komentar, jer cilj nije da se dodeli novom / istom korisniku
                    // već da se ažurira postojeći korisnik ako je currentOwner != null
                    /*
                    
                     if (currentOwner != null) {
                     if (((Customer) customer.getValue()).equals(currentOwner.getFKIDCustomer())) {
                     throw new Exception(
                     "Wrong assignment.\n"
                     + "You cannot assign this fuelstation to this customer,\n"
                     + "as it is already assigned to it !"
                     );
                     }
                    
                     }
                    
                     */
                    if (currentOwner != null) {
                        O.setActive(false);
                        DS.getFSOController().updateExisting(O);
                    } else {
                        O.setActive(true);
                        DS.getFSOController().addNew(O);
                    }

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("FS Owner Date Updated.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (FieldGroup.CommitException ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponent(customer);
        addBeansToForm();
        lockFormFileds(currentOwner == null);
    }

    public Form_FSOwner(final Owner owner, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        Owner O = (owner == null ? new Owner() : owner);
        boolean newOwner = (owner == null);

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;
        btnCaption = BUTTON_CAPTION_SAVE.toString();

        setFieldsFromBean(O);
        fieldGroup.setItemDataSource(new BeanItem(O));
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    owner.setActive(dateTo.getValue() == null);
                    DS.getFSOController().updateExisting(owner);

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("FS Owner Date Updated.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (MyDBNullException | FieldGroup.CommitException ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponent(customer);
        addBeansToForm();
        lockFormFileds(newOwner);
    }

    //<editor-fold defaultstate="collapsed" desc="overrided methods,...">
    @Override
    protected void setBeanFromFields(Owner ownerBean) {
        ownerBean.setFKIDCustomer((Customer) customer.getValue());
        ownerBean.setFkIdFs((Fuelstation) fs.getValue());
        ownerBean.setDateFrom(dateFrom.getValue());
        ownerBean.setDateTo(dateTo.getValue());
        ownerBean.setActive(active.getValue());
    }

    @Override
    public final void setFieldsFromBean(Owner ow) {
        customer.setValue(ow.getFKIDCustomer());
        fs.setValue(ow.getFkIdFs());
        dateFrom.setValue(ow.getDateFrom());
        dateTo.setValue(ow.getDateTo());
        active.setValue(ow.getActive());
    }

    @Override
    protected final void updateDynamicFields() {
        customer.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                customer.setContainerDataSource(new BeanItemContainer(
                        Customer.class,
                        DS.getCustomerController().getAll()
                ));
            }
        });
    }

    @Override
    protected final void initFields() {
        customer.setWidth(250, Unit.PIXELS);
        customer.focus();

        customer.setNullSelectionAllowed(false);
        fs.setNullSelectionAllowed(false);

        dateTo.setEnabled(false);
        active.setEnabled(false);

        setRequiredFields();
    }

    @Override
    protected void setRequiredFields() {
        customer.setRequired(true);
        fs.setRequired(true);
        dateFrom.setRequired(true);
    }

    @Override
    protected final void lockFormFileds(boolean lock) {
        if (lock) {
            customer.setEnabled(true);
            fs.setEnabled(true);
            dateFrom.setEnabled(true);
            dateTo.setEnabled(false);
            active.setValue(true);
        } else {
            customer.setEnabled(false);
            fs.setEnabled(false);
            dateFrom.setEnabled(false);
            dateTo.setEnabled(true);
        }
    }
    //</editor-fold>

}
