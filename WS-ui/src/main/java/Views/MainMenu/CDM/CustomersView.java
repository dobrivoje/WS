package Views.MainMenu.CDM;

import Forms.CDM.CustomerForm;
import Forms.FSM.FSOWNER_Form;
import Forms.SaDesneStraneForm;
import static Menu.MenuDefinitions.CUST_DATA_MANAG_NEW_CUST;
import static Menu.MenuDefinitions.FS_DATA_MANAG_NEW_FS_OWNER;
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
import Tables.CustomerTable;
import Trees.FSOwnerTree;
import Trees.RELCBT_Tree;
import Views.ResetButtonForTextField;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import db.ent.Customer;
import org.superb.apps.utilities.Enums.CrudOperations;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;

public class CustomersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Customers View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();

    private final VerticalLayout vl1;
    private final VerticalLayout propVL = new VerticalLayout();

    private static final String[] propPanelsCaptopns = new String[]{
        "Bussines Type(s)", "FS(s) Owned by this customer", "Licences "};
    private final Panel[] propPanels = new Panel[propPanelsCaptopns.length];

    private final SaDesneStraneForm form = new SaDesneStraneForm();
    private final CustomerTable customersTable = new CustomerTable();

    private Button newCustomer;
    private Button newCustomerOwnerFS;

    public CustomersView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">

        propVL.setSpacing(true);
        propVL.setMargin(true);

        for (int i = 0; i < propPanels.length; i++) {
            propPanels[i] = new Panel();
            propPanels[i].setHeight(167, Unit.PIXELS);
            propPanels[i].setCaption(propPanelsCaptopns[i]);

            propVL.addComponent(propPanels[i]);
            propVL.setComponentAlignment(propPanels[i], Alignment.TOP_CENTER);
        }

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

        newCustomer = new Button("New Customer");
        newCustomer.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newCustomer.setIcon(FontAwesome.YOUTUBE_PLAY);
        newCustomer.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().addWindow(new WindowForm(CUST_DATA_MANAG_NEW_CUST.toString(), true, new CustomerForm(CrudOperations.CREATE)));
            }
        });

        newCustomerOwnerFS = new Button("New FS Owner");
        newCustomerOwnerFS.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newCustomerOwnerFS.setIcon(FontAwesome.YOUTUBE_PLAY);
        newCustomerOwnerFS.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().addWindow(new WindowForm(FS_DATA_MANAG_NEW_FS_OWNER.toString(), false, new FSOWNER_Form(CrudOperations.CREATE)));
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

    private void showPropForm(Customer c) {
        if (c != null) {
            propPanels[0].setContent(new RELCBT_Tree("", c));
            propPanels[1].setContent(new FSOwnerTree("", c));
        } else {
            for (Panel p : propPanels) {
                p.setContent(new Label());
            }
        }
    }
}
