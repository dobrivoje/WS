/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.City;
import db.ent.Customer;

/**
 *
 * @author root
 */
public interface ICustomer extends CRUDInterface<Customer> {

    public void addNewCustomer(String name, String address, City city, String PIB) throws Exception;

    public void updateCustomer(Long customerID, String name, String address, String PIB) throws Exception;
}
