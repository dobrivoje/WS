package org.dobrivoje.utils.colors;

import java.util.List;

public interface IColorGenerator {

    List generateRGBColor(int colorIndex) throws IndexOutOfBoundsException;

}
