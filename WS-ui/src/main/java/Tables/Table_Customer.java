/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Forms.CDM.Form_Customer;
import Forms.CDM.Form_RELCBT;
import Forms.CRM.Form_CRMCase;
import static Uni.MainMenu.MenuDefinitions.CRM_MANAG_NEW_CASE;
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
import db.ent.City;
import db.ent.Customer;
import db.ent.Salesman;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Main.MyUI;
import static Main.MyUI.DS;
import org.superbapps.auth.roles.Roles;
import org.superbapps.utils.common.Enums.Statuses;
import org.superbapps.utils.common.Enums.ViewModes;
import static org.superbapps.utils.common.Enums.ViewModes.FULL;
import static org.superbapps.utils.common.Enums.ViewModes.LICENCE;
import static org.superbapps.utils.common.Enums.ViewModes.SIMPLE;
import org.superbapps.utils.vaadin.Exceptions.CustomTreeNodesEmptyException;
import org.superbapps.utils.vaadin.FancyLabels.StatusLabel;
import org.superbapps.utils.vaadin.MyWindows.WindowForm3;

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
        addGeneratedColumn("options", (final Table source, final Object row, Object column) -> {
            HorizontalLayout custOptionsHL = new HorizontalLayout();

            final Button editBtn = new Button("", (Button.ClickEvent event) -> {
                openForm((Customer) row, source, MyUI.get().isPermitted(Roles.P_WS_CUSTOMERS_MAINTENANCE));
            });

            final Button cbtypeBtn = new Button("", (Button.ClickEvent event) -> {
                showCBTForm((Customer) row,
                        MyUI.get().isPermitted(Roles.P_WS_CUSTOMERS_MAINTENANCE));
            });

            editBtn.setIcon(new ThemeResource("img/ico/icon-user-16x16.png"));
            editBtn.setEnabled(MyUI.get().isPermitted(Roles.P_WS_CUSTOMERS_MAINTENANCE));
            editBtn.setDescription("Update this customer with new data...");

            cbtypeBtn.setIcon(FontAwesome.THUMBS_O_UP);
            cbtypeBtn.setEnabled(MyUI.get().isPermitted(Roles.P_WS_CUSTOMERS_MAINTENANCE));
            cbtypeBtn.setDescription("Appoint this customer to a bussines type...");

            custOptionsHL.addComponents(editBtn, cbtypeBtn);
            custOptionsHL.setSizeFull();
            custOptionsHL.setSpacing(true);
            custOptionsHL.setComponentAlignment(editBtn, Alignment.MIDDLE_CENTER);
            custOptionsHL.setComponentAlignment(cbtypeBtn, Alignment.MIDDLE_CENTER);

            return custOptionsHL;
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
                    openForm((Customer) (source.getValue()), source, MyUI.get().hasRole(Roles.R_WS_CRM_MAINTENANCE));
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="ACTION_CUSTOMER_BUSSINES_TYPE">
                if (action.equals(ACTION_CUSTOMER_BUSSINES_TYPE)) {
                    showCBTForm(
                            (Customer) source.getValue(),
                            MyUI.get().hasRole(Roles.R_WS_CRM_MAINTENANCE));
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="ACTION_CRM_ACTIVE_PROCESSES">
                if (action.equals(ACTION_CRM_ACTIVE_PROCESSES) && MyUI.get().hasRole(Roles.R_WS_CRM_MAINTENANCE)) {
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
                        // Customer c = (Customer) source.getValue();
                        Salesman s;

                        s = MyUI.get().getLoggedSalesman();

                        Form_CRMCase ccf = new Form_CRMCase(s, true);

                        getUI().addWindow(new WindowForm3(
                                CRM_MANAG_NEW_CASE.toString(),
                                ccf,
                                530, 830, Unit.PIXELS,
                                "img/crm/crm-case-new.png", "Save",
                                ccf.getClickListener(), 253, 300,
                                MyUI.get().hasRole(Roles.R_WS_CRM_MAINTENANCE))
                        );

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
                    530, 830, Unit.PIXELS,
                    "img/crm/cbt.png", "Save",
                    rcf.getClickListener(), 202, 220, false)
            );

        } else {
            Notification.show("User Rights Error",
                    "You don't have rights to add \nbussines type to this customers ! ",
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    private void openForm(Customer c, Table source, boolean permitted) {
        if (MyUI.get().isPermitted(Roles.P_WS_CUSTOMERS_MAINTENANCE)) {
            Form_Customer cf = new Form_Customer(
                    c,
                    () -> {
                        source.markAsDirtyRecursive();
                    },
                    false);

            getUI().addWindow(new WindowForm3(
                    "Customer Update Form",
                    cf,
                    530, 830, Unit.PIXELS,
                    "img/crm/crm-user-3.png", "Save",
                    cf.getClickListener(), 205, 250, false)
            );

        } else {
            Notification.show("User Rights Error", "You don't have rights to update customers !", Notification.Type.ERROR_MESSAGE);
        }
    }
}
