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
import java.util.Date;

/**
 *
 * @author root
 */
public class Test1 {

    static dataservice.DataService DS = DataService.getDefault();

    public static void main(String[] args) {
        Fuelstation fs = DS.getFSController().getByID(1L);
        System.out.println("Fuelstation : " + fs.toString());

        Owner currentOwner = DS.getFSOController().getCurrentFSOwner(fs);
        System.err.println("currentOwner  : " + currentOwner != null ? currentOwner.getFKIDCustomer().getName() : " n/a!");

        FsProp currentFsProp = DS.getFSPROPController().getCurrentFSProp(currentOwner);
        System.err.println(currentFsProp == null ? "currentFsProp : n/a!" : currentFsProp.toString());

        FsProp newFsProp = new FsProp();

        if (currentFsProp != null) {
            currentFsProp.setActive(false);
            currentFsProp.setLicDateTo(new Date());
            // DS.getFSPROPController().updateExisting(existingFsProp);

            newFsProp.setNewFsProp(currentFsProp);
            newFsProp.setLicDateFrom(new Date());
            newFsProp.setLicDateTo(null);
        }

        newFsProp.setPropertiesDate(new Date());
        newFsProp.setActive(true);
        // DS.getFSPROPController().addNew(newFsProp);

        System.err.println("stari property : " + currentFsProp.toString()
                + ", from: " + currentFsProp.getLicDateFrom() + " - " + currentFsProp.getLicDateTo());
        System.err.println("novi property : " + newFsProp.toString()
                + ", from: " + newFsProp.getLicDateFrom() + " - " + newFsProp.getLicDateTo());
    }
}
