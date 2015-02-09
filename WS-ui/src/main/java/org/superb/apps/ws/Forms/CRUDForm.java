package org.superb.apps.ws.Forms;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import org.superb.apps.utilities.Enums.CrudOperations;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public abstract class CRUDForm<T> extends FormLayout {

    protected FieldGroup fieldGroup;

    protected Button formButton;
    protected String formButtonCaption;
    protected Button.ClickListener clickListener;

    protected T bean;

    
    public CRUDForm() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);
    }

    public CRUDForm(final CrudOperations crudOperation) {
        this();

        if (crudOperation.equals(CrudOperations.CREATE)) {
            formButtonCaption = BUTTON_CAPTION_NEW.toString();

            clickListener = new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    getValuesFromFields(bean);

                    try {
                        // addNewBean();
                        Notification.show("Customer Added.", Notification.Type.HUMANIZED_MESSAGE);
                    } catch (Exception ex) {
                        Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                    }
                }

            };

            formButton = new Button(formButtonCaption, clickListener);
            formButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        }
    }

    public CRUDForm(final T bean, final IRefreshVisualContainer visualContainer) {
        this();

        fieldGroup.setItemDataSource(new BeanItem(bean));

        formButtonCaption = BUTTON_CAPTION_UPDATE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getValuesFromFields(bean);

                try {
                    updateExistingBean(bean);
                    visualContainer.refreshVisualContainer();
                    Notification.show("Customer Updated.", Notification.Type.HUMANIZED_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        formButton = new Button(formButtonCaption, clickListener);
        formButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    protected abstract void getValuesFromFields(T bean);

    public abstract void addNewBean(T bean) throws Exception;

    public abstract void updateExistingBean(T bean) throws Exception;
}
