/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import db.ent.custom.CustomSearchData;
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
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Form_CustomSearch extends Form_CRUD2<CustomSearchData> {

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("startDate")
    private final DateField startDate = new DateField("Date from");

    @PropertyId("endDate")
    private final DateField endDate = new DateField("Date to");

    @PropertyId("salesman")
    private final ComboBox salesman = new ComboBox("Salesrep",
            new BeanItemContainer(Salesman.class, DS.getSalesmanController().getAll()));

    @PropertyId("customer")
    private final ComboBox customer = new ComboBox("Customer",
            new BeanItemContainer(Customer.class, DS.getCustomerController().getAll()));

    @PropertyId("product")
    private final ComboBox product = new ComboBox("Product",
            new BeanItemContainer(Product.class, DS.getProductController().getAll()));

    @PropertyId("quantity")
    private final TextField quantity = new TextField("Quantity");

    @PropertyId("moneyAmount")
    private final TextField moneyAmount = new TextField("Money Amount");

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

        CustomSearchData cc = new CustomSearchData();

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

    @Override
    protected final void setBeanFromFields(CustomSearchData csd) {
        csd.setStartDate(startDate.getValue());
        csd.setEndDate(endDate.getValue());
        csd.setSalesman((Salesman) salesman.getValue());
        csd.setCustomer((Customer) customer.getValue());
        csd.setProduct((Product) product.getValue());

        try {
            csd.setQuantity(Double.valueOf(quantity.getValue()));
        } catch (Exception e) {
        }

        try {
            csd.setMoneyAmount(Double.valueOf(moneyAmount.getValue()));
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

        try {
            quantity.setValue(Double.toString(cc.getQuantity()));
        } catch (Exception e) {
        }

        try {
            moneyAmount.setValue(Double.toString(cc.getMoneyAmount()));
        } catch (Exception e) {
        }

        caseFinished.setValue(cc.getCaseFinished());
        saleAgreeded.setValue(cc.getSaleAgreeded());
    }

    @Override
    protected final void initFields() {
        salesman.focus();
        setRequiredFields();

        quantity.setConverter(Integer.class);
        moneyAmount.setConverter(Integer.class);
    }

    @Override
    protected void setRequiredFields() {
        caseFinished.setEnabled(true);
        saleAgreeded.setEnabled(false);
    }

    @Override
    protected final void updateDynamicFields() {
        caseFinished.addValueChangeListener((Property.ValueChangeEvent event) -> {
            saleAgreeded.setEnabled(caseFinished.getValue() == null
                    ? false : caseFinished.getValue()
            );

            saleAgreeded.setValue(
                    caseFinished.getValue() == null || caseFinished.getValue() == false
                            ? false : saleAgreeded.getValue()
            );
        });
    }
}
