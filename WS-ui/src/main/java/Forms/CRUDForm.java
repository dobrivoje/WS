package Forms;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_NEW;
import static org.superb.apps.utilities.Enums.CrudOperations.BUTTON_CAPTION_UPDATE;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

public abstract class CRUDForm<T> extends FormLayout {

    protected FieldGroup fieldGroup;
    protected BeanItem<T> beanItem;

    protected Button formButton;
    protected String formButtonCaption;
    protected Button.ClickListener clickListener;

    protected CRUDForm() {
        setSizeFull();
        setMargin(true);
        setStyleName(Reindeer.LAYOUT_BLACK);
    }

    public CRUDForm(final T bean) {
        this();

        // PAZI : OBAVEZNO INICIJALOIZOVATI U IZVEDENOJ KLASI !!!
        // fieldGroup.bindMemberFields(this);

        formButtonCaption = BUTTON_CAPTION_NEW.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                bindFieldsToBean(bean);

                try {
                    addNewBean(bean);
                    Notification.show("Customer Added.", Notification.Type.HUMANIZED_MESSAGE);
                } catch (Exception ex) {
                    Notification.show("Error", "Description: " + ex.toString(), Notification.Type.ERROR_MESSAGE);
                }
            }
        };

        formButton = new Button(formButtonCaption, clickListener);
        formButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        // Moramo obavezno dodati u izvedenom konstruktoru
        // addComponents(field0, field1,...,fieldN, formButton);
    }

    public CRUDForm(final T bean, final IRefreshVisualContainer visualContainer) {
        this();

        // PAZI : OBAVEZNO INICIJALOIZOVATI U IZVEDENOJ KLASI !!!
        fieldGroup.bindMemberFields(this);

        fieldGroup.setItemDataSource(new BeanItem(bean));
        beanItem = (BeanItem<T>) fieldGroup.getItemDataSource();
        
        formButtonCaption = BUTTON_CAPTION_UPDATE.toString();

        clickListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                T beanToUpdate = beanItem.getBean();
                bindFieldsToBean(beanToUpdate);

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

        // Moramo obavezno dodati u izvedenom konstruktoru
        // addComponents(field0, field1,...,fieldN, formButton);
    }

    protected abstract void bindFieldsToBean(T bean);

    public abstract void addNewBean(T bean) throws Exception;

    public abstract void updateExistingBean(T bean) throws Exception;
}
