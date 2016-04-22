package Views.FSDM;

import Forms.FSM.Form_FSOwner;
import Forms.FSPROP.Form_FSProp;
import Gallery.Image.FS.CustomerFuelStationsGallery;
import Gallery.IDocumentGallery;
import Main.MyUI;
import static Main.MyUI.DS;
import static Uni.MainMenu.MenuDefinitions.FS_DATA_MANAG_NEW_FS_OWNER;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import Tables.Table_FS;
import Views.ResetButtonForTextField;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.UI;
import db.ent.Fuelstation;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superbapps.auth.roles.Roles;
import org.superbapps.utils.vaadin.MyWindows.WindowFormProp;
import org.superbapps.utils.common.Enums.LOGS;

public class View_FS extends VerticalLayout implements View {

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();
    private final VerticalLayout propVL = new VerticalLayout();

    private final Table_FS FS_Table = new Table_FS();
    private Form_FSProp fSPROP_Form = null;

    private Button newFSPropButton;
    private Button newFSOButton;

    public View_FS() {
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

        FS_Table.addValueChangeListener((Property.ValueChangeEvent event) -> {
            Fuelstation fs = (Fuelstation) FS_Table.getValue();
            openProperties(fs, true, false);
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
        filter.addTextChangeListener((FieldEvents.TextChangeEvent event) -> {
            FS_Table.setFilter(event.getText());

            try {
                DS.getLOGController().addNew(
                        new Date(),
                        LOGS.DATA_SEARCH.toString(),
                        "User : " + MyUI.get().getLoggedISUser().getUserName() + ", FS search: " + event.getText(),
                        MyUI.get().getLoggedISUser());
            } catch (Exception ex) {
                Logger.getLogger(View_FS.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        newFSPropButton = new Button("New FS Property");
        newFSPropButton.setEnabled(MyUI.get().hasRole(Roles.R_FS_MAINTENANCE));
        newFSPropButton.setWidth(170, Unit.PIXELS);
        newFSPropButton.setIcon(FontAwesome.ARCHIVE);
        newFSPropButton.focus();
        newFSPropButton.addClickListener((Button.ClickEvent event) -> {
            Fuelstation f = (Fuelstation) FS_Table.getValue();
            openProperties(f, false, true);
        });

        newFSOButton = new Button("New FS Owner");
        newFSOButton.setEnabled(MyUI.get().hasRole(Roles.R_FS_MAINTENANCE));
        newFSOButton.setWidth(150, Unit.PIXELS);
        newFSOButton.setIcon(FontAwesome.BULLSEYE);
        newFSOButton.addClickListener((Button.ClickEvent event) -> {
            Fuelstation f = (Fuelstation) FS_Table.getValue();
            IDocumentGallery IG = new CustomerFuelStationsGallery(UI.getCurrent().getUI(), null);

            Form_FSOwner fof;
            String msg;

            if (f != null) {
                fof = new Form_FSOwner(f, null, false);
                msg = "New Fuelstation Owner";
            } else {
                fof = new Form_FSOwner(false);
                msg = FS_DATA_MANAG_NEW_FS_OWNER.toString();
            }

            getUI().addWindow(new WindowFormProp(
                    msg,
                    false,
                    fof.getClickListener(),
                    fof,
                    IG.createMainDocument(IG.createDocument(f, 240, 240))
            ));
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
    public final void openProperties(Fuelstation fs, boolean formFieldsLocked, boolean crudButtonOnForm) {
        if (fs != null) {
            newFSPropButton.setEnabled(MyUI.get().hasRole(Roles.R_FS_MAINTENANCE));
            HL.setSplitPosition(50, Unit.PERCENTAGE);

            if (propVL.getComponentCount() > 0) {
                propVL.removeAllComponents();
            }

            fSPROP_Form = new Form_FSProp(fs, formFieldsLocked, crudButtonOnForm);

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
