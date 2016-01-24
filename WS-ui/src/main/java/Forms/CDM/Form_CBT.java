package Forms.CDM;

import org.superb.apps.utilities.vaadin.Forms.Form_CRUD;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.TextField;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.utilities.vaadin.Forms.IFormNotification;
import db.ent.CustomerBussinesType;
import static Main.MyUI.DS;

public class Form_CBT extends Form_CRUD<CustomerBussinesType> {

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("customerActivity")
    private final TextField customerActivity = new TextField("Activity");
    //</editor-fold>

    public Form_CBT(final CustomerBussinesType customerBussinesType) {
        super(customerBussinesType, new IFormNotification() {
            @Override
            public String getNotification() {
                return "CBT";
            }
        });
        addComponents(customerActivity, formButton);
    }

    public Form_CBT(final CustomerBussinesType existingCBT, final IRefreshVisualContainer visualContainer) {
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
