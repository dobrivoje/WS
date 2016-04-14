/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.Gallery;

/**
 *
 * @author root
 */
public interface IGalleryController extends ICRUD<Gallery> {

    Gallery getDefaultImageGallery();

    void addNew(String galleryName, String storeLocation) throws Exception;

}
