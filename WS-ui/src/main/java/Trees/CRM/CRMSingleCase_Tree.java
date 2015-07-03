/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM;

import com.vaadin.event.ItemClickEvent;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import java.util.Arrays;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import org.superb.apps.utilities.vaadin.Trees.ISetFieldsFromBean;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class CRMSingleCase_Tree extends CustomObjectTree<CrmCase> {

    public CRMSingleCase_Tree(String caption, CrmCase crmCase) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(crmCase));
    }

    public CRMSingleCase_Tree(String caption, CrmCase crmCase, ISetFieldsFromBean sffb) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(crmCase));

        addItemClickListener((ItemClickEvent event) -> {
            sffb.setFieldsFromBean(event.getItemId() instanceof CrmProcess
                    ? (CrmProcess) event.getItemId() : (CrmCase) event.getItemId());
        });
    }

    @Override
    protected void createSubNodes(CrmCase c) {
        if (!c.getFinished()) {
            createNodeItems(c, DS.getCRMController().getCRM_Processes(c, false));
        }
    }
}
