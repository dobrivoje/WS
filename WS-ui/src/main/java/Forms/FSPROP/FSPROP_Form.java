package Forms.FSPROP;

import com.vaadin.data.Item;
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

    private Button.ClickListener clickListener;
    private String btnCaption;

    //<editor-fold defaultstate="collapsed" desc="Form Fields">
    private final TextField currentCustomer = new TextField("Current Customer");

    @PropertyId("fkIdo")
    private final ComboBox owner = new ComboBox("Owner");

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

        for (Component c : fieldGroup.getFields()) {
            if (c instanceof DateField) {
                DateField df = (DateField) c;

                df.setWidth(150, Unit.PIXELS);
                df.setDateFormat(DATE_FORMAT);
            }

            c.setWidth(230, Unit.PIXELS);
        }

        //owner.setNullSelectionAllowed(false);
        owner.focus();

        noOfTanks.setNullRepresentation("0");
        //noOfTanks.addValidator(new MyNumberValidator());
        truckCapable.setNullRepresentation("0");
        //truckCapable.addValidator(new MyNumberValidator());
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
            currentCustomer.setWidth(230, Unit.PIXELS);

            addComponent(currentCustomer);
            for (Component c : fieldGroup.getFields()) {
                addComponents(c);
            }
            addComponents(active, crudButton);
        }
    }

    public FSPROP_Form(Item existingFSProp, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(existingFSProp);
        beanItem = (BeanItem<FsProp>) fieldGroup.getItemDataSource();

        currentCustomer.setWidth(230, Unit.PIXELS);
        currentCustomer.setValue(beanItem.getBean().getFkIdo() == null ? "" : beanItem.getBean().getFkIdo().getFKIDCustomer().getName());
        currentCustomer.setEnabled(false);

        Owner o = beanItem.getBean().getFkIdo();
        setAllOwnersForFS(o.getFkIdFs());
        
        String cName;
        String fsName;

        try {
            cName = o.getFKIDCustomer().getName();
        } catch (Exception e) {
            cName = " C(n/a)! ";
        }

        try {
            fsName = o.getFkIdFs().getName();
        } catch (Exception e) {
            fsName = " FS(n/a)! ";
        }

        owner.setItemCaption(o, cName + "->" + fsName);

        btnCaption = BUTTON_CAPTION_UPDATE.toString();
        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                FsProp existingFSProp = beanItem.getBean();
                bindFieldsToBean(existingFSProp);

                try {
                    DS.getFSPROPController().updateExisting(existingFSProp);
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

        addComponent(currentCustomer);
        for (Component c : fieldGroup.getFields()) {
            addComponents(c);
        }
        addComponents(active, crudButton);
    }

    private void bindFieldsToBean(FsProp fsPropertyBean) {
        fsPropertyBean.setFkIdo((Owner) owner.getValue());
        fsPropertyBean.setPropertiesDate(propertiesDate.getValue());
        fsPropertyBean.setNoOfTanks(Integer.valueOf(noOfTanks.getValue()));
        fsPropertyBean.setTruckCapable(Integer.valueOf(truckCapable.getValue()));
        fsPropertyBean.setRestaurant((boolean) restaurant.getValue());
        fsPropertyBean.setCarWash((boolean) carWash.getValue());
        fsPropertyBean.setCompliance(compliance.getValue());
        fsPropertyBean.setLicence(licence.getValue());
        fsPropertyBean.setLicDateFrom(licDateFrom.getValue());
        fsPropertyBean.setLicDateTo(licDateTo.getValue());
        fsPropertyBean.setActive(active.getValue());
    }

    private void setAllOwnersForFS(Fuelstation f) {
        for (Owner o : DS.getFSOController().getAllOwners(f)) {
            owner.setItemCaption(o, o.getFKIDCustomer().getName() + "->" + o.getFkIdFs().getName());
            owner.addItem(o);
        }
    }
}
