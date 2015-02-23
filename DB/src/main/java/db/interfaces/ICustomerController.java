/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.City;
import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.RelCBType;
import java.util.List;

/**
 *
 * @author root
 */
public interface ICustomerController extends CRUDInterface<Customer> {

    List<Fuelstation> getAllCustomerFS(Customer customer);

    List<RelCBType> getAllCustomerBussinesTypes(Customer customer);

    void addNewCustomer(String name, String address, City city, String PIB) throws Exception;

    void updateCustomer(Long customerID, String name, String address, String PIB) throws Exception;
}
