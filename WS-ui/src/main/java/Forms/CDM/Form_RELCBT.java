package Forms.CDM;

import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.RelCBType;
import db.interfaces.ICustomerController;
import db.interfaces.IRELCBTController;
import java.util.Date;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static Main.MyUI.DS;

public class Form_RELCBT extends Form_CRUD2<RelCBType> {

    private static final IRELCBTController RELCBTController = DS.getRELCBTController();
    private final ICustomerController customerController = DS.getCustomerController();

    private static final BeanItemContainer<CustomerBussinesType> BICBT = new BeanItemContainer(
            CustomerBussinesType.class, DS.getCBTController().getAll());

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox customer = new ComboBox("Customer",
            new BeanItemContainer(Customer.class, customerController.getAll()));

    @PropertyId("fkIdcbt")
    private final ComboBox cBType = new ComboBox("Bussines Type", BICBT);

    @PropertyId("dateFrom")
    private final DateField dateFrom = new DateField("Date From");

    @PropertyId("dateTo")
    private final DateField dateTo = new DateField("Date To");

    @PropertyId("active")
    private final CheckBox active = new CheckBox("Active?");
    //</editor-fold>

    public Form_RELCBT() {
        super(new BeanFieldGroup(RelCBType.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        initFields();

        cBType.focus();
    }

    public Form_RELCBT(boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(new RelCBType()));
        beanItem = (BeanItem<RelCBType>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();
                    RELCBTController.addNew(beanItem.getBean());

                    Notification n = new Notification("New CBT Added.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (FieldGroup.CommitException ce) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponent(customer);
        addBeansToForm();

        initFields();
    }

    public Form_RELCBT(final Customer existingCustomer, final Container container) {
        this();

        customer.setEnabled(false);
        customer.setValue(existingCustomer);

        btnCaption = BUTTON_CAPTION_SAVE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                RelCBType new_RelCBT = new RelCBType();

                setBeanFromFields(new_RelCBT);
                new_RelCBT.setFkIdc(existingCustomer);

                Notification n = new Notification("Customer Bussines Type Updated.", Notification.Type.TRAY_NOTIFICATION);
                n.setDelayMsec(500);

                try {
                    RELCBTController.addNew(new_RelCBT);
                    container.addItem(new_RelCBT);

                    n.show(getUI().getPage());

                } catch (NullPointerException npe) {
                    n.show(getUI().getPage());
                } catch (FieldGroup.CommitException ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };
    }

    public Form_RELCBT(final Customer c, boolean defaultCRUDButtonOnForm) {
        this(c, new BeanItemContainer<>(
                RelCBType.class,
                DS.getCustomerController().getAllCustomerBussinesTypes(c)
        ));

        if (this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm) {
            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);
        }

        addComponents(customer);
        addBeansToForm();
    }

    public Form_RELCBT(RelCBType rct, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        setFieldsFromBean(rct);

        btnCaption = CrudOperations.BUTTON_CAPTION_SAVE.toString();

        fieldGroup.setItemDataSource(new BeanItem(rct));
        beanItem = (BeanItem<RelCBType>) fieldGroup.getItemDataSource();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    RELCBTController.updateExisting(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("Existing CBT Updated.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (FieldGroup.CommitException ex) {
                    Notification.show("Error", "Fields indicated by a red star must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponents(customer);
        addBeansToForm();
        lockFormFileds(false);
    }

    @Override
    public ClickListener getClickListener() {
        return clickListener;
    }

    @Override
    protected final void lockFormFileds(boolean lockAll) {
        if (!lockAll) {
            customer.setEnabled(lockAll);
            cBType.setEnabled(lockAll);
            dateFrom.setEnabled(lockAll);
            dateTo.setEnabled(!lockAll);
            active.setEnabled(!lockAll);
        }
    }

    @Override
    protected void setBeanFromFields(RelCBType relCBT) {
        relCBT.setFkIdc((Customer) customer.getValue());
        relCBT.setFkIdcbt((CustomerBussinesType) cBType.getValue());
        relCBT.setDateFrom(dateFrom.getValue());
        relCBT.setDateTo(dateTo.getValue());
        relCBT.setActive(active.getValue());
    }

    @Override
    public final void setFieldsFromBean(RelCBType rcbt) {
        customer.setValue(rcbt.getFkIdc());
        cBType.setValue(rcbt.getFkIdcbt());
        dateFrom.setValue(rcbt.getDateFrom());
        active.setValue(rcbt.getActive());
    }

    @Override
    protected void updateDynamicFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected final void initFields() {
        customer.setWidth(250, Unit.PIXELS);
        dateFrom.setValue(new Date());
        dateTo.setEnabled(false);

        active.setVisible(true);
        active.setEnabled(false);

        setRequiredFields();

        active.setValue(true);
        active.setEnabled(false);
    }

    @Override
    protected void setRequiredFields() {
        customer.setRequired(true);
        cBType.setRequired(true);
        dateFrom.setRequired(true);
    }
}
