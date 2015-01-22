package org.superb.apps.ws.Views;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.superb.apps.vaadin.utils.MyWindow;
import org.superb.apps.ws.controllers.CustomerBussinesType_Controller;
import org.superb.apps.ws.controllers.CustomerController;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;
import org.superb.apps.ws.functionalities.ICustomer;
import org.superb.apps.ws.functionalities.ICustomerBussinesType;

public class ConsoleView extends VerticalLayout implements View {

    private final HorizontalSplitPanel HSP = new HorizontalSplitPanel();
    private final VerticalLayout HL_VL_LEFT = new VerticalLayout();
    private final FormLayout HR_VL_RIGHT = new FormLayout();

    private final Accordion accordion = new Accordion();

    //<editor-fold defaultstate="collapsed" desc="model">
    private static final ICustomer CUSTOMER_CONTROLLER = new CustomerController();
    private final BeanItemContainer<Customer> Customer_BIC = new BeanItemContainer<>(Customer.class);
    private final Table customerTable = new Table();

    private static final ICustomerBussinesType CBT_CONTROLLER = new CustomerBussinesType_Controller();
    private final BeanItemContainer<Customer> Customer_BIC2 = new BeanItemContainer<>(Customer.class);
    private final Table customerTable2 = new Table("Customers");
    //</editor-fold>

    public ConsoleView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">
        setMargin(true);
        setSpacing(true);
        setSizeFull();

        HL_VL_LEFT.setSizeFull();
        HL_VL_LEFT.setMargin(true);
        HL_VL_LEFT.setSpacing(true);

        HR_VL_RIGHT.setSizeUndefined();
        HR_VL_RIGHT.setMargin(true);
        HR_VL_RIGHT.setSpacing(true);

        accordion.setSizeFull();

        HSP.setSplitPosition(33, Unit.PERCENTAGE);
        HSP.setFirstComponent(HL_VL_LEFT);
        HSP.setSecondComponent(HR_VL_RIGHT);

