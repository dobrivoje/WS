/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.List;

/**
 * <p>
 * CustomDateTree klasa kao čvorove ima objekte tipa T.</p>
 * Za svaki čvor, postoje dva podčvora, sa datumima od, i do
 *
 * @param <T>
 */
public class CustomObjectTree<T> extends CustomTree<T> {

    public CustomObjectTree(String caption, List treeItems) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, treeItems);
    }

    /**
     * <p>
     * Kreiraj stablo sa čvorovima koji se dobijaju iz liste.</p>
     * Postoje tačno dva podčvora svakog čvora, i predastavljaju datume od - do.
     *
     * @param t Čvor
     * @param subList Lista za čvor t
     */
    protected void createSubItems(T t, List subList) {
        super.setSubTreeItems(t, subList);
    }
}
