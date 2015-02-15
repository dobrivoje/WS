/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables.Old;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Table;
import java.util.List;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import db.controllers.FS_Controller;
import db.ent.Fuelstation;
import db.interfaces.IFS;

/**
 *
 * @author root
 */
public class FSTable_OLD_REMOVE extends Table implements IRefreshVisualContainer {

    private final BeanItemContainer<Fuelstation> beanContainer = new BeanItemContainer<>(Fuelstation.class);
    private final IFS FS_CONTROLLER = new FS_Controller();

    public FSTable_OLD_REMOVE() {
        setSizeFull();

        setContainerDataSource(beanContainer);
        updateBeanItemContainer(beanContainer, FS_CONTROLLER.getAll());

        setVisibleColumns("idfs", "name", "city", "address");
        setColumnHeaders("FS ID", "FS NAME", "CITY", "ADDRESS");
        setSelectable(true);

        setColumnWidth("idfs", 80);
        setSelectable(true);
        setImmediate(true);
    }

    private void updateBeanItemContainer(BeanItemContainer beanItemContainer, List listOfData) {
        beanItemContainer.removeAllItems();
        beanItemContainer.addAll(listOfData);
    }

    public void setFilter(String filterString) {
        beanContainer.removeAllContainerFilters();

        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "name", filterString, true, false);
            SimpleStringFilter licenceFilter = new SimpleStringFilter(
                    "city", filterString, true, false);

            beanContainer.addContainerFilter(new Or(nameFilter, licenceFilter));
        }
    }

    @Override
    public void refreshVisualContainer() {
        markAsDirtyRecursive();
    }
}
