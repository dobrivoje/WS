/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Tree;
import db.Exceptions.CustomTreeNodesEmptyException;
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

    private void init(String caption) {
        setCaption(caption);
        elements = new ArrayList();
    }
    
    public CustomTree(String caption) {
        init(caption);
    }

    /**
     * Kreiraj stablo sa čvorovima iz liste.
     *
     * @param caption Naziv stabla
     * @param treeItems Lista čvorova
     * @throws db.Exceptions.CustomTreeNodesEmptyException
     */
    public CustomTree(String caption, List treeItems) throws CustomTreeNodesEmptyException, NullPointerException {
        if (treeItems == null) {
            throw new NullPointerException();
        }

        if (treeItems.isEmpty()) {
            throw new CustomTreeNodesEmptyException();
        }

        init(caption);
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
     * @throws db.Exceptions.CustomTreeNodesEmptyException
     */
    public CustomTree(String caption, BeanItemContainer<T> container) throws CustomTreeNodesEmptyException, NullPointerException {
        if (container == null) {
            throw new NullPointerException();
        }

        if (container.size() > 0) {
            init(caption);

            setContainerDataSource(container);
            elements.clear();
            elements.addAll(container.getItemIds());
        } else {
            throw new CustomTreeNodesEmptyException();
        }
    }

    /**
     * Kreiraj podčvorove iz liste za čvor "t" tipa T.
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
