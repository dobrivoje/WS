/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.Customer;

/**
 *
 * @author root
 */
public class Test1 {

    static dataservice.DataService DS = DataService.getDefault();

    public static void main(String[] args) {
        for (Customer arg : DS.getCustomerController().getAll()) {
            System.err.println(arg.toString());
        }

    }

}
