/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.controllers;

import java.util.List;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.Fuelstation;
import org.superb.apps.ws.db.functionalities.IFS;

/**
 *
 * @author root
 */
public class FS_Controller implements IFS {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read data">@Override
    @Override
    public List<Fuelstation> getAll() {
        return dbh.getAllFuelstation();
    }

    @Override
    public Fuelstation getByID(Long customerID) {
        return dbh.getFuelstationByID(customerID);
    }

    @Override
    public List<Fuelstation> getByName(String partialName) {
        return dbh.getFuelstationByName(partialName);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Write data">
    @Override
    public void addNew(Fuelstation newFS) throws Exception {
        dbh.addNewFS(newFS);
    }

    @Override
    public void addNew(String name, String address, String city, String coordinates) throws Exception {
        dbh.addNewFS(name, address, city, coordinates);
    }

    @Override
    public void updateExisting(Fuelstation fuelstation) throws Exception {
        dbh.updateFS(fuelstation);
    }

    @Override
    public void updateExisting(Long ID, String name, String address, String city, String coordinates) throws Exception {
        dbh.updateFS(ID, name, city, address, coordinates);
    }
    //</editor-fold>

}
