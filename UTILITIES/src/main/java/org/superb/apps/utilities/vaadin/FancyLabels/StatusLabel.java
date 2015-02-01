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
import static org.superb.apps.utilities.Enums.Statuses.AVAILABLE;
import static org.superb.apps.utilities.Enums.Statuses.COMING;
import static org.superb.apps.utilities.Enums.Statuses.DISCONTINUED;

/**
 *
 * @author root
 */
public class StatusLabel extends Label {

    private static Map<Statuses, String> color = new HashMap<>();

    public StatusLabel() {
        setContentMode(ContentMode.HTML);
        setSizeUndefined();
        
        color.put(AVAILABLE, "#2dd085");
        color.put(COMING, "#ffc66e");
        color.put(DISCONTINUED, "#f54993");
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
