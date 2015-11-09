package org.superb.apps.utilities.vaadin.Forms;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import java.util.Date;
import org.dobrivoje.utils.date.formats.DateFormat;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;

public abstract class Form_CRUD2<T> extends FormLayout implements IUpdateData<T> {
    public static final String APP_DATE_FORMAT = DateFormat.DATE_FORMAT_SRB.toString();

    protected FieldGroup fieldGroup;
    protected BeanItem<T> beanItem;

    protected Button crudButton;
    protected Button.ClickListener clickListener;
    protected String btnCaption;
    protected boolean defaultCRUDButtonOnForm;
    protected boolean readOnly;

    /**
     * Listener to call upon form is submitted. This way, event is send to
     * calling object with collected data of T type.
     */
    protected IUpdateData<T> iUpdateDataListener;

    protected Form_CRUD2() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        defaultCRUDButtonOnForm = false;
        readOnly = true;
        crudButton = new Button();
        crudButton.setWidth(150, Unit.PIXELS);
    }

    public Form_CRUD2(BeanItem<T> beanItem) {
        this();
        this.beanItem = beanItem;
    }

    public Form_CRUD2(FieldGroup fieldGroup) {
        this();
        this.fieldGroup = fieldGroup;
    }

    public Button.ClickListener getClickListener() {
        return clickListener;
    }

    //<editor-fold defaultstate="collapsed" desc="UpdateDataListener">
    public IUpdateData getUpdateDataListener() {
        return iUpdateDataListener;
    }

    public void setUpdateDataListener(IUpdateData iUpdateDataListener) {
        this.iUpdateDataListener = iUpdateDataListener;
    }
    //</editor-fold>

    /**
     *
     * @param lockAll - zaključavanje svih polja u formi ?
     */
    protected void lockFormFileds(boolean lockAll) {
        if (lockAll) {
            fieldGroup.getFields().stream().forEach((c) -> {
                c.setEnabled(false);
            });
        }
    }

    /**
     * Postavi vrednost bean-a "t" sakupljanjem vrednosti iz polja na formi.
     *
     * @param t
     */
    protected abstract void setBeanFromFields(T t);

    protected abstract void setFieldsFromBean(T t);

    protected final void setFormFieldsWidths(float width, Sizeable.Unit unit) {
        for (Component c : fieldGroup.getFields()) {

            c.setWidth(width, unit);

            if (c instanceof TextField) {
                ((TextField) c).setNullRepresentation("");
            }
            if (c instanceof DateField) {
                ((DateField) c).setConverter(Date.class);
                ((DateField) c).setDateFormat(APP_DATE_FORMAT);

            }
            if (c instanceof TextArea) {
                ((TextArea) c).setNullRepresentation("");
                ((TextArea) c).setRows(3);
            }
        }
    }

    protected void addBeansToForm() {
        for (Component c : fieldGroup.getFields()) {
            addComponent(c);
        }

        if (defaultCRUDButtonOnForm) {
            crudButton.setCaption(btnCaption);
            crudButton.addClickListener(clickListener);

            addComponents(crudButton);
        }

        initFields();
    }

    /**
     * Inicijalizacija svojstava polja na formi i/ili njihovih vrednosti.
     */
    protected abstract void initFields();

    /**
     * Naznačiti koja polja su obavezna, čime će se na formi označiti
     * zvezdicama.
     */
    protected abstract void setRequiredFields();

    /**
     * Koristiti npr. za combobox komponente čiji se sadržaj dinamički menja
     * kako bi se uvek dobio što ažurniji sadržaj.
     */
    protected abstract void updateDynamicFields();

    @Override
    public void update(T t) {
        setFieldsFromBean(t);
    }
}
