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
    }

    public AccordionMenu(List<String> mainMenuItems, Map<String, List<Button>> subMenuButtons) {
        this.mainMenuItems = new ArrayList<>(mainMenuItems);
        this.subMenuButtons = new HashMap<>(subMenuButtons);
        this.createTabs();
    }

    public final void createTabs() {
        for (String mainMenuItem : mainMenuItems) {
            VerticalLayout VL = new VerticalLayout();
            VL.setSizeFull();
            VL.setMargin(true);
            VL.setSpacing(true);

            for (Button smb : subMenuButtons.get(mainMenuItem)) {
                VL.addComponent(smb);
            }

            addTab(VL, mainMenuItem);
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
        this.mainMenuItems = Arrays.asList(mainMenuItems);
    }

    public Map<String, List<Button>> getSubMenuButtons() {
        return subMenuButtons;
    }

    public void setSubMenuButtons(Map<String, List<Button>> subMenuButtons) {
        this.subMenuButtons = new HashMap<>(subMenuButtons);
    }

    public void setSubMenuButtons(int index, Button... subMenuButtons) {
        this.subMenuButtons = new HashMap<>();
        this.subMenuButtons.put(getMenuOption(index), Arrays.asList(subMenuButtons));
    }

    public Map<String, List<Button.ClickListener>> getSubMenuButtonsClickListeners() {
        return subMenuButtonsClickListeners;
    }

    public void setSubMenuButtonsClickListeners(Map<String, List<Button.ClickListener>> subMenuButtonsClickListeners) {
        this.subMenuButtonsClickListeners = new HashMap<>(subMenuButtonsClickListeners);
    }

    public void setSubMenuButtonsClickListeners(int index, Button.ClickListener... subMenuButtonsClickListeners) {
        this.subMenuButtonsClickListeners = new HashMap<>();
        this.subMenuButtonsClickListeners.put(getMenuOption(index), Arrays.asList(subMenuButtonsClickListeners));
    }
    //</editor-fold>

    private String getMenuOption(int index) {
        return mainMenuItems.get(index);
    }
}
