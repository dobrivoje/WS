/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.Enums;

/**
 *
 * @author root
 */
public enum GalleryIdentifications {

    FS_IMAGE_GALLERY("1L"),
    FS_DOC_GALLERY("2L");

    private final String name;

    private GalleryIdentifications(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public long getGalleryID(GalleryIdentifications GI) {
        return Long.getLong(GI.toString());
    }
}
