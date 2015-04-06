/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms.CRM;

import Forms.CRUDForm2;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
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
public class CRMProcess_Form extends CRUDForm2<CrmProcess> {

    private final ICRMController icrmc = DS.getCrmController();
    private final ISalesmanController isc = DS.getSalesmanController();

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox salesman = new ComboBox("Salesman",
            new BeanItemContainer(Salesman.class, isc.getAll()));

    private final ComboBox customer = new ComboBox("Customer");

    @PropertyId("FK_IDCS")
    private final ComboBox status = new ComboBox("Status",
            new BeanItemContainer(CrmStatus.class, icrmc.getCRM_AllStatuses()));

    @PropertyId("actionDate")
    private final DateField actionDate = new DateField("Date");

    @PropertyId("comment")
    private final TextArea comment = new TextArea("Comment");
    //</editor-fold>

    public CRMProcess_Form() {
        super(new BeanFieldGroup(CrmProcess.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        salesman.setWidth(250, Unit.PIXELS);
        customer.setWidth(250, Unit.PIXELS);

        status.setNullSelectionAllowed(false);
        salesman.setNullSelectionAllowed(false);
        customer.setNullSelectionAllowed(false);

        salesman.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                customer.setContainerDataSource(new BeanItemContainer(
                        Customer.class,
                        icrmc.getCRM_Customers((Salesman) salesman.getValue())));
            }
        });

        salesman.focus();
    }

    public CRMProcess_Form(Salesman s, Customer c, final IRefreshVisualContainer visualContainer) {
        this();

        RelSALESMANCUST r = null;

        try {
            r = icrmc.getCRM_R_SalesmanCustomer(s, c);
        } catch (Exception ex) {
        }

        salesman.setValue(s);
        customer.setValue(c);

        CrmProcess crmProcess = new CrmProcess(r, null, null, new Date());

        fieldGroup.setItemDataSource(new BeanItem(crmProcess));
        beanItem = (BeanItem<CrmProcess>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                bindFieldsToBean(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    icrmc.addNewCRM_Process(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("New CRM Process Added.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponents(salesman, customer);
        addBeansToForm();

    }

    @Override
    protected final void bindFieldsToBean(CrmProcess crmProcess) {
        crmProcess.setActionDate(actionDate.getValue());
        crmProcess.setComment(comment.getValue());
        crmProcess.setFK_IDCS((CrmStatus) status.getValue());

        try {
            crmProcess.setFK_IDRSMC(
                    icrmc.getCRM_R_SalesmanCustomer(
                            (Salesman) salesman.getValue(),
                            (Customer) customer.getValue()
                    ));
        } catch (Exception ex) {
            Notification.show("Error", ex.toString(), Notification.Type.ERROR_MESSAGE);
        }
    }
}
