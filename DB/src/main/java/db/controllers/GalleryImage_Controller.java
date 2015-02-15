/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import db.DBHandler;
import db.ent.Gallery;
import db.ent.Image;
import db.ent.RelSALESMANIMAGE;
import db.ent.Salesman;

/**
 *
 * @author root
 */
public class GalleryImage_Controller {

    private final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read data">
    public List<Gallery> getAllGalleries() {
        return dbh.getAllGalleries();
    }

    public Gallery getGallery(Long IDGallery) {
        return dbh.getGallery(IDGallery);
    }

    public Image getImage(Long IDImage) {
        return dbh.getImage(IDImage);
    }

    public List<RelSALESMANIMAGE> getAllSalesmanImages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RelSALESMANIMAGE getSalesmanImage(Salesman salesman) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    public void addNewGallery(String galleryname) throws Exception {
        dbh.addNewGallery(galleryname);
    }

    public void addNewImage(Gallery gallery, String imageName, Serializable imageData, Date imageUploadDate) throws Exception {
        dbh.addNewImage(gallery, imageName, imageData, imageUploadDate);
    }

    public void addNewSalesmanImage(Salesman salesman, Image image) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //</editor-fold>
}
