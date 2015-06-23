/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageGallery;

import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * Interfejs za kreiranje galerije slika za univerzalni tip podataka.
 */
public interface IImageGallery<T> {

    /**
     *
     * @param customObject
     * @param height
     * @param width
     * @return
     */
    Image createObjectImage(T customObject, float height, float width);

    /**
     * Za bean T, kreiraj glavnu sliku koja reprezentuje T.
     *
     * @param customObject
     * @return
     */
    VerticalLayout createMainImage(T customObject);

    VerticalLayout createImageGallery(T customObject, boolean uploaderOnForm);

    void openObjectGalleryWindow(String caption, T customObject);

    List<DocImg> getAllImages(T customObject);

}
