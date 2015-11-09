/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialogs;

import db.ent.custom.CustomSearchData;
import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
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
    private final ComboBox salesrep = new ComboBox("Salesrep",
            new BeanItemContainer(Salesman.class, DS.getSalesmanController().getAll()));

    @PropertyId("customer")
    private final ComboBox customer = new ComboBox("Customer",
            new BeanItemContainer(Customer.class, DS.getCustomerController().getAll()));

    @PropertyId("product")
    private final ComboBox product = new ComboBox("Product",
            new BeanItemContainer(Product.class, DS.getProductController().getAll()));

    @PropertyId("quantity")
    private final TextField quantity = new TextField("Quantity", "122000");

    @PropertyId("moneyAmount")
    private final TextField moneyAmount = new TextField("Amount");

    @PropertyId("caseFinished")
    private final CheckBox caseFinished = new CheckBox("Case Finished ?");

    @PropertyId("saleAgreeded")
    private final CheckBox saleAgreeded = new CheckBox("Sale Agreed ?");
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Konstruktori">
    public Form_CustomSearch() {
        super(new BeanFieldGroup(CustomSearchData.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);

        CustomSearchData cc = new CustomSearchData();

        fieldGroup.setItemDataSource(new BeanItem(cc));
        beanItem = (BeanItem<CustomSearchData>) fieldGroup.getItemDataSource();

        updateDynamicFields();

        clickListener = (Button.ClickEvent event) -> {
            setBeanFromFields(beanItem.getBean());

            if (iUpdateDataListener != null) {
                iUpdateDataListener.update(beanItem.getBean());
            }
        };

        addBeansToForm();
    }

    /**
     * Forma sa vizuelnom reprezentacijom polja, regulisana parametrom.
     *
     * @param options Predstavlja bit reprezentaciju polja na formi.<br>
     * Ukoliko je 1 na mestu za to polje, polje se pojavljuje na formi.<br>
     * Redosled polja je sleva na desno, počevši od polja kako su raspoređeni na
     * formi, dakle, prvo je "startDate", zatim "endDate", pa "salesrep", itd.
     * <p>
     * Primer : options = 0b111100001, uključuje prva četiri, i poslednje polje
     * na formi, dakle start, end, salesman, customer, i saleAgreed.
     */
    public Form_CustomSearch(int options) {
        this();

        fieldGroup.getFields().stream().forEach((c) -> {
            c.setEnabled(false);
        });

        int mask = (int) Math.pow(2, fieldGroup.getFields().size());

        //<editor-fold defaultstate="collapsed" desc="for petlja za ispitivanje">
        for (int i = 0; i < 8; i++) {

            switch (options & mask) {
                case 0b100000000:
                    // startDate.setVisible(true);
                    startDate.setEnabled(true);
                    break;
                case 0b10000000:
                    // endDate.setVisible(true);
                    endDate.setEnabled(true);
                    break;
                case 0b1000000:
                    // salesrep.setVisible(true);
                    salesrep.setEnabled(true);
                    break;
                case 0b100000:
                    // customer.setVisible(true);
                    customer.setEnabled(true);
                    break;
                case 0b10000:
                    // product.setVisible(true);
                    product.setEnabled(true);
                    break;
                case 0b1000:
                    // quantity.setVisible(true);
                    quantity.setEnabled(true);
                    break;
                case 0b100:
                    // moneyAmount.setVisible(true);
                    moneyAmount.setEnabled(true);
                    break;
                case 0b10:
                    // caseFinished.setVisible(true);
                    caseFinished.setEnabled(true);
                    break;
                case 0b1:
                    // saleAgreeded.setVisible(true);
                    saleAgreeded.setEnabled(true);
                    break;
                default:
            }

            mask >>= 1;
        }
        //</editor-fold>

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="overrided metode...">
    @Override
    protected final void setBeanFromFields(CustomSearchData csd) {
        csd.setStartDate(startDate.getValue());
        csd.setEndDate(endDate.getValue());
        csd.setSalesman((Salesman) salesrep.getValue());
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
    public final void setFieldsFromBean(CustomSearchData csd) {
        startDate.setValue(csd.getStartDate());
        endDate.setValue(csd.getEndDate());
        salesrep.setValue(csd.getSalesman());
        customer.setValue(csd.getCustomer());
        product.setValue(csd.getProduct());

        try {
            quantity.setValue(Double.toString(csd.getQuantity()));
        } catch (Exception e) {
        }

        try {
            moneyAmount.setValue(Double.toString(csd.getMoneyAmount()));
        } catch (Exception e) {
        }

        caseFinished.setValue(csd.getCaseFinished());
        saleAgreeded.setValue(csd.getSaleAgreeded());
    }

    @Override
    protected final void initFields() {
        salesrep.focus();
        setRequiredFields();

        // quantity.setConverter(Integer.class);
        // moneyAmount.setConverter(Integer.class);
        quantity.setEnabled(false);
        moneyAmount.setEnabled(false);
        caseFinished.setEnabled(false);
        saleAgreeded.setEnabled(false);
    }

    @Override
    protected void setRequiredFields() {
        caseFinished.setEnabled(true);
        saleAgreeded.setEnabled(caseFinished.getValue() == null ? false : caseFinished.getValue());
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
    //</editor-fold>

}
