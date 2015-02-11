package org.superb.apps.ws.Views.MainMenu.CDM;

import com.vaadin.data.util.BeanItem;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.utilities.vaadin.MyWindows.MyWindow;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.ws.Forms.CDM.CustomerForm;
import org.superb.apps.ws.Tables.CustomerTable;
import org.superb.apps.ws.Views.ResetButtonForTextField;
import org.superb.apps.ws.db.entities.Customer;

public class AllCustomersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "All Customers View";

    private final VerticalLayout VL = new VerticalLayout();
    private final CustomerTable customersTable = new CustomerTable();

    private Button newCustomer;
    private Button newCustomerOwnerFS;

    public AllCustomersView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout topLayout = createTopBar();

        VerticalLayout barAndTableLayout = new VerticalLayout();
        barAndTableLayout.addComponent(topLayout);
        barAndTableLayout.addComponent(customersTable);
        barAndTableLayout.setMargin(true);
        barAndTableLayout.setSpacing(true);
        barAndTableLayout.setSizeFull();
        barAndTableLayout.setExpandRatio(customersTable, 1);
        barAndTableLayout.setStyleName("crud-main-layout");

        addComponent(barAndTableLayout);

        customersTable.setWidth(100, Unit.PERCENTAGE);
        customersTable.setCacheRate(20);
        //</editor-fold>

        customersTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Customer c = (Customer) event.getItemId();
                    CustomerForm customerForm = new CustomerForm(new BeanItem(c), customersTable);
                    getUI().addWindow(new WindowForm("Customer Update Form", customerForm));
                }
            }
        });
        //</editor-fold>

        addComponent(VL);
    }
    //<editor-fold defaultstate="collapsed" desc="Customer Table - Double click - Customer Form">


    @Override
    public void enter(ViewChangeEvent event) {
    }

    public final HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("Filter");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(false);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                // setTableFilter(allCustomersTable.getContainerDataSource(), event.getText());
            }
        });

        newCustomer = new Button("New Customer");
        newCustomer.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newCustomer.setIcon(FontAwesome.YOUTUBE_PLAY);
        newCustomer.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // viewLogic.newProduct();
            }
        });

        newCustomerOwnerFS = new Button("New FS Owner");
        newCustomerOwnerFS.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newCustomerOwnerFS.setIcon(FontAwesome.YOUTUBE_PLAY);
        newCustomerOwnerFS.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().addWindow(new MyWindow("New Customer Owner FS"));
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth("100%");
        topLayout.addComponents(filter, newCustomer, newCustomerOwnerFS);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }
}
