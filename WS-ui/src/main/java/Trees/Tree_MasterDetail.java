package Trees;

import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import org.superbapps.utils.vaadin.Exceptions.CustomTreeNodesEmptyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.superbapps.utils.vaadin.MyWindows.WindowFormProp.WINDOW_HEIGHT_DEFAULT_NORM;
import static org.superbapps.utils.vaadin.MyWindows.WindowFormProp.WINDOW_WIDTH_DEFAULT_NORM;
import org.superbapps.utils.vaadin.Forms.Form_CRUD2;

/**
 *
 * @author root
 */
public class Tree_MasterDetail extends Tree {

    //<editor-fold defaultstate="collapsed" desc="fields">
    protected VerticalLayout propPanel;
    protected List propTrees;

    protected String winFormCaption;
    protected Panel winFormPropPanel;

    protected Form_CRUD2 crudForm;

    protected boolean expandRootNodes;

    /**
     * Parameter to regulate Window carrier height.
     */
    protected int winFormHeight;

    /**
     * Parameter to regulate Window carrier width.
     */
    protected int winFormWidth;

    protected boolean readOnly = true;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="constructors">
    protected final void init(String caption) {
        clear();
        setCaption(caption);

        expandRootNodes = false;

        winFormHeight = WINDOW_HEIGHT_DEFAULT_NORM;
        winFormWidth = WINDOW_WIDTH_DEFAULT_NORM;

        propPanel = new VerticalLayout();
        propPanel.setMargin(true);
        propTrees = new ArrayList<>();
    }

    public Tree_MasterDetail(String caption) {
        init(caption);
    }

    /**
     * Custom tree creation with treeItems as root nodes list.
     *
     * @param caption Tree caption
     * @param treeModel Tree data model
     * @throws org.superbapps.utils.vaadin.Exceptions.CustomTreeNodesEmptyException
     */
    public Tree_MasterDetail(String caption, Map<Object, List> treeModel) throws CustomTreeNodesEmptyException, NullPointerException {
        this(caption, treeModel, false);
    }

    /**
     *
     * @param caption
     * @param treeModel
     * @param expandRootNodes
     * @throws CustomTreeNodesEmptyException
     * @throws NullPointerException
     */
    public Tree_MasterDetail(String caption, Map<Object, List> treeModel, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        init(caption);

        this.expandRootNodes = expandRootNodes;

        addItems(treeModel.keySet());
        createMasterDetail(treeModel, expandRootNodes);
    }

    /**
     * <b>Root nodes sub-nodes creation.</b>
     *
     * @param rootNode root node.
     * @param rootNodeChildItemsList List of the sub nodes for the root node.
     * @param expandRootNodes
     */
    protected void setNodeItems(Object rootNode, List rootNodeChildItemsList, boolean expandRootNodes) {
        for (Object childItem : rootNodeChildItemsList) {
            if (this.containsId(rootNode)) {
                addItem(childItem);
                setParent(childItem, rootNode);
                setChildrenAllowed(childItem, false);
            }
        }

        if (expandRootNodes) {
            this.expandRootNodes = expandRootNodes;

            expandItem(rootNode);
        }
    }

    /**
     *
     * @param treeModel Data model for the tree.
     * @param expandRootNodes
     */
    protected final void createMasterDetail(Map<Object, List> treeModel, boolean expandRootNodes) {
        treeModel.entrySet().stream().forEach((ES) -> {
            this.setNodeItems(ES.getKey(), ES.getValue(), expandRootNodes);
        });
    }

}
