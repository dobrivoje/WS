/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.common;

import com.vaadin.ui.Image;
import db.ent.Document;

/**
 *
 * @author root
 */
public interface IDocBean<T> {

    void setBean(T bean);

    T getBean();
    
    Image getImageRepresentation();

    void setDocument(Document bean);

    Document getDocument();

}
