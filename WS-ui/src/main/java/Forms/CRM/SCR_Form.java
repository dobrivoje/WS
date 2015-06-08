package Forms.CRM;

import Forms.CRUDForm2;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import db.ent.Customer;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import java.util.Date;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

public class SCR_Form extends CRUDForm2<RelSALESMANCUST> {

    private final ICRMController CRMController = DS.getCrmController();

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("FK_IDS")
    private final ComboBox salesman = new ComboBox("Salesman",
            new BeanItemContainer(Salesman.class, DS.getSalesmanController().getAll()));

    @PropertyId("FK_IDC")
    private final ComboBox customer = new ComboBox("Customer",
            new BeanItemContainer(Customer.class, DS.getCustomerController().getAll()));

    @PropertyId("dateFrom")
    private final DateField dateFrom = new DateField("Date From");

    @PropertyId("dateTo")
    private final DateField dateTo = new DateField("Date To");

    @PropertyId("active")
    private final CheckBox active = new CheckBox("Active?");
    //</editor-fold>

    public SCR_Form() {
        super(new BeanFieldGroup(RelSALESMANCUST.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);
    }

    public SCR_Form(boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(new RelSALESMANCUST()));
        beanItem = (BeanItem<RelSALESMANCUST>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    CRMController.addNew_R_Salesman_Cust(beanItem.getBean());

                    Notification n = new Notification("New relation between Customer Salesman added.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (CommitException ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();

        initFields();
    }

    public SCR_Form(Salesman s, Customer c, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        try {
            RelSALESMANCUST r = DS.getCrmController().getCRM_R_SalesmanCustomer(s, c);

            fieldGroup.setItemDataSource(new BeanItem(r));
            beanItem = (BeanItem<RelSALESMANCUST>) fieldGroup.getItemDataSource();

            btnCaption = BUTTON_CAPTION_SAVE.toString();
            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    RelSALESMANCUST R_SalesmanCustomer_ToUpdate = beanItem.getBean();
                    setBeanFromFields(R_SalesmanCustomer_ToUpdate);

                    try {
                        fieldGroup.commit();

                        CRMController.update_R_Salesman_Cust(R_SalesmanCustomer_ToUpdate);

                        if (visualContainer != null) {
                            visualContainer.refreshVisualContainer();
                        }

                        Notification n = new Notification("Relation between Customer and Salesman is updated.", Notification.Type.TRAY_NOTIFICATION);

                        n.setDelayMsec(500);
                        n.show(getUI().getPage());

                    } catch (FieldGroup.CommitException ex) {
                        Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            addBeansToForm();
            setRequiredFields();
        } catch (Exception ex) {
            Notification.show("Error", "Description: " + ex.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    protected void setBeanFromFields(RelSALESMANCUST R_SalesmanCustomer) {
        R_SalesmanCustomer.setFK_IDC((Customer) customer.getValue());
        R_SalesmanCustomer.setFK_IDS((Salesman) salesman.getValue());
        R_SalesmanCustomer.setDateFrom(dateFrom.getValue());
        R_SalesmanCustomer.setDateTo(dateTo.getValue());
        R_SalesmanCustomer.setActive(active.getValue());
    }

    @Override
    protected void setFieldsFromBean(RelSALESMANCUST r) {
        customer.setValue(r.getFK_IDC());
        salesman.setValue(r.getFK_IDS());
        dateFrom.setValue(r.getDateFrom());
        dateTo.setValue(r.getDateTo());
        active.setValue(r.getActive());
    }

    @Override
    protected void updateDynamicFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected final void initFields() {
        dateFrom.setValue(new Date());
        dateTo.setEnabled(false);
        active.setValue(true);
        active.setEnabled(false);

        setRequiredFields();
    }

    @Override
    protected void setRequiredFields() {
        salesman.setRequired(true);
        customer.setRequired(true);
        dateFrom.setRequired(true);
    }
}
