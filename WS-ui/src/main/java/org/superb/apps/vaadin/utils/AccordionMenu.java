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

    private final List<String> mainMenuOptions = new ArrayList<>();

    private final Map<String, List<String>> subMenuButtonCaptions = new HashMap<>();
    private final Map<String, List<Button>> subMenuButtons = new HashMap<>();

    // private final String ACCORDION_STYLE1 = "mojAccordion";
    // private final String BUTTON_STYLE1 = "mojiDugmici_GlavniMeni";

    public AccordionMenu() {
        // setStyleName(ACCORDION_STYLE1);
        
        for (String mainMenuOption : mainMenuOptions) {
            subMenuButtonCaptions.put(mainMenuOption, subMenuButtonCaptions.get(mainMenuOption));
            subMenuButtons.put(mainMenuOption, subMenuButtons.get(mainMenuOption));
        }

        setCaption("Main Menu");
        setSizeFull();
        createTabs();
    }

    private void createTabs() {
        for (String item : mainMenuOptions) {
            VerticalLayout vl = new VerticalLayout();
            vl.setMargin(true);
            vl.setSpacing(true);
            vl.setCaption(item);
            addComponent(vl);

            for (Button subMenuButton : subMenuButtons.get(item)) {
                // subMenuButton.setStyleName(BUTTON_STYLE1);
                subMenuButton.setWidth("80%");
                subMenuButton.setHeight("90px");

                vl.addComponent(subMenuButton);
                vl.setComponentAlignment(subMenuButton, Alignment.MIDDLE_CENTER);
            }
        }
    }
}
