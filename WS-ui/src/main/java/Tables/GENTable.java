/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;
import java.util.List;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

/**
 *
 * @author root
 * @param <T>
 */
public class GENTable<T> extends Table implements IRefreshVisualContainer {

    protected final BeanItemContainer<T> beanContainer;
    protected List list;

    public GENTable(BeanItemContainer<T> beanContainer, List list) {
        this.beanContainer = beanContainer;
        this.list = list;

        setContainerDataSource(beanContainer);
        updateBeanItemContainer(list);

        setSizeFull();

        setPageLength(20);
        setCacheRate(4);
        setSelectable(true);
        setColumnCollapsingAllowed(true);
        //setImmediate(true);
    }

    protected final void updateBeanItemContainer(List list) {
        this.beanContainer.removeAllItems();
        this.beanContainer.addAll(list);
    }

    @Override
    public void refreshVisualContainer() {
        updateBeanItemContainer(this.list);
        markAsDirtyRecursive();
    }
}