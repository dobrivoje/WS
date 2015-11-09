package Trees.CRM;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;

/**
 *
 * @author root
 */
public class Tree_SalesmanCRMCases2 extends CustomObjectTree<CrmCase> {

    public Tree_SalesmanCRMCases2(String caption, Map<CrmCase, List<? extends Object>> customTree, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {
        
        super(caption, customTree);
    }

    @Override
    protected void createSubNodes(CrmCase cc) {
    }
}
