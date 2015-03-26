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
public enum ImageTypes {

    BMP("image/bmp"),
    BMP_E("bmp"),
    GIF("image/gif"),
    GIF_E("gif"),
    JPG("image/jpeg"),
    JPG_E("jpeg"),
    JPG1("image/jpg"),
    JPG1_E("jpg"),
    TIFF("image/tiff"),
    TIFF_E("tiff"),
    PNG("image/png"),
    PNG_E("png");

    private final String name;

    private ImageTypes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(String imgType) {
        boolean r = false;

        for (ImageTypes it : values()) {
            if (imgType.toLowerCase().contains(it.toString())) {
                r = true;
                break;
            }
        }

        return r;
    }
}
