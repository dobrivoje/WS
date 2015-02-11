package org.superb.apps.ws.Views.MainMenu.FSDM;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.ws.db.controllers.FS_Controller;
import org.superb.apps.ws.db.entities.Fuelstation;

public class FSForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Fuelstation.class);
    private Button crudButton;
    private BeanItem<Fuelstation> beanItem;

    private Button.ClickListener clickListener;
    private String btnCaption;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("name")
    private final TextField name = new TextField("Fuelstation Name");

    @PropertyId("city")
    private final TextField city = new TextField("Fuelstation City");

    @PropertyId("address")
    private final TextField address = new TextField("Fuelstation Address");

    @PropertyId("coordinates")
    private final TextField coordinates = new TextField("Fuelstation Coordinates");
    //</editor-fold>

    public FSForm() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);

        fieldGroup.bindMemberFields(this);
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
                        Notification n = new Notification("Fuelstation Added.", Notification.Type.HUMANIZED_MESSAGE);
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
                Fuelstation fuelstationToUpdate = beanItem.getBean();
                bindFieldsToBean(fuelstationToUpdate);

                try {
                    new FS_Controller().updateExisting(fuelstationToUpdate);
                    visualContainer.refreshVisualContainer();
                    Notification n = new Notification("Customer Updated.", Notification.Type.HUMANIZED_MESSAGE);
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
        FSBean.setCity(city.getValue());
        FSBean.setCoordinates(coordinates.getValue());
    }
}
