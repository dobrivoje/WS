/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.Tables;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import java.util.List;
import org.superb.apps.utilities.Enums.Statuses;
import org.superb.apps.utilities.vaadin.FancyLabels.StatusLabel;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.ws.Forms.CDM.CustomerForm;
import org.superb.apps.ws.controllers.Customer_Controller;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.functionalities.ICustomer;

/**
 *
 * @author root
 */
public class CustomerTable extends Table implements IRefreshVisualContainer {

    private final BeanItemContainer<Customer> container = new BeanItemContainer<>(Customer.class);
    private final ICustomer CUSTOMER_CONTROLLER = new Customer_Controller();

    public CustomerTable() {
        setSizeFull();

        setContainerDataSource(container);
        updateBeanItemContainer(container, CUSTOMER_CONTROLLER.getAllCustomers());

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
        addGeneratedColumn("Licene Status", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                int k = ((Customer) row).getName().hashCode() % 4;
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
                }

                return new StatusLabel(s, s.toString());
            }
        });

        setVisibleColumns("idc", "name", "Licene Status", "FS", "city", /*"address", "zip", "pib", */ "region");
        setColumnHeaders("CLIENT ID", "CLIENT NAME", "LICENCE STATUS", "FS", "CITY", /* "ADDRESS", "POSTAL CODE", "PIB", */ "REGION");
        setSelectable(true);

        setColumnWidth("idc", 80);
        setSelectable(true);
        setImmediate(true);

        addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Customer c = (Customer) event.getItemId();
                    CustomerForm customerForm = new CustomerForm(new BeanItem(c), new IRefreshVisualContainer() {
                        @Override
                        public void refreshVisualContainer() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });

                    getUI().addWindow(new WindowForm("Customer Update Form", customerForm));
                }
            }
        });
    }

    private void updateBeanItemContainer(BeanItemContainer beanItemContainer, List listOfData) {
        beanItemContainer.removeAllItems();
        beanItemContainer.addAll(listOfData);
    }

    private void setTableFilter(BeanItemContainer container, String filterString) {
        container.removeAllContainerFilters();

        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "name", filterString, true, false);
            SimpleStringFilter availabilityFilter = new SimpleStringFilter(
                    "Licene Status", filterString, true, false);

            container.addContainerFilter(new Or(nameFilter, availabilityFilter));
        }
    }

    @Override
    public void refreshVisualContainer() {
        markAsDirtyRecursive();
    }
}
