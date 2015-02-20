package Forms.CDM;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import date.formats.DateFormat;
import db.controllers.CBT_Controller;
import db.controllers.RelCBT_Controller;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.RelCBType;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public class RELCBTForm extends FormLayout {

    private static final String DATE_FORMAT = DateFormat.DATE_FORMAT_ENG.toString();
    private final FieldGroup fieldGroup = new BeanFieldGroup(RelCBType.class);
    private BeanItem<RelCBType> beanItem;

    private final BeanItemContainer<Customer> bic = new BeanItemContainer(Customer.class);
    private final BeanItemContainer<CustomerBussinesType> bicbt = new BeanItemContainer(CustomerBussinesType.class, new CBT_Controller().getAll());

    private Button crudButton;
    private Button.ClickListener clickListener;
    private String btnCaption;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    //@PropertyId("fkIdc")
    private final TextField customer = new TextField("Bussines Type");

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
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);

        fieldGroup.bindMemberFields(this);

        dateFrom.setDateFormat(DATE_FORMAT);
        dateTo.setDateFormat(DATE_FORMAT);

        customer.setWidth(50, Unit.PERCENTAGE);
        cBType.setWidth(50, Unit.PERCENTAGE);
        dateFrom.setWidth(50, Unit.PERCENTAGE);
        dateTo.setWidth(50, Unit.PERCENTAGE);

        cBType.setNullSelectionAllowed(false);

        cBType.focus();
    }

    public RELCBTForm(final CrudOperations crudOperation) {
        this();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    RelCBType newRelCBType = new RelCBType();
                    // bindFieldsToBean(newRelCBType,Exception);

                    try {
                        //new rel .addNew(newRelCBType);
                        Notification n = new Notification("Customer Bussines Type Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);

            crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
            addComponents(customer, cBType, dateFrom, dateTo, active, crudButton);
        }
    }

    public RELCBTForm(final Customer existingCustomer, final IRefreshVisualContainer visualContainer) {
        this();

        customer.setEnabled(false);
        customer.setValue(existingCustomer.getName());

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                RelCBType newRelCBType = new RelCBType();
                bindFieldsToBean(newRelCBType, existingCustomer);

                try {
                    new RelCBT_Controller().addNew(newRelCBType);

                    visualContainer.refreshVisualContainer();

                    Notification n = new Notification("Customer Bussines Type Updated.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (NullPointerException npe) {
                    // uhvatiti ovaj exception ako je : visualContainer==null !
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);

        crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponents(customer, cBType, dateFrom, dateTo, active, crudButton);
    }

    private void bindFieldsToBean(RelCBType existingRelCBT, Customer existingCustomer) {
        existingRelCBT.setFkIdc(existingCustomer);
        existingRelCBT.setFkIdcbt((CustomerBussinesType) cBType.getValue());
        existingRelCBT.setDateFrom(dateFrom.getValue());
        existingRelCBT.setDateTo(dateTo.getValue());
        existingRelCBT.setActive(active.getValue());
    }
}
