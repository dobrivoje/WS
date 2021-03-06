/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.City;
import db.interfaces.ICityController;
import java.util.List;

/**
 *
 * @author root
 */
public class City_Controller implements ICityController {

    private static DBHandler dbh;

    public City_Controller(DBHandler dbh) {
        City_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Customer read data">
    @Override
    public List<City> getAll() {
        return dbh.getAllCities();
    }

    @Override
    public City getByID(Long ID) {
        return dbh.getCity(ID);
    }

    @Override
    public List<City> getByName(String partialName) {
        return dbh.getCityByName(partialName);
    }

    @Override
    public List<City> getCityByContainingName(String partialName) {
        return dbh.getCityByContainingName(partialName);
    }

    @Override
    public List<City> getCityByMunicipality(String partialName) {
        return dbh.getCityByMunicipality(partialName);
    }

    @Override
    public List<City> getCityByDistrict(String partialName) {
        return dbh.getCityByDistrict(partialName);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    @Override
    public void addNew(City newObject) throws Exception {
        dbh.addNewCity(newObject);
    }

    @Override
    public void updateExisting(City object) throws Exception {
        dbh.updateExistingCity(object);
    }
    //</editor-fold>

}
