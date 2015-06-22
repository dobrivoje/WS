package Views.MainMenu.FSDM;

import Forms.FSM.FSOWNER_Form;
import Forms.FSPROP.FSPROP_Form;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dobrivoje.auth.roles.RolesPermissions;
import org.superb.apps.utilities.Enums.LOGS;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import ws.MyUI;
import static ws.MyUI.DS;

public class FSView extends VerticalLayout implements View {

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();
    private final VerticalLayout propVL = new VerticalLayout();

    private final FSTable FS_Table = new FSTable();
    private FSPROP_Form fSPROP_Form = null;

    private Button newFSPropButton;
    private Button newFSOButton;

    private final String UN = MyUI.get().getAccessControl().getPrincipal();

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

        // ubaciti getActionManager() kao parametar samo da bi se pokrenuo handler !
        // vrednost getActionManager() se ovde uopšte ne koristi !!!
        FS_Table.addActionHandler(getActionManager());

        addComponent(VL);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

    //<editor-fold defaultstate="collapsed" desc="createTopBar">
    public final HorizontalLayout createTopBar() {
        final TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("search fuel station...");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                FS_Table.setFilter(event.getText());

                try {
                    DS.getLOGController().addNew(
                            new Date(),
                            LOGS.DATA_SEARCH.toString(),
                            "User : " + UN + ", FS search: " + event.getText(),
                            DS.getINFSYSUSERController().getByID(MyUI.get().getAccessControl().getPrincipal()));
                } catch (Exception ex) {
                    Logger.getLogger(FSView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        newFSPropButton = new Button("New FS Property");
        newFSPropButton.setEnabled(MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_FUELSALES_USER_FS_NEW_PROPERTY));
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

        newFSOButton = new Button("New FS Owner");
        newFSOButton.setEnabled(MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_FUELSALES_USER_FS_NEW_OWNER));
        newFSOButton.setWidth(150, Unit.PIXELS);
        newFSOButton.setIcon(FontAwesome.BULLSEYE);
        newFSOButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Fuelstation f = (Fuelstation) FS_Table.getValue();

                if (f != null) {
                    getUI().addWindow(new WindowForm(
                            "New Fuelstation Owner",
                            false,
                            new FSOWNER_Form(f, null, false)
                    ));
                } else {
                    getUI().addWindow(new WindowForm(
                            FS_DATA_MANAG_NEW_FS_OWNER.toString(),
                            false,
                            new FSOWNER_Form(false))
                    );
                }
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth(100, Unit.PERCENTAGE);
        topLayout.addComponents(filter, newFSPropButton, newFSOButton);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="openProperties">
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
    //</editor-fold>
}
