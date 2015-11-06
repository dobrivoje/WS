package org.superb.apps.utilities.vaadin.Trees;

import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.superb.apps.utilities.vaadin.Tables.IRefreshVisualContainer;

/**
 * <p>
 * <b>CustomDateTree</b> class has root nodes of the type T.</p>
 * For every root node, there is a list with it's sub-nodes.
 *
 * @param <T>
 */
public abstract class CustomObjectTree<T> extends CustomTree<T> implements IRefreshVisualContainer {

    protected Date dateFrom;
    protected Date dateTo;
    protected boolean caseFinished;
    
    protected List rootNodeSubList;

    public CustomObjectTree(String caption, List rootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, rootNodes);
        init();
    }

    /**
     * Create a tree with root node and list of its subnodes.
     *
     * @param caption
     * @param rootNode
     * @param rootNodeSubList
     * @throws CustomTreeNodesEmptyException
     * @throws NullPointerException
     */
    public CustomObjectTree(String caption, T rootNode, List rootNodeSubList) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, Arrays.asList(rootNode));
        super.setNodeItems(rootNode, rootNodeSubList);
        
        this.rootNodeSubList = rootNodeSubList;
    }

    public CustomObjectTree(String caption, List rootNodes, Date dateFrom, Date dateTo) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, rootNodes);

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        init();
    }

    public CustomObjectTree(String caption, List rootNodes, boolean caseFinished) throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, rootNodes);

        this.caseFinished = caseFinished;

        init();
    }

    //<editor-fold defaultstate="collapsed" desc="init">
    private void init() {
        if (!elements.isEmpty()) {
            for (T e : elements) {
                createSubNodes(e);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createSubNodes">
    /**
     * <p>
     * <b>createSubNodes</b> Dynamically create child nodes.</p>
     * Used in the init() method, in order to check each "t" root node.
     *
     *
     * @param t
     */
    protected abstract void createSubNodes(T t);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createNodeItems">
    /**
     * <p>
     * Create this tree with root node "t" of the type T, with it's sub-nodes
     * list "subList".</p>
     *
     * @param t root node.
     * @param subList root's nodes sub-list.
     */
    protected void createNodeItems(T t, List subList) {
        super.setNodeItems(t, subList);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="refreshVisualContainer">
    @Override
    public void refreshVisualContainer() {
        init();
    }
    //</editor-fold>

}
