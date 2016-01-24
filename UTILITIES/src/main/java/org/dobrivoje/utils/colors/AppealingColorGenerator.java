/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.colors;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AppealingColorGenerator implements IColorGenerator {

    private final Color baseColor;
    private final float opacity;

    private static final Color[] COLOR_BASE = new Color[]{Color.BLUE, Color.GRAY, Color.RED, Color.GREEN, Color.ORANGE};

    public AppealingColorGenerator(Color baseColor, float opacity) {
        this.baseColor = baseColor;
        this.opacity = opacity;
    }

    public AppealingColorGenerator() {
        this.baseColor = COLOR_BASE[(int) (COLOR_BASE.length * Math.random())];
        this.opacity = 0.6f;
    }

    @Override
    public List generateRGBColor(int index) {
        try {
            Random random = new Random();

            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);

            red = (red + baseColor.getRed()) / 2;
            green = (green + baseColor.getGreen()) / 2;
            blue = (blue + baseColor.getBlue()) / 2;

            return Arrays.asList(red, green, blue, opacity);
        } catch (Exception e) {
            return null;
        }
    }

}
