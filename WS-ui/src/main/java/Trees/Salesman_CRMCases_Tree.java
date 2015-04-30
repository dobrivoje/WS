/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Salesman_CRMCases_Tree extends CustomObjectTree<CrmCase> {

    public Salesman_CRMCases_Tree(String caption, Salesman s) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCrmController().getCRM_Cases(s, false));
        createSubItems();
    }

    public final void createSubItems() {
        for (CrmCase c : elements) {
            createSubItems(c, c.getCrmProcessList());
        }
    }
}
