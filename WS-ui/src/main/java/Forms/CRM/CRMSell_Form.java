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
import com.vaadin.ui.TextField;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.Product;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import db.interfaces.ISalesmanController;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CRMSell_Form extends CRUDForm2<RelSALE> {

    private final ICRMController CRM_Controller = DS.getCrmController();
    private final ISalesmanController Salesman_Controller = DS.getSalesmanController();

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox salesman = new ComboBox("Salesman",
            new BeanItemContainer(Salesman.class, Salesman_Controller.getAll()));

    @PropertyId("FK_IDCA")
    private final ComboBox crmCase = new ComboBox("CRM Case", new BeanItemContainer(CrmCase.class));

    @PropertyId("sellDate")
    private final DateField sellDate = new DateField("Sell Date");

    @PropertyId("product")
    private final ComboBox product = new ComboBox("Product", DS.getiPRODUCTController().getAll());

    @PropertyId("ammount")
    private final TextField ammount = new TextField("Product Ammount");

    @PropertyId("paymentMethod")
    private final TextField paymentMethod = new TextField("Payment Method");
    //</editor-fold>

    public CRMSell_Form() {
        super(new BeanFieldGroup(RelSALE.class));

        // poništi margine iz super klase, da bi se polja lepše prikazala na formi !
        setMargin(false);

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        salesman.setWidth(250, Unit.PIXELS);

        salesman.setRequired(true);
        crmCase.setRequired(true);
        sellDate.setRequired(true);
        product.setRequired(true);
        ammount.setRequired(true);
        paymentMethod.setRequired(true);

        salesman.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    crmCase.setContainerDataSource(
                            new BeanItemContainer(
                                    CrmCase.class,
                                    CRM_Controller.getCRM_CompletedCases((Salesman) salesman.getValue(), true)
                            )
                    );
                } catch (Exception e) {
                    Notification.show("Notification", "There is no concluded CRM cases.", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        salesman.focus();
    }

    public CRMSell_Form(final CrudOperations crudOperation, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(new RelSALE()));
        beanItem = (BeanItem<RelSALE>) fieldGroup.getItemDataSource();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_SAVE.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    setBeanFromFields(beanItem.getBean());

                    try {
                        fieldGroup.commit();
                        CRM_Controller.addNewSale(beanItem.getBean());

                        Notification n = new Notification("New Sale Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (FieldGroup.CommitException ce) {
                        Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            addBeansToForm();
        }
    }

    @Override
    protected final void setBeanFromFields(RelSALE sale) {
        try {
            sale.setSellDate(sellDate.getValue());
        } catch (Exception e) {
        }

        try {
            sale.setAmmount(Double.parseDouble(ammount.getValue()));
        } catch (Exception e) {
        }

        try {
            sale.setFkIdp((Product) product.getValue());
        } catch (Exception e) {
        }

        try {
            sale.setPaymentMethod(paymentMethod.getValue());
        } catch (Exception e) {
        }

        try {
            // sale.setFkIdca(null);
        } catch (Exception e) {
        }

    }

    @Override
    protected final void setFieldsFromBean(RelSALE sale) {
        sellDate.setValue(sale.getSellDate());
        ammount.setValue(String.valueOf(sale.getAmmount()));
        product.setValue(sale.getFkIdp().getName());
        paymentMethod.setValue(sale.getPaymentMethod());
        crmCase.setValue(sale.getFkIdca());
        salesman.setValue(sale.getFkIdca().getFK_IDRSC().getFK_IDS());
    }

    @Override
    protected final void lockFormFileds(boolean lockAll) {
        super.lockFormFileds(lockAll);
        salesman.setEnabled(false);
    }
}
