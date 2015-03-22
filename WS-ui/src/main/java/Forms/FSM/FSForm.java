package Forms.FSM;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import db.ent.City;
import db.ent.Fuelstation;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

public class FSForm extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(Fuelstation.class);
    private final BeanItemContainer<City> bicc = new BeanItemContainer(City.class,
            DS.getCityController().getAll());

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

        setTextFieldWidth(250, Unit.PIXELS);

        name.focus();
    }

    public FSForm(final CrudOperations crudOperation) {
        this();

        fieldGroup.setItemDataSource(new BeanItem(new Fuelstation()));
        beanItem = (BeanItem<Fuelstation>) fieldGroup.getItemDataSource();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    bindFieldsToBean(beanItem.getBean());

                    try {
                        fieldGroup.commit();
                        DS.getFSController().addNew(beanItem.getBean());

                        Notification n = new Notification("New Fuelstation Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);

            addBeansToForm();
        }
    }

    public FSForm(Fuelstation fuelstation, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(new BeanItem(fuelstation));
        beanItem = (BeanItem<Fuelstation>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                bindFieldsToBean(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    DS.getFSController().updateExisting(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("Fuelstation Data Updated.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);
        crudButton.setWidth(150, Unit.PIXELS);

        addBeansToForm();
    }

    private void bindFieldsToBean(Fuelstation FSBean) {
        FSBean.setName(name.getValue());
        FSBean.setAddress(address.getValue());
        FSBean.setFkIdc((City) city.getValue());
        FSBean.setCoordinates(coordinates.getValue());
    }

    private void addBeansToForm() {
        for (Component c : fieldGroup.getFields()) {
            if (c instanceof TextField) {
                TextField tf = (TextField) c;
                tf.setNullRepresentation("");
            }
            addComponent(c);
        }

        addComponents(crudButton);
    }

    private void setTextFieldWidth(float width, Sizeable.Unit unit) {
        for (Component c : fieldGroup.getFields()) {
            if (c instanceof TextField) {
                ((TextField) c).setWidth(width, unit);
            }
            if (c instanceof ComboBox) {
                ((ComboBox) c).setWidth(width, unit);
            }
            if (c instanceof DateField) {
                ((DateField) c).setWidth(width, unit);
            }
            if (c instanceof TextArea) {
                ((TextArea) c).setWidth(width, unit);
            }
        }
    }

}
