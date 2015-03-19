/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.Customer;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.Date;

/**
 *
 * @author root
 */
public class Test1 {

    static DataService DS = DataService.getDefault();

    public static void main(String[] args) {
        Fuelstation fs = DS.getFSController().getByID(1L);
        System.out.println("Fuelstation : " + fs.toString());

        Owner currentOwner = DS.getFSOController().getCurrentFSOwner(fs);
        System.err.println("currentOwner  : " + (currentOwner != null ? currentOwner.getFKIDCustomer().getName() : " n/a!"));

        FsProp currentFsProp = DS.getFSPROPController().getCurrentFSProp(currentOwner);
        System.err.println("currentFsProp  : " + (currentFsProp != null
                ? currentFsProp.getPropertiesDate() + ", "
                + (currentFsProp.getActive() ? "active" : "not active") + ", "
                + (currentFsProp.getCarWash() ? "car wash" : "car wash-na") + ", "
                + (currentFsProp.getNoOfTanks() != null ? "NoOfTanks=" + currentFsProp.getNoOfTanks() : "NoOfTanks n/a") + ", "
                + "TruckCapable=" + currentFsProp.getTruckCapable()
                : " n/a!"));

        Owner o1 = DS.getFSOController().getByID(52L);
        Owner newOwner = new Owner(o1);
        System.err.println("o1==newOwner ? " + (o1 == newOwner));
        System.err.println("");

        System.err.println("o1  : " + (o1 != null
                ? o1.getDateFrom() + ", "
                + (o1.getActive() ? " o1 active" : " o1 not active") + ", "
                + (o1.getFKIDCustomer() != null ? " o1 Cust:" + o1.getFKIDCustomer() : " o1 cust n/a")
                : " o1 n/a!"));

        System.err.println("newOwner  : " + (o1 != null
                ? newOwner.getDateFrom() + ", "
                + (newOwner.getActive() ? " newOwner active" : " newOwner not active") + ", "
                + (newOwner.getFKIDCustomer() != null ? " newOwner Cust:" + newOwner.getFKIDCustomer() : " newOwner cust n/a")
                : " newOwner n/a!"));

        FsProp fsProp = new FsProp();

        try {
            fsProp = DS.getFSPROPController().getCurrentFSProp(o1);
        } catch (Exception e) {
        }

        fsProp.setActive(false);
        fsProp.setPropertiesDate(new Date());

        System.err.println("fsProp : " + fsProp.toString());
        
        
        FsProp fsProp1 = DBHandler.getDefault().getCurrentFSProp(currentOwner);
        System.err.println("feprop1 : "+fsProp1);
    }
}
