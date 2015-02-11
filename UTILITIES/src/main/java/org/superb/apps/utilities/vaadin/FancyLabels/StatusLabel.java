/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.FancyLabels;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import java.util.HashMap;
import java.util.Map;
import org.superb.apps.utilities.Enums.Statuses;

/**
 *
 * @author root
 */
public class StatusLabel extends Label {

    private final Map<Statuses, String> color = new HashMap<>();

    public StatusLabel() {
        setContentMode(ContentMode.HTML);
        setSizeUndefined();

        color.put(Statuses.OK, "#2dd085");
        color.put(Statuses.BLACK_LIST, "#583388");
        color.put(Statuses.IN_PROGRESS, "#F3A344");
        color.put(Statuses.NO_LICENCE, "#f54993");
    }

    public StatusLabel(Statuses status, String property) {
        this();

        String iconCode = "<span class=\"v-icon\" style=\"font-family: "
                + FontAwesome.CIRCLE.getFontFamily()
                + ";color:"
                + color.get(status)
                + "\">&#x"
                + Integer
                .toHexString(FontAwesome.CIRCLE.getCodepoint())
                + ";</span>";

        setValue(iconCode + " " + property);
    }
}
