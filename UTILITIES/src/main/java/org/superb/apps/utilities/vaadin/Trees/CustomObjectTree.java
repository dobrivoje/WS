/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.List;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

/**
 * <p>
 * CustomDateTree klasa kao čvorove ima objekte tipa T.</p>
 * Za svaki čvor, postoji lista.
 *
 * @param <T>
 */
public abstract class CustomObjectTree<T> extends CustomTree<T> implements IRefreshVisualContainer {

    public CustomObjectTree(String caption, List treeItems) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, treeItems);
        init();
    }

    private void init() {
        for (T e : elements) {
            scanAllNodes(e);
        }
    }

    protected abstract void scanAllNodes(T e);

    /**
     * <p>
     * Kreiraj stablo sa podčvorovima za čvor "t" tipa T</p>
     *
     * @param t Čvor
     * @param subList Lista podčvorova čvora "t"
     */
    protected void createNodeItems(T t, List subList) {
        super.setNodeItems(t, subList);
    }

    @Override
    public void refreshVisualContainer() {
        init();
    }

}
