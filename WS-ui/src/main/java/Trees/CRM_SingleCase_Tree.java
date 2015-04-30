/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import java.util.ArrayList;
import java.util.Arrays;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;

/**
 *
 * @author root
 */
public class CRM_SingleCase_Tree extends CustomObjectTree<CrmCase> {

    public CRM_SingleCase_Tree(String caption, CrmCase crmCase) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, new ArrayList(Arrays.asList(crmCase)));
        createSubItems();
    }

    public final void createSubItems() {
        for (CrmCase c : elements) {
            createSubItems(c, c.getCrmProcessList());
        }
    }
}
