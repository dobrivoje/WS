/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.superb.apps.ws.controllers.CustomerController;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.functionalities.ICustomer;

/**
 *
 * @author root
 */
public class UpdateTest {

    private static final ICustomer customerController = new CustomerController();

    public static void main(String[] args) {
        Customer c2 = customerController.getCustomerByID(6L);

        c2.setName("Livnica Sevojno");
        c2.setCity("Sevojno");
        c2.setAddress("Vuka Karadžića bb");
        c2.setZip("31410");
        c2.setRegion("Zlatiborski okrug");

        try {
            customerController.updateCustomer(c2);
        } catch (Exception ex) {
            System.err.println("Greška! Nije ažuriran postojeći kupac.");
        }
    }
}
