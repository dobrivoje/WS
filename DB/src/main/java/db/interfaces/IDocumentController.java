/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.Document;
import db.ent.Fuelstation;
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

    Document addNewDocument(Gallery gallery, String name, Serializable docData, String docLocation, Date uploadDate, String docType) throws Exception;

    //<editor-fold defaultstate="collapsed" desc="FS Document Handler">
    /**
     * <b>Lista svih dokumenata FS.</b>
     * <p>
     * Dokumenta mogu biti bilo koji fajlovi, s tim da u ovom projektu dokumente
     * delimo na slike, office dokumenta, pdf, i eventualno multimedijalni
     * sadržaji.</p>
     *
     * @param fuelstation
     * @return
     */
    List<Document> getAllFSDocuments(Fuelstation fuelstation);

    /**
     * <b>Podrazumevana slika stanice</b>.
     * <p>
     * Ako slika stanice ne postoji vraća se podrazumevana slika iz webinf
     * resursa.
     * </p>
     *
     * @param fuelstation
     * @return
     */
    Document getDefaultFSImage(Fuelstation fuelstation);

    public void addNewFSDocument(Fuelstation fuelstation, Document document, Date docDate, boolean defaultDocument, int prioroty) throws Exception;

    //</editor-fold>
}
