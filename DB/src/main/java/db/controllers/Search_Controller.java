/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.RelSALE;
import db.ent.custom.CustomSearchData;
import db.interfaces.ISearchController;
import java.util.List;

/**
 *
 * @author root
 */
public class Search_Controller implements ISearchController<CustomSearchData> {

    private static DBHandler dbh;

    public Search_Controller(DBHandler dbh) {
        Search_Controller.dbh = dbh;
    }

    @Override
    public List<RelSALE> getAllSales(CustomSearchData csd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CrmCase> getAllCrmCases(CustomSearchData csd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CrmProcess> getAllCrmProcesses(CustomSearchData csd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
