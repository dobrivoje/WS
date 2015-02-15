/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.util.List;
import db.DBHandler;
import db.ent.Fuelstation;
import db.interfaces.IFS;

/**
 *
 * @author root
 */
public class FS_Controller implements IFS {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Customer read data">@Override
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

    //<editor-fold defaultstate="collapsed" desc="Customer add/update data">
    @Override
    public void addNew(Fuelstation newFS) throws Exception {
        dbh.addNewFS(newFS);
    }

    @Override
    public void addNew(String name, String address, String city, String coordinates) throws Exception {
        dbh.addNewFS(name, city, address, coordinates);
    }

    @Override
    public void updateExisting(Fuelstation fuelstation) throws Exception {
        dbh.addNewFS(fuelstation);
    }

    @Override
    public void updateExisting(int ID, String name, String address, String city, String coordinates) throws Exception {
        dbh.addNewFS(name, city, address, coordinates);
    }
    //</editor-fold>

}
