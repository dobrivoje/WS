/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.functionalities;

import java.util.List;
import org.superb.apps.ws.db.entities.Customer;

/**
 *
 * @author root
 */
public interface ICustomer extends CRUDInterface<Customer> {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    @Override
    public List<Customer> getAll();

    @Override
    public List<Customer> getByName(String partialName);

    @Override
    public Customer getByID(long ID);

    public List<Customer> getCustomerByCity(String partialCityName);

    public List<Customer> getCustomerByAddress(String partialAddress);

    public List<Customer> getCustomerByZIP(String zip);

    public List<Customer> getCustomerByRegion(String partialRegion);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    @Override
    public void addNew(Customer newObject) throws Exception;

    @Override
    public void updateExisting(Customer object) throws Exception;

    public void addNewCustomer(String name, String address, String city, String zip, String region, String PIB) throws Exception;

    public void updateExisting(Long customerID, String name, String address, String city, String zip, String region, String PIB) throws Exception;
    //</editor-fold>
}
