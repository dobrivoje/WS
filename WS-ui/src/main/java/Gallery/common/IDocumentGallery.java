/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery.common;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * Interfejs za kreiranje galerije za univerzalni tip podataka.
 *
 * @param <T>
 */
public interface IDocumentGallery<T> {

    /**
     *
     * @param customObject
     * @param height
     * @param width
     * @param defaultDocument
     * @return
     */
    Component createDocument(T customObject, float height, float width, boolean defaultDocument);

    /**
     * <p>
     * Kreiraj glavni dokument koji reprezentuje klasu T.</p>
     * Npr. podrazumevana slika galerije slika.
     *
     * @param component
     * @return
     */
    VerticalLayout createMainDocLayout(Component component);

    /**
     * <p>
     * Kreiraj glavni dokument koji reprezentuje klasu T.</p>
     * Npr. podrazumevana slika galerije slika.
     *
     * @param customObject
     * @param uploaderOnForm - Dugme kojim upload-ujemo dokument u bazu !
     * @return
     */
    VerticalLayout createDocumentGallery(T customObject, boolean uploaderOnForm);

    void openDocumentGalleryWindow(String caption, T customObject);

    List getAllDocuments(T customObject);

}
