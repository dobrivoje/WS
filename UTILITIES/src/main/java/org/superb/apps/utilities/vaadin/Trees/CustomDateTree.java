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
public class CustomDateTree<T> extends CustomObjectTree<T> {

    private static final String DATEFORMAT = DateFormat.DATE_FORMAT_SRB.toString();
    private static final String[] MSG = new String[]{
        "no start date !", "From: ",
        "no end date !", "To: "
    };

    public CustomDateTree(String caption, List treeItems, boolean formEditAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, treeItems);

        super.addItemClickListener((ItemClickEvent event) -> {
            if (formEditAllowed) {
                if (event.isDoubleClick()) {
                    if (event.getItemId() instanceof List) {
                        
                    }
                }
            }
        });
    }

    /**
     * <p>
     * Kreiraj stablo sa čvorovima koji se dobijaju iz liste.</p>
     * Postoje tačno dva podčvora svakog čvora, i predastavljaju datume od - do.
     *
     * @param t Čvor
     * @param dateFrom 1. Podčvor - Datum Od
     * @param dateTo 2. Podčvor - Datum Do
     */
    protected void createSubItems(T t, Date dateFrom, Date dateTo) {
        String df;
        String dt;

        df = (dateFrom == null ? "" : new SimpleDateFormat(DATEFORMAT).format(dateFrom));
        dt = (dateTo == null ? "" : new SimpleDateFormat(DATEFORMAT).format(dateTo));

        setSubTreeItems(t, Arrays.asList(df.equals("") ? MSG[0] : MSG[1] + df, dt.equals("") ? MSG[2] : MSG[3] + dt));
    }
}
