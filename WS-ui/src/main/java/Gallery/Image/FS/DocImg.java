/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.Image.FS;

import com.vaadin.ui.Image;
import db.ent.Document;
import Gallery.common.IDocBeans;

/**
 *
 * @author root
 */
public class DocImg implements IDocBeans<Image, Document> {

    private Image image;
    private Document doc;

    public DocImg(Image image, Document doc) {
        this.image = image;
        this.doc = doc;
    }

    @Override
    public Document getBean2() {
        return doc;
    }

    @Override
    public void setBean1(Image bean) {
        this.image = bean;
    }

    @Override
    public Image getBean1() {
        return image;
    }

    @Override
    public void setBean2(Document bean) {
        this.doc = bean;
    }
};
