/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import java.util.Date;

/**
 *
 * @author root
 */
public interface ICBT extends CRUDInterface<CustomerBussinesType> {

    List<Customer> getAllCustomersForBussinesType(Long cBTID);

    List<Customer> getAllCustomersForBussinesType(CustomerBussinesType cBT);

    void addNewCBT(Customer customer, CustomerBussinesType CBType, Date dateFrom, Date dateTo, boolean active)
            throws Exception;
}
