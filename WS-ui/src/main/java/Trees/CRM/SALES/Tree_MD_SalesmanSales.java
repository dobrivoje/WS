/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trees.CRM.SALES;

import static Main.MyUI.DS;
import db.Exceptions.CustomTreeNodesEmptyException;
import Trees.Tree_MasterDetail;
import db.ent.custom.CustomSearchData;

/**
 *
 * @author root
 */
public class Tree_MD_SalesmanSales extends Tree_MasterDetail {

    public Tree_MD_SalesmanSales(CustomSearchData csd, boolean formAllowed)
            throws CustomTreeNodesEmptyException, NullPointerException {

        super("", DS.getCRMController().getCRM_MD_CRM_Sales(csd.getSalesman(), csd.getStartDate(), csd.getEndDate()));

    }

}
