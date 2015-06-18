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

    public FSOwner_Tree(String caption, Customer customer, boolean justActive, boolean formEditAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCustomerController().getAllFSOwnedByCustomer(customer, justActive), formEditAllowed);
    }

    /**
     * <p>
     * Kreiraj stablo sa čvorovima koji se dobijaju iz liste.</p>
     * Postoje tačno dva podčvora svakog čvora, i predastavljaju datume od - do.
     *
     * @param owner Čvor
     */
    @Override
    protected void scanAllNodes(Owner owner) {
        scanAllNodesForDates(owner, owner.getDateFrom(), owner.getDateTo());
    }
}
