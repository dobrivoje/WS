/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.chars.translators;

import com.ibm.icu.text.Transliterator;

/**
 *
 * @author Dobrivoje
 *
 * Klasa za bezbednu konverziju ćirilice u englesku latinicu ! Ulazni stringmože
 * biti YU latinica, ili ćirilica. Izlazni string je uvek eng. latinica.
 *
 * Klasa pomaže u situacijama kada treba obezbediti latinicni string, npr.
 * prilikom kreiranja foldera u fajl sistemu.
 */
public class CharsAdapter {

    private static final String TRANSLITERATE_ID = "Any-Latin; NFD; [^\\p{Alnum}] Remove";
    public static final String NORMALIZE_ID = "NFD; [:Nonspacing Mark:] Remove; NFC";

    /**
     * Metod za adaptaciju latiničnog stringa u latinični. Metod vraća ascii
     * reprezentaciju stringa bez znakova Š Đ Ž Č Ć
     *
     * @param textToAdapt
     * @return
     */
    public static String LatinUTFToASCII(String textToAdapt) {
        return java.text.Normalizer.normalize(
                textToAdapt, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("\\Đ", "Dj")
                .replaceAll("\\đ", "dj");
    }

    /**
     * Metod za adaptaciju ćirilično stringa u latinični. Metod vraća UTF
     * reprezentaciju stringa
     *
     * @param textToAdapt
     * @return
     */
    public static String CyrilicsToUTFLatin(String textToAdapt) {
        return Transliterator.getInstance(TRANSLITERATE_ID).transform(textToAdapt);

    }

    /**
     * Metod za adaptaciju stringa koji može biti ili ćirilični ili latinični.
     * Metod vraća ascii reprezentaciju stringa bez znakova Š Đ Ž Č Ć
     *
     * @param textToAdapt
     * @return
     */
    public static String safeAdapt(String textToAdapt) {
        return LatinUTFToASCII(CyrilicsToUTFLatin(textToAdapt));
    }
}
