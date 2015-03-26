/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.chars.translators;

public class Converter {

    public enum ConvertType {

        LAT2CYR, CYR2LAT
    }

    private String[] listLat = null;
    private String[] listCyr = null;

    public Converter() {
        initList();
    }

    private void initList() {
        String lat = "DŽ LJ NJ A B C Č Ć D Đ E F G H I J K L M N O P R S Š T U V Z Ž";
        lat += " " + lat.toLowerCase();
        lat += " Dž Lj Nj";

        String cyr = "Џ Љ Њ А Б Ц Ч Ћ Д Ђ Е Ф Г Х И Ј К Л М Н О П Р С Ш Т У В З Ж";
        cyr += " " + cyr.toLowerCase();
        cyr += " Џ Љ Њ";

        listLat = lat.split(" ");
        listCyr = cyr.split(" ");
    }

    public String convertText(String line, ConvertType type) {

        int i = 0;

        if (type == ConvertType.LAT2CYR) {
            for (String item : listLat) {
                line = line.replaceAll(item, listCyr[i]);
                i++;
            }
        } else if (type == ConvertType.CYR2LAT) {
            for (String item : listCyr) {
                line = line.replaceAll(item, listLat[i]);
                i++;
            }
        }

        return line;
    }

}
