package Views;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
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
import java.util.List;
import Forms.CDM.Form_Customer;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import static Main.MyUI.DS;
import org.superbapps.utils.common.Enums.Statuses;
import org.superbapps.utils.vaadin.FancyLabels.StatusLabel;
import org.superbapps.utils.vaadin.MyMenus.AccordionMenu;
import org.superbapps.utils.vaadin.MyWindows.WindowForm;
import org.superbapps.utils.vaadin.Tables.CustomTable;

public class View_Console extends VerticalLayout implements View {

    private final HorizontalSplitPanel HSP = new HorizontalSplitPanel();
    private final VerticalLayout HL_VL_LEFT = new VerticalLayout();
    private final FormLayout HR_VL_RIGHT = new FormLayout();

    //<editor-fold defaultstate="collapsed" desc="UI">
    private final AccordionMenu menu = new AccordionMenu();

    private final ComboBox Customer_ComboBox = new ComboBox("Customer");
    private final ComboBox CBT_ComboBox = new ComboBox("Customer Bussines Type");
    private final ComboBox BussinesTypes_ComboBox = new ComboBox();
    private final CustomTable allCustomersTable = new CustomTable();
    private final CustomTable cBT_Table = new CustomTable();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MODEL">
    private final BeanItemContainer<Customer> Customer_Container = new BeanItemContainer<>(Customer.class);
    private final BeanItemContainer<Customer> CustomersForBT_Container = new BeanItemContainer<>(Customer.class);
    private final BeanItemContainer<CustomerBussinesType> CBT_Container = new BeanItemContainer<>(CustomerBussinesType.class);
    //</editor-fold>

