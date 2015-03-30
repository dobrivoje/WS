package Forms.CRM;

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
import db.ent.Customer;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

public class SCR_Form extends CRUDForm2<RelSALESMANCUST> {

    private final ICRMController CRMController = DS.getCrmController();

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("fkIdc")
    private final ComboBox customer = new ComboBox("Customer",
            new BeanItemContainer(Customer.class, DS.getCustomerController().getAll()));

    @PropertyId("fkIds")
    private final ComboBox salesman = new ComboBox("Salesman",
            new BeanItemContainer(Salesman.class, DS.getSalesmanController().getAll()));

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
        customer.focus();
    }

    public SCR_Form(final CrudOperations crudOperation) {
        this();

        fieldGroup.setItemDataSource(new BeanItem(new RelSALESMANCUST()));
        beanItem = (BeanItem<RelSALESMANCUST>) fieldGroup.getItemDataSource();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    bindFieldsToBean(beanItem.getBean());

                    try {
                        fieldGroup.commit();
                        CRMController.addNew_RelSalesman_Cust(beanItem.getBean());

                        Notification n = new Notification("Relation Customer-Salesman Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            addBeansToForm();
        }
    }

    public SCR_Form(RelSALESMANCUST R_SalesmanCustomer, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(new BeanItem(R_SalesmanCustomer));
        beanItem = (BeanItem<RelSALESMANCUST>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                RelSALESMANCUST R_SalesmanCustomer_ToUpdate = beanItem.getBean();
                bindFieldsToBean(R_SalesmanCustomer_ToUpdate);

                try {
                    fieldGroup.commit();

                    CRMController.update_SalesmanCustomer(R_SalesmanCustomer_ToUpdate);

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("Relation Customer-Salesman Updated.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();
    }

    @Override
    protected void bindFieldsToBean(RelSALESMANCUST R_SalesmanCustomer) {
        R_SalesmanCustomer.setFkIdc((Customer) customer.getValue());
        R_SalesmanCustomer.setFkIds((Salesman) salesman.getValue());
        R_SalesmanCustomer.setDateFrom(dateFrom.getValue());
        R_SalesmanCustomer.setDateTo(dateTo.getValue());
        R_SalesmanCustomer.setActive(active.getValue());
    }
}
