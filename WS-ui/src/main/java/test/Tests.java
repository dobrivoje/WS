/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Tables.GENTable;
import db.ent.Document;
import db.ent.Fuelstation;
import java.io.File;
import org.dobrivoje.utils.chars.translators.CharsAdapter;
import org.superb.apps.utilities.Enums.ImageTypes;
import org.superb.apps.utilities.os.OS;
import static ws.MyUI.DS;

/**
 *
 * @author dprtenjak
 */
public class Tests {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Fuelstation f = DS.getFSController().getByID(1L);

        String path = DS.getGalleryController().getDefaultImageGallery().getStoreLocation()
                + CharsAdapter.safeAdapt(f.getName())
                + OS.getSeparator()
                + DS.getDocumentTypeController().getImageDocumentType().getDocType()
                + OS.getSeparator();

        System.err.println("file path : " + path);

        String parent1 = new File(path).getParent();
        String parent2 = new File(parent1).getParent();
        System.err.println("file path - dir up : " + parent1);
        System.err.println("file path - dir up : " + parent2);

        for (Document d : DS.getDocumentController().getAllFSDocuments(f)) {
            //System.err.println(d.getAbsolutePath(true));

            if (ImageTypes.contains(d.getName().toLowerCase())) {
                System.err.println("OK !!!  OK !!!  OK !!!  OK !!!  ");
            }
        }

        final String imgGalleryLoc = DS.getGalleryController().getDefaultImageGallery().getStoreLocation();
        final String docType = DS.getDocumentTypeController().getImageDocumentType().getDocType();

        final String absPath = imgGalleryLoc + CharsAdapter.safeAdapt(f.getName()) + OS.getSeparator() + docType + OS.getSeparator();

        System.err.println("putanja : "+absPath);
    }
}
