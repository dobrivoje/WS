/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM.SALES;

import db.Exceptions.CustomTreeNodesEmptyException;
import db.ent.CrmCase;
import db.ent.Salesman;
import java.util.Date;
import org.superb.apps.utilities.vaadin.Trees.CustomObjectTree;
import static ws.MyUI.DS;

/**
 *
 * @author root
 */
public class SalesmanSales_Tree extends CustomObjectTree<CrmCase> {

    private final Date dateFrom;
    private final Date dateTo;

    public SalesmanSales_Tree(String caption, Salesman salesman, Date dateFrom, Date dateTo, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {
        super(caption, DS.getCRMController().getCRM_CompletedCases(salesman, true, true));

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    protected void createSubNodes(CrmCase cc) {
        createNodeItems(cc, DS.getCRMController().getCRM_Sales(cc, dateFrom, dateTo));
    }

}
