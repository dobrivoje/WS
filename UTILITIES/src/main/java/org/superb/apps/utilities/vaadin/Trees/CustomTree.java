/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.ui.Tree;
import com.vaadin.ui.themes.Reindeer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class CustomTree<T> extends Tree {
    
    private String caption;
    private List<T> treeItems;
    
    public CustomTree() {
        setStyleName(Reindeer.LAYOUT_BLACK);
        
        treeItems = new ArrayList<>();
        caption = "Tree";
        setCaption(caption);
    }
    
    public CustomTree(String caption, List treeItems) {
        this.caption = caption;
        this.treeItems = treeItems;
        
        setCaption(caption);
        addItems(treeItems);
    }
    
    public List<T> getTreeItems() {
        return treeItems;
    }
    
    public void setTreeItems(List<T> treeItems) {
        this.treeItems = treeItems;
    }
    
}
