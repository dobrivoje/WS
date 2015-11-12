/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Forms.CDM.Form_Customer;
import Forms.CDM.Form_RELCBT;
import Forms.CRM.Form_CRMCase;
import static Menu.MenuDefinitions.CRM_MANAG_NEW_CASE;
import Trees.Tree_RelCBT;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.City;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.dobrivoje.auth.roles.RolesPermissions;
import org.superb.apps.utilities.Enums.Statuses;
import org.superb.apps.utilities.Enums.ViewModes;
import static org.superb.apps.utilities.Enums.ViewModes.SIMPLE;
import org.superb.apps.utilities.vaadin.FancyLabels.StatusLabel;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm3;
import ws.MyUI;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Table_Customer extends Table_GEN<Customer> {

    private static final Action ACTION_CUSTOMER_UPDATE = new Action("Customer Data Update");
    private static final Action ACTION_CUSTOMER_BUSSINES_TYPE = new Action("Customer Bussines Type");
    private static final Action ACTION_CRM_ACTIVE_PROCESSES = new Action("CRM Activities");
    private static final Action ACTION_CRM_NEW_CASE = new Action("New Customer CRM Case");

    private Action.Handler actionHandler;

    public Table_Customer() {
        this(new BeanItemContainer<>(Customer.class), DS.getCustomerController().getAll());

        actionHandler = new Action.Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
            }
        };

        addActionHandler(actionHandler);
    }

    public Table_Customer(BeanItemContainer<Customer> beanContainer, List list) {
        super(beanContainer, list);

        tableColumnsID = new ArrayList(Arrays.asList(
                "navCode", "name", "licence", "options", "myCity", "zone", "matBr"));

        //<editor-fold defaultstate="collapsed" desc="Generisane kolone tabele,...">
        addGeneratedColumn("options", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                HorizontalLayout custOptionsHL = new HorizontalLayout();

                final Button editBtn = new Button("", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        openForm((Customer) row, source, MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
                    }
                });

                final Button cbtypeBtn = new Button("", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        showCBTForm((Customer) row,
                                MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
                    }
                });

                editBtn.setIcon(new ThemeResource("img/ico/icon-user-16x16.png"));
                editBtn.setEnabled(MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
                editBtn.setDescription("Update this customer with new data...");

                cbtypeBtn.setIcon(FontAwesome.THUMBS_O_UP);
                cbtypeBtn.setEnabled(MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
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
                City c = ((Customer) row).getFK_IDCity();
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
        setColumnWidth("options", 110);

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
        super.addActionHandler(actionHandler = new Action.Handler() {

            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{
                    ACTION_CUSTOMER_UPDATE,
                    ACTION_CUSTOMER_BUSSINES_TYPE,
                    ACTION_CRM_ACTIVE_PROCESSES,
                    ACTION_CRM_NEW_CASE
                };
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                final Table_Customer source = (Table_Customer) sender;

                //<editor-fold defaultstate="collapsed" desc="ACTION_CUSTOMER_UPDATE">
                if (action.equals(ACTION_CUSTOMER_UPDATE)) {
                    openForm((Customer) (source.getValue()), source, MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="ACTION_CUSTOMER_BUSSINES_TYPE">
                if (action.equals(ACTION_CUSTOMER_BUSSINES_TYPE)) {
                    showCBTForm(
                            (Customer) source.getValue(),
                            MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL));
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="ACTION_CRM_ACTIVE_PROCESSES">
                if (action.equals(ACTION_CRM_ACTIVE_PROCESSES)) {
                    Customer c = (Customer) source.getValue();
                    if (DS.getCRMController().getCRM_Processes(c, false, null, null) == null
                            || DS.getCRMController().getCRM_Processes(c, false, null, null).size() < 1) {
                        Notification.show("Warning", "No CRM activity \nfor this customer !", Notification.Type.ERROR_MESSAGE);
                    }
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="ACTION_CRM_NEW_CASE">
                if (action.equals(ACTION_CRM_NEW_CASE)) {
                    try {
                        if (MyUI.get().isPermitted(RolesPermissions.P_CRM_NEW_CRM_PROCESS)) {
                            Customer c = (Customer) source.getValue();
                            Salesman s;

                            s = MyUI.get().getLoggedSalesman();

                            Form_CRMCase ccf = new Form_CRMCase(s, true);

                            getUI().addWindow(new WindowForm3(
                                    CRM_MANAG_NEW_CASE.toString(),
                                    ccf,
                                    "img/crm/crm-case-new.png",
                                    ccf.getClickListener(),
                                    300, 253)
                            );

                        } else {
                            Notification.show("User Rights Error",
                                    "You don't have rights\nto add new CRM case ! ",
                                    Notification.Type.ERROR_MESSAGE);
                        }
                    } catch (NullPointerException | IllegalArgumentException e) {
                        Notification.show("Warning", e.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
                //</editor-fold>
            }
        });
    }

    public void showCBTForm(Customer customer, boolean formEditAllowed) {
        if (formEditAllowed) {
            Tree cbtTree;

            try {
                cbtTree = new Tree_RelCBT("BUSSINES TYPES", customer, formEditAllowed);
            } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
                cbtTree = new Tree("No Customer Bussines Type.");
            }

            Form_RELCBT rcf = new Form_RELCBT(customer, false);

            getUI().addWindow(new WindowForm3(
                    "New Customer Business Type",
                    rcf,
                    "img/crm/cbt.png",
                    rcf.getClickListener(),
                    220, 202)
            );
        } else {
            Notification.show("User Rights Error",
                    "You don't have rights to add \nbussines type to this customers ! ",
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    private void openForm(Customer c, Table source, boolean permitted) {
        if (MyUI.get().isPermitted(RolesPermissions.P_CUSTOMERS_EDIT_ALL)) {
            Form_Customer cf = new Form_Customer(
                    c,
                    () -> {
                        source.markAsDirtyRecursive();
                    },
                    false);

            getUI().addWindow(new WindowForm3(
                    "Customer Update Form",
                    cf,
                    "img/crm/crm-user-3.png",
                    cf.getClickListener(),
                    250, 205)
            );
        } else {
            Notification.show("User Rights Error", "You don't have rights to update customers !", Notification.Type.ERROR_MESSAGE);
        }
    }
}
