package Forms;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public abstract class CRUDForm2<T> extends FormLayout {

    protected FieldGroup fieldGroup;
    protected BeanItem<T> beanItem;

    protected Button crudButton;
    protected Button.ClickListener clickListener;
    protected String btnCaption;

    protected CRUDForm2() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        crudButton = new Button();
        crudButton.setWidth(150, Unit.PIXELS);
    }

    public CRUDForm2(BeanItem<T> beanItem) {
        this();
        this.beanItem = beanItem;
    }

    public CRUDForm2(FieldGroup fieldGroup) {
        this();
        this.fieldGroup = fieldGroup;
    }

    protected abstract void bindFieldsToBean(T t);

    protected final void setFormFieldsWidths(float width, Sizeable.Unit unit) {
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

    protected void addBeansToForm() {
        for (Component c : fieldGroup.getFields()) {
            if (c instanceof TextField) {
                TextField tf = (TextField) c;
                tf.setNullRepresentation("");
            }
            addComponent(c);
        }

        crudButton.setCaption(btnCaption);
        crudButton.addClickListener(clickListener);

        addComponents(crudButton);
    }

    /*
     protected void setComboBoxCaption(ComboBox comboBox, T t) {
     T2 t2;
    
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
     */
}
