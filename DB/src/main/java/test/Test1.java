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
        Fuelstation fs = DS.getFSController().getByID(1L);

        System.out.println(fs.toString());
        
        try {
            System.out.println(DS.getFSOController().getFSOwner(fs).toString());
        } catch (Exception e) {
        }
        
        System.err.println("getNewestFSPropForFS  : " + DS.getFSPROPController().getNewestFSPropForFS(fs));
    }
}
