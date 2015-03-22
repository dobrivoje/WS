/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.Document;
import db.ent.Gallery;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public interface IDocumentController extends CRUDInterface<Document> {

    List<Document> getDocumentsByGallery(Gallery gallery);

    void addNewDocument(String name, String location, Serializable binaryContent, Date uploadDate, Gallery gallery) throws Exception;
}
