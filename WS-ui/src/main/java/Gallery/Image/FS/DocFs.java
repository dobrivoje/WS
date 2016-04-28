/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.Image.FS;

import com.vaadin.ui.Image;
import db.ent.Document;
import Gallery.common.IDocBean;
import static Main.MyUI.DS;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import db.ent.Fuelstation;
import java.io.File;

/**
 *
 * @author root
 */
public class DocFs implements IDocBean<Fuelstation> {

    private Fuelstation fs;
    private Document doc;

    public DocFs(Fuelstation fs, Document doc) {
        this.fs = fs;
        this.doc = doc;
    }
    
    public DocFs(Fuelstation fs) {
        this.fs = fs;
        this.doc = DS.getDocumentController().getDefaultFSImage(fs);
    }
    

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
    public void setBean(Fuelstation fs) {
        this.fs = fs;
    }

    @Override
    public Fuelstation getBean() {
        return this.fs;
    }

    @Override
    public Image getImageRepresentation() {
        Image image;

        try {
            Document defDoc = DS.getDocumentController().getDefaultFSImage(fs);

            image = new Image("", new FileResource(new File(defDoc.getAbsolutePath(true))));
            image.setDescription("Double click to open\nan Image Gallery.");

        } catch (Exception e) {
            image = new Image(null, new ThemeResource("img/fs/1.png"));
        }

        return image;
    }

    @Override
    public void setDocument(Document bean) {
        this.doc = bean;
    }
};
