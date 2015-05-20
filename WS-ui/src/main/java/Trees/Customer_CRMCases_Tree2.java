/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;

/**
 *
 * @author root
 */
public class Customer_CRMCases_Tree2 extends Customer_CRMCases_Tree {

    public Customer_CRMCases_Tree2(String caption, final Customer customer, boolean formAllowed) 
            throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, customer, formAllowed);
    }

    @Override
    public void refreshVisualContainer() {
        super.refreshVisualContainer();
    }
}
