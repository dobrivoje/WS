/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmProcess;
import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Salesman_CRMProcessesTree extends CustomObjectTree<CrmProcess> {

    public Salesman_CRMProcessesTree(String caption, Salesman s) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCRMController().getCRM_Processes(s, false));
    }
}
