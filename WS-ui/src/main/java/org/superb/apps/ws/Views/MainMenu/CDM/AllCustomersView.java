package org.superb.apps.ws.Views.MainMenu.CDM;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import org.superb.apps.utilities.Enums.Statuses;
import org.superb.apps.utilities.vaadin.FancyLabels.StatusLabel;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.Tables.CustomTable;
import org.superb.apps.ws.Forms.CDM.CustomerForm;
import org.superb.apps.ws.Views.ResetButtonForTextField;
import org.superb.apps.ws.controllers.Customer_Controller;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.functionalities.ICustomer;

public class AllCustomersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "All Customers View";

    private final VerticalLayout VL = new VerticalLayout();
    private final CustomTable allCustomersTable = new CustomTable();

    private Button newCustomer;

    //<editor-fold defaultstate="collapsed" desc="MODEL">
    private static final ICustomer CUSTOMER_CONTROLLER = new Customer_Controller();
    private final BeanItemContainer<Customer> Customer_Container = new BeanItemContainer<>(Customer.class);
    //</editor-fold>

    public AllCustomersView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout topLayout = createTopBar();

        VerticalLayout barAndTableLayout = new VerticalLayout();
        barAndTableLayout.addComponent(topLayout);
        barAndTableLayout.addComponent(allCustomersTable);
        barAndTableLayout.setMargin(true);
        barAndTableLayout.setSpacing(true);
        barAndTableLayout.setSizeFull();
        barAndTableLayout.setExpandRatio(allCustomersTable, 1);
        barAndTableLayout.setStyleName("crud-main-layout");

        addComponent(barAndTableLayout);

        // allCustomersTable.setSizeFull();
        // allCustomersTable.setCacheRate(20);
        // VL.setSizeFull();
        // VL.setMargin(true);
        // VL.addComponent(allCustomersTable);
        //</editor-fold>
        updateBeanItemContainer(Customer_Container, CUSTOMER_CONTROLLER.getAllCustomers());

        allCustomersTable.setContainerDataSource(Customer_Container);
        allCustomersTable.setPageLength(Customer_Container.size());

        allCustomersTable.addGeneratedColumn("FS", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                final Button changeButton = new Button("Show", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer c = (Customer) row;
                        CustomerForm customerForm = new CustomerForm(new BeanItem(c), allCustomersTable);
                        getUI().addWindow(new WindowForm("Customer Update Form", customerForm));
                    }
                });

                return changeButton;
            }
        });
        allCustomersTable.addGeneratedColumn("Licene Status", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                int k = ((Customer) row).getName().hashCode() % 3;
                Statuses s;

                switch (k) {
                    case 0:
                        s = Statuses.AVAILABLE;
                        break;
                    case 1:
                    default:
                        s = Statuses.COMING;
                        break;
                    case 2:
                        s = Statuses.DISCONTINUED;
                        break;
                }

                return new StatusLabel(s, s.toString());
            }
        });
        allCustomersTable.setVisibleColumns(
                "idc", "name", "Licene Status", "FS", "city", /*"address", "zip", "pib", */ "region");
        allCustomersTable.setColumnHeaders(
                "CLIENT ID", "CLIENT NAME", "LICENCE STATUS", "FS", "CITY", /* "ADDRESS", "POSTAL CODE", "PIB", */ "REGION");

        allCustomersTable.setSelectable(true);
        allCustomersTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Customer c = (Customer) event.getItemId();
                    CustomerForm customerForm = new CustomerForm(new BeanItem(c), allCustomersTable);
                    getUI().addWindow(new WindowForm("Customer Update Form", customerForm));
                }
            }
        });
        //</editor-fold>

        addComponent(VL);
    }
    //<editor-fold defaultstate="collapsed" desc="Customer Table - Double click - Customer Form">

    private void updateBeanItemContainer(BeanItemContainer beanItemContainer, List listOfData) {
        beanItemContainer.removeAllItems();
        beanItemContainer.addAll(listOfData);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    public final HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("Filter");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                // setTableFilter(allCustomersTable.getContainerDataSource(), event.getText());
            }
        });

        newCustomer = new Button("New product");
        newCustomer.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newCustomer.setIcon(FontAwesome.YOUTUBE_PLAY);
        newCustomer.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // viewLogic.newProduct();
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth("100%");
        topLayout.addComponent(filter);
        topLayout.addComponent(newCustomer);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }

    private final void setTableFilter(BeanItemContainer container, String filterString) {
        container.removeAllContainerFilters();
        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "productName", filterString, true, false);
            SimpleStringFilter availabilityFilter = new SimpleStringFilter(
                    "availability", filterString, true, false);
            SimpleStringFilter categoryFilter = new SimpleStringFilter(
                    "category", filterString, true, false);
            container.addContainerFilter(new Or(nameFilter, availabilityFilter,
                    categoryFilter));
        }

    }
}
