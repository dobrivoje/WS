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
public class AddTest {

    private static final ICustomer customerController = new CustomerController();

    public static void main(String[] args) {
        Customer c1 = new Customer();
        String errorMessage = "";
        int errorID = 1;

        c1.setName("KRMAČA Produkt");
        c1.setCity("Požega");
        c1.setAddress("Neznanih budala 111");
        c1.setZip("31230");
        c1.setRegion("Zlatiborski okrug");
        c1.setPib("4444444444");

        try {
            customerController.addNewCustomer(c1);
        } catch (Exception e) {
            if (e.toString().contains("java.sql.SQLException: Cannot insert the value NULL into column")) {
                errorMessage = "NULL Value detected! ";
                errorID <<= 1;
            }

            if (e.toString().contains("Violation of UNIQUE KEY constraint \'CUSTOMER_PIB\'")) {
                errorMessage += "PIB MUST BE UNIQUE ! ";
                errorID <<= 1;
            }

            if (e.toString().contains("java.sql.SQLException: Violation of UNIQUE KEY constraint \'CUSTOMER_Unique_Data\'")) {
                errorMessage += "CUSTOMER PROBABLY ALREADY EXIST !!!";
                errorID <<= 1;
            }
        } finally {
            if (!errorMessage.isEmpty()) {
                System.err.println("Error: " + errorMessage);
                System.err.println("Error ID: " + errorID);
            }
        }
    }
}
