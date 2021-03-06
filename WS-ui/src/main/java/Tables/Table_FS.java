/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Gallery.common.IDocumentGallery;
import Gallery.Image.FS.CustomerFuelStationsGallery;
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
import org.superbapps.utils.vaadin.MyWindows.WindowFormProp;
import Main.MyUI;
import static Main.MyUI.DS;
import org.superbapps.auth.roles.Roles;
import org.superbapps.utils.vaadin.Forms.Form_CRUD2;

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

        addGeneratedColumn("options", (final Table source, final Object row, Object column) -> {
            HorizontalLayout optLayout = new HorizontalLayout();

            final Button editBtn = new Button("", (Button.ClickEvent event) -> {
                Fuelstation f = (Fuelstation) row;

                Form_FS cf = new Form_FS(f, Table_FS.this::refreshVisualContainer, false);

                getUI().addWindow(
                        new WindowFormProp(
                                "Fuelstation Update Form",
                                500, 760, Unit.PIXELS,
                                false,
                                cf.getClickListener(),
                                cf,
                                IG.createDocumentGallery(f, true)
                        )
                );

            });

            final Button ownerBtn = new Button("", (Button.ClickEvent event) -> {
                Fuelstation f = (Fuelstation) row;

                Form_FSOwner fof = new Form_FSOwner(f, Table_FS.this::refreshVisualContainer, false);

                getUI().addWindow(
                        new WindowFormProp(
                                "Fuelstation Owner Form",
                                500, 760, Unit.PIXELS,
                                false,
                                fof.getClickListener(),
                                fof,
                                IG.createMainDocLayout(IG.createDocument(f, 240, 240, false))
                        )
                );
            });

            editBtn.setIcon(new ThemeResource("img/ico/fs.small.16x16.png"));
            editBtn.setDescription("Update this Fuelstation with new data...");
            editBtn.setEnabled(MyUI.get().hasRole(Roles.R_FS_MAINTENANCE));

            ownerBtn.setIcon(new ThemeResource("img/ico/icon-user-16x16.png"));
            ownerBtn.setDescription("Appoint this Fuelstation to Customer...");
            ownerBtn.setEnabled(MyUI.get().hasRole(Roles.R_FS_MAINTENANCE));

            optLayout.addComponents(editBtn, ownerBtn);
            optLayout.setSizeFull();
            optLayout.setComponentAlignment(editBtn, Alignment.MIDDLE_CENTER);
            optLayout.setComponentAlignment(ownerBtn, Alignment.MIDDLE_CENTER);

            return optLayout;
        });

        addGeneratedColumn("img", (final Table source, final Object row, Object column)
                -> IG.createDocument((Fuelstation) row, 80, 80, false)
        );

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
                } else if (MyUI.get().hasRole(Roles.R_FS_MAINTENANCE)) {
                    if (action.equals(Table_FS.ACTION_FS_OWNER)) {
                        caption = "Fuelstation Owner Form";
                        cf = new Form_FSOwner(f, null, false);
                    }

                    if (action.equals(Table_FS.ACTION_FS_UPDATE)) {
                        caption = "Fuelstation Update Form";
                        cf = new Form_FS(f, null, false);
                    }

                    if (cf != null) {

                        getUI().addWindow(
                                new WindowFormProp(
                                        caption,
                                        500, 760, Unit.PIXELS,
                                        false,
                                        cf.getClickListener(),
                                        cf,
                                        IG.createMainDocLayout(IG.createDocument(f, 240, 240, false))
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
