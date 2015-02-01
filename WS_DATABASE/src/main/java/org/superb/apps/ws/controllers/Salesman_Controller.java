/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.controllers;

import java.util.List;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.BussinesLine;
import org.superb.apps.ws.db.entities.Salesman;
import org.superb.apps.ws.functionalities.ISalesman;

/**
 *
 * @author root
 */
public class Salesman_Controller implements ISalesman {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public Salesman getSalesmanByID(int customerID) {
        return dbh.getSalesman(customerID);
    }

    @Override
    public List<Salesman> getSalesmanByName(String partialName) {
        return dbh.getSalesmanByName(partialName);
    }

    @Override
    public List<Salesman> getSalesmanBySurname(String partialSurname) {
        return dbh.getSalesmanBySurname(partialSurname);
    }

    @Override
    public List<Salesman> getSalesmanByPosition(String partialPosition) {
        return dbh.getSalesmanByPosition(partialPosition);
    }

    @Override
    public List<Salesman> getAllActiveSalesman(boolean active) {
        return dbh.getAllSalesmanByActivity(active);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    @Override
    public void addNewSalesman(Salesman newSalesman) throws Exception {
        dbh.addNewSalesman(newSalesman);
    }

    @Override
    public void addNewSalesman(String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        dbh.addNewSalesman(name, surname, position, active, dateFrom, dateTo, BL);
    }

    @Override
    public void updateSalesman(Salesman salesman) throws Exception {
        dbh.updateSalesman(salesman);
    }

    @Override
    public void updateSalesman(int SalesmanID, String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        dbh.updateSalesman(SalesmanID, name, surname, position, active, dateFrom, dateTo, BL);
    }
    //</editor-fold>
}
