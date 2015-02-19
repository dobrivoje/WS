package Views.MainMenu.CDM;

import Forms.SaDesneStraneForm;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.utilities.vaadin.MyWindows.MyWindow;
import Tables.CustomerTable;
import Views.ResetButtonForTextField;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import db.ent.Customer;

public class CustomersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Customers View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();
    private final CustomerTable customersTable = new CustomerTable();

    private final SaDesneStraneForm form = new SaDesneStraneForm();

    private Button newCustomer;
    private Button newCustomerOwnerFS;

    public CustomersView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">
        setSizeFull();
        addStyleName("crud-view");
        VL.setSizeFull();
        VL.setMargin(true);
        VL.setSpacing(true);
        HL.setSizeFull();
        HL.setSplitPosition(70, Unit.PERCENTAGE);
        HorizontalLayout topLayout = createTopBar();
        
        // kreiraj panel za tabelu i properies tabele :
        VerticalLayout vl1 = new VerticalLayout(customersTable);
        vl1.setMargin(true);
        vl1.setSizeFull();
        HL.setFirstComponent(vl1);
        VL.addComponent(topLayout);
        VL.addComponent(HL);
        VL.setSizeFull();
        VL.setExpandRatio(HL, 1);
        VL.setStyleName("crud-main-layout");
        addComponent(VL);
        //</editor-fold>

        customersTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Customer c = (Customer) customersTable.getValue();
                    showPropForm(c);
                }
            }
        });

        customersTable.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Customer c = (Customer) customersTable.getValue();
            }
        });
        //</editor-fold>

        addComponent(VL);
    }
    //<editor-fold defaultstate="collapsed" desc="Customer Table - Double click - Customer Form">

    @Override
    public void enter(ViewChangeEvent event) {
    }

    //<editor-fold defaultstate="collapsed" desc="createTopBar">
    public final HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("Filter");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(false);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                customersTable.setFilter(event.getText());
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
        topLayout.setWidth(100, Unit.PERCENTAGE);
        topLayout.addComponents(filter, newCustomer, newCustomerOwnerFS);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }

    private VerticalLayout createProperties(Customer customer) {
        VerticalLayout vl = new VerticalLayout();
        vl.setMargin(true);
        vl.setSpacing(true);

        vl.addComponent(new Label(customer == null ? "no data" : customer.toString()));
        return vl;
    }
    //</editor-fold>

    private void showPropForm(Customer customer) {
        if (customer != null) {
            form.addStyleName("visible");
            form.setEnabled(true);
        } else {
            form.removeStyleName("visible");
            form.setEnabled(false);
        }
    }
}
