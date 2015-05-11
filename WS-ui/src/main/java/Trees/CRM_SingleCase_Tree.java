/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import java.util.Arrays;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CRM_SingleCase_Tree extends CustomObjectTree<CrmCase> implements IRefreshVisualContainer {

    public CRM_SingleCase_Tree(String caption, CrmCase crmCase) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(crmCase));
        refreshVisualContainer();
    }

    @Override
    public final void refreshVisualContainer() {
        CrmCase cc = elements.get(0);
        
        if (!cc.isFinished()) {
            createSubItems(cc, DS.getCrmController().getCRM_Processes(cc, false));
        }
    }
}
