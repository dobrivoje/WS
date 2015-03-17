package Forms.FSPROP;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Owner;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import static ws.MyUI.DATE_FORMAT;
import static ws.MyUI.DS;

public class FSPROP_Form extends FormLayout {

    private final FieldGroup fieldGroup = new BeanFieldGroup(FsProp.class);
    private Button crudButton;
    private BeanItem<FsProp> beanItem;
    private Owner currentOwner;

    private Button.ClickListener clickListener;
    private String btnCaption;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final TextField currentCustomerTxtField = new TextField("Current Customer");

    // @PropertyId("fkIdo")
    // private final ComboBox ownerComboBox = new ComboBox("Owner");
    @PropertyId("propertiesDate")
    private final DateField propertiesDate = new DateField("Properties Date");

    @PropertyId("noOfTanks")
    private final TextField noOfTanks = new TextField("No. of tanks");

    @PropertyId("truckCapable")
    private final TextField truckCapable = new TextField("Truck Capable");

    @PropertyId("restaurant")
    private final CheckBox restaurant = new CheckBox("Restaurant ?");

    @PropertyId("carWash")
    private final CheckBox carWash = new CheckBox("Car Wash ?");

    @PropertyId("compliance")
    private final TextField compliance = new TextField("Compliance");

    @PropertyId("licence")
    private final TextField licence = new TextField("Licence");

    @PropertyId("licDateFrom")
    private final DateField licDateFrom = new DateField("Licence From");

    @PropertyId("licDateTo")
    private final DateField licDateTo = new DateField("Licence To");

    @PropertyId("active")
    private final CheckBox active = new CheckBox("Active ?");
    //</editor-fold>

    public FSPROP_Form() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);

        fieldGroup.bindMemberFields(this);

        currentCustomerTxtField.setWidth(230, Unit.PIXELS);
        currentCustomerTxtField.setEnabled(false);

        for (Component c : fieldGroup.getFields()) {
            if (c instanceof DateField) {
                DateField df = (DateField) c;

                df.setWidth(150, Unit.PIXELS);
                df.setDateFormat(DATE_FORMAT);
            }

            c.setWidth(230, Unit.PIXELS);
        }

        noOfTanks.setNullRepresentation("");
        truckCapable.setNullRepresentation("");
        restaurant.setValue(false);
        carWash.setValue(false);
        compliance.setNullRepresentation("");
        licence.setNullRepresentation("");
    }

    public FSPROP_Form(final CrudOperations crudOperation) {
        this();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            btnCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    FsProp newFSProp = new FsProp();
                    bindFieldsToBean(newFSProp);

                    try {
                        DS.getFSPROPController().addNew(newFSProp);
                        Notification n = new Notification("New FS Property Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);
            currentCustomerTxtField.setWidth(230, Unit.PIXELS);

            addComponent(currentCustomerTxtField);
            for (Component c : fieldGroup.getFields()) {
                addComponents(c);
            }
            addComponents(active, crudButton);
        }
    }

    public FSPROP_Form(Fuelstation existingFS, boolean formFieldsEnabled, final IRefreshVisualContainer visualContainer) {
        this();

        currentOwner = DS.getFSOController().getCurrentFSOwner(existingFS);
        final FsProp fsProp = DS.getFSPROPController().getCurrentFSProp(currentOwner);

        fieldGroup.setItemDataSource(new BeanItem(fsProp != null ? fsProp : new FsProp()));
        beanItem = (BeanItem<FsProp>) fieldGroup.getItemDataSource();

        currentCustomerTxtField.setValue(currentOwner == null ? "This FS belongs to no one !" : currentOwner.getFKIDCustomer().getName());

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                bindFieldsToBean(fsProp);

                try {
                    DS.getFSPROPController().updateExisting(fsProp);
                    visualContainer.refreshVisualContainer();
                    Notification n = new Notification("FS Property Updated.", Notification.Type.TRAY_NOTIFICATION);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        crudButton = new Button(btnCaption, clickListener);

        addComponent(currentCustomerTxtField);

        for (Component c : fieldGroup.getFields()) {
            c.setEnabled(!formFieldsEnabled);
            addComponents(c);
        }

        crudButton.setEnabled(!formFieldsEnabled);
        addComponents(crudButton);
    }

    private void bindFieldsToBean(FsProp fsPropertyBean) {
        fsPropertyBean.setFkIdo(currentOwner);
        fsPropertyBean.setPropertiesDate(propertiesDate.getValue());

        try {
            fsPropertyBean.setNoOfTanks(Integer.valueOf(noOfTanks.getValue()));
        } catch (Exception e) {
            fsPropertyBean.setNoOfTanks(0);
        }

        try {
            fsPropertyBean.setTruckCapable(Integer.valueOf(truckCapable.getValue()));
        } catch (Exception e) {
            fsPropertyBean.setTruckCapable(0);
        }

        fsPropertyBean.setRestaurant((boolean) restaurant.getValue());
        fsPropertyBean.setCarWash((boolean) carWash.getValue());
        fsPropertyBean.setCompliance(compliance.getValue());
        fsPropertyBean.setLicence(licence.getValue());
        fsPropertyBean.setLicDateFrom(licDateFrom.getValue());
        fsPropertyBean.setLicDateTo(licDateTo.getValue());
        fsPropertyBean.setActive(active.getValue());
    }

    private void setComboBoxCaption(ComboBox comboBox, Fuelstation f) {
        Owner o;

        try {
            o = DS.getFSOController().getCurrentFSOwner(f);

            comboBox.removeAllItems();
            comboBox.setItemCaption(o, o.getFKIDCustomer().getName() + "->" + o.getFkIdFs().getName());
            comboBox.addItem(o);

        } catch (Exception e) {
            Notification.show(
                    "Error",
                    "This FS must have exaclty one active owner !\n"
                    + "Error description: " + e.toString(),
                    Notification.Type.ERROR_MESSAGE);
        }
    }
}
