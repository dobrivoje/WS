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
import db.interfaces.ISalesmanController;

/**
 *
 * @author root
 */
public class Salesman_Controller implements ISalesmanController {

    private static DBHandler dbh;

    public Salesman_Controller(DBHandler dbh) {
        Salesman_Controller.dbh = dbh;
    }

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
    public List<Salesman> getSalesman(BussinesLine bl) {
        return dbh.getSalesman(bl);
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
    //</editor-fold>

}
