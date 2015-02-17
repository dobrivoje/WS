/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.util.List;
import db.DBHandler;
import db.ent.City;
import db.ent.Customer;
import db.interfaces.ICustomer;

/**
 *
 * @author root
 */
public class Customer_Controller implements ICustomer {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Customer read data">
    @Override
    public List<Customer> getAll() {
        return dbh.getAllCustomers();
    }

    @Override
    public Customer getByID(Long ID) {
        return dbh.getCustomerByID(ID);
    }

    @Override
    public List<Customer> getByName(String partialName) {
        return dbh.getCustomerByName(partialName);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Customer add/update data">
    @Override
    public void addNewCustomer(String name, String address, City city, String PIB) throws Exception {
        dbh.addNewCustomer(name, address, city, PIB);
    }

    @Override
    public void updateCustomer(Long customerID, String name, String address, String PIB) throws Exception {
        dbh.updateCustomer(customerID, name, address, PIB);
    }

    @Override
    public void addNew(Customer newObject) throws Exception {
        dbh.addNewCustomer(newObject);
    }

    @Override
    public void updateExisting(Customer object) throws Exception {
        dbh.updateCustomer(object);
    }
    //</editor-fold>
}
