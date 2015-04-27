/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import Forms.CRM.CRMCase_Form;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.Customer;
import org.superb.apps.utilities.vaadin.Trees.CustomDateTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CRM_CustCRMCases_Tree extends CustomDateTree<CRMCase_Form> {

    public CRM_CustCRMCases_Tree(String caption, Customer customer) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCrmController().getCRM_Cases(customer, false));
    }
}
