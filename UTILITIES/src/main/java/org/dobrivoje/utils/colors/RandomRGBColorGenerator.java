/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.colors;

import java.util.Arrays;
import java.util.List;

public class RandomRGBColorGenerator implements IColorGenerator {

    private final float opacity;

    public RandomRGBColorGenerator() {
        this.opacity = 0.6f;
    }

    public RandomRGBColorGenerator(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public List generateRGBColor(int index) {
        try {
            return Arrays.asList(
                    (int) (255 * Math.random()),
                    (int) (255 * Math.random()),
                    (int) (255 * Math.random()),
                    opacity
            );
        } catch (Exception e) {
            return null;
        }
    }

}
