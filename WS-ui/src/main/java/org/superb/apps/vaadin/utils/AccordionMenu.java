/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.vaadin.utils;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccordionMenu extends Accordion {

    private List<String> mainMenuItems;
    private Map<String, List<Button>> subMenuButtons;
    private Map<String, List<Button.ClickListener>> subMenuButtonsClickListeners;

    public AccordionMenu() {
        this.mainMenuItems = new ArrayList<>();
        this.subMenuButtons = new HashMap<>();
        this.subMenuButtonsClickListeners = new HashMap<>();
    }

    public final void createTabs() {
        for (String m : mainMenuItems) {
            VerticalLayout VL = new VerticalLayout();
            VL.setMargin(true);
            VL.setSpacing(true);

            if (subMenuButtons.containsKey(m)) {
                for (Button smb : subMenuButtons.get(m)) {
                    VL.addComponent(smb);
                }
            }

            addTab(VL, m);
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

    public Map<String, List<Button>> getSubMenuButtons() {
        return subMenuButtons;
    }

    public void setSubMenuButtons(Map<String, List<Button>> subMenuButtons) {
        this.subMenuButtons = new HashMap<>(subMenuButtons);
    }

    public void setSubMenuButtons(int index, Button... subMenuButtons) {
        this.subMenuButtons.put(this.mainMenuItems.get(index), Arrays.asList(subMenuButtons));
    }

    public Map<String, List<Button.ClickListener>> getSubMenuButtonsClickListeners() {
        return subMenuButtonsClickListeners;
    }

    public void setSubMenuButtonsClickListeners(Map<String, List<Button.ClickListener>> subMenuButtonsClickListeners) {
        this.subMenuButtonsClickListeners = new HashMap<>(subMenuButtonsClickListeners);
    }

    public void setSubMenuButtonsClickListeners(int index, Button.ClickListener... subMenuButtonsClickListeners) {
        this.subMenuButtonsClickListeners.put(this.mainMenuItems.get(index), Arrays.asList(subMenuButtonsClickListeners));
    }
    //</editor-fold>
}
