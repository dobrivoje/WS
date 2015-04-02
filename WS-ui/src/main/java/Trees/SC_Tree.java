/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.Trees.CustomSCTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class SC_Tree extends CustomSCTree<Salesman> {

    public SC_Tree(String caption, Salesman s) {
        super(caption, s);
        createSubItems();
    }

    private void createSubItems() {
        for (Salesman s : elements) {
            createSubItems(s, DS.getCrmController().getCRM_SalesmansCustomers(s));
        }
    }
}
