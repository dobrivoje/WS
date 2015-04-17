/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.data.util.BeanItemContainer;
import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.List;

/**
 * <p>
 * CustomDateTree klasa kao čvorove ima objekte tipa T.</p>
 *
 * @param <T>
 */
public class CustomSCTree<T> extends CustomTree<T> {

    public CustomSCTree(String caption) {
        super(caption);
    }

    /**
     * Kreiraj stablo sa čvorovima koji se dobijaju iz liste.
     *
     * @param caption
     * @param treeItems Lista čvorova
     * @throws db.Exceptions.CustomTreeNodesEmptyException
     */
    public CustomSCTree(String caption, List treeItems) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, treeItems);
    }

    public CustomSCTree(String caption, BeanItemContainer<T> container) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, container);
    }

    /**
     * Kreiraj stablo za zadati čvor T
     *
     * @param caption
     * @param t Čvor
     */
    public CustomSCTree(String caption, T t) {
        this(caption);
        addItem(t);
        elements.add(t);
    }

    /**
     * <p>
     * Kreiraj stablo sa čvorovima koji se dobijaju iz liste.</p>
     * Postoji tačno jedan podčvor svakog čvora - lista customer-a
     *
     * @param t Čvor
     * @param list Lista podčvorova za čvor t
     */
    protected void createSubItems(T t, List list) {
        setSubTreeItems(t, list);
    }
}
