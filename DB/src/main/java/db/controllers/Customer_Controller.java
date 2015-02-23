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
import db.ent.Fuelstation;
import db.ent.Owner;
import db.ent.RelCBType;
import db.interfaces.ICustomerController;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class Customer_Controller implements ICustomerController {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read data">
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

    @Override
    public List<Fuelstation> getAllCustomerFS(Customer customer) {
        List<Fuelstation> fs = new ArrayList<>();

        // List<Owner> customerOwned = new FSOwner_Controller().getAllFSOwnedByCustomer(customer);
        List<Owner> customerOwned = dbh.getAllFSOwnedByCustomer(customer);
        for (Owner o : customerOwned) {
            fs.add(o.getFkIdFs());
        }

        return fs;
    }

    @Override
    public List<RelCBType> getAllCustomerBussinesTypes(Customer customer) {
        return dbh.getAllCustomerBussinesTypes(customer);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
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
