/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms.CRM;

import Forms.CRUDForm2;
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
public class CRMCase_Form extends CRUDForm2<CrmCase> {

    private final ICRMController CRM_Controller = DS.getCrmController();
    private final ISalesmanController Salesman_Controller = DS.getSalesmanController();

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox salesman = new ComboBox("Salesman",
            new BeanItemContainer(Salesman.class, Salesman_Controller.getAll()));

    private final ComboBox customer = new ComboBox("Customer");

    @PropertyId("startDate")
    private final DateField startDate = new DateField("Case Start Date");

    @PropertyId("description")
    private final TextArea description = new TextArea("Description");

    @PropertyId("finished")
    private final CheckBox finished = new CheckBox("Case finished ?");

    @PropertyId("endDate")
    private final DateField endDate = new DateField("Case End Date");
    //</editor-fold>

    public CRMCase_Form() {
        super(new BeanFieldGroup(CrmCase.class));

        // poništi margine iz super klase, da bi se polja lepĹˇe prikazala na formi !
        setMargin(false);
        setSizeUndefined();

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        salesman.setWidth(250, Unit.PIXELS);
        customer.setWidth(250, Unit.PIXELS);
        description.setRows(3);

        salesman.setNullSelectionAllowed(false);
        customer.setNullSelectionAllowed(false);

        salesman.setRequired(true);
        customer.setRequired(true);

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

        salesman.focus();
        finished.setEnabled(false);
    }

    public CRMCase_Form(Salesman s, Customer c, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        RelSALESMANCUST relSC = null;

        try {
            relSC = CRM_Controller.getCRM_R_SalesmanCustomer(s, c);
        } catch (Exception ex) {
        }

        this.salesman.setValue(s);
        this.customer.setValue(c);

        CrmCase crmCase = new CrmCase(new Date(), null, relSC);

        fieldGroup.setItemDataSource(new BeanItem(crmCase));
        beanItem = (BeanItem<CrmCase>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    CRM_Controller.addNewCRM_Case(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("New CRM Case Added.", Notification.Type.TRAY_NOTIFICATION);

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

    public CRMCase_Form(CrmCase crmCase, final IRefreshVisualContainer visualContainer) {
        this(crmCase, true, visualContainer);
    }

    public CRMCase_Form(CrmCase crmCase, boolean defaultCRUDButtonOnForm, final IRefreshVisualContainer visualContainer) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        CrmCase cc = crmCase == null ? new CrmCase(new Date(), "", new RelSALESMANCUST()) : crmCase;
        setFieldsFromBean(cc);

        fieldGroup.setItemDataSource(new BeanItem(cc));
        beanItem = (BeanItem<CrmCase>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();
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

        lockFormFields(crmCase != null);
    }

    @Override
    protected final void setBeanFromFields(CrmCase crmCase) {
        crmCase.setStartDate(startDate.getValue());
        crmCase.setEndDate(endDate.getValue());
        crmCase.setDescription(description.getValue());
        crmCase.setFinished(finished.getValue());

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
    protected final void setFieldsFromBean(CrmCase cc) {
        startDate.setValue(cc.getStartDate());
        endDate.setValue(cc.getEndDate());
        description.setValue(cc.getDescription());
        finished.setValue(cc.isFinished());
        salesman.setValue(cc.getFK_IDRSC().getFK_IDS());
        customer.setValue(cc.getFK_IDRSC().getFK_IDC());
    }

    private void lockFormFields(boolean lock) {
        if (lock) {
            salesman.setEnabled(false);
            customer.setEnabled(false);
            startDate.setEnabled(false);
            endDate.setEnabled(false);
        }
    }
}
