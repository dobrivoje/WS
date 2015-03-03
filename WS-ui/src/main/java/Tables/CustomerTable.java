/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import Forms.CDM.CustomerForm;
import Forms.CDM.RELCBTForm;
import Trees.RELCBT_Tree;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import db.ent.Customer;
import java.util.List;
import org.superb.apps.utilities.Enums.Statuses;
import org.superb.apps.utilities.vaadin.FancyLabels.StatusLabel;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CustomerTable extends GENTable<Customer> {

    public CustomerTable() {
        this(new BeanItemContainer<>(Customer.class), DS.getCustomerController().getAll());
    }

    public CustomerTable(BeanItemContainer<Customer> beanContainer, List list) {
        super(beanContainer, list);

        addGeneratedColumn("options", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                HorizontalLayout custOptionsHL = new HorizontalLayout();

                final Button editBtn = new Button("u", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer c = (Customer) row;
                        CustomerForm customerForm = new CustomerForm(new BeanItem(c), new IRefreshVisualContainer() {
                            @Override
                            public void refreshVisualContainer() {
                                source.markAsDirtyRecursive();
                            }
                        });
                        getUI().addWindow(new WindowForm("Customer Update Form", customerForm));
                    }
                });
                final Button cbTapeBtn = new Button("t", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer c = (Customer) row;

                        RELCBT_Tree cbtTree = new RELCBT_Tree("BUSSINES TYPE(S)", c);
                        RELCBTForm relCBT_Form = new RELCBTForm(c);

                        getUI().addWindow(new WindowFormProp("Customer Bussines Type Form", relCBT_Form, cbtTree));
                    }
                });

                editBtn.setDescription("Update this customer with new data...");
                cbTapeBtn.setDescription("Appoint this customer to a bussines type...");

                custOptionsHL.addComponents(editBtn, cbTapeBtn);
                custOptionsHL.setSizeFull();
                custOptionsHL.setSpacing(true);
                custOptionsHL.setComponentAlignment(editBtn, Alignment.MIDDLE_CENTER);
                custOptionsHL.setComponentAlignment(cbTapeBtn, Alignment.MIDDLE_CENTER);

                return custOptionsHL;
            }
        });
        addGeneratedColumn("licence", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {

                int k = ((Customer) row).getName().hashCode() % 5;
                Statuses s;

                switch (k) {
                    case 0:
                        s = Statuses.OK;
                        break;
                    case 1:
                        s = Statuses.BLACK_LIST;
                        break;
                    case 2:
                        s = Statuses.IN_PROGRESS;
                        break;
                    case 3:
                        s = Statuses.NO_LICENCE;
                        break;
                    case 4:
                    default:
                        s = Statuses.UNKNOWN;
                }

                return new StatusLabel(s, s.toString());
            }
        });
        addGeneratedColumn("city", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                String c = ((Customer) row).getFKIDCity().getName();
                return c == null ? "" : c;
            }
        });
        addGeneratedColumn("munic", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                String c = ((Customer) row).getFKIDCity().getMunicipality();
                return c == null ? "" : c;
            }
        });
        addGeneratedColumn("district", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                String c = ((Customer) row).getFKIDCity().getDistrict();
                return c == null ? "" : c;
            }
        });

        setVisibleColumns(/*"idc",*/"name", "licence", "options", "city", "munic", "district");
        setColumnHeaders(/*"CLIENT ID",*/"CLIENT NAME", "LICENCE STATUS", "OPTIONS", "CITY", "MUNIC.", "DISTRICT");

        //setColumnWidth("idc", 80);
        setColumnWidth("licence", 120);
    }

    public void setFilter(String filterString) {
        beanContainer.removeAllContainerFilters();

        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "name", filterString, true, false);
            SimpleStringFilter licenceFilter = new SimpleStringFilter(
                    "licence", filterString, true, false);
            SimpleStringFilter cityFilter = new SimpleStringFilter(
                    "city", filterString, true, false);
            SimpleStringFilter municipalityFilter = new SimpleStringFilter(
                    "munic", filterString, true, false);
            SimpleStringFilter districtFilter = new SimpleStringFilter(
                    "district", filterString, true, false);

            beanContainer.addContainerFilter(
                    new Or(
                            nameFilter, licenceFilter,
                            cityFilter, municipalityFilter,
                            districtFilter));
        }
    }
}
