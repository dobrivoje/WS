/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.superb.apps.ws.db.controllers.FS_Controller;
import org.superb.apps.ws.db.entities.Fuelstation;

/**
 *
 * @author root
 */
public class Test2 {

    public static void main(String[] args) {

        Fuelstation fs;

        fs = new Fuelstation();
        fs.setName("Petrol & Gas");
        fs.setAddress("Uplašenih hajduka BB");
        fs.setCity("Tihi Gaj");

        try {
            new FS_Controller().addNew(fs);
        } catch (Exception ex) {
        }

        for (Fuelstation f : new FS_Controller().getAll()) {
            System.err.print(f.toString());
        }

    }
}
