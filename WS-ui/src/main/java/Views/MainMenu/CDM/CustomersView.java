package Views.MainMenu.CDM;

import Forms.CDM.CustomerForm;
import Forms.CDM.RELCBTForm;
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
import Tables.CustomerTable;
import Trees.FSOwnerTree;
import Trees.RELCBT_Tree;
import Views.ResetButtonForTextField;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import db.ent.Customer;
import static org.superb.apps.utilities.Enums.ViewModes.FULL;
import static org.superb.apps.utilities.Enums.ViewModes.SIMPLE;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm2;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public class CustomersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Customers View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();

    private final VerticalLayout vl1;
    private final VerticalLayout propVL = new VerticalLayout();

    private static final String[] propPanelsCaptions = new String[]{
        "Bussines Type(s)", "FS(s) Owned by this customer", "Customer Options "};
    private final Panel[] propPanels = new Panel[propPanelsCaptions.length];

    private final SaDesneStraneForm form = new SaDesneStraneForm();
    private final CustomerTable customersTable = new CustomerTable();

    private Button simpleViewMode;
    private Button fullViewMode;

    public CustomersView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">
        propVL.setSpacing(true);
        propVL.setMargin(true);

        createPropertyPanels();

        setSizeFull();
        addStyleName("crud-view");
        VL.setSizeFull();
        VL.setMargin(true);
        VL.setSpacing(true);
        HL.setSizeFull();
        HL.setSplitPosition(70, Unit.PERCENTAGE);
        HorizontalLayout topLayout = createTopBar();

        // kreiraj panel za tabelu i properies tabele :
        vl1 = new VerticalLayout(customersTable);
        vl1.setMargin(true);
        vl1.setSizeFull();
        HL.setFirstComponent(vl1);
        HL.setSecondComponent(propVL);

        VL.addComponent(topLayout);
        VL.addComponent(HL);
        VL.setSizeFull();
        VL.setExpandRatio(HL, 1);
        VL.setStyleName("crud-main-layout");
        addComponent(VL);
        //</editor-fold>

        customersTable.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Customer c = (Customer) customersTable.getValue();
                showPropForm(c);
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
        filter.setInputPrompt("search customer...");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                customersTable.setFilter(event.getText());
            }
        });

        simpleViewMode = new Button("Simple View");
        simpleViewMode.setWidth(150, Unit.PIXELS);
        simpleViewMode.setDescription("Basic Customer Information");
        simpleViewMode.focus();
        simpleViewMode.setIcon(FontAwesome.BRIEFCASE);
        simpleViewMode.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // getUI().addWindow(new WindowForm2(CUST_DATA_MANAG_NEW_CUST.toString(), new CustomerForm(CrudOperations.CREATE)));
                customersTable.setTablePerspective(SIMPLE);
            }
        });
        
        fullViewMode = new Button("Full View");
        fullViewMode.setWidth(150, Unit.PIXELS);
        fullViewMode.setDescription("Full Customer Information");
        fullViewMode.setIcon(FontAwesome.BUILDING);
        fullViewMode.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                customersTable.setTablePerspective(FULL);
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth(100, Unit.PERCENTAGE);
        topLayout.addComponents(filter, simpleViewMode, fullViewMode);
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

    private void showPropForm(Customer c) {
        if (c != null) {
            propPanels[0].setContent(new RELCBT_Tree("", c));
            propPanels[1].setContent(new FSOwnerTree("", c));
            propPanels[2].setContent(buildCustomerOptions(c, customersTable));

        } else {
            for (Panel p : propPanels) {
                p.setContent(new Label());
            }
        }
    }

    // Ovaj metod drastično ubrzava generisanje Customer tabele, zato što na klik na Customer-a
    // u tabeli generiše definisane dugmiće, umesto da se unapred za sve kupce generišu opcije !
    private Component buildCustomerOptions(final Customer c, final CustomerTable customersTable) {
        VerticalLayout VL1 = new VerticalLayout();

        HorizontalLayout HL1 = new HorizontalLayout();

        Button editBtn = new Button("u", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                CustomerForm customerForm = new CustomerForm(new BeanItem(c), new IRefreshVisualContainer() {
                    @Override
                    public void refreshVisualContainer() {
                        customersTable.refreshVisualContainer();
                    }
                });

                getUI().addWindow(new WindowForm2("Customer Update Form", customerForm));
            }
        });
        Button cbTapeBtn = new Button("t", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                RELCBT_Tree cbtTree = new RELCBT_Tree("BUSSINES TYPE(S)", c);
                RELCBTForm relCBT_Form = new RELCBTForm(c);

                getUI().addWindow(new WindowFormProp("Customer Bussines Type Form", false, relCBT_Form, cbtTree));
            }
        });

        editBtn.setDescription("Update this customer with new data...");
        cbTapeBtn.setDescription("Appoint this customer to a bussines type...");

        HL1.setSpacing(true);
        HL1.setMargin(true);
        HL1.addComponents(editBtn, cbTapeBtn);

        VL1.addComponents(HL1);

        return VL1;
    }

    private void createPropertyPanels() {
        for (int i = 0; i < propPanels.length; i++) {
            propPanels[i] = new Panel();
            propPanels[i].setHeight(167, Unit.PIXELS);
            propPanels[i].setCaption(propPanelsCaptions[i]);

            propVL.addComponent(propPanels[i]);
            propVL.setComponentAlignment(propPanels[i], Alignment.TOP_CENTER);
        }
    }
}
