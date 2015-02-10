/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.Views;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.TextField;

/**
 *
 * @author dprtenjak
 */
public class ResetButtonForTextField extends AbstractExtension {

    public static void extend(TextField field) {
        new ResetButtonForTextField().extend((AbstractClientConnector) field);
    }
}
