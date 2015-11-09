package org.superb.apps.utilities.vaadin.Forms;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.Reindeer;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_SAVE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public abstract class Form_CRUD<T> extends FormLayout {

    protected FieldGroup fieldGroup;
    protected BeanItem<T> beanItem;

    protected Button formButton;
    protected String formButtonCaption;
    protected Button.ClickListener clickListener;

    protected Form_CRUD() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);
    }

    public Form_CRUD(final T bean, final IFormNotification notification) {
        this();

        // PAZI : OBAVEZNO INICIJALOIZOVATI U IZVEDENOJ KLASI !!!
        // fieldGroup.bindMemberFields(this);
        formButtonCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                bindFieldsToBean(bean);

                try {
                    addNewBean(bean);
                    Notification.show("New " + notification.getNotification() + " Added.", Notification.Type.TRAY_NOTIFICATION);
                } catch (Exception ex) {
                    Notification.show("Error", "Fields indicated by a red star must be provided", Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        formButton = new Button(formButtonCaption, clickListener);

        // Moramo obavezno dodati u izvedenom konstruktoru
        // addComponents(field0, field1,...,fieldN, formButton);
    }

    public Form_CRUD(final T bean, final IRefreshVisualContainer visualContainer) {
        this();

        // PAZI : OBAVEZNO INICIJALOIZOVATI U IZVEDENOJ KLASI !!!
        fieldGroup.bindMemberFields(this);

        fieldGroup.setItemDataSource(new BeanItem(bean));
        beanItem = (BeanItem<T>) fieldGroup.getItemDataSource();

        formButtonCaption = BUTTON_CAPTION_SAVE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                T beanToUpdate = beanItem.getBean();
                bindFieldsToBean(beanToUpdate);

                try {
                    updateExistingBean(bean);
                    
                    if (visualContainer != null) {
                        visualContainer.refreshVisualContainer();
                    }
                    
                    Notification.show("Customer Updated.", Notification.Type.TRAY_NOTIFICATION);
                } catch (Exception ex) {
                    Notification.show("Error", ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        formButton = new Button(formButtonCaption, clickListener);

        // Moramo obavezno dodati u izvedenom konstruktoru
        // addComponents(field0, field1,...,fieldN, formButton);
    }

    protected abstract void bindFieldsToBean(T bean);

    public abstract void addNewBean(T bean) throws Exception;

    public abstract void updateExistingBean(T bean) throws Exception;
}
