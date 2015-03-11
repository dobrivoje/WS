/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.vaadin.validators;

import com.vaadin.data.Validator;

/**
 *
 * @author dprtenjak
 */
public class MyNumberValidator implements Validator {

    @Override
    public void validate(Object value) throws InvalidValueException {
        String text = (String) value;

        if (text == null || "".equals(text.trim())) {
            throw new InvalidValueException("Must enter at least zero !");
        }
        if (!text.matches("\\d*")) {
            throw new InvalidValueException("Just numbers allowed !");
        }
    }

}
