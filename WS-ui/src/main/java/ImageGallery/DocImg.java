/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageGallery;

import com.vaadin.ui.Image;
import db.ent.Document;

/**
 *
 * @author root
 */
public class DocImg {

    private final Image image;
    private final Document doc;

    public DocImg(Image image, Document doc) {
        this.image = image;
        this.doc = doc;
    }

    public Image getImage() {
        return image;
    }

    public Document getDoc() {
        return doc;
    }
};
