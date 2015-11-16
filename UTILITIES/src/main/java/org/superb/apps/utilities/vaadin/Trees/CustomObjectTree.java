package org.superb.apps.utilities.vaadin.Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.vaadin.Forms.Form_CRUD2;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

/**
 * <p>
 * <b>CustomObjectTree</b> class has root nodes of the type T.</p>
 * For every root node, there is a list with it's own sub-nodes.
 *
 * @param <T>
 */
public abstract class CustomObjectTree<T> extends CustomTree<T> implements IRefreshVisualContainer {

    protected Date dateFrom;
    protected Date dateTo;

    protected List rootNodeSubList;

    protected Form_CRUD2 crudForm;

    public CustomObjectTree(String caption, List rootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, rootNodes);
        init();
    }

    /**
     * Create a tree with root node and list of its sub-nodes.
     *
     * @param caption
     * @param rootNode
     * @param rootNodeSubList
     * @throws CustomTreeNodesEmptyException
     * @throws NullPointerException
     */
    public CustomObjectTree(String caption, T rootNode, List rootNodeSubList) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(rootNode));
        this.rootNodeSubList = rootNodeSubList;

        super.setNodeItems(rootNode, rootNodeSubList);
    }

    /**
     *
     * @param caption
     * @param customTree This is a Map<T, List> for which we create custom tree.
     * @throws CustomTreeNodesEmptyException
     * @throws NullPointerException
     */
    public CustomObjectTree(String caption, Map<T, List<? extends Object>> customTree) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption);
        init();
        createCustomTree(customTree);
    }

    /**
     * Create a tree with list of the root nodes and date interval.
     *
     * @param caption
     * @param rootNodes
     * @param dateFrom
     * @param dateTo
     * @throws CustomTreeNodesEmptyException
     * @throws NullPointerException
     */
    public CustomObjectTree(String caption, List rootNodes, Date dateFrom, Date dateTo) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, rootNodes);

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        init();
    }
    
    //<editor-fold defaultstate="collapsed" desc="init">
    private void init() {
        if (!elements.isEmpty()) {
            elements.stream().forEach((e) -> {
                createSubNodes(e);
            });
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createSubNodes">
    /**
     * For all defined root nodes, dynamically create<br>
     * all child nodes for each root node !<br>
     * How sub-nodes list is generated, is defined in the extended class<br>
     *
     * @param t This is the one of the list of root nodes,<br>
     * for which we want to create it's own list of sub-nodes.
     */
    protected abstract void createSubNodes(T t);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createSingleRootChildNodes">
    /**
     * Create this tree with <b>single</b> type T root node,<br>
     * with it's sub-nodes list "subList".
     *
     * @param root root node.
     * @param rootChildListNodes root's nodes sub-list.
     */
    protected void createSingleRootChildNodes(T root, List rootChildListNodes) {
        super.setNodeItems(root, rootChildListNodes);
    }

    private void createCustomTree(Map<T, List<? extends Object>> customTree) {
        customTree.entrySet().stream().forEach((ES) -> {
            super.setNodeItems(ES.getKey(), ES.getValue());
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="refreshVisualContainer">
    @Override
    public void refreshVisualContainer() {
        init();
    }
    //</editor-fold>

}
