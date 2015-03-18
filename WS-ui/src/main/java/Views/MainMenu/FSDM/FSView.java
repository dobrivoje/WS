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
import static ws.MyUI.DS;

public class FSView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "FS Search Engine View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();
    private final VerticalLayout propVL = new VerticalLayout();

    private final FSTable FS_Table = new FSTable();
    private FSPROP_Form fSPROP_Form = null;

    private Button newFSPropButton;
    private Button newFSButton;
    private Button newFSOButton;

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
                openProperties(fs, true, false);
            }
        });

        addComponent(VL);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    public FSPROP_Form getfSPROP_Form() {
        return fSPROP_Form;
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

        newFSPropButton = new Button("New FS Property");
        newFSPropButton.setEnabled(false);
        newFSPropButton.setWidth(170, Unit.PIXELS);
        newFSPropButton.setIcon(FontAwesome.ARCHIVE);
        newFSPropButton.focus();
        newFSPropButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Fuelstation f = (Fuelstation) FS_Table.getValue();
                openProperties(f, false, true);
            }
        });

        newFSButton = new Button("New FS");
        newFSButton.setWidth(150, Unit.PIXELS);
        newFSButton.setIcon(FontAwesome.BRIEFCASE);
        newFSButton.focus();
        newFSButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().addWindow(new WindowForm(FS_DATA_MANAG_NEW_FS.toString(), false, new FSForm(CrudOperations.CREATE)));
            }
        });

        newFSOButton = new Button("New FS Owner");
        newFSOButton.setWidth(150, Unit.PIXELS);
        newFSOButton.setIcon(FontAwesome.BULLSEYE);
        newFSOButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Fuelstation f = (Fuelstation) FS_Table.getValue();
                FSOWNER_Form fsoForm;

                if (f != null) {
                    fsoForm = new FSOWNER_Form(f, new IRefreshVisualContainer() {
                        @Override
                        public void refreshVisualContainer() {
                            FS_Table.markAsDirtyRecursive();
                        }
                    });
                    getUI().addWindow(new WindowForm("FS Owner Form", false, fsoForm));
                } else {
                    getUI().addWindow(new WindowForm(FS_DATA_MANAG_NEW_FS_OWNER.toString(), false, new FSOWNER_Form(CrudOperations.CREATE)));
                }
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth(100, Unit.PERCENTAGE);
        topLayout.addComponents(filter, newFSPropButton, newFSButton, newFSOButton);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }
    //</editor-fold>

    public void openProperties(Fuelstation fs, boolean formFieldsLocked, boolean crudButtonOnForm) {
        if (fs != null) {
            newFSPropButton.setEnabled(DS.getFSOController().getCurrentFSOwner(fs) != null);
            HL.setSplitPosition(50, Unit.PERCENTAGE);

            if (propVL.getComponentCount() > 0) {
                propVL.removeAllComponents();
            }

            fSPROP_Form = new FSPROP_Form(fs, formFieldsLocked, crudButtonOnForm);

            propVL.addComponent(fSPROP_Form);

        } else {
            fSPROP_Form = null;
            newFSPropButton.setEnabled(false);
            propVL.removeAllComponents();
            HL.setSplitPosition(100, Unit.PERCENTAGE);
        }
    }
}
