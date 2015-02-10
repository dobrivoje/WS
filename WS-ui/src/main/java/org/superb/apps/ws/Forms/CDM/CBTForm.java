package org.superb.apps.ws.Forms.CDM;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.TextField;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.ws.Forms.CRUDForm;
import org.superb.apps.ws.controllers.CustomerBussinesType_Controller;
import org.superb.apps.ws.db.entities.CustomerBussinesType;

public class CBTForm extends CRUDForm<CustomerBussinesType> {

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("customerActivity")
    private final TextField customerActivity = new TextField("customer Activity");
    //</editor-fold>

    public CBTForm(final CustomerBussinesType customerBussinesType) {
        super(customerBussinesType);
        addComponents(customerActivity, formButton);
    }

    public CBTForm(final CustomerBussinesType existingCBT, final IRefreshVisualContainer visualContainer) {
        super(existingCBT, visualContainer);
        addComponents(customerActivity, formButton);
    }

    @Override
    protected void bindFieldsToBean(CustomerBussinesType bean) {
        bean.setCustomerActivity(customerActivity.getValue());
    }

    @Override
    public void addNewBean(CustomerBussinesType bean) throws Exception {
        new CustomerBussinesType_Controller().addNewCBT(bean);
    }

    @Override
    public void updateExistingBean(CustomerBussinesType bean) throws Exception {
        new CustomerBussinesType_Controller().updateNewCBT(bean);
    }
}
