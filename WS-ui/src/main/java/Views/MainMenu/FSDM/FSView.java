package Views.MainMenu.FSDM;

import Forms.FSM.FSForm;
import Forms.FSM.FSOWNER_Form;
import Forms.FSPROP.FSPROP_Form;
import static Menu.MenuDefinitions.FS_DATA_MANAG_NEW_FS;
import static Menu.MenuDefinitions.FS_DATA_MANAG_NEW_FS_OWNER;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import Tables.FSTable;
import Views.ResetButtonForTextField;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import db.ent.Fuelstation;
import org.superb.apps.utilities.Enums.CrudOperations;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public class FSView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "FS Search Engine View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();
    private final VerticalLayout propVL = new VerticalLayout();

    private final FSTable FS_Table = new FSTable();

    private Button newFS;
    private Button newFSO;

    public FSView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">
        setSizeFull();
        addStyleName("crud-view");
        VL.setSizeFull();
        VL.setMargin(true);
        VL.setSpacing(true);
        HL.setSizeFull();
        HL.setSplitPosition(100, Unit.PERCENTAGE);
        HorizontalLayout topLayout = createTopBar();

        // kreiraj panel za tabelu i properies tabele :
        VerticalLayout vl1 = new VerticalLayout(FS_Table);

        propVL.setMargin(true);
        propVL.setSpacing(true);

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

        FS_Table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Fuelstation fs = (Fuelstation) FS_Table.getValue();
                openProperties(fs);
            }
        });

        addComponent(VL);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    //<editor-fold defaultstate="collapsed" desc="createTopBar">
    public final HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("search fuel station...");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                FS_Table.setFilter(event.getText());
            }
        });

        newFS = new Button("New FS");
        newFS.setWidth(150, Unit.PIXELS);
        newFS.setIcon(FontAwesome.BRIEFCASE);
        newFS.focus();
        newFS.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().addWindow(new WindowForm(FS_DATA_MANAG_NEW_FS.toString(), false, new FSForm(CrudOperations.CREATE)));
            }
        });

        newFSO = new Button("New FS Owner");
        newFSO.setWidth(150, Unit.PIXELS);
        newFSO.setIcon(FontAwesome.BULLSEYE);
        newFSO.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().addWindow(new WindowForm(FS_DATA_MANAG_NEW_FS_OWNER.toString(), false, new FSOWNER_Form(CrudOperations.CREATE)));
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth(100, Unit.PERCENTAGE);
        topLayout.addComponents(filter, newFS, newFSO);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }
    //</editor-fold>

    private void openProperties(Fuelstation fs) {
        if (fs != null) {

            HL.setSplitPosition(50, Unit.PERCENTAGE);

            if (propVL.getComponentCount() > 0) {
                propVL.removeAllComponents();
            }

            propVL.addComponent(
                    new FSPROP_Form(fs, new IRefreshVisualContainer() {
                        @Override
                        public void refreshVisualContainer() {
                            FS_Table.refreshVisualContainer();
                        }
                    }));

        } else {
            propVL.removeAllComponents();
            HL.setSplitPosition(100, Unit.PERCENTAGE);
        }
    }
}
