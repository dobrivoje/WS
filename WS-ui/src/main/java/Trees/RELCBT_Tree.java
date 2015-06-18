/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;
import db.ent.RelCBType;
import org.superb.apps.utilities.vaadin.Trees.CustomDateTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class RELCBT_Tree extends CustomDateTree<RelCBType> {

    public RELCBT_Tree(String caption, Customer customer, boolean formEditAllowed) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCustomerController().getAllCustomerBussinesTypes(customer), formEditAllowed);
    }

    /**
     * <p>
     * Kreiraj stablo sa čvorovima koji se dobijaju iz liste.</p>
     * Postoje tačno dva podčvora svakog čvora, i predastavljaju datume od - do.
     *
     * @param rcbt Čvor
     */
    @Override
    protected void scanAllNodes(RelCBType rcbt) {
        scanAllNodesForDates(rcbt, rcbt.getDateFrom(), rcbt.getDateTo());
    }
}
