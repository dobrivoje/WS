/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import java.util.List;
import db.DBHandler;
import db.ent.Document;
import db.ent.DocumentType;
import db.ent.Fuelstation;
import db.ent.Gallery;
import db.interfaces.IDocumentController;
import db.interfaces.IDocumentTypeController;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class Document_Controller implements IDocumentController, IDocumentTypeController {

    private static DBHandler dbh;

    public Document_Controller(DBHandler dbh) {
        Document_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<Document> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Document getByID(Long ID) {
        return dbh.getDocument(ID);
    }

    @Override
    public List<Document> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Document> getDocumentsByGallery(Gallery gallery) {
        return dbh.getAllDocumentsByGallery(gallery);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    @Override
    public void addNew(Document newObject) throws Exception {
        dbh.addNewDocument(newObject);
    }

    @Override
    public Document addNewDocument(Gallery gallery, String name, Serializable docData, String docLocation, Date uploadDate, String docType) throws Exception {
        return dbh.addNewDocument(gallery, name, docData, docLocation, uploadDate, docType);
    }

    @Override
    public void updateExisting(Document document) throws Exception {
        dbh.updateDocument(document);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Document type">
    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return dbh.getAllDocumentTypes();
    }

    @Override
    public DocumentType getDocumentType(long ID) {
        return dbh.getDocumentType(ID);
    }

    @Override
    public DocumentType getImageDocumentType() {
        return dbh.getDocumentType(1L);
    }

    @Override
    public DocumentType getDocDocumentType() {
        return dbh.getDocumentType(2L);
    }

    @Override
    public DocumentType getMMDocumentType() {
        return dbh.getDocumentType(3L);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FS Documents Handler">
    @Override
    public List<Document> getAllFSDocuments(Fuelstation fuelstation) {
        return dbh.getAllFSDocuments(fuelstation);
    }

    @Override
    public Document getFSImage(Fuelstation fuelstation) {
        return dbh.getFSDefaultImage(fuelstation, true);
    }

    @Override
    public void addNewFSDocument(Fuelstation fuelstation, Document document, Date docDate, boolean defaultDocument) throws Exception {
        dbh.addNewFSDocument(fuelstation, document, docDate, defaultDocument);
    }
    //</editor-fold>

}
