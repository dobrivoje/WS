/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.Customer;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import db.interfaces.IAdvancedSearchController;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public List getCRMCases(CustomSearchData csd) {
        return dbh.getCRMCases(csd);
    }

    @Override
    public Map<Object, List> getSalesrepSales(CustomSearchData csd) {
        return dbh.getSalesrepSales(csd);
    }

    @Override
    public Map<Object, List> getSalesrepCRMCases(CustomSearchData csd) {
        return dbh.getSalesrepCRMCases(csd);
    }

    @Override
    public Map<Object, List> getCustomerCRMCases(CustomSearchData criteria) {
        return dbh.getCustomerCRMCases(criteria);
    }

    @Override
    public Set<Customer> getCustomers(CustomSearchData csd) {
        return dbh.getCustomers(csd);
    }

    @Override
    public Set<Salesman> getSalesreps(CustomSearchData csd) {
        return dbh.getSalesreps(csd);
    }

}
