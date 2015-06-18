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
public class CRM_SingleCase_Tree extends CustomObjectTree<CrmCase> {

    public CRM_SingleCase_Tree(String caption, CrmCase crmCase) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(crmCase));
    }

    @Override
    protected void scanAllNodes(CrmCase c) {
        if (!c.getFinished()) {
            createNodeItems(c, DS.getCRMController().getCRM_Processes(c, false));
        }
    }
}
