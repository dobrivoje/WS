/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Customer;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Customer_CRMCases_Tree extends CustomObjectTree<CrmCase> implements IRefreshVisualContainer {

    public Customer_CRMCases_Tree(String caption, final Customer customer) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCrmController().getCRM_Cases(customer, false));
        createSubItems();
    }

    private void createSubItems() {
        for (CrmCase crmCase : elements) {
            if (!crmCase.isFinished()) {
                createSubItems(crmCase, DS.getCrmController().getCRM_Processes(crmCase, false));
            }
        }
    }

    @Override
    public final void refreshVisualContainer() {
        createSubItems();
    }
}
