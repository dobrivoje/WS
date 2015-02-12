/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.controllers;

import java.util.List;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.functionalities.ICustomer;

/**
 *
 * @author root
 */
public class Customer_Controller implements ICustomer {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<Customer> getAll() {
        return dbh.getAllCustomers();
    }

    @Override
    public Customer getByID(long ID) {
        return dbh.getCustomerByID(ID);
    }

    @Override
    public List<Customer> getByName(String partialName) {
        return dbh.getCustomerByName(partialName);
    }

    @Override
    public List<Customer> getCustomerByCity(String partialCityName) {
        return dbh.getCustomerByCity(partialCityName);
    }

    @Override
    public List<Customer> getCustomerByAddress(String partialAddress) {
        return dbh.getCustomerByAddress(partialAddress);
    }

    @Override
    public List<Customer> getCustomerByZIP(String zip) {
        return dbh.getCustomerByZIP(zip);
    }

    @Override
    public List<Customer> getCustomerByRegion(String partialRegion) {
        return dbh.getCustomerByRegion(partialRegion);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Write data">
    @Override
    public void addNew(Customer newCustomer) throws Exception {
        dbh.addNewCustomer(newCustomer);
    }

    @Override
    public void addNewCustomer(String name, String address, String city, String zip, String region, String PIB) throws Exception {
        dbh.addNewCustomer(name, address, city, zip, region, PIB);
    }

    @Override
    public void updateExisting(Long customerID, String name, String address, String city, String zip, String region, String PIB) throws Exception {
        dbh.updateCustomer(customerID, name, address, city, zip, region, PIB);
    }

    @Override
    public void updateExisting(Customer customer) throws Exception {
        dbh.updateCustomer(customer);
    }
    //</editor-fold>
}
