/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.util.List;
import db.DBHandler;
import db.ent.InfSysUser;
import db.ent.Log;
import db.interfaces.ILOGController;
import java.util.Date;

/**
 *
 * @author root
 */
public class LOG_Controller implements ILOGController {
    
    private static DBHandler dbh;
    
    public LOG_Controller(DBHandler dbh) {
        LOG_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read Data">
    @Override
    public Log getByID(Long ID) {
        return dbh.getLogByID(ID);
    }
    
    @Override
    public List<Log> getLogByInfSysUser(InfSysUser isu) {
        return dbh.getLogByInfSysUser(isu);
    }
    
    @Override
    public List<Log> getLogByInfSysUser(InfSysUser isu, Date dateFrom, Date dateTo) {
        return dbh.getLogByInfSysUser(isu, dateFrom, dateTo);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    @Override
    public void addNew(Date logDate, String actionCode, String description, InfSysUser infSysUser) throws Exception {
        dbh.addNewLog(logDate, actionCode, description, infSysUser);
    }
    
    @Override
    public void addNew(Log newObject) throws Exception {
        dbh.addNewLog(newObject);
    }
    //</editor-fold>

}
