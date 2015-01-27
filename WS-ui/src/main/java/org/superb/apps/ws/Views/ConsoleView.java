package org.superb.apps.ws.Views;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.text.SimpleDateFormat;
import org.superb.apps.vaadin.utils.AccordionMenu;
import org.superb.apps.vaadin.utils.WindowForm;
import org.superb.apps.ws.Forms.CustomerForm;
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

    private final AccordionMenu menu = new AccordionMenu();

    //<editor-fold defaultstate="collapsed" desc="Model">
    private static final ICustomer CUSTOMER_CONTROLLER = new CustomerController();
    private final BeanItemContainer<Customer> Customer_BIC = new BeanItemContainer<>(Customer.class);
    private final Table customerTable = new Table();

    private static final ICustomerBussinesType CBT_CONTROLLER = new CustomerBussinesType_Controller();
    private final BeanItemContainer<Customer> Customer_BIC2 = new BeanItemContainer<>(Customer.class);
    private final Table customerTable2;
    //</editor-fold>

    public ConsoleView() {
        customerTable2 = new Table("Customers");

        //<editor-fold defaultstate="collapsed" desc="Menu buttons init">
        Button buttonNewCustomer = new Button("New Customer", new Button.ClickListener() {
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

                        Notification.show("Saved.", Notification.Type.HUMANIZED_MESSAGE);
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
                HR_VL_RIGHT.addComponent(customerPIB_TextField);
                HR_VL_RIGHT.addComponent(saveNewCustomer_Button);
            }
        });
        Button buttonNewCBT = new Button("New Customer Bussines Type", new Button.ClickListener() {
            ComboBox FK_IDC_ComboBox = new ComboBox("Customer", new BeanItemContainer(Customer.class, CUSTOMER_CONTROLLER.getAllCustomers()));
            ComboBox FK_CBT_ComboBox = new ComboBox("Customer Bussines Type",
                    new BeanItemContainer(CustomerBussinesType.class, CBT_CONTROLLER.getAllBussinesTypes()));

            DateField CBT_DateFrom_TextField = new DateField("Date From");
            DateField CBT_DateTo_TextField = new DateField("Date To");
            CheckBox activeCheckBox = new CheckBox("Active", true);

            Button saveNewCBT_Button = new Button("Save", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    FK_IDC_ComboBox.setNullSelectionAllowed(false);
                    FK_IDC_ComboBox.setTextInputAllowed(false);

                    FK_CBT_ComboBox.setNullSelectionAllowed(false);
                    FK_CBT_ComboBox.setTextInputAllowed(false);

                    CBT_DateFrom_TextField.setDateFormat("yyyy-MM-dd hh:mm:ss");
                    CBT_DateTo_TextField.setDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        CBT_CONTROLLER.addNewCBT(
                                (Customer) FK_IDC_ComboBox.getValue(),
                                (CustomerBussinesType) FK_CBT_ComboBox.getValue(),
                                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(CBT_DateFrom_TextField.getValue()),
                                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(CBT_DateTo_TextField.getValue()),
                                activeCheckBox.getValue());

                        Notification.show("Saved.", "Message", Notification.Type.HUMANIZED_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error.", ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            });

            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();

                HR_VL_RIGHT.addComponent(FK_IDC_ComboBox);
                HR_VL_RIGHT.addComponent(FK_CBT_ComboBox);
                HR_VL_RIGHT.addComponent(CBT_DateFrom_TextField);
                HR_VL_RIGHT.addComponent(CBT_DateTo_TextField);
                HR_VL_RIGHT.addComponent(activeCheckBox);
                HR_VL_RIGHT.addComponent(saveNewCBT_Button);
            }
        });

        final ComboBox comboBox_BussinesTypes = new ComboBox("Bussines Types", CBT_CONTROLLER.getAllBussinesTypes());
        comboBox_BussinesTypes.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                //<editor-fold defaultstate="collapsed" desc="Customer table 2">
                try {
                    Customer_BIC2.removeAllItems();

                    Customer_BIC2.addAll(CBT_CONTROLLER.getAllCustomersForBussinesType(
                            (CustomerBussinesType) event.getProperty().getValue()));

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

        Button buttonCBT_List = new Button("Customer Bussines Types List", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();
                HR_VL_RIGHT.addComponent(comboBox_BussinesTypes);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="UI setup">
        setSizeFull();

        HL_VL_LEFT.setSizeFull();
        HL_VL_LEFT.setMargin(true);
        HL_VL_LEFT.setSpacing(true);

        HR_VL_RIGHT.setSizeUndefined();
        HR_VL_RIGHT.setMargin(true);
        HR_VL_RIGHT.setSpacing(true);

        menu.setSizeFull();

        HSP.setSplitPosition(33, Unit.PERCENTAGE);
        HSP.setFirstComponent(HL_VL_LEFT);
        HSP.setSecondComponent(HR_VL_RIGHT);

        HL_VL_LEFT.addComponent(menu);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Customer Table - Double click - Customer Form">
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
                        Customer c = (Customer) row;
                        getUI().addWindow(new WindowForm("Customer Update Form", new CustomerForm(new BeanItem(c), customerTable)));
                    }
                });

                return changeButton;
            }
        });

        customerTable.setSelectable(true);
        customerTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Customer c = (Customer) event.getItemId();
                    getUI().addWindow(new WindowForm("Customer Update Form", new CustomerForm(new BeanItem(c), customerTable)));
                }
            }
        });

        Button customersListButton = new Button("List of Customers", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();
                HR_VL_RIGHT.addComponent(customerTable);
            }
        });
        //</editor-fold>

        menu.setMainMenuOptions("App Management", "Customers Management", "Tasks/Todo");
        menu.setSubMenuButtons(1, customersListButton, buttonNewCustomer, buttonNewCBT, buttonCBT_List);
        menu.createTabs();

        addComponent(HSP);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
