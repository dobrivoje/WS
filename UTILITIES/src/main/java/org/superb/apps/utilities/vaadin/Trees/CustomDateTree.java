/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.event.ItemClickEvent;
import db.Exceptions.CustomTreeNodesEmptyException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.dobrivoje.utils.date.formats.DateFormat;

/**
 * <p>
 * CustomDateTree klasa kao čvorove ima objekte tipa T.</p>
 * Za svaki čvor, postoje dva podčvora, sa datumima od, i do
 *
 * @param <T>
 */
public abstract class CustomDateTree<T> extends CustomObjectTree<T> {

    protected static final String DATEFORMAT = DateFormat.DATE_FORMAT_SRB.toString();
    protected static final String[] MSG = new String[]{
        "no start date !", "From: ",
        "no end date !", "To: "
    };

    public CustomDateTree(String caption, List treeItems, boolean formEditAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, treeItems);

        //<editor-fold defaultstate="collapsed" desc="addItemClickListener">
        super.addItemClickListener((ItemClickEvent event) -> {
            if (formEditAllowed) {
                if (event.isDoubleClick()) {
                    if (event.getItemId() instanceof List) {
                        
                    }
                }
            }
        });
        //</editor-fold>
    }

    protected void iterateAllNodesForDates(T t, Date dateFrom, Date dateTo) {
        String df;
        String dt;

        df = (dateFrom == null ? "" : new SimpleDateFormat(DATEFORMAT).format(dateFrom));
        dt = (dateTo == null ? "" : new SimpleDateFormat(DATEFORMAT).format(dateTo));

        setNodeItems(t, Arrays.asList(df.equals("") ? MSG[0] : MSG[1] + df, dt.equals("") ? MSG[2] : MSG[3] + dt));
    }
    
}
