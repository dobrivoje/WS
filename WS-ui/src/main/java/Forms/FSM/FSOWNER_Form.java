package Forms.FSM;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.Date;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DATE_FORMAT;
import static ws.MyUI.DS;

public class FSOWNER_Form extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Owner.class);
    private Button crudButton;
    private BeanItem<Owner> beanItem;

    private final BeanItemContainer<Customer> bicc = new BeanItemContainer(Customer.class, DS.getCustomerController().getAll());
    private final BeanItemContainer<Fuelstation> bicf = new BeanItemContainer(Fuelstation.class, DS.getFSController().getAll());

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
    private final CheckBox active = new CheckBox("Active ?", true);
    //</editor-fold>

    public FSOWNER_Form() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);

        fieldGroup.bindMemberFields(this);

        setTextFieldWidth(250, Unit.PIXELS);

        dateFrom.setDateFormat(DATE_FORMAT);
        dateTo.setDateFormat(DATE_FORMAT);

        dateFrom.setConverter(Date.class);
        dateTo.setConverter(Date.class);

        active.setEnabled(false);

        customer.setNullSelectionAllowed(false);
        fs.setNullSelectionAllowed(false);

        customer.focus();
    }

    public FSOWNER_Form(final CrudOperations crudOperation) {
        this();

        fieldGroup.setItemDataSource(new BeanItem(new Owner()));
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    bindFieldsToBean(beanItem.getBean());

                    try {
                        fieldGroup.commit();
                        DS.getFSOController().addNew(beanItem.getBean());

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

            addBeansToForm();
        }
    }

    public FSOWNER_Form(final Fuelstation fuelstation, final IRefreshVisualContainer visualContainer) {
        this();

        final Owner currentOwner = DS.getFSOController().getCurrentFSOwner(fuelstation);
        final Owner newOwner = new Owner(null, fuelstation, new Date(), null, true);

        fieldGroup.setItemDataSource(new BeanItem(newOwner));
        beanItem = (BeanItem<Owner>) fieldGroup.getItemDataSource();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                bindFieldsToBean(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    if (currentOwner != null) {
                        if (((Customer) customer.getValue()).equals(currentOwner.getFKIDCustomer())) {
                            throw new Exception(
                                    "Wrong assigment.\n"
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

                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        btnCaption = BUTTON_CAPTION_UPDATE.toString();

        crudButton = new Button(btnCaption, clickListener);
        crudButton.setWidth(150, Unit.PIXELS);

        addBeansToForm();
    }

    private void bindFieldsToBean(Owner ownerBean) {
        ownerBean.setFKIDCustomer((Customer) customer.getValue());
        ownerBean.setFkIdFs((Fuelstation) fs.getValue());
        ownerBean.setDateFrom(dateFrom.getValue() == null ? new Date() : dateFrom.getValue());
        ownerBean.setDateTo(dateTo.getValue());
        ownerBean.setActive(active.getValue());
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

    private void setTextFieldWidth(float width, Sizeable.Unit unit) {
        for (Component c : fieldGroup.getFields()) {
            if (c instanceof TextField) {
                ((TextField) c).setWidth(width, unit);
            }
            if (c instanceof ComboBox) {
                ((ComboBox) c).setWidth(width, unit);
            }
            if (c instanceof DateField) {
                ((DateField) c).setWidth(width, unit);
            }
            if (c instanceof TextArea) {
                ((TextArea) c).setWidth(width, unit);
            }
        }
    }
}
