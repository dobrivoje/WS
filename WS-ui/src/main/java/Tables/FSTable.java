/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import Forms.FSM.FSForm;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import db.controllers.FS_Controller;
import db.ent.Fuelstation;
import java.util.List;
import org.superb.apps.utilities.vaadin.MyWindows.WindowForm;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

/**
 *
 * @author root
 */
public class FSTable extends GENTable<Fuelstation> {

    public FSTable() {
        this(new BeanItemContainer<>(Fuelstation.class), new FS_Controller().getAll());
    }

    public FSTable(BeanItemContainer<Fuelstation> BIC_FS, List list) {
        super(BIC_FS, new FS_Controller().getAll());

        addGeneratedColumn("options", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table source, final Object row, Object column) {
                HorizontalLayout btnPlace = new HorizontalLayout();

                final Button editBtn = new Button("u", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        Fuelstation f = (Fuelstation) row;
                        FSForm customerForm = new FSForm(new BeanItem(f), new IRefreshVisualContainer() {
                            @Override
                            public void refreshVisualContainer() {
                                source.markAsDirtyRecursive();
                            }
                        });
                        getUI().addWindow(new WindowForm("FS Update Form", customerForm));
                    }
                });

                editBtn.setDescription("Update this Fuelstation with new data...");

                btnPlace.addComponent(editBtn);
                btnPlace.setSizeFull();
                btnPlace.setComponentAlignment(editBtn, Alignment.MIDDLE_CENTER);

                return btnPlace;
            }
        });

        setVisibleColumns("name", "options", "fkIdc", "address");
        setColumnHeaders("FUEL STATION", "OPTIONS", "CITY", "ADDRESS");
    }

    public void setFilter(String filterString) {
        beanContainer.removeAllContainerFilters();

        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "name", filterString, true, false);
            SimpleStringFilter cityFilter = new SimpleStringFilter(
                    "fkIdc", filterString, true, false);
            SimpleStringFilter addressFilter = new SimpleStringFilter(
                    "address", filterString, true, false);

            beanContainer.addContainerFilter(new Or(nameFilter, cityFilter, addressFilter));
        }
    }
}
