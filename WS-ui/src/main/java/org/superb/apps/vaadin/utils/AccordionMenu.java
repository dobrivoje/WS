/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.vaadin.utils;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccordionMenu extends Accordion {

    private List<String> mainMenuItems = new ArrayList<>();

    private Map<String, List<String>> subMenuButtonCaptions = new HashMap<>();
    private Map<String, List<Button>> subMenuButtons = new HashMap<>();
    private Map<String, List<Button.ClickListener>> subMenuButtonsClickListeners = new HashMap<>();

    // private final String ACCORDION_STYLE1 = "mojAccordion";
    // private final String BUTTON_STYLE1 = "mojiDugmici_GlavniMeni";
    public AccordionMenu(List<String> mainMenuItems,
            Map<String, List<String>> subMenuButtonCaptions,
            Map<String, List<Button>> subMenuButtons) {

        // setStyleName(ACCORDION_STYLE1);
        setSizeFull();
        createTabs();
    }

    private void createTabs() {
        for (String mainMenuItem : mainMenuItems) {
            subMenuButtonCaptions.put(mainMenuItem, subMenuButtonCaptions.get(mainMenuItem));
            subMenuButtons.put(mainMenuItem, subMenuButtons.get(mainMenuItem));

            VerticalLayout VL = new VerticalLayout();
            VL.setMargin(true);
            VL.setSpacing(true);

            addComponent(VL);

            for (Button subMenuButton : subMenuButtons.get(mainMenuItem)) {
                // subMenuButton.setStyleName(BUTTON_STYLE1);
                subMenuButton.setWidth("80%");
                subMenuButton.setHeight("90px");

                VL.addComponent(subMenuButton);
                VL.setComponentAlignment(subMenuButton, Alignment.MIDDLE_CENTER);
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public List<String> getMainMenuOptions() {
        return mainMenuItems;
    }

    public void setMainMenuOptions(List<String> mainMenuOptions) {
        this.mainMenuItems = mainMenuOptions;
    }

    public Map<String, List<String>> getSubMenuButtonCaptions() {
        return subMenuButtonCaptions;
    }

    public void setSubMenuButtonCaptions(Map<String, List<String>> subMenuButtonCaptions) {
        this.subMenuButtonCaptions = subMenuButtonCaptions;
    }

    public Map<String, List<Button>> getSubMenuButtons() {
        return subMenuButtons;
    }

    public void setSubMenuButtons(Map<String, List<Button>> subMenuButtons) {
        this.subMenuButtons = subMenuButtons;
    }

    public Map<String, List<Button.ClickListener>> getSubMenuButtonsClickListeners() {
        return subMenuButtonsClickListeners;
    }

    public void setSubMenuButtonsClickListeners(Map<String, List<Button.ClickListener>> subMenuButtonsClickListeners) {
        this.subMenuButtonsClickListeners = subMenuButtonsClickListeners;
    }
    //</editor-fold>
}
