/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uni.MainMenu;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import java.util.List;

/**
 *
 * @author dprtenjak
 */
public class PropertyAccordion extends Accordion {

    private CssLayout tabLayout;

    public PropertyAccordion() {
        setSizeFull();
    }

    /**
     * Moja verzija Accordina. Property accordion
     *
     * @param tab
     * @param components
     */
    public void addTab(String tab, List<Image> components) {
        tabLayout = new CssLayout() {
            @Override
            protected String getCss(Component c) {
                return "display: inline-block;";
            }
        };

        tabLayout.setCaption(tab);

        for (Component c : components) {
            tabLayout.addComponents(c);
        }
    }
}
