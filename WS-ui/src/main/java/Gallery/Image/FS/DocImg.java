/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.Image.FS;

import com.vaadin.ui.Image;
import db.ent.Document;
import Gallery.common.IDocBean;

/**
 *
 * @author root
 */
public class DocImg implements IDocBean<Image> {

    private Image image;
    private Document doc;

    public DocImg(Image image, Document doc) {
        this.image = image;
        this.doc = doc;
    }

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
    public void setBean(Image bean) {
        this.image = bean;
    }

    @Override
    public Image getBean() {
        return image;
    }

    @Override
    public Image getImageRepresentation() {
        return image;
    }

    @Override
    public void setDocument(Document bean) {
        this.doc = bean;
    }
};