        HL_VL_LEFT.addComponent(accordion);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Accordion init">
        final VerticalLayout VL1 = new VerticalLayout();
        VL1.setSizeFull();
        VL1.setSpacing(true);
        VL1.setMargin(true);
        Button button1 = new Button("New Customer", new Button.ClickListener() {
            TextField customerName_TextField = new TextField("customer Name");
            TextField customerAddress_TextField = new TextField("customer Address");
            TextField customerCity_TextField = new TextField("customer City");
            TextField customerZIP_TextField = new TextField("customer ZIP");
            TextField customerRegion_TextField = new TextField("customer Region");
            TextField customerPIB_TextField = new TextField("customer PIB");
            Button saveNewCustomer_Button = new Button("Save", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        CUSTOMER_CONTROLLER.addNewCustomer(
                                customerName_TextField.getValue(),
                                customerAddress_TextField.getValue(),
                                customerCity_TextField.getValue(),
                                customerZIP_TextField.getValue(),
                                customerRegion_TextField.getValue(),
                                customerPIB_TextField.getValue());
                    } catch (Exception ex) {
                        Notification.show("Error.", ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            });

            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();

                HR_VL_RIGHT.addComponent(customerName_TextField);
                HR_VL_RIGHT.addComponent(customerAddress_TextField);
                HR_VL_RIGHT.addComponent(customerCity_TextField);
                HR_VL_RIGHT.addComponent(customerZIP_TextField);
                HR_VL_RIGHT.addComponent(customerRegion_TextField);
                HR_VL_RIGHT.addComponent(saveNewCustomer_Button);
            }
        });

        VL1.addComponent(button1);
        VL1.setComponentAlignment(button1, Alignment.TOP_CENTER);
        accordion.addTab(VL1, "Customer");

        final ComboBox comboBox_BussinesTypes = new ComboBox("Bussines Types", CBT_CONTROLLER.getAllBussinesTypes());

        comboBox_BussinesTypes.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                //<editor-fold defaultstate="collapsed" desc="Customer table 2">
                try {
                    Customer_BIC2.removeAllItems();

                    Customer_BIC2.addAll(
                            CBT_CONTROLLER.getAllCustomersForBussinesType(
                                    ((CustomerBussinesType) event.getProperty().getValue())
                                    .getIdcbt()));

                    customerTable2.setContainerDataSource(Customer_BIC2);
                    customerTable2.setPageLength(Customer_BIC2.size());
                    customerTable2.setVisibleColumns(
                            "idc", "name", "city", "address", "zip", "pib", "region");
                    customerTable2.setColumnHeaders(
                            "CLIENT ID", "CLIENT NAME", "CITY", "ADDRESS", "POSTAL CODE", "PIB", "REGION");

                    HR_VL_RIGHT.addComponent(customerTable2);

                } catch (Exception e) {
                    Notification.show("Error.", e.toString(), Notification.Type.ERROR_MESSAGE);
                }
                //</editor-fold>
            }
        });

        VerticalLayout VL2 = new VerticalLayout();
        VL2.setSizeFull();
        VL2.setSpacing(true);
        VL2.setMargin(true);
        Button button2 = new Button("Customer Bussines Types", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();
                HR_VL_RIGHT.addComponent(comboBox_BussinesTypes);
            }
        });

        VL2.addComponent(button2);
        VL2.setComponentAlignment(button2, Alignment.TOP_CENTER);
        accordion.addTab(VL2, "Customer Bussines Types");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Customer Table1">
        Customer_BIC.addAll(CUSTOMER_CONTROLLER.getAllCustomers());

        customerTable.setContainerDataSource(Customer_BIC);
        customerTable.setVisibleColumns(
                "idc", "name", "city", "address", "zip", "pib", "region");
        customerTable.setColumnHeaders(
                "CLIENT ID", "CLIENT NAME", "CITY", "ADDRESS", "POSTAL CODE", "PIB", "REGION");
        customerTable.addGeneratedColumn("CHANGE DATA", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                final Button changeButton = new Button("Change.", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer item = (Customer) row;
                        getUI().addWindow(new MyWindow(item.toString()));
                    }
                });

                return changeButton;
            }
        });

        customerTable.setSelectable(true);
        customerTable.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (event.getProperty().getType().equals(Customer.class)) {
                    Customer c = (Customer) event.getProperty().getValue();
                    try {
                        CUSTOMER_CONTROLLER.updateCustomer(c);
                    } catch (Exception ex) {
                        getUI().addWindow(new MyWindow("Greška! Opis: ".concat(ex.toString())));
                    }
                }
            }
        });

        customerTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    // customerTable.setEditable(!customerTable.isEditable());
                    Customer c = (Customer) event.getItemId();
                    MyWindow mw = new MyWindow("VVV: ");

                    mw.setText(c.toString());
                    getUI().addWindow(mw);
                    //}
                }
            }
        });

        Button button = new Button("Refresh Table");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                customerTable.refreshRowCache();
            }
        });

        final TextField customerName_TextField = new TextField("Name");
        final TextField customerAddress_TextField = new TextField("Address");
        final TextField customerCity_TextField = new TextField("City");
        final TextField customerZIP_TextField = new TextField("ZIP");
        final TextField customerRegion_TextField = new TextField("Region");

        Button saveNewCustomer_Button = new Button("Save New Customer", new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                String errorMessage = null;
                try {
                    Customer newCustomer = new Customer();

                    newCustomer.setName(customerName_TextField.getValue());
                    newCustomer.setAddress(customerAddress_TextField.getValue());
                    newCustomer.setCity(customerCity_TextField.getValue());
                    newCustomer.setZip(customerZIP_TextField.getValue());
                    newCustomer.setRegion(customerRegion_TextField.getValue());
                    newCustomer.setPib("12313");

                    CUSTOMER_CONTROLLER.addNewCustomer(newCustomer);
                } catch (Exception e) {
                    if (e.toString().contains("java.sql.SQLException: Cannot insert the value NULL into column")) {
                        errorMessage = "NULL Value detected! ";
                    }

                    if (e.toString().contains("Violation of UNIQUE KEY constraint \'CUSTOMER_PIB\'")) {
                        errorMessage += "PIB MUST BE UNIQUE ! ";
                    }

                    if (e.toString().contains("java.sql.SQLException: Violation of UNIQUE KEY constraint \'CUSTOMER_Unique_Data\'")) {
                        errorMessage += "CUSTOMER PROBABLY ALREADY EXIST !!!";
                    }

                    Notification.show("Error.", errorMessage, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        //</editor-fold>

        addComponent(HSP);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
