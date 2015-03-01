package Forms.FSM;

import com.vaadin.data.Item;
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
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import date.formats.DateFormat;
import db.controllers.Customer_Controller;
import db.controllers.FSOwner_Controller;
import db.controllers.FS_Controller;
import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;
import db.interfaces.ICustomerController;
import db.interfaces.IFSController;
import db.interfaces.IFSOController;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public class FSOWNER_Form extends FormLayout {

    private final ICustomerController customerController = new Customer_Controller();
    private final IFSOController fsOwnerController = new FSOwner_Controller();
    private final IFSController fsController = new FS_Controller();
    
    private static final String DATE_FORMAT = DateFormat.DATE_FORMAT_SRB.toString();

    private final FieldGroup fieldGroup = new BeanFieldGroup(Fuelstation.class);
    private Button crudButton;
    private BeanItem<Owner> beanItem;

    private final BeanItemContainer<Customer> bicc = new BeanItemContainer(Customer.class, customerController.getAll());
    private final BeanItemContainer<Fuelstation> bicf = new BeanItemContainer(Fuelstation.class, fsController.getAll());

    private Button.ClickListener clickListener;
    private String btnCaption;

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
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);

        fieldGroup.bindMemberFields(this);

        dateFrom.setDateFormat(DATE_FORMAT);
        dateTo.setDateFormat(DATE_FORMAT);

        customer.setNullSelectionAllowed(false);
        fs.setNullSelectionAllowed(false);

        customer.setWidth(50, Unit.PERCENTAGE);
        fs.setWidth(50, Unit.PERCENTAGE);
        dateFrom.setWidth(50, Unit.PERCENTAGE);
        dateTo.setWidth(50, Unit.PERCENTAGE);

        customer.focus();
    }

    public FSOWNER_Form(final CrudOperations crudOperation) {
        this();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Owner newOwner = new Owner();
                    bindFieldsToBean(newOwner);

                    try {
                        fsOwnerController.addNew(newOwner);
                        Notification n = new Notification("New FS Owner Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);
            crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

            addComponents(customer, fs, dateFrom, dateTo, active, crudButton);
        }
    }

    public FSOWNER_Form(Item existingCustomer, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(existingCustomer);
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Owner existingOwner = beanItem.getBean();
                bindFieldsToBean(existingOwner);

                try {
                    fsOwnerController.updateExisting(existingOwner);
                    visualContainer.refreshVisualContainer();
                    Notification n = new Notification("FS Owner Updated.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);

        crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponents(customer, fs, dateFrom, dateTo, active, crudButton);
    }

    private void bindFieldsToBean(Owner ownerBean) {
        ownerBean.setFKIDCustomer((Customer) customer.getValue());
        ownerBean.setFkIdFs((Fuelstation) fs.getValue());
        ownerBean.setDateFrom(dateFrom.getValue());
        ownerBean.setDateTo(dateTo.getValue());
        ownerBean.setActive(active.getValue());
    }
}
