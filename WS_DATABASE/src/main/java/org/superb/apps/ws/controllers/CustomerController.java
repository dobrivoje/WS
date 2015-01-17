/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.controllers;

import java.util.List;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.functionalities.ICustomer;

/**
 *
 * @author root
 */
public class CustomerController implements ICustomer {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Customer read data">
    @Override
    public List<Customer> getAllCustomers() {
        return dbh.getAllCustomers();
    }

    @Override
    public Customer getCustomerByID(Long customerID) {
        return dbh.getCustomerByID(customerID);
    }

    @Override
    public List<Customer> getCustomerByName(String partialName) {
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

    //<editor-fold defaultstate="collapsed" desc="Customer add/update data">
    @Override
    public void addNewCustomer(Customer newCustomer) throws Exception {
        dbh.addNewCustomer(newCustomer);
    }

    @Override
    public void addNewCustomer(String name, String address, String city, String zip, String region, String PIB) throws Exception {
        dbh.addNewCustomer(name, address, city, zip, region, PIB);
    }

    @Override
    public void updateCustomer(Long customerID, String name, String address, String city, String zip, String region, String PIB) throws Exception {
        dbh.updateCustomer(customerID, name, address, city, zip, region, PIB);
    }

    @Override
    public void updateCustomer(Customer customer) throws Exception {
        dbh.updateCustomer(customer);
    }
    //</editor-fold>
}
