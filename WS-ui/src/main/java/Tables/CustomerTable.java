/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Forms.CDM.CustomerForm;
import Forms.CDM.RELCBTForm;
import Forms.CRM.CRMCase_Form;
import Trees.RELCBT_Tree;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.City;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.InfSysUser;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.dobrivoje.auth.roles.RolesPermissions;
import org.superb.apps.utilities.Enums.Statuses;
import org.superb.apps.utilities.Enums.ViewModes;
import static org.superb.apps.utilities.Enums.ViewModes.SIMPLE;
import org.superb.apps.utilities.vaadin.FancyLabels.StatusLabel;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import ws.MyUI;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CustomerTable extends GENTable<Customer> {

    private static final Action ACTION_CUSTOMER_UPDATE = new Action("Customer Data Update");
    private static final Action ACTION_CUSTOMER_BUSSINES_TYPE = new Action("Customer Bussines Type");
    private static final Action ACTION_CRM_ACTIVE_PROCESSES = new Action("Active Customer CRM Processes");
    private static final Action ACTION_CRM_NEW_PROCESS = new Action("New Customer CRM Processes");

    public CustomerTable() {
        this(new BeanItemContainer<>(Customer.class), DS.getCustomerController().getAll());

        // inicijalizuj handler,...
        addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
            }
        });
    }

    public CustomerTable(BeanItemContainer<Customer> beanContainer, List list) {
        super(beanContainer, list);

        tableColumnsID = new ArrayList(Arrays.asList(
                "navCode", "name", "licence", "options", "myCity", "zone", "matBr"));

        //<editor-fold defaultstate="collapsed" desc="Generisane kolone tabele,...">
        addGeneratedColumn("options", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                HorizontalLayout custOptionsHL = new HorizontalLayout();

                final Button editBtn = new Button("u", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer c = (Customer) row;

                        CustomerForm cf = new CustomerForm(c, new IRefreshVisualContainer() {
                            @Override
                            public void refreshVisualContainer() {
                                source.markAsDirtyRecursive();
                            }
                        });

                        getUI().addWindow(new WindowForm3(
                                "Customer Update Form",
                                cf,
                                null,
                                cf.getClickListener())
                        );
                    }
                });
                final Button cbtypeBtn = new Button("t", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        showCBTForm((Customer) row, RolesPermissions.P_CUSTOMERS_EDIT_ALL);
                        /*
                         RELCBT_Tree cbtTree;
                         RELCBTForm relCBT_Form;
                        
                         try {
                         cbtTree = new RELCBT_Tree("BUSSINES TYPE(S)", c);
                         relCBT_Form = new RELCBTForm(c);
                         getUI().addWindow(new WindowFormProp("Customer Bussines Type Form", false, relCBT_Form, cbtTree));
                         } catch (CustomTreeNodesEmptyException | NullPointerException | IllegalArgumentException ex) {
                         Notification.show("Notification", "There's no bussines type for this customer !", Notification.Type.ERROR_MESSAGE);
                         }
                         */
                    }
                });

                editBtn.setEnabled(MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
                editBtn.setDescription("Update this customer with new data...");

                cbtypeBtn.setEnabled(MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
                cbtypeBtn.setDescription("Appoint this customer to a bussines type...");

                custOptionsHL.addComponents(editBtn, cbtypeBtn);
                custOptionsHL.setSizeFull();
                custOptionsHL.setSpacing(true);
                custOptionsHL.setComponentAlignment(editBtn, Alignment.MIDDLE_CENTER);
                custOptionsHL.setComponentAlignment(cbtypeBtn, Alignment.MIDDLE_CENTER);

                return custOptionsHL;
            }
        });

        addGeneratedColumn("licence", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                Property property = source.getItem(row).getItemProperty(column);
                Statuses s = Statuses.UNKNOWN;

                try {
                    s = (boolean) property.getValue() ? Statuses.OK : Statuses.NO_LICENCE;
                } catch (Exception e) {
                }

                return new StatusLabel(s, s.toString());
            }
        });

        addGeneratedColumn("navCode", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                Property property = source.getItem(row).getItemProperty(column);
                String navID = "";

                if (property != null) {
                    try {
                        navID = (String) property.getValue();
                    } catch (Exception ex) {
                    }
                }

                return navID;
            }
        });

        addGeneratedColumn("zone", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                Property property = source.getItem(row).getItemProperty(column);
                String zone1 = " - ";

                if (property != null) {
                    try {
                        zone1 = (String) property.getValue();
                    } catch (Exception ex) {
                    }
                }

                return zone1;
            }
        });

        addGeneratedColumn("matBr", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                Property property = source.getItem(row).getItemProperty(column);
                String matBrojID = "";

                if (property != null) {
                    try {
                        matBrojID = (String) property.getValue();
                    } catch (Exception ex) {
                    }
                }

                return matBrojID;
            }
        });

        addGeneratedColumn("city", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                City c = ((Customer) row).getFKIDCity();
                return (c != null) ? c.toString() : " - ";
            }
        });

        setVisibleColumns("navCode", "name", "licence", "options", "myCity",
                "zone", "matBr", "myCityMunicipality", "myCityDistrict", "myCityRegion");
        setColumnHeaders("NAV ID", "CLIENT NAME", "LICENCE", "OPTIONS", "CITY",
                "ZONE", "MATBR", "MUNIC.", "DISTRICT", "REGION");

        setColumnWidth("navCode", 90);
        setColumnWidth("matBr", 90);
        setColumnWidth("zone", 90);
        setColumnWidth("myCity", 220);
        setColumnWidth("licence", 120);
        setColumnWidth("options", 90);

        setTablePerspective(SIMPLE);
        //</editor-fold>
    }

    public void setFilter(String filterString) {
        beanContainer.removeAllContainerFilters();

        if (filterString.length() > 0) {
            SimpleStringFilter navCodeFilter = new SimpleStringFilter(
                    "navCode", filterString, true, false);
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "name", filterString, true, false);
            SimpleStringFilter licenceFilter = new SimpleStringFilter(
                    "licence", filterString, true, false);
            SimpleStringFilter matBrFilter = new SimpleStringFilter(
                    "matBr", filterString, true, false);
            SimpleStringFilter myCityFilter = new SimpleStringFilter(
                    "myCity", filterString, true, false);
            SimpleStringFilter zoneFilter = new SimpleStringFilter(
                    "zone", filterString, true, false);
            SimpleStringFilter myCityDistrictFilter = new SimpleStringFilter(
                    "myCityDistrict", filterString, true, false);
            SimpleStringFilter myCityRegionFilter = new SimpleStringFilter(
                    "myCityRegion", filterString, true, false);

            beanContainer.addContainerFilter(
                    new Or(
                            navCodeFilter,
                            nameFilter, licenceFilter,
                            matBrFilter, myCityFilter, zoneFilter,
                            myCityDistrictFilter, myCityRegionFilter
                    )
            );
        }
    }

    public final void setTablePerspective(ViewModes mode, String... visibleColumns) {
        switch (mode) {
            case SIMPLE:
                setTableView("navCode", "name", "zone", "matBr", "myCity", "myCityDistrict", "myCityRegion");
                break;
            case LICENCE:
                setTableView("name", "licence", "matBr");
                break;
            case FULL:
            default:
                setTableView("navCode", "name", "licence", "options", "myCity", "zone", "matBr");
        }
    }

    @Override
    public final void addActionHandler(Action.Handler actionHandler) {
        super.addActionHandler(new Action.Handler() {

            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{
                    ACTION_CUSTOMER_UPDATE,
                    ACTION_CUSTOMER_BUSSINES_TYPE,
                    ACTION_CRM_ACTIVE_PROCESSES,
                    ACTION_CRM_NEW_PROCESS
                };
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                final CustomerTable source = (CustomerTable) sender;

                if (action.equals(ACTION_CUSTOMER_UPDATE)) {
                    Customer c = (Customer) (source.getValue());
                    CustomerForm cf = new CustomerForm(c, new IRefreshVisualContainer() {
                        @Override
                        public void refreshVisualContainer() {
                            source.markAsDirtyRecursive();
                        }
                    });

                    if (MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL)) {
                        getUI().addWindow(new WindowForm3(
                                "Customer Update Form",
                                cf, null, cf.getClickListener())
                        );

                    } else {
                        Notification.show("User Rights Error", "You don't have rights to update customers !", Notification.Type.ERROR_MESSAGE);
                    }
                }

                if (action.equals(ACTION_CUSTOMER_BUSSINES_TYPE)) {
                    showCBTForm((Customer) source.getValue(), RolesPermissions.P_CUSTOMERS_EDIT_ALL);
                }

                if (action.equals(ACTION_CRM_ACTIVE_PROCESSES)) {
                    Customer c = (Customer) source.getValue();
                    if (DS.getCrmController().getCRM_Processes(c, false, null, null).size() < 1) {
                        Notification.show("Warning", "No CRM processes \nfor this customer !", Notification.Type.ERROR_MESSAGE);
                    }
                }

                if (action.equals(ACTION_CRM_NEW_PROCESS)) {
                    try {
                        if (MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_CRM_NEW_CRM_PROCESS)) {
                            Customer c = (Customer) source.getValue();

                            String infsysuser = MyUI.get().accessControl.getPrincipal();
                            InfSysUser iu = DS.getInfSysUserController().getByID(infsysuser);

                            Salesman s = DS.getInfSysUserController().getSalesman(iu);

                            CrmCase cs = DS.getCrmController().getCRM_LastActive_CRMCase(c, s);

                            getUI().addWindow(new WindowForm(
                                    Menu.MenuDefinitions.CRM_MANAG_NEW_PROCESS.toString(),
                                    false,
                                    new CRMCase_Form(cs, source)));
                        } else {
                            Notification.show("User Rights Error",
                                    "You don't have rights\nto add new CRM process ! ",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    } catch (NullPointerException | IllegalArgumentException e) {
                        Notification.show("Warning", e.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            }
        }
        );
    }

    public void showCBTForm(Customer customer, String permission) {
        if (MyUI.get().getAccessControl().isPermitted(permission)) {
            Tree cbtTree;

            try {
                cbtTree = new RELCBT_Tree("BUSSINES TYPES", customer);
            } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
                cbtTree = new Tree("No Customer Bussines Type.");
            }

            getUI().addWindow(new WindowFormProp(
                    "Customer Bussines Type Form",
                    false,
                    new RELCBTForm(customer), cbtTree));
        } else {
            Notification.show("User Rights Error",
                    "You don't have rights to add \nbussines type to this customers ! ",
                    Notification.Type.ERROR_MESSAGE);
        }
    }

}
