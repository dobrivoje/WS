/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.superb.apps.utilities.datum.Dates;

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

        //<editor-fold defaultstate="collapsed" desc="imagetypes - test ok">
        /*
         for (ImageTypes it : ImageTypes.values()) {
         System.err.println(it);
         System.err.println(it.name());
         }
        
         String hh = "\\\\imlbelgfile01.INTERMOL.SYS.CORP\\is\\Gallery\\FS\\BoskoPetrolDOO\\img\\werwe.jpg";
         System.err.println(ImageTypes.contains(hh) ? "sadrži jpeg !" : "ne sadrži.");
         System.err.println(hh.toLowerCase().endsWith("jpg"));
         */
        //</editor-fold>

        Dates d = new Dates();
        d.setFrom(7, 9, 1975);
        System.err.println("moj rođendan : " + d.getFrom());

        d.setFrom(18, 4, 1953);
        System.err.println("majkin rođendan : " + d.getFrom());

        d.setFrom(15, 4, 1979);
        System.err.println("krmetov rođendan : " + d.getFrom());

        d.setFrom(3, 10, 1948);
        System.err.println("kurjakov rođendan : " + d.getFrom());

        Dates d1 = new Dates(1);
        System.err.println("od : " + d1.getFrom());
        System.err.println("do : " + d1.getTo());

        Dates d2 = new Dates(-3);
        System.err.println("od : " + d2.getFrom());
        System.err.println("do : " + d2.getTo());

    }
}
