/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.interfaces.ICBT;
import java.util.List;
import db.DBHandler;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import java.util.Date;

/**
 *
 * @author root
 */
public class CBT_Controller implements ICBT {

    private static final DBHandler dbh = DBHandler.getDefault();

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
    public void addNewCBT(Customer customer, CustomerBussinesType CBType, Date dateFrom, Date dateTo, boolean active)
            throws Exception {
        dbh.addNew_RelCBT(customer, CBType, dateFrom, dateTo, active);
    }

    @Override
    public void addNew(CustomerBussinesType customerBussinesType) throws Exception {
        dbh.addNewCustomerBussinesType(customerBussinesType);
    }

    @Override
    public void updateExisting(CustomerBussinesType customerBussinesType) throws Exception {
        dbh.updateCustomerBussinesType(customerBussinesType);
    }

    public void updateExisting(Long ID, Customer customer, CustomerBussinesType CBType, Date dateFrom, Date dateTo, boolean active)
            throws Exception {
        dbh.updateRelCBT(ID, customer, CBType, dateFrom, dateTo, active);
    }
    //</editor-fold>

}
