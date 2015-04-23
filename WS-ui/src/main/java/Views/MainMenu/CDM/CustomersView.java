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
import Tables.CustomerTable;
import Trees.CRM_CurrCustProcesses_Tree;
import Trees.FSOwner_Tree;
import Trees.RELCBT_Tree;
import Views.ResetButtonForTextField;
import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superb.apps.utilities.Enums.LOGS;
import static org.superb.apps.utilities.Enums.ViewModes.FULL;
import static org.superb.apps.utilities.Enums.ViewModes.SIMPLE;
import ws.MyUI;
import static ws.MyUI.DS;

public class CustomersView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "Customers View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();

    private final VerticalLayout vl1;
    private final VerticalLayout propVL = new VerticalLayout();

    private static final String[] propPanelsCaptions = new String[]{
        "Bussines Type(s)", "FS(s) Owned by this customer", "Customer's CRM process(es)"};
    private final Panel[] propPanels = new Panel[propPanelsCaptions.length];

    private final SaDesneStraneForm form = new SaDesneStraneForm();
    private final CustomerTable customersTable = new CustomerTable();

    private Button simpleViewMode;
    private Button fullViewMode;

    private final String UN = MyUI.get().getAccessControl().getPrincipal();

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

        // ubaciti getActionManager() kao parametar samo da bi se pokrenuo handler !
        // vrednost getActionManager() se ovde uopšte ne koristi !!!
        customersTable.addActionHandler(getActionManager());

        //</editor-fold>
        addComponent(VL);
    }
    //<editor-fold defaultstate="collapsed" desc="Customer Table - Double click - Customer Form">

    @Override
    public void enter(ViewChangeEvent event) {
    }

    //<editor-fold defaultstate="collapsed" desc="createTopBar">
    public final HorizontalLayout createTopBar() {
        final TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("search customer...");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                customersTable.setFilter(event.getText());

                try {
                    DS.getLogController().addNew(
                            new Date(),
                            LOGS.DATA_SEARCH.toString(),
                            "User : " + UN + ", Customer search: " + event.getText(),
                            DS.getInfSysUserController().getByID(UN)
                    );
                } catch (Exception ex) {
                    Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            try {
                propPanels[0].setContent(new RELCBT_Tree("", c));
            } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
                propPanels[0].setContent(new Tree());
            }

            try {
                propPanels[1].setContent(new FSOwner_Tree("", c, true));
            } catch (CustomTreeNodesEmptyException | NullPointerException ex) {
                propPanels[1].setContent(new Tree());
            }

            try {
                propPanels[2].setContent(new CRM_CurrCustProcesses_Tree("", c));
            } catch (CustomTreeNodesEmptyException | NullPointerException e) {
                propPanels[2].setContent(new Tree());
            }

        } else {
            for (Panel p : propPanels) {
                p.setContent(new Label());
            }
        }
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
