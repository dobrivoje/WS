package org.superb.apps.ws.Forms.CDM;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.ws.controllers.CustomerBussinesType_Controller;
import org.superb.apps.ws.db.entities.CustomerBussinesType;

public class CBTForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(CustomerBussinesType.class);
    private Item newUpdate_CBT;
    private Button crudButton;
    private BeanItem<CustomerBussinesType> beanItemCBT;

    private Button.ClickListener clickListener;
    private String btnCaption;
    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("customerActivity")
    private final TextField customerActivity = new TextField("customer Activity");
    //</editor-fold>

    public CBTForm() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);

        fieldGroup.bindMemberFields(this);
    }

    public CBTForm(final CrudOperations crudOperation) {
        this();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    CustomerBussinesType newCBT = new CustomerBussinesType();
                    getValuesFromFields(newCBT);

                    try {
                        new CustomerBussinesType_Controller().addNewCBT(newCBT);
                        Notification.show("Customer Added.", Notification.Type.HUMANIZED_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);

            crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
            addComponents(customerActivity, crudButton);
        }

    }

    public CBTForm(Item existingCBT, final IRefreshVisualContainer visualContainer) {

        this();

        fieldGroup.bindMemberFields(this);

        this.newUpdate_CBT = existingCBT;
        setNewUpdate_Customer(existingCBT);
        beanItemCBT = (BeanItem<CustomerBussinesType>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                CustomerBussinesType CBTToUpdate = beanItemCBT.getBean();
                getValuesFromFields(CBTToUpdate);

                try {
                    new CustomerBussinesType_Controller().updateNewCBT(CBTToUpdate);
                    visualContainer.refreshVisualContainer();
                    Notification.show("Customer Updated.", Notification.Type.HUMANIZED_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);

        crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponents(customerActivity, crudButton);
    }
    
    private void getValuesFromFields(CustomerBussinesType CBTBean) {
        CBTBean.setCustomerActivity(customerActivity.getValue());
    }

    public Item getNewUpdate_Customer() {
        return newUpdate_CBT;
    }

    public final void setNewUpdate_Customer(Item newUpdate_CBT) {
        this.newUpdate_CBT = newUpdate_CBT;
        fieldGroup.setItemDataSource(newUpdate_CBT);
    }

}
