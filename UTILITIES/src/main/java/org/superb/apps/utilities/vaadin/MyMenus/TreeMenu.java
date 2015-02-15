/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.MyMenus;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Tree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeMenu extends Tree {

    private List<String> mainMenuItems;
    private Map<String, List<String>> subMenuItems;
    private Map<String, List<ItemClickEvent.ItemClickListener>> subMenuItemClickListeners;

    public TreeMenu() {
        this.mainMenuItems = new ArrayList<>();
        this.subMenuItems = new HashMap<>();
        this.subMenuItemClickListeners = new HashMap<>();
    }

    public final void createMenu() {
        //addItems(mainMenuItems, subMenuItems.values());
        
        for (String m : mainMenuItems) {
            setChildrenAllowed(m, true);
            List<String> smi = subMenuItems.get(m);

        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public List<String> getMainMenuOptions() {
        return mainMenuItems;
    }

    public void setMainMenuOptions(List<String> mainMenuItems) {
        this.mainMenuItems = new ArrayList<>(mainMenuItems);
    }

    public void setMainMenuOptions(String... mainMenuItems) {
        this.mainMenuItems = new ArrayList<>(Arrays.asList(mainMenuItems));
    }

    public Map<String, List<String>> getSubMenuItems() {
        return subMenuItems;
    }

    public void setSubMenuItems(Map<String, List<String>> subMenuButtons) {
        this.subMenuItems = new HashMap<>(subMenuButtons);
    }

    public void setSubMenuItems(int index, String... subMenuButtons) {
        this.subMenuItems.put(this.mainMenuItems.get(index), Arrays.asList(subMenuButtons));
    }

    public Map<String, List<ItemClickEvent.ItemClickListener>> getSubMenuItemsClickListeners() {
        return subMenuItemClickListeners;
    }

    public void setSubMenuButtonsClickListeners(Map<String, List<ItemClickEvent.ItemClickListener>> subMenuItemClickListeners) {
        this.subMenuItemClickListeners = new HashMap<>(subMenuItemClickListeners);
    }

    public void setSubMenuButtonsClickListeners(int index, ItemClickEvent.ItemClickListener... subMenuItemClickListeners) {
        this.subMenuItemClickListeners.put(this.mainMenuItems.get(index), Arrays.asList(subMenuItemClickListeners));
    }
    //</editor-fold>
}
