/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import db.controllers.FS_Controller;
import db.ent.Fuelstation;
import db.interfaces.IFS;

/**
 *
 * @author root
 */
public class FSTable extends GENTable<Fuelstation> {

    public FSTable() {
        this(new BeanItemContainer<>(Fuelstation.class), new FS_Controller());
    }

    public FSTable(BeanItemContainer<Fuelstation> BIC_FS, IFS controller) {
        super(BIC_FS, controller);

        setVisibleColumns("name", "city", "address");
        setColumnHeaders("FS NAME", "CITY", "ADDRESS");
    }

    public void setFilter(String filterString) {
        beanContainer.removeAllContainerFilters();

        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "name", filterString, true, false);
            SimpleStringFilter cityFilter = new SimpleStringFilter(
                    "city", filterString, true, false);

            beanContainer.addContainerFilter(new Or(nameFilter, cityFilter));
        }
    }
}
