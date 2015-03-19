/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import com.vaadin.data.Container;
import db.ent.Customer;
import db.ent.Owner;
import java.util.List;
import org.superb.apps.utilities.vaadin.Trees.CustomTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class FSOwnerTree extends CustomTree<Owner> {

    public FSOwnerTree(String caption, List treeItems) {
        super(caption, treeItems);
        createSubItems();
    }

    public FSOwnerTree(String caption, Customer customer, boolean justActive) {
        super(caption, DS.getCustomerController().getAllFSOwnedByCustomer(customer, justActive));
        createSubItems();
    }

    public FSOwnerTree(String caption, Container container) {
        super(caption, container);
    }

    private void createSubItems() {
        for (Owner o : elements) {
            createSubItems(o, o.getDateFrom(), o.getDateTo());
        }
    }
}
