/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.List;

/**
 *
 * @author root
 */
public class Test1 {

    static dataservice.DataService DS = DataService.getDefault();

    public static void main(String[] args) {
        Fuelstation fs = DS.getFSController().getByID(1L);
        List<Owner> lo = fs.getOwnerList();

        FsProp fsProp = DS.getFSPROPController().getNewestFSPropForFS(fs);

        for (Owner o1 : lo) {
            System.err.println(o1.getFKIDCustomer().toString() + " - " + o1.getFkIdFs().getName());
        }

        System.err.println("fs.toString() : " + fs.toString());
        System.err.println("fsProp.toString() : " + fsProp.toString());

        // for (Customer arg : DS.getCustomerController().getAll()) {
        //     System.err.println(arg.toString());
        // }
    }

}
