/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.List;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

/**
 *
 * @author root
 * @param <T>
 */
public abstract class GENTable<T> extends Table implements IRefreshVisualContainer {

    protected final BeanItemContainer<T> beanContainer;
    protected List list;

    public GENTable(BeanItemContainer<T> beanContainer, List list) {
        this.beanContainer = beanContainer;
        this.list = list;

        updateBeanItemContainer(list);
        setContainerDataSource(beanContainer);

        setSizeFull();

        setPageLength(20);
        setCacheRate(4);
        setSelectable(true);
        setColumnCollapsingAllowed(true);
        setImmediate(true);
    }

    protected final void updateBeanItemContainer(List list) {
        if (this.beanContainer.size() > 0) {
            this.beanContainer.removeAllItems();
        }
        this.beanContainer.addAll(list);
    }

    @Override
    public void refreshVisualContainer() {
        updateBeanItemContainer(this.list);
        markAsDirtyRecursive();
    }
    
    protected Image createImage(int imgIndex, float height, float width) {
        String img = "img/fs/" + imgIndex + ".png";

        final Image image = new Image(null, new ThemeResource(img));
        image.setDescription("Double click to open the image.");

        image.setHeight(height, Unit.PIXELS);
        image.setWidth(width, Unit.PIXELS);

        image.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                if (event.isDoubleClick()) {

                    VerticalLayout VLI = new VerticalLayout();
                    VLI.setSizeFull();
                    VLI.setMargin(true);

                    Image i = new Image();
                    i.setSource(image.getSource());

                    VLI.addComponent(i);
                    VLI.setComponentAlignment(i, Alignment.MIDDLE_CENTER);

                    Window w = new Window("Fuelstation image(s)", VLI);

                    w.setWidth(60, Unit.PERCENTAGE);
                    w.setHeight(75, Unit.PERCENTAGE);
                    w.center();

                    getUI().addWindow(w);
                }
            }
        });

        return image;
    }
    
}
