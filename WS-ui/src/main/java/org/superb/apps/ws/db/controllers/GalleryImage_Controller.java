/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.Gallery;
import org.superb.apps.ws.db.entities.Image;
import org.superb.apps.ws.db.entities.RelSALESMANIMAGE;
import org.superb.apps.ws.db.entities.Salesman;
import org.superb.apps.ws.db.functionalities.IGalleryImage;
import org.superb.apps.ws.db.functionalities.ISalesmanImage;

/**
 *
 * @author root
 */
public class GalleryImage_Controller implements IGalleryImage, ISalesmanImage {

    private final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<Gallery> getAllGalleries() {
        return dbh.getAllGalleries();
    }

    @Override
    public Gallery getGallery(Long ID) {
        return dbh.getGallery(ID);
    }

    @Override
    public Image getImage(Long ID) {
        return dbh.getImage(ID);
    }

    @Override
    public List<RelSALESMANIMAGE> getAllSalesmanImages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RelSALESMANIMAGE getSalesmanImage(Salesman salesman) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update">
    @Override
    public void addNewGallery(String galleryname) throws Exception {
        dbh.addNewGallery(galleryname);
    }

    @Override
    public void addNewImage(Gallery gallery, String imageName, Serializable imageData, Date imageUploadDate) throws Exception {
        dbh.addNewImage(gallery, imageName, imageData, imageUploadDate);
    }

    @Override
    public void addNewSalesmanImage(Salesman salesman, Image image) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //</editor-fold>
}
