/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.functionalities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.superb.apps.ws.db.entities.Gallery;
import org.superb.apps.ws.db.entities.Image;

/**
 *
 * @author root
 */
public interface IGalleryImage {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    public List<Gallery> getAllGalleries();

    public Gallery getGallery(int IDGallery);

    public Image getImage(long IDImage);
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    public void addNewGallery(String galleryName) throws Exception;

    public void addNewImage(Gallery gallery, String imageName, Serializable imageData, Date imageUploadDate) throws Exception;

    //</editor-fold>
}
