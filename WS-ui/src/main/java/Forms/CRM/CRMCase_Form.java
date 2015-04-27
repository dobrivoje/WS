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
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
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
    private final DateField startDate = new DateField("Date");

    @PropertyId("description")
    private final TextArea description = new TextArea("Description");
    
    @PropertyId("finished")
    private final CheckBox finished = new CheckBox("Case finished ?");
    //</editor-fold>

    public CRMCase_Form() {
        super(new BeanFieldGroup(CrmCase.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        salesman.setWidth(250, Unit.PIXELS);
        customer.setWidth(250, Unit.PIXELS);

        salesman.setNullSelectionAllowed(false);
        customer.setNullSelectionAllowed(false);

        salesman.setRequired(true);
        customer.setRequired(true);
        finished.setValue(false);
        
        salesman.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                customer.setContainerDataSource(new BeanItemContainer(
                        Customer.class,
                        CRM_Controller.getCRM_Customers((Salesman) salesman.getValue())));
            }
        });

        salesman.focus();
    }

    public CRMCase_Form(Salesman s, Customer c, final IRefreshVisualContainer visualContainer) {
        this();

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

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                bindFieldsToBean(beanItem.getBean());

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
                    Notification.show("Error", "Fields indicated by a red star must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponents(salesman, customer);
        addBeansToForm();

    }

    @Override
    protected final void bindFieldsToBean(CrmCase crmCase) {
        crmCase.setStartDate(startDate.getValue());
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
}
