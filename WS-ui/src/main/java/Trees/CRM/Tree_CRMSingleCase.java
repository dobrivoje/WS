/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import java.util.Arrays;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Tree_CRMSingleCase extends CustomObjectTree<CrmCase> {

    public Tree_CRMSingleCase(String caption, CrmCase crmCase) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(crmCase));
    }

    @Override
    protected void createSubNodes(CrmCase c) {
        if (!c.getFinished()) {
            createNodeItems(c, DS.getCRMController().getCRM_Processes(c, false));
        }
    }
}
