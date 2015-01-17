/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.functionalities;

import java.util.List;
import org.superb.apps.ws.db.entities.Customer;

/**
 *
 * @author root
 */
public interface ICustomer {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    public List<Customer> getAllCustomers();

    public Customer getCustomerByID(Long customerID);

    public List<Customer> getCustomerByName(String partialName);

    public List<Customer> getCustomerByCity(String partialCityName);

    public List<Customer> getCustomerByAddress(String partialAddress);

    public List<Customer> getCustomerByZIP(String zip);

    public List<Customer> getCustomerByRegion(String partialRegion);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    public void addNewCustomer(Customer newCustomer) throws Exception;

    public void addNewCustomer(String name, String address, String city, String zip, String region, String PIB) throws Exception;

    public void updateCustomer(Customer customer) throws Exception;

    public void updateCustomer(Long customerID, String name, String address, String city, String zip, String region, String PIB) throws Exception;
    //</editor-fold>
}
