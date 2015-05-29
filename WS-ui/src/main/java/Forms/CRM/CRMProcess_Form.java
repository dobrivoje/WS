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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import db.interfaces.ISalesmanController;
import java.util.Date;
import org.superb.apps.utilities.Enums.CrudOperations;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CRMProcess_Form extends CRUDForm2<CrmProcess> {

    private final ICRMController CRM_Controller = DS.getCrmController();
    private final ISalesmanController Salesman_Controller = DS.getSalesmanController();

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox salesman = new ComboBox("Salesman",
            new BeanItemContainer(Salesman.class, Salesman_Controller.getAll()));

    @PropertyId("FK_IDCA")
    private final ComboBox crmCase = new ComboBox("CRM Case",
            new BeanItemContainer(CrmCase.class));

    @PropertyId("FK_IDCS")
    private final ComboBox status = new ComboBox("Status",
            new BeanItemContainer(CrmStatus.class, CRM_Controller.getCRM_AllStatuses()));

    @PropertyId("actionDate")
    private final DateField actionDate = new DateField("Date");

    @PropertyId("comment")
    private final TextArea comment = new TextArea("Comment");
    //</editor-fold>

    public CRMProcess_Form() {
        super(new BeanFieldGroup(CrmProcess.class));

        // poništi margine iz super klase, da bi se polja lepše prikazala na formi !
        setMargin(false);

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        salesman.setWidth(250, Unit.PIXELS);

        salesman.setRequired(true);
        crmCase.setRequired(true);
        status.setRequired(true);
        actionDate.setRequired(true);

        salesman.setNullSelectionAllowed(false);
        crmCase.setNullSelectionAllowed(false);
        status.setNullSelectionAllowed(false);

        salesman.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    crmCase.setContainerDataSource(new BeanItemContainer(
                            CrmCase.class,
                            CRM_Controller.getCRM_Cases((Salesman) salesman.getValue(), false)));
                } catch (Exception e) {
                    Notification.show("Notification", "There is no active CRM cases for this customer.", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        salesman.focus();
    }

    public CRMProcess_Form(CrmCase crmCase, final boolean newCRMProcess, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        CrmProcess crmProcess = new CrmProcess(new CrmCase(), null, null, new Date());

        try {
            if (newCRMProcess) {
                btnCaption = CrudOperations.BUTTON_CAPTION_SAVE.toString();
            } else {
                btnCaption = CrudOperations.BUTTON_CAPTION_SAVE.toString();
            }
        } catch (Exception e) {
            btnCaption = CrudOperations.BUTTON_CAPTION_SAVE.toString();
        }

        fieldGroup.setItemDataSource(new BeanItem(crmProcess));
        beanItem = (BeanItem<CrmProcess>) fieldGroup.getItemDataSource();

        clickListener = (Button.ClickEvent event) -> {
            setBeanFromFields(beanItem.getBean());

            try {
                fieldGroup.commit();

                if (newCRMProcess) {
                    CRM_Controller.addNewCRM_Process(beanItem.getBean());
                } else {
                    CRM_Controller.updateCRM_Process(beanItem.getBean());
                }

                if (visualContainer != null) {
                    visualContainer.refreshVisualContainer();
                }

                Notification n = new Notification("New CRM Process Added.", Notification.Type.TRAY_NOTIFICATION);

                n.setDelayMsec(500);
                n.show(getUI().getPage());

            } catch (FieldGroup.CommitException ex) {
                Notification.show("Error", "Fields indicated by a red star must be provided.", Notification.Type.ERROR_MESSAGE);
            } catch (Exception ex) {
                Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        };

        addComponents(salesman);
        addBeansToForm();
    }

    public CRMProcess_Form(CrmProcess crmProcess, final IRefreshVisualContainer visualContainer) {
        this();

        setFieldsFromBean(crmProcess);

        btnCaption = CrudOperations.BUTTON_CAPTION_SAVE.toString();

        fieldGroup.setItemDataSource(new BeanItem(crmProcess));
        beanItem = (BeanItem<CrmProcess>) fieldGroup.getItemDataSource();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    CRM_Controller.updateCRM_Process(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("New CRM Process Added.", Notification.Type.TRAY_NOTIFICATION);

                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (FieldGroup.CommitException ex) {
                    Notification.show("Error", "Fields indicated by a red star must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addComponents(salesman);
        addBeansToForm();
        lockFormFileds(false);
    }

    @Override
    protected final void setBeanFromFields(CrmProcess crmProcess) {
        crmProcess.setActionDate(actionDate.getValue());
        crmProcess.setComment(comment.getValue());
        crmProcess.setFK_IDCA((CrmCase) crmCase.getValue());
        crmProcess.setFK_IDCS((CrmStatus) status.getValue());
    }

    @Override
    protected final void setFieldsFromBean(CrmProcess cp) {
        salesman.setValue(cp.getFK_IDCA().getFK_IDRSC().getFK_IDS());
        crmCase.setValue(cp.getFK_IDCA());
    }

    @Override
    protected final void lockFormFileds(boolean lockAll) {
        if (lockAll) {
            super.lockFormFileds(lockAll);
        } else {
            salesman.setEnabled(false);
            crmCase.setEnabled(false);
            status.setEnabled(false);
            actionDate.setEnabled(false);
        }
    }
}
