/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Salesman;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class Salesman_CRMCases_Tree extends CustomObjectTree<CrmCase> implements IRefreshVisualContainer {

    public Salesman_CRMCases_Tree(String caption, Salesman s) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCrmController().getCRM_Cases(s, false));
        refreshVisualContainer();
    }

    @Override
    public final void refreshVisualContainer() {
        for (CrmCase c : elements) {
            createSubItems(c, DS.getCrmController().getCRM_Cases(
                    c.getFK_IDRSC().getFK_IDS(), false));
        }
    }
}
