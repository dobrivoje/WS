package Trees.CRM;

import com.vaadin.event.ItemClickEvent;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import java.util.Arrays;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import db.ent.RelSALE;

/**
 *
 * @author root
 */
public class Tree_CRM_Sell_SingleCase extends CustomObjectTree<CrmCase> {

    public Tree_CRM_Sell_SingleCase(String caption, CrmCase crmCase, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(crmCase), expandRootNodes);
    }

    public Tree_CRM_Sell_SingleCase(String caption, CrmCase crmCase, IUpdateData iUpdateCrmProcess, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        this(caption, crmCase, expandRootNodes);

        addItemClickListener((ItemClickEvent event) -> {
            if (event.getItemId() instanceof RelSALE) {
                iUpdateCrmProcess.update((RelSALE) event.getItemId());
            }
        });
    }

    @Override
    protected void createSubNodes(CrmCase c) {
        createChildNodesForTheRoot(c, c.getRelSALEList());
    }
}
