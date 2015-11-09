package Forms.CRM;

import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import db.interfaces.ISalesmanController;
import java.util.Date;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Form_CRMCase extends Form_CRUD2<CrmCase> {

    private final ICRMController CRM_Controller = DS.getCRMController();
    private final ISalesmanController Salesman_Controller = DS.getSalesmanController();

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox salesman = new ComboBox("Salesrep",
            new BeanItemContainer(Salesman.class, Salesman_Controller.getAll()));

    private final ComboBox customer = new ComboBox("Customer");

    @PropertyId("startDate")
    private final DateField startDate = new DateField("Case Start Date");

    @PropertyId("description")
    private final TextArea description = new TextArea("Description");

    @PropertyId("finished")
    private final CheckBox finished = new CheckBox("Case Finished ?");

    @PropertyId("saleAgreeded")
    private final CheckBox saleAgreeded = new CheckBox("Sale Agreeded ?");

    @PropertyId("endDate")
    private final DateField endDate = new DateField("Case End Date");
    //</editor-fold>

    public Form_CRMCase() {
        super(new BeanFieldGroup(CrmCase.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        initFields();
        updateDynamicFields();

        salesman.focus();
    }

    public Form_CRMCase(CrmCase crmCase, final IRefreshVisualContainer visualContainer, boolean newCase, boolean readOnly) {
        this(crmCase, false, visualContainer);

        if (readOnly) {
            lockFormFileds(readOnly);
            salesman.setEnabled(!readOnly);
            customer.setEnabled(!readOnly);
        } else {
            salesman.setEnabled(newCase);
            customer.setEnabled(newCase);
            startDate.setEnabled(newCase);

            finished.setEnabled(!newCase);
            saleAgreeded.setEnabled(!newCase);
            endDate.setEnabled(false);
        }

    }

    public Form_CRMCase(Salesman s, boolean newCase) {
        this(new CrmCase(new Date(), "", new RelSALESMANCUST()), false, null);

        salesman.setEnabled(newCase);
        customer.setEnabled(newCase);
        startDate.setEnabled(newCase);

        finished.setEnabled(!newCase);
        saleAgreeded.setEnabled(!newCase);
        endDate.setEnabled(false);

        salesman.setValue(s);
    }

    public Form_CRMCase(CrmCase crmCase, boolean defaultCRUDButtonOnForm, final IRefreshVisualContainer visualContainer) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        CrmCase cc = crmCase == null ? new CrmCase(new Date(), "", new RelSALESMANCUST()) : crmCase;
        setFieldsFromBean(cc);

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        fieldGroup.setItemDataSource(new BeanItem(cc));
        beanItem = (BeanItem<CrmCase>) fieldGroup.getItemDataSource();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    CRM_Controller.updateCRM_Case(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("Existing CRM Case Updated.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (FieldGroup.CommitException ce) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponents(salesman, customer);
        addBeansToForm();
    }

    @Override
    protected final void setBeanFromFields(CrmCase crmCase) {
        crmCase.setStartDate(startDate.getValue());
        crmCase.setEndDate(endDate.getValue());
        crmCase.setDescription(description.getValue());
        crmCase.setFinished(finished.getValue());
        crmCase.setSaleAgreeded(saleAgreeded.getValue());

        try {
            crmCase.setFK_IDRSC(
                    CRM_Controller.getCRM_R_SalesmanCustomer(
                            (Salesman) salesman.getValue(),
                            (Customer) customer.getValue()
                    ));
        } catch (Exception e) {
            Notification.show("Error", "Relation between Salesman and Customer\ndoes not exist !", Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public final void setFieldsFromBean(CrmCase cc) {
        startDate.setValue(cc.getStartDate());
        endDate.setValue(cc.getEndDate());
        description.setValue(cc.getDescription());
        finished.setValue(cc.getFinished());
        saleAgreeded.setValue(cc.getSaleAgreeded());
        salesman.setValue(cc.getFK_IDRSC().getFK_IDS());
        customer.setValue(cc.getFK_IDRSC().getFK_IDC());
    }

    @Override
    protected final void updateDynamicFields() {
        salesman.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                customer.setContainerDataSource(new BeanItemContainer(
                        Customer.class,
                        CRM_Controller.getCRM_Customers((Salesman) salesman.getValue())));
            }
        });

        finished.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                endDate.setEnabled(finished.getValue());
                endDate.setValue(finished.getValue() ? new Date() : null);
            }
        });
    }

    @Override
    protected final void initFields() {
        salesman.setWidth(250, Unit.PIXELS);
        customer.setWidth(250, Unit.PIXELS);
        description.setRows(3);

        salesman.setNullSelectionAllowed(false);
        customer.setNullSelectionAllowed(false);

        finished.setValue(false);
        finished.setEnabled(false);

        saleAgreeded.setValue(false);
        saleAgreeded.setEnabled(false);

        endDate.setValue(null);
        endDate.setEnabled(false);

        setRequiredFields();
    }

    @Override
    protected void setRequiredFields() {
        salesman.setRequired(true);
        customer.setRequired(true);
        startDate.setRequired(true);
    }
}
