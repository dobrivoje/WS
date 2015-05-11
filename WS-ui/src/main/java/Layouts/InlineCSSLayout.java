/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layouts;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 *
 * @author dprtenjak
 */
public class InlineCSSLayout extends CssLayout {

    public InlineCSSLayout() {
        super();
    }

    @Override
    protected String getCss(Component c) {
        return "display: inline-block;";
    }

}
