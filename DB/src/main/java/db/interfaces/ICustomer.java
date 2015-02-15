/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;
import db.ent.Customer;

/**
 *
 * @author root
 */
public interface ICustomer extends CRUDInterface<Customer> {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    public List<Customer> getCustomerByCity(String partialCityName);

    public List<Customer> getCustomerByRegion(String partialRegion);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    public void addNewCustomer(String name, String address, String city, String zip, String region, String PIB) throws Exception;

    public void updateCustomer(Long customerID, String name, String address, String city, String zip, String region, String PIB) throws Exception;
    //</editor-fold>
}
