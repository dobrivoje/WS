package Views.MainMenu.FSDM;

import Forms.FSPROP.FSPROP_Form;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import Tables.FSTable;
import Views.ResetButtonForTextField;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import db.ent.FsProp;
import db.ent.Fuelstation;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

public class FSView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "FS Search Engine View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();
    private final VerticalLayout propVL = new VerticalLayout();

    private final FSTable FS_Table = new FSTable();

    private Button newFS;

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
        newFS.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newFS.setIcon(FontAwesome.YOUTUBE_PLAY);
        newFS.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                // viewLogic.newProduct();
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth(100, Unit.PERCENTAGE);
        topLayout.addComponents(filter, newFS);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }
    //</editor-fold>

    private void openProperties(Fuelstation fs) {
        if (fs != null) {

            HL.setSplitPosition(60, Unit.PERCENTAGE);

            if (propVL.getComponentCount() > 0) {
                propVL.removeAllComponents();
            }

            FsProp fsProp = DS.getFSPROPController().getNewestFSPropForFS(fs);

            if (fsProp != null) {
                FSPROP_Form fspropForm = new FSPROP_Form(
                        new BeanItem(fsProp), new IRefreshVisualContainer() {
                            @Override
                            public void refreshVisualContainer() {
                                FS_Table.refreshVisualContainer();
                            }
                        });
                propVL.addComponent(fspropForm);
            }

        } else {
            propVL.removeAllComponents();
            HL.setSplitPosition(100, Unit.PERCENTAGE);
        }
    }
}
