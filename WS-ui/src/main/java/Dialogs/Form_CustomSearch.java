/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import Forms.Form_CRUD2;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import db.ent.Customer;
import db.ent.Product;
import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Form_CustomSearch extends Form_CRUD2<CustomSearchData> {

    private IUpdateData iUpdateDataListener;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("startDate")
    private final DateField startDate = new DateField("From");

    @PropertyId("endDate")
    private final DateField endDate = new DateField("To");

    @PropertyId("salesman")
    private final ComboBox salesman = new ComboBox("Salesrep",
            new BeanItemContainer(Salesman.class, DS.getSalesmanController().getAll()));

    @PropertyId("customer")
    private final ComboBox customer = new ComboBox("Customer",
            new BeanItemContainer(Customer.class, DS.getCustomerController().getAll()));

    @PropertyId("product")
    private final ComboBox product = new ComboBox("Product",
            new BeanItemContainer(Product.class, DS.getProductController().getAll()));

    @PropertyId("amount")
    private final TextField amount = new TextField("Amount");

    @PropertyId("caseFinished")
    private final CheckBox caseFinished = new CheckBox("Case Finished ?");

    @PropertyId("saleAgreeded")
    private final CheckBox saleAgreeded = new CheckBox("Sale Agreed ?");
    //</editor-fold>

    public Form_CustomSearch() {
        super(new BeanFieldGroup(CustomSearchData.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        initFields();
        updateDynamicFields();

        salesman.focus();
    }

    public Form_CustomSearch(String actionButtonCaption /*,IUpdateData<CustomSearchData> iUpdateData */) {
        this();

        this.defaultCRUDButtonOnForm = false;

        CustomSearchData cc = new CustomSearchData();
        setFieldsFromBean(cc);

        btnCaption = actionButtonCaption;

        fieldGroup.setItemDataSource(new BeanItem(cc));
        beanItem = (BeanItem<CustomSearchData>) fieldGroup.getItemDataSource();

        clickListener = (Button.ClickEvent event) -> {
            setBeanFromFields(beanItem.getBean());

            if (iUpdateDataListener != null) {
                iUpdateDataListener.update(beanItem.getBean());
            }
        };

        addBeansToForm();
    }

    //<editor-fold defaultstate="collapsed" desc="UpdateDataListener">
    public IUpdateData getUpdateDataListener() {
        return iUpdateDataListener;
    }

    public void setUpdateDataListener(IUpdateData iUpdateDataListener) {
        this.iUpdateDataListener = iUpdateDataListener;
    }
    //</editor-fold>

    @Override
    protected final void setBeanFromFields(CustomSearchData csd) {
        csd.setStartDate(startDate.getValue());
        csd.setEndDate(endDate.getValue());
        csd.setSalesman((Salesman) salesman.getValue());
        csd.setCustomer((Customer) customer.getValue());
        csd.setProduct((Product) product.getValue());

        try {
            csd.setAmount(Integer.valueOf(amount.getValue()));
        } catch (Exception e) {
        }

        csd.setCaseFinished(caseFinished.getValue());
        csd.setSaleAgreeded(saleAgreeded.getValue());
    }

    @Override
    public final void setFieldsFromBean(CustomSearchData cc) {
        startDate.setValue(cc.getStartDate());
        endDate.setValue(cc.getEndDate());
        salesman.setValue(cc.getSalesman());
        customer.setValue(cc.getCustomer());
        product.setValue(cc.getProduct());
        caseFinished.setValue(cc.getCaseFinished());
        saleAgreeded.setValue(cc.getSaleAgreeded());
    }

    @Override
    protected final void initFields() {
        salesman.setWidth(250, Unit.PIXELS);
        product.setWidth(250, Unit.PIXELS);

        caseFinished.setEnabled(true);
        saleAgreeded.setEnabled(false);
    }

    @Override
    protected void setRequiredFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected final void updateDynamicFields() {
        caseFinished.addValueChangeListener((Property.ValueChangeEvent event) -> {
            saleAgreeded.setEnabled(caseFinished.getValue() == null
                    ? false : caseFinished.getValue()
            );
        });
    }
}
