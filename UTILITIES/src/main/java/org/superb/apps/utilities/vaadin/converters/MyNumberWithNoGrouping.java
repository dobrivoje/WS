/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.converters;

import com.vaadin.data.util.converter.StringToDoubleConverter;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Dobri
 */
public class MyNumberWithNoGrouping extends StringToDoubleConverter {

    /**
     * Sprečavanje formiranje grupisanja cifara u broju pošto se javljaju greške
     * kod unosa decimalnog broja. Kada se unese veliki broj, posle commit-a se
     * dodaju 1000 separatori u broju u vidu zareza i onda dolazi do greške.
     *
     * Da bi se sprečile greške, potrebno je isključiti grupisanje.
     */
    public MyNumberWithNoGrouping() {
    }

    @Override
    protected NumberFormat getFormat(Locale locale) {
        NumberFormat nf = super.getFormat(locale);
        nf.setGroupingUsed(false);

        return nf;
    }

}
