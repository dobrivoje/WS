package Forms.FSM;

import Forms.CRUDForm2;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
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
import static ws.MyUI.DS;

public class FSOWNER_Form extends CRUDForm2<Owner> {

    private final BeanItemContainer<Customer> bicc = new BeanItemContainer(Customer.class, DS.getCustomerController().getAll());
    private final BeanItemContainer<Fuelstation> bicf = new BeanItemContainer(Fuelstation.class, DS.getFSController().getAll());

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("fKIDCustomer")
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

    public FSOWNER_Form() {
        super(new BeanFieldGroup(Owner.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);
        initFields();

        customer.focus();
    }

    public FSOWNER_Form(boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(new Owner()));
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        setFieldsFromBean(new Owner(null, null, new Date(), null, true));

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

                } catch (MyDBNullException ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();

        initFields();
    }

    public FSOWNER_Form(final Fuelstation fuelstation, final IRefreshVisualContainer visualContainer) {
        this(fuelstation, visualContainer, true);
    }

    public FSOWNER_Form(final Fuelstation fuelstation, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        final Owner currentOwner = DS.getFSOController().getCurrentFSOwner(fuelstation);
        final Owner newOwner = new Owner(null, fuelstation, new Date(), null, true);

        fieldGroup.setItemDataSource(new BeanItem(newOwner));
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    if (currentOwner != null) {
                        if (((Customer) customer.getValue()).equals(currentOwner.getFKIDCustomer())) {
                            throw new Exception(
                                    "Wrong assignment.\n"
                                    + "You cannot assign this fuelstation to this customer,\n"
                                    + "as it is already assigned to it !"
                            );
                        }

                        currentOwner.setDateTo(new Date());
                        currentOwner.setActive(false);

                        DS.getFSOController().updateExisting(currentOwner);
                    }

                    DS.getFSOController().addNew(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("FS Owner Updated.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (MyDBNullException ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();

        initFields();
    }

    public FSOWNER_Form(final Owner owner, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        setFieldsFromBean(owner);

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        fieldGroup.setItemDataSource(new BeanItem(owner));
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

                } catch (MyDBNullException ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();

        lockFormFileds(false);
    }

    @Override
    protected void setBeanFromFields(Owner ownerBean) {
        ownerBean.setFKIDCustomer((Customer) customer.getValue());
        ownerBean.setFkIdFs((Fuelstation) fs.getValue());
        ownerBean.setDateFrom(dateFrom.getValue());
        ownerBean.setDateTo(dateTo.getValue());
        ownerBean.setActive(active.getValue());
    }

    @Override
    public final void setFieldsFromBean(Object o) {
        if (o instanceof Owner) {
            Owner ow = (Owner) o;

            customer.setValue(ow.getFKIDCustomer());
            fs.setValue(ow.getFkIdFs());
            dateFrom.setValue(ow.getDateFrom());
            dateTo.setValue(ow.getDateTo());
            active.setValue(ow.getActive());
        }
    }

    @Override
    protected final void updateDynamicFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected final void initFields() {
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
    protected final void lockFormFileds(boolean lockAll) {
        if (!lockAll) {
            customer.setEnabled(false);
            fs.setEnabled(false);
            dateFrom.setEnabled(false);
            dateTo.setEnabled(true);
        }
    }

}
