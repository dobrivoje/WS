/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;
import db.ent.Customer;
import db.ent.CustomerBussinesType;

/**
 *
 * @author root
 */
public interface ICBTController extends ICRUD<CustomerBussinesType> {

    List<Customer> getAllCustomersForBussinesType(Long cBTID);

    List<Customer> getAllCustomersForBussinesType(CustomerBussinesType cBT);
}
