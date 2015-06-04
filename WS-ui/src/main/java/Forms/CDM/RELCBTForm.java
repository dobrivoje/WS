package Forms.CDM;

import Forms.CRUDForm2;
import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.RelCBType;
import db.interfaces.IRELCBTController;
import java.util.Date;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import static ws.MyUI.DS;

public class RELCBTForm extends CRUDForm2<RelCBType> {

    private static final IRELCBTController RELCBTController = DS.getRELCBTController();

    private final BeanItemContainer<CustomerBussinesType> bicbt = new BeanItemContainer(
            CustomerBussinesType.class, DS.getCBTController().getAll());

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    
    private final TextField customer = new TextField("Customer");

    @PropertyId("fkIdcbt")
    private final ComboBox cBType = new ComboBox("Bussines Type", bicbt);

    @PropertyId("dateFrom")
    private final DateField dateFrom = new DateField("Date From");

    @PropertyId("dateTo")
    private final DateField dateTo = new DateField("Date To");

    @PropertyId("active")
    private final CheckBox active = new CheckBox("Active?");
    //</editor-fold>

    public RELCBTForm() {
        super(new BeanFieldGroup(RelCBType.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);
        
        customer.setWidth(250, Unit.PIXELS);
        dateFrom.setValue(new Date());
        
        customer.setRequired(true);
        cBType.setRequired(true);
        dateFrom.setRequired(true);

        active.setValue(true);
        active.setEnabled(false);
        
        cBType.focus();
    }

    public RELCBTForm(final Customer existingCustomer, final Container container) {
        this();

        customer.setEnabled(false);
        customer.setValue(existingCustomer.getName());

        btnCaption = BUTTON_CAPTION_SAVE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                RelCBType new_RelCBT = new RelCBType();
                setBeanFromFields(new_RelCBT, existingCustomer);

                Notification n = new Notification("Customer Bussines Type Updated.", Notification.Type.TRAY_NOTIFICATION);
                n.setDelayMsec(500);

                try {
                    RELCBTController.addNew(new_RelCBT);
                    container.addItem(new_RelCBT);

                    n.show(getUI().getPage());
                } catch (NullPointerException npe) {
                    // uhvatiti ovaj exception tj. ako je : visualContainer==null !
                    // odn. ako nije dodeljen visual container koji treba
                    // da se osveži.
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Fields indicated by a red star must be provided", Notification.Type.ERROR_MESSAGE);
                }
            }
        };
    }

    public RELCBTForm(final Customer c) {
        this(c, new BeanItemContainer<>(
                RelCBType.class,
                DS.getCustomerController().getAllCustomerBussinesTypes(c))
        );

        if (this.defaultCRUDButtonOnForm) {
            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);
            addComponents(customer, cBType, dateFrom, dateTo, active, crudButton);
        }

        addComponents(customer, cBType, dateFrom, dateTo, active);
    }

    public RELCBTForm(final Customer c, boolean defaultCRUDButtonOnForm) {
        this(c, new BeanItemContainer<>(
                RelCBType.class,
                DS.getCustomerController().getAllCustomerBussinesTypes(c)
        ));

        if (this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm) {
            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);
            addComponents(customer, cBType, dateFrom, dateTo, active, crudButton);
        }

        addComponents(customer, cBType, dateFrom, dateTo, active);
    }

    @Override
    public ClickListener getClickListener() {
        return clickListener;
    }

    @Override
    protected void setBeanFromFields(RelCBType relCBT) {
        relCBT.setFkIdcbt((CustomerBussinesType) cBType.getValue());
        relCBT.setDateFrom(dateFrom.getValue());
        relCBT.setDateTo(dateTo.getValue());
        relCBT.setActive(active.getValue());
    }

    @Override
    protected void setFieldsFromBean(RelCBType rcbt) {
        customer.setValue(rcbt.getFkIdc().getName());
        cBType.setValue(rcbt.getFkIdcbt());
        dateFrom.setValue(rcbt.getDateFrom());
        active.setValue(rcbt.getActive());
    }

    private void setBeanFromFields(RelCBType rcbt, Customer c) {
        setBeanFromFields(rcbt);
        rcbt.setFkIdc(c);
    }

    @Override
    protected void updateDynamicFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
