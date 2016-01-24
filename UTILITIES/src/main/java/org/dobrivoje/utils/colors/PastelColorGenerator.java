/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.colors;

import java.util.Arrays;
import java.util.List;

public class PastelColorGenerator implements IColorGenerator {

    private final float opacity;

    // definition for the visually appealing pastel colors.
    // may be maintained over time by simple addition in the array..
    public static final String[] COLORS = new String[]{
        "898CFF",
        "FF89B5",
        "FFDC89",
        "90D4F7",
        "71E096",
        "F5A26F",
        "668DE5",
        "ED6D79",
        "5AD0E5",
        "DA97E0",
        "CFF381",
        "FF96E3",
        "BB96FF",
        "67EEBD",
        "1F7399",
        "66ACCC",
        "F8FCDC",
        "F6BFC4",
        "F09AAD",
        "B5E0F3",
        "87A9D8",
        "647BB1"
    };

    public PastelColorGenerator(float opacity) {
        this.opacity = opacity;
    }

    public PastelColorGenerator() {
        this.opacity = 0.6f;
    }

    @Override
    public List generateRGBColor(int index) {

        try {
            int red = Integer.parseInt(COLORS[index].substring(0, 2), 16);
            int green = Integer.parseInt(COLORS[index].substring(2, 4), 16);
            int blue = Integer.parseInt(COLORS[index].substring(4, 6), 16);

            return Arrays.asList(red, green, blue, opacity);
        } catch (Exception e) {
            return null;
        }
    }

}
