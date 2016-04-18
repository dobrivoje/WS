/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gallery;

import Gallery.Image.DocImg;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * Interfejs za kreiranje galerije slika za univerzalni tip podataka.
 */
public interface IDocumentGallery<T> {

    /**
     *
     * @param customObject
     * @param height
     * @param width
     * @return
     */
    Component createDocument(T customObject, float height, float width);

    /**
     * <p>
     * Kreiraj glavni dokument koji reprezentuje klasu T.</p>
     * Npr. podrazumevana slika galerije slika.
     *
     * @param component
     * @return
     */
    VerticalLayout createMainDocument(Component component);

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

    List<DocImg> getAllDocuments(T customObject);

}
