/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.util.List;
import db.DBHandler;
import db.ent.BussinesLine;
import db.ent.Salesman;
import db.interfaces.ISalesman;

/**
 *
 * @author root
 */
public class Salesman_Controller implements ISalesman {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<Salesman> getAll() {
        return dbh.getAllSalesman();
    }

    @Override
    public Salesman getByID(Long customerID) {
        return dbh.getSalesman(customerID);
    }

    @Override
    public List<Salesman> getByName(String partialName) {
        return dbh.getSalesmanByName(partialName);
    }

    @Override
    public List<Salesman> getSalesmanBySurname(String partialSurname) {
        return dbh.getSalesmanBySurname(partialSurname);
    }

    @Override
    public List<Salesman> getAllActiveSalesman(boolean active) {
        return dbh.getAllSalesmanByActivity(active);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    @Override
    public void addNew(Salesman newSalesman) throws Exception {
        dbh.addNewSalesman(newSalesman);
    }

    @Override
    public void addNewSalesman(String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        dbh.addNewSalesman(name, surname, position, active, dateFrom, dateTo, BL);
    }

    @Override
    public void updateExisting(Salesman salesman) throws Exception {
        dbh.updateSalesman(salesman);
    }

    @Override
    public void updateSalesman(Long SalesmanID, String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        dbh.updateSalesman(SalesmanID, name, surname, position, active, dateFrom, dateTo, BL);
    }
    //</editor-fold>

}
