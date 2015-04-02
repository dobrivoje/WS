/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Tree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public class CustomTree<T> extends Tree {

    /**
     * <b>Lista počvorova ovog stabla</b>
     */
    protected List<T> elements;

    public CustomTree(String caption) {
        setCaption(caption);
        elements = new ArrayList();
    }

    /**
     * Kreiraj stablo sa čvorovima iz liste.
     *
     * @param caption Naziv stabla
     * @param treeItems Lista čvorova
     */
    public CustomTree(String caption, List treeItems) {
        this(caption);
        addItems(treeItems);

        elements.clear();
        elements.addAll(treeItems.subList(0, treeItems.size()));
    }

    /**
     * <p>
     * Kreiraj stablo sa čvorovima iz bean container-a.</p>
     * Ova opcija je zgodna kod automatskog ažuriranja podataka, jer kad radimo
     * sa kontejnerom, sve izmene na kontejneru su odmah vidljive i u UI
     * komponenti.
     *
     * @param caption
     * @param container BeanContainer za klasu koju pratimo.
     */
    public CustomTree(String caption, BeanItemContainer<T> container) {
        this(caption);
        setContainerDataSource(container);

        elements.clear();
        elements.addAll(container.getItemIds());
    }

    /**
     * Kreiraj podčvorove iz liste za čvor T.
     *
     * @param item Čvor
     * @param childItems Lista podčvorova
     */
    public void setSubTreeItems(Object item, List childItems) {
        for (Object childItem : childItems) {
            if (this.containsId(item)) {
                addItem(childItem);
                setParent(childItem, item);
                setChildrenAllowed(childItem, false);
            }
        }
    }
}
