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
    private static String errorMessage;

    public static void main(String[] args) {
        Customer c = customerController.getCustomerByID(6L);

        c.setName("Livnica Sevojno");
        c.setCity("Sevojno");
        c.setAddress("Vuka Karadžića bb");
        c.setZip("31410");
        c.setRegion("Zlatiborski okrug");

        try {
            customerController.updateCustomer(c);
        } catch (Exception e) {
            if (e.toString().contains("java.sql.SQLException: Cannot insert the value NULL into column")) {
                errorMessage = "NULL Value detected! ";
            }

            if (e.toString().contains("Violation of UNIQUE KEY constraint \'CUSTOMER_PIB\'")) {
                errorMessage += "PIB MUST BE UNIQUE ! ";
            }

            if (e.toString().contains("java.sql.SQLException: Violation of UNIQUE KEY constraint \'CUSTOMER_Unique_Data\'")) {
                errorMessage += "CUSTOMER PROBABLY ALREADY EXIST !!!";
            }
        } finally {
            if (!errorMessage.isEmpty()) {
                System.err.println("Error: " + errorMessage);
            }
        }
    }
}
