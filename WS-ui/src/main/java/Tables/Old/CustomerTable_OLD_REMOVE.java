/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables.Old;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import db.controllers.Customer_Controller;
import java.util.List;
import org.superb.apps.utilities.Enums.Statuses;
import org.superb.apps.utilities.vaadin.FancyLabels.StatusLabel;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import Forms.CDM.CustomerForm;
import db.ent.Customer;
import db.interfaces.ICustomer;

/**
 *
 * @author root
 */
public class CustomerTable_OLD_REMOVE extends Table implements IRefreshVisualContainer {

    private final BeanItemContainer<Customer> beanContainer = new BeanItemContainer<>(Customer.class);
    private final ICustomer CUSTOMER_CONTROLLER = new Customer_Controller();

    public CustomerTable_OLD_REMOVE() {
        setSizeFull();

        setContainerDataSource(beanContainer);
        updateBeanItemContainer(beanContainer, CUSTOMER_CONTROLLER.getAll());

        addGeneratedColumn("FS", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                final Button changeButton = new Button("Show", new Button.ClickListener() {
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

                return changeButton;
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
                    default:
                        s = Statuses.BLACK_LIST;
                        break;
                    case 2:
                        s = Statuses.IN_PROGRESS;
                        break;
                    case 3:
                        s = Statuses.NO_LICENCE;
                        break;
                    case 4:
                        s = Statuses.UNKNOWN;
                        break;
                }

                return new StatusLabel(s, s.toString());
            }
        });

        setVisibleColumns("idc", "name", "licence", "FS", "city", /*"address", "zip", "pib", */ "region");
        setColumnHeaders("CLIENT ID", "CLIENT NAME", "LICENCE STATUS", "FS", "CITY", /* "ADDRESS", "POSTAL CODE", "PIB", */ "REGION");
        setSelectable(true);

        setColumnWidth("idc", 80);
        setColumnWidth("licence", 120);
        setColumnWidth("FS", 80);
        setSelectable(true);
        setImmediate(true);
    }

    private void updateBeanItemContainer(BeanItemContainer beanItemContainer, List listOfData) {
        beanItemContainer.removeAllItems();
        beanItemContainer.addAll(listOfData);
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
            SimpleStringFilter regionFilter = new SimpleStringFilter(
                    "region", filterString, true, false);

            beanContainer.addContainerFilter(new Or(nameFilter, licenceFilter, cityFilter, regionFilter));
        }
    }

    @Override
    public void refreshVisualContainer() {
        markAsDirtyRecursive();
    }
}
