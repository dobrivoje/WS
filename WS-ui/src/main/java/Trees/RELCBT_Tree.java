/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import date.formats.DateFormat;
import db.ent.RelCBType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.superb.apps.utilities.vaadin.Trees.CustomTree;

/**
 *
 * @author root
 */
public class RELCBT_Tree extends CustomTree<RelCBType> {

    public RELCBT_Tree(String caption, List treeItems) {
        super(caption, treeItems);
        //createSubItems();
    }

    public RELCBT_Tree(String caption, Container container) {
        super(caption, container);
        //createSubItems();
    }
    

    public final void createSubItems() {
        String df;
        String dt;

        for (Object r : ((BeanItemContainer) getContainerDataSource()).getItemIds()) {
            Date ddf = ((RelCBType) r).getDateFrom();
            Date ddt = ((RelCBType) r).getDateTo();

            df = (ddf == null ? ""
                    : new SimpleDateFormat(DateFormat.DATE_FORMAT_SRB.toString()).format(ddf));
            dt = (ddt == null ? ""
                    : new SimpleDateFormat(DateFormat.DATE_FORMAT_SRB.toString()).format(ddt));

            super.setSubTreeItems(r, new ArrayList<>(
                    Arrays.asList(
                            df.equals("") ? "no start date !" : "From: " + df,
                            dt.equals("") ? "no end date !" : "To: " + dt
                    )));
        }
    }
}
