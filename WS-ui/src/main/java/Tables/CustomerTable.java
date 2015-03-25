/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Forms.CDM.CustomerForm;
import Forms.CDM.RELCBTForm;
import Trees.RELCBT_Tree;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import db.ent.City;
import db.ent.Customer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.superb.apps.utilities.Enums.Statuses;
import org.superb.apps.utilities.Enums.ViewModes;
import static org.superb.apps.utilities.Enums.ViewModes.SIMPLE;
import org.superb.apps.utilities.vaadin.FancyLabels.StatusLabel;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm2;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CustomerTable extends GENTable<Customer> {

    private static final Action ACTION_CUSTOMER_UPDATE = new Action("Customer Data Update");
    private static final Action ACTION_CUSTOMER_BUSSINES_TYPE = new Action("Customer Bussines Type");

    public CustomerTable() {
        this(new BeanItemContainer<>(Customer.class), DS.getCustomerController().getAll());
    }

    public CustomerTable(BeanItemContainer<Customer> beanContainer, List list) {
        super(beanContainer, list);

        tableColumnsID = new ArrayList(Arrays.asList(
                "navCode", "name", "licence", "options", "myCity", "zone", "matBr"));

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

                        getUI().addWindow(new WindowForm2("Customer Update Form", cf));
                    }
                });
                final Button cbTapeBtn = new Button("t", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Customer c = (Customer) row;

                        RELCBT_Tree cbtTree = new RELCBT_Tree("BUSSINES TYPE(S)", c);
                        RELCBTForm relCBT_Form = new RELCBTForm(c);

                        getUI().addWindow(new WindowFormProp("Customer Bussines Type Form", false, relCBT_Form, cbtTree));
                    }
                });

                editBtn.setDescription("Update this customer with new data...");
                cbTapeBtn.setDescription("Appoint this customer to a bussines type...");

                custOptionsHL.addComponents(editBtn, cbTapeBtn);
                custOptionsHL.setSizeFull();
                custOptionsHL.setSpacing(true);
                custOptionsHL.setComponentAlignment(editBtn, Alignment.MIDDLE_CENTER);
                custOptionsHL.setComponentAlignment(cbTapeBtn, Alignment.MIDDLE_CENTER);

                return custOptionsHL;
            }
        });

        addGeneratedColumn("licence", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table source, Object row, Object column) {
                Property property = source.getItem(row).getItemProperty(column);
                Statuses s = Statuses.UNKNOWN;

                if (property != null) {
                    s = (boolean) property.getValue() ? Statuses.OK : Statuses.NO_LICENCE;
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
                    } catch (Exception e) {
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
                    } catch (Exception e) {
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
                    } catch (Exception e) {
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
    public void addActionHandler(Action.Handler actionHandler) {
        super.addActionHandler(new Action.Handler() {

            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{ACTION_CUSTOMER_UPDATE, ACTION_CUSTOMER_BUSSINES_TYPE};
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

                    getUI().addWindow(new WindowForm2("Customer Update Form", cf));
                } else {
                    Customer c = (Customer) source.getValue();

                    RELCBT_Tree cbtTree = new RELCBT_Tree("BUSSINES TYPE(S)", c);
                    RELCBTForm relCBT_Form = new RELCBTForm(c);

                    getUI().addWindow(new WindowFormProp("Customer Bussines Type Form", false, relCBT_Form, cbtTree));
                }
            }
        });
    }

}
