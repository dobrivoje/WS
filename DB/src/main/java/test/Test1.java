/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import db.controllers.Customer_Controller;
import db.ent.Customer;
import db.interfaces.ICustomerController;

/**
 *
 * @author root
 */
public class Test1 {

    public static void main(String[] args) {
        ICustomerController fsc = new Customer_Controller();
        Customer c = fsc.getByID(13L);

        System.err.println(fsc.getAllCustomerBussinesTypes(c));
    }

}
