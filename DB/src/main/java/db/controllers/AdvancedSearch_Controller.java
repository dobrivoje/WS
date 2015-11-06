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
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import db.interfaces.IAdvancedSearchController;
import java.util.List;
import java.util.Map;

/**
 *
 * @author root
 */
public class AdvancedSearch_Controller implements IAdvancedSearchController<CustomSearchData> {

    private static DBHandler dbh;

    public AdvancedSearch_Controller(DBHandler dbh) {
        AdvancedSearch_Controller.dbh = dbh;
    }

    @Override
    public List<RelSALE> getAllSales(CustomSearchData csd) {
        return dbh.getAllSales(csd);
    }

    @Override
    public List<CrmCase> getAllCrmCases(CustomSearchData csd) {
        return dbh.getAllCrmCases(csd);
    }

    @Override
    public List<CrmProcess> getAllCrmProcesses(CustomSearchData csd) {
        return dbh.getAllCrmProcesses(csd);
    }

    @Override
    public Map<Salesman, List<RelSALE>> getAllSalesrepSales(CustomSearchData csd) {
        return dbh.getAllSalesrepSales(csd);
    }

}
