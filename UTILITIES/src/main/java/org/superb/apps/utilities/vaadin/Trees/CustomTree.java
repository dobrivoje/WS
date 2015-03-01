/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.data.Container;
import com.vaadin.ui.Tree;
import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public class CustomTree<T> extends Tree {

    public CustomTree(String caption) {
        setCaption(caption);
    }

    public CustomTree(String caption, Container container) {
        this(caption);
        setContainerDataSource(container);
    }

    public CustomTree(String caption, List treeItems) {
        this(caption);
        addItems(treeItems);
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
