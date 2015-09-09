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
 * CustomDateTree klasa kao ÄŤvorove ima objekte tipa T.</p>
 * Za svaki ÄŤvor, postoji lista.
 *
 * @param <T>
 */
public abstract class CustomObjectTree<T> extends CustomTree<T> implements IRefreshVisualContainer {

    public CustomObjectTree(String caption, List treeItems) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, treeItems);
        init();
    }

    //<editor-fold defaultstate="collapsed" desc="init">
    private void init() {
        if (!elements.isEmpty()) {
            for (T e : elements) {
                createSubNodes(e);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createSubNodes">
    /**
     * <p>
     * createSubNodes metod, se koristi u init metodu </p>
     * kako bi se ispitao svaki čvor "t"
     *
     *
     * @param t
     */
    protected abstract void createSubNodes(T t);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createNodeItems">
    /**
     * <p>
     * Kreiraj stablo sa podÄŤvorovima za ÄŤvor "t" tipa T</p>
     *
     * @param t ÄŚvor
     * @param subList Lista podÄŤvorova ÄŤvora "t"
     */
    protected void createNodeItems(T t, List subList) {
        super.setNodeItems(t, subList);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="refreshVisualContainer">
    @Override
    public void refreshVisualContainer() {
        init();
    }
    //</editor-fold>

}
