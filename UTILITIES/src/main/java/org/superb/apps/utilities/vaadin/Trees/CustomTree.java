/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.data.Container;
import com.vaadin.ui.Tree;
import date.formats.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public class CustomTree<T> extends Tree {

    private static final String DF = DateFormat.DATE_FORMAT_SRB.toString();
    private static final String[] M = new String[]{
        "no start date !", "From: ",
        "no end date !", "To: "
    };

    protected List<T> elements;

    public CustomTree(String caption) {
        setCaption(caption);
        elements = new ArrayList();
    }

    public CustomTree(String caption, Container container) {
        this(caption);
        setContainerDataSource(container);
    }

    public CustomTree(String caption, List treeItems) {
        this(caption);
        addItems(treeItems);

        elements.addAll(treeItems.subList(0, treeItems.size()));
    }

    protected void createSubItems(T t, Date dateFrom, Date dateTo) {
        String df;
        String dt;

        df = (dateFrom == null ? "" : new SimpleDateFormat(DF).format(dateFrom));
        dt = (dateTo == null ? "" : new SimpleDateFormat(DF).format(dateTo));

        setSubTreeItems(t, new ArrayList<>(
                Arrays.asList(df.equals("") ? M[0] : M[1] + df, dt.equals("") ? M[2] : M[3] + dt)));
    }

    public void setSubTreeItems(Object item, List childItems) {
        for (Object childItem : childItems) {
            if (this.containsId(item)) {
                addItem(childItem);
                setParent(childItem, item);
                setChildrenAllowed(childItem, false);
            }
        }
    }
}
