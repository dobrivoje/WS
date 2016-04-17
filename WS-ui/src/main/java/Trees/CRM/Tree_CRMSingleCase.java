package Trees.CRM;

import com.vaadin.event.ItemClickEvent;
import org.superbapps.utils.vaadin.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import static Main.MyUI.DS;
import java.util.Arrays;
import org.superbapps.utils.vaadin.Trees.CustomObjectTree;
import org.superbapps.utils.vaadin.Trees.IUpdateData;

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
        createChildNodesForTheRoot(c, DS.getCRMController().getCRM_Processes(c));
    }
}
