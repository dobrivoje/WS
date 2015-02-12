/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.functionalities;

import java.util.List;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;

/**
 *
 * @author root
 */
public interface ICBT extends CRUDInterface<CustomerBussinesType>{

    List<Customer> getAllCustomersForBussinesType(int IDCustomerBussinesType);

    List<Customer> getAllCustomersForBussinesType(CustomerBussinesType customerBussinesType);

    void addNewCBT(Customer customer, CustomerBussinesType CBType, String dateFrom, String dateTo, boolean active)
            throws Exception;
}
