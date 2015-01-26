/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.superb.apps.ws.controllers.CustomerBussinesType_Controller;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;
import org.superb.apps.ws.functionalities.ICustomerBussinesType;

/**
 *
 * @author root
 */
public class Test2 {

    private static final ICustomerBussinesType CBTController = new CustomerBussinesType_Controller();

    public static void main(String[] args) {
        CustomerBussinesType cbt = DBHandler.getDefault().getCustomerBussinesType(1);

        for (Customer c : CBTController.getAllCustomersForBussinesType(1)) {
            System.err.println(c.toString());
        }
    }
}
