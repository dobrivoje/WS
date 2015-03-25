/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.util.List;
import db.DBHandler;
import db.ent.Gallery;
import db.interfaces.IGalleryController;

/**
 *
 * @author root
 */
public class Gallery_Controller implements IGalleryController {

    private static DBHandler dbh;

    public Gallery_Controller(DBHandler dbh) {
        Gallery_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public Gallery getDefaultImageGallery() {
        return getByID(1L);
    }

    @Override
    public List<Gallery> getAll() {
        return dbh.getAllGalleries();
    }

    @Override
    public Gallery getByID(Long ID) {
        return dbh.getGallery(ID);
    }

    @Override
    public List<Gallery> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    @Override
    public void addNew(Gallery newGallery) throws Exception {
        dbh.addNewGallery(newGallery);
    }

    public void addNewGallery(String galleryName, String storeLocation) throws Exception {
        dbh.addNewGallery(galleryName, storeLocation);
    }

    @Override
    public void addNew(String galleryName, String storeLocation) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateExisting(Gallery object) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //</editor-fold>
}
