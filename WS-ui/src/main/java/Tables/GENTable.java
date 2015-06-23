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
public abstract class GENTable<T> extends Table implements IRefreshVisualContainer {

    protected List<String> tableColumnsID;
    protected final BeanItemContainer<T> beanContainer;
    protected List list;

    /**
     * <p>
     * Polje koje se odnosi na ispravnu inicijalizaciju kontekstnog menija.</p>
     * Rešavanje problema kada se ne izabere red u tabeli, a startuje se
     * kontekstni meni, koji izaziva razne greške i izuzetke.
     * <p>
     * Ispravno je jedino podići kontekstni meni kada se označi red u izabranoj
     * tabeli.</p>
     */
    protected boolean tableSelected;

    public GENTable(BeanItemContainer<T> beanContainer, List list) {
        this.tableSelected = false;
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

    /**
     * Metod za kreiranje pogleda na tabelu. Biranjem kolona, biramo i prikaz
     * kolona tabele, čime pravimo različite "poglede" nad istom tabelom. Ovim
     * metodom osim biznis funkcionalnosti, možemo kontrolisati i performanse
     * iscrtavanja tabele sa velikom količinom podataka.
     *
     * @param columns
     */
    protected void setTableView(String... columns) {
        for (String c : tableColumnsID) {
            setColumnCollapsed(c, true);
        }

        for (String c : columns) {
            setColumnCollapsed(c, false);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getter/setter tableSelected">
    public boolean isTableSelected() {
        return tableSelected;
    }

    public void setTableSelected(boolean tableSelected) {
        this.tableSelected = tableSelected;
    }
    //</editor-fold>

}
