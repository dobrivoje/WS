/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.functionalities;

import java.util.List;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;

/**
 *
 * @author root
 */
public interface ICustomerBussinesType {

    List<Customer> getAllCustomersForBussinesType(int IDCustomerBussinesType);

    List<CustomerBussinesType> getAllBussinesTypes();

}
