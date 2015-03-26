/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.superb.apps.utilities.Enums.ImageTypes;

/**
 *
 * @author root
 */
public class Test {

    public static void main(String[] args) {
        //<editor-fold defaultstate="collapsed" desc="Test - OK">
        /*
         System.err.println(CharsAdapter.CyrilicsToUTFLatin("Бошко Петрол"));
         System.err.println(CharsAdapter.CyrilicsToUTFLatin("Миле Kачавенда"));
         System.err.println(CharsAdapter.CyrilicsToUTFLatin("Љубица Ђенадић"));

         System.err.println(CharsAdapter.LatinUTFToASCII("Boško Petrol"));
         System.err.println(CharsAdapter.LatinUTFToASCII("Mile Kačavenda"));
         System.err.println(CharsAdapter.LatinUTFToASCII("Ljubica Đenadić"));

         System.err.println(CharsAdapter.safeAdapt("Бошко Петрол Š Đ Ž Ć Č "));
         System.err.println(CharsAdapter.safeAdapt("Миле Kачавенда"));
         System.err.println(CharsAdapter.safeAdapt("Љубица Ђенадић"));

         System.err.println(CharsAdapter.safeAdapt("Š Đ Ž Ć Č"));
         System.err.println(CharsAdapter.safeAdapt("Ђ Ш Ж Ћ Ч Ѕ Џ Љ Њ"));
         System.err.println(CharsAdapter.safeAdapt("Džemal Uskokovi ŠćĐ đČ"));
         */
        //</editor-fold>

        for (ImageTypes it : ImageTypes.values()) {
            System.err.println(it);
            System.err.println(it.name());
        }

        String hh = "\\\\imlbelgfile01.INTERMOL.SYS.CORP\\is\\Gallery\\FS\\BoskoPetrolDOO\\img\\werwe.jpg";

        System.err.println(ImageTypes.contains(hh) ? "sadrži jpeg !" : "ne sadrži.");

        System.err.println(hh.toLowerCase().endsWith("jpg"));

    }
}
