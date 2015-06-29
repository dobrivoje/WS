package Forms.FSPROP;

import Forms.CRUDForm2;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.Date;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import static ws.MyUI.DS;

public class FSPROP_Form extends CRUDForm2<FsProp> {

    private Owner currentOwner;

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

    @PropertyId("comment")
    private final TextArea comment = new TextArea("Comment");

    @PropertyId("active")
    private final CheckBox active = new CheckBox("Active ?", true);
    //</editor-fold>

    public FSPROP_Form() {
        super(new BeanFieldGroup(FsProp.class));

        fieldGroup.bindMemberFields(this);
        setFormFieldsWidths(200, Unit.PIXELS);

        initFields();
    }

    public FSPROP_Form(Fuelstation fuelstation, boolean formFieldsLocked, boolean crudButtonOnForm) {
        this();

        currentOwner = DS.getFSOController().getCurrentFSOwner(fuelstation);
        final FsProp currentFsProp = DS.getFSPROPController().getCurrentFSProp(currentOwner);
        final FsProp newFsProp = currentFsProp != null ? new FsProp(currentFsProp) : new FsProp();

        if (!formFieldsLocked) {
            newFsProp.setPropertiesDate(new Date());
            newFsProp.setComment("");
            newFsProp.setActive(true);
        }

        currentCustomerTxtField.setValue(currentOwner == null
                ? "This FS has NO owner!" : currentOwner.getFKIDCustomer().getName());

        fieldGroup.setItemDataSource(new BeanItem(newFsProp));
        beanItem = (BeanItem<FsProp>) fieldGroup.getItemDataSource();

        addComponent(currentCustomerTxtField);

        for (Component c : fieldGroup.getFields()) {
            if (!(c instanceof CheckBox && ((CheckBox) c).equals(active))) {
                c.setEnabled(!formFieldsLocked);
            } else {
                c.setEnabled(false);
                ((CheckBox) c).setValue(true);
            }
            addComponents(c);
        }

        if (crudButtonOnForm) {
            btnCaption = BUTTON_CAPTION_SAVE.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    setBeanFromFields(beanItem.getBean());

                    try {
                        fieldGroup.commit();

                        if (currentFsProp != null) {
                            currentFsProp.setActive(false);
                            DS.getFSPROPController().updateExisting(currentFsProp);
                        }

                        DS.getFSPROPController().addNew(beanItem.getBean());

                        Notification n = new Notification("New FS Property Added.", Notification.Type.TRAY_NOTIFICATION);
                        n.setDelayMsec(500);
                        n.show(getUI().getPage());

                    } catch (FieldGroup.CommitException ex) {
                        Notification.show("Error", "Fields indicated by red stars, must be provided.", Notification.Type.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
                    }
                }
            };

            crudButton = new Button(btnCaption, clickListener);
            crudButton.setWidth(150, Unit.PIXELS);

            addComponents(crudButton);
        }

    }

    @Override
    protected void setBeanFromFields(FsProp fsPropertyBean) {
        fsPropertyBean.setFkIdo(currentOwner);
        fsPropertyBean.setPropertiesDate(propertiesDate.getValue());

        try {
            fsPropertyBean.setNoOfTanks(Integer.valueOf(noOfTanks.getValue()));
        } catch (Exception ex) {
            fsPropertyBean.setNoOfTanks(0);
        }

        try {
            fsPropertyBean.setTruckCapable(Integer.valueOf(truckCapable.getValue()));
        } catch (Exception ex) {
            fsPropertyBean.setTruckCapable(0);
        }

        try {
            fsPropertyBean.setRestaurant((boolean) restaurant.getValue());
        } catch (Exception ex) {
            fsPropertyBean.setRestaurant(false);
        }

        try {
            fsPropertyBean.setCarWash((boolean) carWash.getValue());
        } catch (Exception ex) {
            fsPropertyBean.setCarWash(false);
        }

        fsPropertyBean.setCompliance(compliance.getValue());
        fsPropertyBean.setComment(comment.getValue());
        fsPropertyBean.setLicence(licence.getValue());
        fsPropertyBean.setLicDateFrom(licDateFrom.getValue());
        fsPropertyBean.setLicDateTo(licDateTo.getValue());

        fsPropertyBean.setActive(true);
    }

    @Override
    public void setFieldsFromBean(Object o) {
        if (o instanceof FsProp) {
            FsProp fsp = (FsProp) o;

            currentOwner = fsp.getFkIdo();

            propertiesDate.setValue(fsp.getPropertiesDate());

            try {
                noOfTanks.setValue(Integer.toString(fsp.getNoOfTanks()));
            } catch (Exception ex) {
            }

            try {
                truckCapable.setValue(Integer.toString(fsp.getTruckCapable()));
            } catch (Exception ex) {
            }

            try {
                restaurant.setValue(fsp.getRestaurant());
            } catch (Property.ReadOnlyException | Converter.ConversionException ex) {
            }

            try {
                carWash.setValue(fsp.getCarWash());
            } catch (Property.ReadOnlyException | Converter.ConversionException ex) {
            }

            compliance.setValue(fsp.getCompliance());
            comment.setValue(fsp.getComment());
            licence.setValue(fsp.getLicence());
            licDateFrom.setValue(fsp.getLicDateFrom());
            licDateTo.setValue(fsp.getLicDateTo());
        }
    }

    @Override
    protected final void updateDynamicFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected final void initFields() {
        currentCustomerTxtField.setWidth(200, Unit.PIXELS);
        currentCustomerTxtField.setEnabled(false);

        restaurant.setValue(false);
        carWash.setValue(false);
        comment.setRows(5);

        // postavi validatore
        noOfTanks.setConverter(new StringToIntegerConverter());
        noOfTanks.setConversionError("Must be natural number !");
        truckCapable.setConverter(new StringToIntegerConverter());
        truckCapable.setConversionError("Must be natural number !");

        // sprečiti korisnika da menja vrednost, jer pogrešne vrednosti ovog polja
        // mogu napraviti problem sa prikazom u formi !
        active.setEnabled(false);
    }

    @Override
    protected void setRequiredFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
