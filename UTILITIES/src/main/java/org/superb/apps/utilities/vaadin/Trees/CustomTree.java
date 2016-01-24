/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.Trees;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import db.Exceptions.CustomTreeNodesEmptyException;
import java.util.ArrayList;
import java.util.List;
import static org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp.WINDOW_HEIGHT_DEFAULT_NORM;
import static org.superb.apps.utilities.vaadin.MyWindows.WindowFormProp.WINDOW_WIDTH_DEFAULT_NORM;

/**
 *
 * @author Dobri
 * @param <T>
 */
public class CustomTree<T> extends Tree {

    protected VerticalLayout propPanel;
    protected List propTrees;

    protected String winFormCaption;
    protected Panel winFormPropPanel;

    /**
     * Parameter to regulate Window carrier height.
     */
    protected int winFormHeight;

    /**
     * Parameter to regulate Window carrier width.
     */
    protected int winFormWidth;

    protected boolean readOnly = true;

    protected boolean expandRootNodes = false;

    /**
     * <b>elements</b> - List of the root nodes elements for this Custom tree.
     */
    protected List<T> elements;

    private void init(String caption) {
        setCaption(caption);
        elements = new ArrayList();

        if (items.size() > 0) {
            items.removeAllItems();
        }

        winFormHeight = WINDOW_HEIGHT_DEFAULT_NORM;
        winFormWidth = WINDOW_WIDTH_DEFAULT_NORM;

        propPanel = new VerticalLayout();
        propPanel.setMargin(true);
        propTrees = new ArrayList<>();
    }

    public CustomTree(String caption) {
        init(caption);
    }

    public CustomTree(String caption, boolean expandRootNodes) {
        this.expandRootNodes = expandRootNodes;
        init(caption);
    }

    /**
     * Custom tree creation with treeItems as root nodes list.
     *
     * @param caption Tree caption
     * @param rootItems root nodes list
     * @throws db.Exceptions.CustomTreeNodesEmptyException
     */
    public CustomTree(String caption, List rootItems) throws CustomTreeNodesEmptyException, NullPointerException {
        this(caption, rootItems, false);
    }

    public CustomTree(String caption, List rootItems, boolean expandRootNodes) throws CustomTreeNodesEmptyException, NullPointerException {
        this.expandRootNodes = expandRootNodes;
        createRoots(caption, rootItems);
    }

    protected final void createRoots(String caption, List rootItems) throws UnsupportedOperationException, CustomTreeNodesEmptyException, NullPointerException {
        if (rootItems == null) {
            throw new NullPointerException();
        }

        if (rootItems.isEmpty()) {
            throw new CustomTreeNodesEmptyException();
        }

        init(caption);
        addItems(rootItems);

        elements.clear();
        elements.addAll(rootItems.subList(0, rootItems.size()));
    }

    /**
     * <p>
     * Create this tree with nodes from the supplied bean container.</p>
     * Very useful in the cases where real-time updates are needed in the UI.
     *
     * @param caption
     * @param container BeanContainer
     * @throws db.Exceptions.CustomTreeNodesEmptyException
     */
    public CustomTree(String caption, BeanItemContainer<T> container) throws CustomTreeNodesEmptyException, NullPointerException {
        if (container == null) {
            throw new NullPointerException();
        }

        if (container.size() <= 0) {
            throw new CustomTreeNodesEmptyException();
        }

        init(caption);
        setContainerDataSource(container);

        elements.clear();
        elements.addAll(container.getItemIds());
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
            expandItem(rootNode);
        }
    }

    protected void setNodeItems(Object rootNode, List rootNodeChildItemsList) {
        this.setNodeItems(false, rootNodeChildItemsList, readOnly);
    }

}
