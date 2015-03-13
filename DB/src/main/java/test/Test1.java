/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.Fuelstation;

/**
 *
 * @author root
 */
public class Test1 {

    static dataservice.DataService DS = DataService.getDefault();

    public static void main(String[] args) {
        Fuelstation fs = DS.getFSController().getByID(2L);

        // System.err.println("getAllFSPropertiesByCustomer  : " + DS.getFSOController().getAllOwners(fs));
        // System.err.println("getNewestFSPropForFS  : " + DS.getFSPROPController().getNewestFSPropForFS(fs));
        /*
         try {
         DS.getFSOController().updateAllOwnerFSActiveFalse(fs);
         } catch (Exception ex) {
         Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        System.out.println(fs.toString());
        System.out.println(DS.getFSOController().getFSOwner(fs));
    }
}
