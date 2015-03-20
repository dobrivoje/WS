/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.RelCBType;
import db.interfaces.IRELCBTController;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class RelCBT_Controller implements IRELCBTController {

    private static DBHandler dbh;

    public RelCBT_Controller(DBHandler dbh) {
        RelCBT_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read Data">
    @Override
    public List<RelCBType> getAll() {
        return dbh.getAllRelCBT();
    }

    @Override
    public RelCBType getByID(Long ID) {
        return dbh.getRelCBType(ID);
    }

    @Override
    public List<RelCBType> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    @Override
    public void addNew(Customer customer, CustomerBussinesType customerBussinesType, Date dateFrom, Date dateTo, boolean active) throws Exception {
        dbh.addNewRelCBT(customer, customerBussinesType, dateFrom, dateTo, active);
    }

    @Override
    public void addNew(RelCBType newObject) throws Exception {
        dbh.addNewRelCBT(newObject);
    }

    @Override
    public void updateExisting(RelCBType object) throws Exception {
        dbh.updateRelCBT(object);
    }
    //</editor-fold>
}
