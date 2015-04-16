/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;
import db.ent.Owner;
import org.superb.apps.utilities.vaadin.Trees.CustomDateTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class FSOwner_Tree extends CustomDateTree<Owner> {

    public FSOwner_Tree(String caption, Customer customer, boolean justActive) throws CustomTreeNodesEmptyException {
        super(caption, DS.getCustomerController().getAllFSOwnedByCustomer(customer, justActive));
        createSubItems();
    }

    private void createSubItems() {
        for (Owner o : elements) {
            createSubItems(o, o.getDateFrom(), o.getDateTo());
        }
    }
}
