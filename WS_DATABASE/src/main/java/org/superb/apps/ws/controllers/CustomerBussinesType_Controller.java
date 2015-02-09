/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.controllers;

import org.superb.apps.ws.functionalities.ICustomerBussinesType;
import java.util.List;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;

/**
 *
 * @author root
 */
public class CustomerBussinesType_Controller implements ICustomerBussinesType {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read Data">
    @Override
    public List<Customer> getAllCustomersForBussinesType(int IDCustomerBussinesType) {
        return dbh.getCustomerBussinesType(IDCustomerBussinesType)
                .getAllCustomersForBussinesType();
    }

    @Override
    public List<Customer> getAllCustomersForBussinesType(CustomerBussinesType customerBussinesType) {
        return dbh.getCustomerBussinesType(customerBussinesType.getIdcbt()).getAllCustomersForBussinesType();
    }

    @Override
    public List<CustomerBussinesType> getAllBussinesTypes() {
        return dbh.getAllCustomerBussinesTypes();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    @Override
    public void addNewCBT(Customer customer, CustomerBussinesType CBType, String dateFrom, String dateTo, boolean active)
            throws Exception {
        dbh.addNew_CBT_CUSTOMER(customer, CBType, dateFrom, dateTo, active);
    }

    @Override
    public void addNewCBT(CustomerBussinesType customerBussinesType) throws Exception {
        dbh.addNewCustomerBussinesType(customerBussinesType);
    }

    @Override
    public void updateNewCBT(CustomerBussinesType customerBussinesType) throws Exception {
        dbh.updateCustomerBussinesType(customerBussinesType);
    }
    //</editor-fold>
}
