/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.TMarginWHS;
import db.interfaces.ICRUD2;
import java.util.List;

/**
 *
 * @author root
 */
public class TMarginWHS_Controller implements ICRUD2<TMarginWHS> {

    private static DBHandler dbh;

    public TMarginWHS_Controller(DBHandler dbh) {
        TMarginWHS_Controller.dbh = dbh;
    }

    @Override
    public List<TMarginWHS> getAll() {
        return dbh.get_All_TMarginWHS();
    }

    @Override
    public TMarginWHS getByID(Long ID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TMarginWHS> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addNew(TMarginWHS newTMarginWHS) throws Exception {
        dbh.addNew_TMarginWHS(newTMarginWHS);
    }

    @Override
    public void updateExisting(TMarginWHS object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(long index) throws Exception {
        dbh.delete_TMarginWHS(index);
    }

    @Override
    public void deleteAll() throws Exception {
        dbh.deleteAll_TMarginWHS();
    }

    @Override
    public void addAll(List list) throws Exception {
        dbh.addAll_TMarginWHS(list);
    }

}
