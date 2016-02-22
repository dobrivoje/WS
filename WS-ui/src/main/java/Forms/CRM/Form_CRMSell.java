package Forms.CRM;

import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
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
import db.ent.Product;
import db.ent.RelSALE;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import db.interfaces.IPRODUCTController;
import db.interfaces.ISalesmanController;
import java.util.Date;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.converters.MyNumberWithNoGrouping;
import static Main.MyUI.DS;

/**
 *
 * @author root
 */
public class Form_CRMSell extends Form_CRUD2<RelSALE> {

    private final ICRMController CRM_Controller = DS.getCRMController();
    private final IPRODUCTController PRODUCT_Controller = DS.getProductController();
    private final ISalesmanController Salesman_Controller = DS.getSalesmanController();

    private boolean newCase;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final ComboBox salesman = new ComboBox("Salesrep",
            new BeanItemContainer(Salesman.class, Salesman_Controller.getAll()));

    @PropertyId("FK_IDCA")
    private final ComboBox crmCase = new ComboBox("Concluded CRM Case", new BeanItemContainer(CrmCase.class));

    @PropertyId("sellDate")
    private final DateField sellDate = new DateField("Sell Date");

    @PropertyId("FK_IDP")
    private final ComboBox product = new ComboBox("Product", DS.getProductController().getAll());

    @PropertyId("ammount")
    private final TextField amount = new TextField("Product Amount");

    @PropertyId("paymentMethod")
    private final TextField paymentMethod = new TextField("Payment Method");

    private final Button sellCrmCaseButton = new Button("Sell CRM Case");
    //</editor-fold>

    public Form_CRMSell() {
        super(new BeanFieldGroup(RelSALE.class));

        fieldGroup.bindMemberFields(this);

        setFormFieldsWidths(250, Unit.PIXELS);

        initFields();
        updateDynamicFields();

        salesman.focus();
    }

    public Form_CRMSell(Salesman salesRep) {
        this(null, true, false);

        salesman.setValue(salesRep);
    }

    public Form_CRMSell(RelSALE relSALE, boolean newCase, boolean readOnly) {
        this();

        this.defaultCRUDButtonOnForm = false;

        this.readOnly = readOnly;
        this.newCase = newCase;

        RelSALE rs = relSALE == null
                ? new RelSALE(new Date(), 0, "", new CrmCase(new Date(), "", new RelSALESMANCUST()), new Product())
                : relSALE;

        setFieldsFromBean(rs);

        fieldGroup.setItemDataSource(new BeanItem(rs));
        beanItem = (BeanItem<RelSALE>) fieldGroup.getItemDataSource();

        salesman.setValue(rs.getFK_IDCA().getFK_IDRSC().getFK_IDS());

        btnCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = (Button.ClickEvent event) -> {
            // setBeanFromFields(beanItem.getBean());

            try {
                fieldGroup.commit();

                if (amount.getValue().isEmpty() || Double.parseDouble(amount.getValue()) <= 0) {
                    throw new Exception("Amount Must Not Be Empty, Or Less Than Zero.");
                }

                CRM_Controller.addNewSale(beanItem.getBean());

                Notification n = new Notification("New Sale Added.", Notification.Type.TRAY_NOTIFICATION);
                n.setDelayMsec(500);
                n.show(getUI().getPage());
            } catch (FieldGroup.CommitException ce) {
                Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
            } catch (Exception ex) {
                Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        };

        addComponents(salesman);
        addBeansToForm();

        lockFormFileds(readOnly);
    }

    @Override
    protected final void setBeanFromFields(RelSALE sale) {
        try {
            sale.setSellDate(sellDate.getValue());
        } catch (Exception e) {
        }

        try {
            sale.setAmmount(Double.parseDouble(amount.getValue()));
        } catch (Exception e) {
        }

        try {
            sale.setFK_IDP((Product) product.getValue());
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
    public final void setFieldsFromBean(RelSALE sale) {
        sellDate.setValue(sale.getSellDate());

        if (sale.getAmmount() > 0) {
            amount.setValue(String.valueOf(sale.getAmmount()));
        }

        product.setValue(sale.getFK_IDP().getName());
        paymentMethod.setValue(sale.getPaymentMethod());
        crmCase.setValue(sale.getFK_IDCA());
        salesman.setValue(sale.getFK_IDCA().getFK_IDRSC().getFK_IDS());
    }

    @Override
    protected final void lockFormFileds(boolean lockAll) {
        salesman.setEnabled(false);

        if (!lockAll) {
            crmCase.setEnabled(newCase);
            sellDate.setEnabled(true);
            product.setEnabled(newCase);
            amount.setEnabled(true);
            paymentMethod.setEnabled(true);
        } else {
            lockFormFileds(lockAll);
        }
    }

    @Override
    protected final void updateDynamicFields() {
        //<editor-fold defaultstate="collapsed" desc="salesman listener init">
        salesman.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    Salesman s = (Salesman) salesman.getValue();

                    crmCase
                            .setContainerDataSource(
                                    new BeanItemContainer(
                                            CrmCase.class,
                                            CRM_Controller.getCRM_CasesStats(s, true, true)
                                    )
                            );

                    product.setContainerDataSource(
                            new BeanItemContainer(Product.class,
                                    PRODUCT_Controller.getProductsForBussinesLine(s))
                    );
                } catch (Exception e) {
                    Notification.show("Notification", "There is no concluded CRM cases.", Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        //</editor-fold>
    }

    @Override
    protected final void initFields() {
        salesman.setWidth(250, Unit.PIXELS);

        amount.setConverter(new MyNumberWithNoGrouping());
        amount.setConversionError("Amount must be a decimal number.");
        salesman.setNullSelectionAllowed(false);
        crmCase.setNullSelectionAllowed(false);
        product.setNullSelectionAllowed(false);

        setRequiredFields();
    }

    @Override
    protected void setRequiredFields() {
        salesman.setRequired(true);
        crmCase.setRequired(true);
        sellDate.setRequired(true);
        product.setRequired(true);
        amount.setRequired(true);
        paymentMethod.setRequired(true);
    }
}
