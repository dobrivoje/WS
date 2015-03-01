package Views.MainMenu.FSDM;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import Tables.FSTable;
import Views.ResetButtonForTextField;
import db.ent.Fuelstation;

public class FSView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "FS Search Engine View";

    private final VerticalLayout VL = new VerticalLayout();
    private final HorizontalSplitPanel HL = new HorizontalSplitPanel();
    private final FSTable FS_Table;

    private VerticalLayout customersProperties;

    private Button newFS;

    public FSView() {
        //<editor-fold defaultstate="collapsed" desc="UI setup">
        FS_Table = new FSTable();

        setSizeFull();
        addStyleName("crud-view");

        VL.setSizeFull();
        VL.setMargin(true);
        VL.setSpacing(true);

        HL.setSizeFull();
        HL.setSplitPosition(70, Unit.PERCENTAGE);

        HorizontalLayout topLayout = createTopBar();
        // kreiraj panel za tabelu i properies tabele :
        VerticalLayout vl1 = new VerticalLayout(FS_Table);
        vl1.setMargin(true);
        vl1.setSizeFull();
        HL.setFirstComponent(vl1);

        VL.addComponent(topLayout);
        VL.addComponent(HL);
        VL.setSizeFull();
        VL.setExpandRatio(HL, 1);
        VL.setStyleName("crud-main-layout");

        addComponent(VL);
        //</editor-fold>

        FS_Table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                Fuelstation bean = (Fuelstation) event.getItemId();

                if (customersProperties != null) {
                    HL.removeComponent(customersProperties);
                }
                customersProperties = createProperties(bean, event);
                customersProperties.setMargin(true);
                HL.setSecondComponent(customersProperties);
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

    private VerticalLayout createProperties(Fuelstation bean, ItemClickEvent event) {
        VerticalLayout vl = new VerticalLayout();
        vl.setMargin(true);
        vl.setSpacing(true);

        vl.addComponent(new Label(bean == null ? "no data" : bean.toString()));
        vl.addComponent(new Label("item :" + event.getItem().toString() + ", getItemId" + event.getItemId().toString()));
        return vl;
    }
    //</editor-fold>
}
