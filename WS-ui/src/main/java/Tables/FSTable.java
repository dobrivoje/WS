/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Forms.FSM.FSForm;
import Forms.FSM.FSOWNER_Form;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import db.ent.Fuelstation;
import java.util.List;
import org.dobrivoje.auth.roles.RolesPermissions;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import ws.MyUI;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class FSTable extends GENTable<Fuelstation> {

    private static final Action ACTION_FS_UPDATE = new Action("Fuelstation Data Update");
    private static final Action ACTION_FS_OWNER = new Action("New Fuelstation Owner");
    private static final Action ACTION_FS_IMG_GALLERY = new Action("Fuelstation Image Gallery");

    public FSTable() {
        this(new BeanItemContainer<>(Fuelstation.class), DS.getFSController().getAll());
    }

    public FSTable(BeanItemContainer<Fuelstation> BIC_FS, List list) {
        super(BIC_FS, DS.getFSController().getAll());

        addGeneratedColumn("options", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                HorizontalLayout optLayout = new HorizontalLayout();

                final Button editBtn = new Button("u", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Fuelstation f = (Fuelstation) row;
                        FSForm customerForm = new FSForm(f, null);

                        getUI().addWindow(new WindowFormProp("Fuelstation Update Form", false, customerForm, createImageGallery(f)));
                    }
                });

                final Button ownerBtn = new Button("o", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Fuelstation f = (Fuelstation) row;
                        FSOWNER_Form fsoForm = new FSOWNER_Form(f, null);
                        getUI().addWindow(new WindowForm("Fuelstation Owner Form", false, fsoForm));
                    }
                });

                editBtn.setDescription("Update this Fuelstation with new data...");
                editBtn.setEnabled(MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_FUELSTATIONS_EDIT_ALL));

                ownerBtn.setDescription("Appoint this Fuelstation to Customer...");
                ownerBtn.setEnabled(MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_FUELSALES_USER_FS_NEW_OWNER));

                optLayout.addComponents(editBtn, ownerBtn);
                optLayout.setSizeFull();
                optLayout.setComponentAlignment(editBtn, Alignment.MIDDLE_CENTER);
                optLayout.setComponentAlignment(ownerBtn, Alignment.MIDDLE_CENTER);

                return optLayout;
            }
        });

        addGeneratedColumn("img", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                return createFSImage((Fuelstation) row, 80, 80);
            }
        });

        setVisibleColumns("name", "options", "img", "FK_City", "address");
        setColumnHeaders("FUEL STATION", "OPTIONS", "FS IMAGE", "CITY", "ADDRESS");

        setColumnWidth("options", 110);
        setColumnWidth("img", 90);
    }

    public void setFilter(String filterString) {
        beanContainer.removeAllContainerFilters();

        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "name", filterString, true, false);
            SimpleStringFilter cityFilter = new SimpleStringFilter(
                    "FK_City", filterString, true, false);
            SimpleStringFilter addressFilter = new SimpleStringFilter(
                    "address", filterString, true, false);

            beanContainer.addContainerFilter(new Or(nameFilter, cityFilter, addressFilter));
        }
    }

    @Override
    public void addActionHandler(Action.Handler actionHandler) {
        super.addActionHandler(new Action.Handler() {

            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{ACTION_FS_OWNER, ACTION_FS_UPDATE, ACTION_FS_IMG_GALLERY};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                final FSTable source = (FSTable) sender;
                Fuelstation f = (Fuelstation) source.getValue();
                String caption;

                if (action.equals(FSTable.ACTION_FS_OWNER)) {
                    caption = "Fuelstation Owner Form";

                    if (MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_FUELSALES_USER_FS_NEW_OWNER)) {
                        getUI().addWindow(new WindowForm(caption, false, new FSOWNER_Form(f, null)));
                    } else {
                        Notification.show("User Rights Error", "You don't have rights\nto change customer owner !", Notification.Type.ERROR_MESSAGE);
                    }
                }
                if (action.equals(FSTable.ACTION_FS_UPDATE)) {
                    caption = "Fuelstation Update Form";

                    if (MyUI.get().getAccessControl().isPermitted(RolesPermissions.P_FUELSTATIONS_EDIT_ALL)) {
                        getUI().addWindow(new WindowFormProp(caption, false, new FSForm(f, null), createImageGallery(f)));
                    } else {
                        Notification.show("User Rights Error", "You don't have rights\nto change customer owner !", Notification.Type.ERROR_MESSAGE);
                    }
                }
                if (action.equals(FSTable.ACTION_FS_IMG_GALLERY)) {
                    openFSGalleryWindow("Fuelstation - " + f.getName(), f);
                }
            }
        });
    }

}
