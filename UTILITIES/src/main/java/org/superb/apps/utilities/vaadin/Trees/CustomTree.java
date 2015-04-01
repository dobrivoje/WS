/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.data.Container;
import com.vaadin.ui.Tree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public class CustomTree<T> extends Tree {

    protected List<T> elements;

    public CustomTree(String caption) {
        setCaption(caption);
        elements = new ArrayList();
    }

    public CustomTree(String caption, Container container) {
        this(caption);
        setContainerDataSource(container);
    }

    public CustomTree(String caption, List treeItems) {
        this(caption);
        addItems(treeItems);

        elements.addAll(treeItems.subList(0, treeItems.size()));
    }

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
