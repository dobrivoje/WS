package Forms.CDM;

import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.RelCBType;
import db.interfaces.IRELCBTController;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import static ws.MyUI.APP_DATE_FORMAT;
import static ws.MyUI.DS;

public class RELCBTForm extends FormLayout {

    private static final IRELCBTController RELCBTController = DS.getRELCBTController();

    private final FieldGroup fieldGroup = new BeanFieldGroup(RelCBType.class);

    private final BeanItemContainer<CustomerBussinesType> bicbt = new BeanItemContainer(
            CustomerBussinesType.class, DS.getCBTController().getAll());

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

        dateFrom.setDateFormat(APP_DATE_FORMAT);
        dateTo.setDateFormat(APP_DATE_FORMAT);

        customer.setWidth(230, Unit.PIXELS);
        cBType.setWidth(230, Unit.PIXELS);
        dateFrom.setWidth(230, Unit.PIXELS);
        dateTo.setWidth(230, Unit.PIXELS);

        cBType.setNullSelectionAllowed(false);

        cBType.focus();
    }

    public RELCBTForm(final Customer existingCustomer, final Container container) {
        this();

        customer.setEnabled(false);
        customer.setValue(existingCustomer.getName());

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                RelCBType newRelCBType = new RelCBType();
                bindFieldsToBean(newRelCBType, existingCustomer);

                Notification n = new Notification("Customer Bussines Type Updated.", Notification.Type.TRAY_NOTIFICATION);
                n.setDelayMsec(500);

                try {
                    RELCBTController.addNew(newRelCBType);
                    container.addItem(newRelCBType);

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

        crudButton = new Button(btnCaption, clickListener);
        crudButton.setWidth(150, Unit.PIXELS);

        addComponents(customer, cBType, dateFrom, dateTo, active, crudButton);
    }

    public RELCBTForm(final Customer c) {
        this(c, new BeanItemContainer<>(
                RelCBType.class,
                DS.getCustomerController().getAllCustomerBussinesTypes(c)
        ));
    }

    private void bindFieldsToBean(RelCBType existingRelCBT, Customer existingCustomer) {
        existingRelCBT.setFkIdc(existingCustomer);
        existingRelCBT.setFkIdcbt((CustomerBussinesType) cBType.getValue());
        existingRelCBT.setDateFrom(dateFrom.getValue());
        existingRelCBT.setDateTo(dateTo.getValue());
        existingRelCBT.setActive(active.getValue());
    }
}
