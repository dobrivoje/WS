package Forms.FSM;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import db.controllers.City_Controller;
import db.controllers.FS_Controller;
import db.ent.City;
import db.ent.Fuelstation;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public class FSForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Fuelstation.class);
    private final BeanItemContainer<City> bicc = new BeanItemContainer(City.class, new City_Controller().getAll());

    private Button crudButton;
    private BeanItem<Fuelstation> beanItem;

    private Button.ClickListener clickListener;
    private String btnCaption;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("name")
    private final TextField name = new TextField("Fuelstation Name");

    @PropertyId("fkIdc")
    private final ComboBox city = new ComboBox("City", bicc);

    @PropertyId("address")
    private final TextField address = new TextField("Address");

    @PropertyId("coordinates")
    private final TextField coordinates = new TextField("Coordinates");
    //</editor-fold>

    public FSForm() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);

        fieldGroup.bindMemberFields(this);

        name.setWidth(50, Unit.PERCENTAGE);
        city.setWidth(50, Unit.PERCENTAGE);
        address.setWidth(50, Unit.PERCENTAGE);
        coordinates.setWidth(50, Unit.PERCENTAGE);

        city.setNullSelectionAllowed(false);

        name.focus();
    }

    public FSForm(final CrudOperations crudOperation) {
        this();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Fuelstation newFuelstation = new Fuelstation();
                    bindFieldsToBean(newFuelstation);

                    try {
                        new FS_Controller().addNew(newFuelstation);
                        Notification n = new Notification("Fuelstation Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);

            crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
            addComponents(name, address, city, coordinates, crudButton);
        }
    }

    public FSForm(Item existingCustomer, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(existingCustomer);
        beanItem = (BeanItem<Fuelstation>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Fuelstation FSToUpdate = beanItem.getBean();
                bindFieldsToBean(FSToUpdate);

                try {
                    new FS_Controller().updateExisting(FSToUpdate);
                    visualContainer.refreshVisualContainer();
                    Notification n = new Notification("Fuelstation Updated.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);

        crudButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponents(name, address, city, coordinates, crudButton);
    }

    private void bindFieldsToBean(Fuelstation FSBean) {
        FSBean.setName(name.getValue());
        FSBean.setAddress(address.getValue());
        FSBean.setFkIdc((City) city.getValue());
        FSBean.setCoordinates(coordinates.getValue());
    }
}