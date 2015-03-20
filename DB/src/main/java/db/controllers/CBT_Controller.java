/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.interfaces.ICBTController;
import java.util.List;
import db.DBHandler;
import db.ent.Customer;
import db.ent.CustomerBussinesType;

/**
 *
 * @author root
 */
public class CBT_Controller implements ICBTController {

    private static DBHandler dbh;

    public CBT_Controller(DBHandler dbh) {
        CBT_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read Data">
    @Override
    public List<Customer> getAllCustomersForBussinesType(Long IDCustomerBussinesType) {
        return dbh.getAllCustomersForBussinesType(getByID(IDCustomerBussinesType));
    }

    @Override
    public List<Customer> getAllCustomersForBussinesType(CustomerBussinesType customerBussinesType) {
        return dbh.getAllCustomersForBussinesType(customerBussinesType);
    }

    @Override
    public List<CustomerBussinesType> getAll() {
        return dbh.getAllCustomerBussinesTypes();
    }

    @Override
    public CustomerBussinesType getByID(Long ID) {
        return dbh.getCustomerBussinesType(ID);
    }

    @Override
    public List<CustomerBussinesType> getByName(String partialName) {
        return dbh.getAllCustomerBussinesTypes(partialName);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    @Override
    public void addNew(CustomerBussinesType customerBussinesType) throws Exception {
        dbh.addNewCustomerBussinesType(customerBussinesType);
    }

    @Override
    public void updateExisting(CustomerBussinesType customerBussinesType) throws Exception {
        dbh.updateCustomerBussinesType(customerBussinesType);
    }
    //</editor-fold>
}
