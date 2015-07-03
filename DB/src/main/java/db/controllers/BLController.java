/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.BussinesLine;
import db.ent.City;
import db.interfaces.IBLController;
import db.interfaces.ICityController;
import java.util.List;

/**
 *
 * @author root
 */
public class BLController implements IBLController {

    private static DBHandler dbh;

    public BLController(DBHandler dbh) {
        BLController.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Customer read data">
    @Override
    public BussinesLine getByID(Long ID) {
        return dbh.getBussinesLine(ID);
    }

    @Override
    public List<BussinesLine> getAll() {
        return dbh.getBussinesLines();
    }

    @Override
    public List<BussinesLine> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    @Override
    public void addNew(BussinesLine newObject) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateExisting(BussinesLine object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>

}
