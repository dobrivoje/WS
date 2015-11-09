package Trees.CRM;

import com.vaadin.event.ItemClickEvent;
import org.superb.apps.utilities.vaadin.Trees.IUpdateData;
import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
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

    public Tree_CRMSingleCase(String caption, CrmCase crmCase, IUpdateData iUpdateCrmProcess) throws CustomTreeNodesEmptyException, NullPointerException {
        this(caption, crmCase);

        addItemClickListener((ItemClickEvent event) -> {
            if (event.getItemId() instanceof CrmProcess) {
                iUpdateCrmProcess.update((CrmProcess) event.getItemId());
            }
        });
    }

    @Override
    protected void createSubNodes(CrmCase c) {
        // if (!c.getFinished()) {
        createSingleRootChildNodes(c, DS.getCRMController().getCRM_Processes(c));
        //}
    }
}
