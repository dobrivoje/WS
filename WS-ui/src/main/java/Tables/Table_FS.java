/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import ImageGallery.IDocumentGallery;
import ImageGallery.CustomerFuelStationsGallery;
import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
import Forms.FSM.Form_FS;
import Forms.FSM.Form_FSOwner;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.Action;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import db.ent.Fuelstation;
import java.util.List;
import org.dobrivoje.auth.roles.RolesPermissions;
import static org.dobrivoje.auth.roles.RolesPermissions.P_FUELSALES_USER_FS_NEW_OWNER;
import static org.dobrivoje.auth.roles.RolesPermissions.P_FUELSALES_USER_FS_NEW_STATION;
import org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp;
import Main.MyUI;
import static Main.MyUI.DS;

/**
 *
 * @author root
 */
public class Table_FS extends Table_GEN<Fuelstation> {

    private static final Action ACTION_FS_OWNER = new Action("New Fuelstation Owner");
    private static final Action ACTION_FS_UPDATE = new Action("Fuelstation Data Update");
    private static final Action ACTION_FS_PROPERTY = new Action("New Fuelstation Property");
    private static final Action ACTION_FS_IMG_GALLERY = new Action("Fuelstation Image Gallery");

    private IDocumentGallery IG;

    public Table_FS() {
        this(new BeanItemContainer<>(Fuelstation.class), DS.getFSController().getAll());

        IG = new CustomerFuelStationsGallery(UI.getCurrent().getUI(), Table_FS.this::refreshVisualContainer);
    }

    public Table_FS(BeanItemContainer<Fuelstation> BIC_FS, List list) {
        super(BIC_FS, DS.getFSController().getAll());

        addGeneratedColumn("options", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                HorizontalLayout optLayout = new HorizontalLayout();

                final Button editBtn = new Button("", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Fuelstation f = (Fuelstation) row;

                        Form_FS cf = new Form_FS(f, Table_FS.this::refreshVisualContainer, false);

                        getUI().addWindow(new WindowFormProp(
                                "Fuelstation Update Form",
                                false,
                                cf.getClickListener(),
                                cf,
                                IG.createDocumentGallery(f, true))
                        );
                    }
                });

                final Button ownerBtn = new Button("", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Fuelstation f = (Fuelstation) row;

                        Form_FSOwner fof = new Form_FSOwner(f, Table_FS.this::refreshVisualContainer, false);

                        getUI().addWindow(new WindowFormProp(
                                "Fuelstation Owner Form",
                                false,
                                fof.getClickListener(),
                                fof,
                                IG.createMainDocument(IG.createDocument(f, 240, 240))
                        ));
                    }
                });

                editBtn.setIcon(new ThemeResource("img/ico/fs.small.16x16.png"));
                editBtn.setDescription("Update this Fuelstation with new data...");
                editBtn.setEnabled(MyUI.get().isPermitted(P_FUELSALES_USER_FS_NEW_STATION));

                ownerBtn.setIcon(new ThemeResource("img/ico/icon-user-16x16.png"));
                ownerBtn.setDescription("Appoint this Fuelstation to Customer...");
                ownerBtn.setEnabled(MyUI.get().isPermitted(P_FUELSALES_USER_FS_NEW_OWNER));

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
                return IG.createDocument((Fuelstation) row, 80, 80);
            }
        });

        setVisibleColumns("name", "options", "img", "FK_City", "address");
        setColumnHeaders("FUEL STATION", "OPTIONS", "FS IMAGE", "CITY", "ADDRESS");

        setColumnWidth("options", 124);
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
                return new Action[]{ACTION_FS_OWNER, ACTION_FS_UPDATE, ACTION_FS_PROPERTY, ACTION_FS_IMG_GALLERY};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                final Table_FS source = (Table_FS) sender;
                Fuelstation f = (Fuelstation) source.getValue();

                String caption = "";
                Form_CRUD2 cf = null;

                // svima je dozvoljeno da gledaju galeriju stanica !
                if (action.equals(Table_FS.ACTION_FS_IMG_GALLERY)) {
                    try {
                        IG.openDocumentGalleryWindow("Fuelstation - " + f.getName(), f);
                    } catch (Exception e) {
                        Notification.show("Gallery Info Message", "Fuelstation Has No Images Yet.", Notification.Type.ERROR_MESSAGE);
                    }

                    // obavezno proveriti prava
                } else if (MyUI.get().isPermitted(RolesPermissions.P_FUELSTATIONS_MANAGEMENT)) {
                    if (action.equals(Table_FS.ACTION_FS_OWNER)) {
                        caption = "Fuelstation Owner Form";
                        cf = new Form_FSOwner(f, null, false);
                    }

                    if (action.equals(Table_FS.ACTION_FS_UPDATE)) {
                        caption = "Fuelstation Update Form";
                        cf = new Form_FS(f, null, false);
                    }

                    if (cf != null) {
                        getUI().addWindow(new WindowFormProp(
                                caption,
                                false,
                                cf.getClickListener(),
                                cf,
                                IG.createMainDocument(IG.createDocument(f, 240, 240))
                        )
                        );
                    }
                } else {
                    Notification.show("User Rights Error", "You don't have appropriate rights !", Notification.Type.ERROR_MESSAGE);
                }
            }
        }
        );
    }

    @Override
    public void refreshVisualContainer() {
        super.refreshVisualContainer(); //To change body of generated methods, choose Tools | Templates.
    }

}
