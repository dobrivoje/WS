package Forms.FSM;

import Forms.CRUDForm2;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import db.ent.City;
import db.ent.Fuelstation;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DS;

public class FSForm extends CRUDForm2<Fuelstation> {

    private final BeanItemContainer<City> bicc = new BeanItemContainer(City.class,
            DS.getCityController().getAll());

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    @PropertyId("name")
    private final TextField name = new TextField("Fuelstation Name");

    @PropertyId("FK_City")
    private final ComboBox city = new ComboBox("City", bicc);

    @PropertyId("address")
    private final TextField address = new TextField("Address");

    @PropertyId("coordinates")
    private final TextField coordinates = new TextField("Coordinates");
    //</editor-fold>

    public FSForm() {
        super(new BeanFieldGroup(Fuelstation.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(250, Unit.PIXELS);
        name.focus();

        name.setRequired(true);
        city.setRequired(true);
    }

    public FSForm(final CrudOperations crudOperation) {
        this(crudOperation, false);
    }

    public FSForm(final CrudOperations crudOperation, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(new Fuelstation()));
        beanItem = (BeanItem<Fuelstation>) fieldGroup.getItemDataSource();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_SAVE.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    setBeanFromFields(beanItem.getBean());

                    try {
                        fieldGroup.commit();
                        DS.getFSController().addNew(beanItem.getBean());

                        Notification n = new Notification("New Fuelstation Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());

                    } catch (FieldGroup.CommitException ex) {
                        Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            addBeansToForm();
        }
    }

    public FSForm(Fuelstation fuelstation, final IRefreshVisualContainer visualContainer) {
        this(fuelstation, visualContainer, true);
    }

    public FSForm(Fuelstation fuelstation, final IRefreshVisualContainer visualContainer, boolean defaultCRUDButtonOnForm) {
        this();

        this.defaultCRUDButtonOnForm = defaultCRUDButtonOnForm;

        fieldGroup.setItemDataSource(new BeanItem(fuelstation));
        beanItem = (BeanItem<Fuelstation>) fieldGroup.getItemDataSource();

        btnCaption = BUTTON_CAPTION_SAVE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setBeanFromFields(beanItem.getBean());

                try {
                    fieldGroup.commit();

                    DS.getFSController().updateExisting(beanItem.getBean());

                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }

                    Notification n = new Notification("Fuelstation Data Updated.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());

                } catch (FieldGroup.CommitException ex) {
                    Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        addBeansToForm();
    }

    @Override
    protected void setBeanFromFields(Fuelstation FSBean) {
        FSBean.setName(name.getValue());
        FSBean.setAddress(address.getValue());
        FSBean.setFK_City((City) city.getValue());
        FSBean.setCoordinates(coordinates.getValue());
    }

    @Override
    protected void setFieldsFromBean(Fuelstation t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected final void updateDynamicFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
