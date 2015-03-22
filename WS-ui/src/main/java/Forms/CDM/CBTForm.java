package Forms.CDM;

import Forms.CRUDForm;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.TextField;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import Forms.IFormNotification;
import db.ent.CustomerBussinesType;
import static ws.MyUI.DS;

public class CBTForm extends CRUDForm<CustomerBussinesType> {

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("customerActivity")
    private final TextField customerActivity = new TextField("Activity");
    //</editor-fold>

    public CBTForm(final CustomerBussinesType customerBussinesType) {
        super(customerBussinesType, new IFormNotification() {
            @Override
            public String getNotification() {
                return "CBT";
            }
        });
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
    public void updateExistingBean(CustomerBussinesType bean) throws Exception {
        DS.getCBTController().updateExisting(bean);
    }

    @Override
    public void addNewBean(CustomerBussinesType bean) throws Exception {
        DS.getCBTController().addNew(bean);
    }
}