    public View_Console() {
        Customer_ComboBox.setContainerDataSource(Customer_Container);
        CBT_ComboBox.setContainerDataSource(CBT_Container);
        BussinesTypes_ComboBox.setContainerDataSource(CBT_Container);

        Customer_ComboBox.setNullSelectionAllowed(false);

        CBT_ComboBox.setNullSelectionAllowed(false);
        CBT_ComboBox.setTextInputAllowed(false);

        BussinesTypes_ComboBox.setNullSelectionAllowed(false);
        BussinesTypes_ComboBox.setTextInputAllowed(false);

        cBT_Table.addGeneratedColumn("licence", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                return new StatusLabel(Statuses.OK, "Ok");
            }
        });

        updateBeanItemContainer(Customer_Container, DS.getCustomerController().getAll());
        updateBeanItemContainer(CBT_Container, DS.getCBTController().getAll());

        //<editor-fold defaultstate="collapsed" desc="Menu buttons init">
        Button buttonNewCustomer = new Button("New Customer", new Button.ClickListener() {
            TextField customerName_TextField = new TextField("customer Name");
            TextField customerAddress_TextField = new TextField("customer Address");
            TextField customerPIB_TextField = new TextField("customer PIB");
            Button saveNewCustomer_Button = new Button("Save", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    try {
                        DS.getCustomerController().addNewCustomer(
                                customerName_TextField.getValue(),
                                customerAddress_TextField.getValue(),
                                null,
                                customerPIB_TextField.getValue());

                        updateBeanItemContainer(Customer_Container, DS.getCustomerController().getAll());
                        Notification.show("Saved.", Notification.Type.TRAY_NOTIFICATION);
                    } catch (Exception ex) {
                        Notification.show("Error.", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            });

            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();

                HR_VL_RIGHT.addComponent(customerName_TextField);
                HR_VL_RIGHT.addComponent(customerAddress_TextField);
                HR_VL_RIGHT.addComponent(customerPIB_TextField);
                HR_VL_RIGHT.addComponent(saveNewCustomer_Button);
            }
        });
        Button buttonNewCBT = new Button("New Customer Bussines Type", new Button.ClickListener() {
            DateField CBT_DateFrom_TextField = new DateField("Date From");
            DateField CBT_DateTo_TextField = new DateField("Date To");
            CheckBox activeCheckBox = new CheckBox("Active", true);

            Button saveNewCBT_Button = new Button("Save", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    updateBeanItemContainer(Customer_Container, DS.getCustomerController().getAll());

                    CBT_DateFrom_TextField.setDateFormat("yyyy-MM-dd hh:mm:ss");
                    CBT_DateTo_TextField.setDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        DS.getRELCBTController().addNew(
                                (Customer) Customer_ComboBox.getValue(),
                                (CustomerBussinesType) CBT_ComboBox.getValue(),
                                CBT_DateFrom_TextField.getValue(),
                                CBT_DateTo_TextField.getValue(),
                                activeCheckBox.getValue());

                        Notification.show("Saved.", "Message", Notification.Type.TRAY_NOTIFICATION);
                    } catch (Exception ex) {
                        Notification.show("Error.", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            });

            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();

                HR_VL_RIGHT.addComponent(Customer_ComboBox);
                HR_VL_RIGHT.addComponent(CBT_ComboBox);
                HR_VL_RIGHT.addComponent(CBT_DateFrom_TextField);
                HR_VL_RIGHT.addComponent(CBT_DateTo_TextField);
                HR_VL_RIGHT.addComponent(activeCheckBox);
                HR_VL_RIGHT.addComponent(saveNewCBT_Button);
            }
        });

        BussinesTypes_ComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                //<editor-fold defaultstate="collapsed" desc="Customer table 2">
                try {
                    updateBeanItemContainer(CustomersForBT_Container, DS.getCBTController().getAllCustomersForBussinesType(
                            (CustomerBussinesType) event.getProperty().getValue()));

                    // cBT_Table.setPageLength(CustomersForBT_Container.size());
                    cBT_Table.setVisibleColumns("name", "licence", "city", "address", "region");
                    cBT_Table.setColumnHeaders("CLIENT NAME", "LICENCE", "CITY", "ADDRESS", "REGION");

                    HR_VL_RIGHT.removeAllComponents();
                    HR_VL_RIGHT.addComponent(cBT_Table);
                } catch (Exception ex) {
                    Notification.show("Error.", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
                //</editor-fold>
            }
        });

        Button buttonCBT_List = new Button("Customer Bussines Types List", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                HR_VL_RIGHT.removeAllComponents();
                HR_VL_RIGHT.addComponent(BussinesTypes_ComboBox);
            }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="UI setup">
        setSizeFull();

        HL_VL_LEFT.setSizeFull();
        HL_VL_LEFT.setMargin(true);
        //HL_VL_LEFT.setSpacing(true);

        HR_VL_RIGHT.setSizeUndefined();
        //HR_VL_RIGHT.setWidth(100, Unit.PERCENTAGE);
        HR_VL_RIGHT.setMargin(true);
        // HR_VL_RIGHT.setSpacing(true);

        menu.setSizeFull();

        HSP.setSplitPosition(33, Sizeable.Unit.PERCENTAGE);
        HSP.setFirstComponent(HL_VL_LEFT);
        HSP.setSecondComponent(HR_VL_RIGHT);

        HL_VL_LEFT.addComponent(menu);
        //</editor-fold>

        allCustomersTable.setContainerDataSource(Customer_Container);
        allCustomersTable.setPageLength(Customer_Container.size());

        cBT_Table.setContainerDataSource(CustomersForBT_Container);
        cBT_Table.setPageLength(CustomersForBT_Container.size());

        allCustomersTable.addGeneratedColumn("CHANGE", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                final Button changeButton = new Button("Change.", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer c = (Customer) row;
                        Form_Customer customerForm = new Form_Customer(c, allCustomersTable);
                        getUI().addWindow(new WindowForm("Customer Update Form", true, customerForm));
                    }
                });

                return changeButton;
            }
        });
        allCustomersTable.addGeneratedColumn("Status", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                int k = ((Customer) row).getName().hashCode() % 3;
                Statuses s;

                switch (k) {
                    case 0:
                        s = Statuses.OK;
                        break;
                    case 1:
                    default:
                        s = Statuses.IN_PROGRESS;
                        break;
                    case 2:
                        s = Statuses.NO_LICENCE;
                        break;
                }

                return new StatusLabel(s, s.toString());
            }
        });

        allCustomersTable.setVisibleColumns(
                "idc", "name", "Status", "CHANGE", "address", "pib");
        allCustomersTable.setColumnHeaders(
                "CLIENT ID", "CLIENT NAME", "STATUS", "CHANGE", "ADDRESS", "PIB");

        allCustomersTable.setSelectable(true);
        allCustomersTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    Customer c = (Customer) event.getItemId();
                    Form_Customer customerForm = new Form_Customer(c, allCustomersTable);
                    getUI().addWindow(new WindowForm("Customer Update Form", true, customerForm));
                }
            }
        });

        Button customersListButton = new Button("List of Customers", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                updateBeanItemContainer(Customer_Container, DS.getCustomerController().getAll());
                HR_VL_RIGHT.removeAllComponents();
                HR_VL_RIGHT.addComponent(allCustomersTable);
            }
        });
        //</editor-fold>

        menu.setMainMenuOptions("App Management", "Customers Management", "Tasks/Todo");
        menu.setSubMenuButtons(1, customersListButton, buttonNewCustomer, buttonNewCBT, buttonCBT_List);
        menu.createTabs();

        addComponent(HSP);
    }
    //<editor-fold defaultstate="collapsed" desc="Customer Table - Double click - Customer Form">

    private void updateBeanItemContainer(BeanItemContainer beanItemContainer, List listOfData) {
        beanItemContainer.removeAllItems();
        beanItemContainer.addAll(listOfData);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }
}
