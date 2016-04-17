package Forms.CDM;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.TextField;
import db.ent.CustomerBussinesType;
import static Main.MyUI.DS;
import org.superbapps.utils.vaadin.Forms.Form_CRUD;
import org.superbapps.utils.vaadin.Tables.IRefreshVisualContainer;

public class Form_CBT extends Form_CRUD<CustomerBussinesType> {

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("customerActivity")
    private final TextField customerActivity = new TextField("Activity");
    //</editor-fold>

    public Form_CBT(final CustomerBussinesType customerBussinesType) {
        super(customerBussinesType, () -> "CBT");
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
