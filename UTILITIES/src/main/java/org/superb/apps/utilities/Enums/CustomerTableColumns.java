/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.Enums;

/**
 *
 * @author Dobri
 */
public enum CustomerTableColumns {

    idc("CLIENT ID"),
    name("Black List!"),
    IN_PROGRESS("In progress.."),
    UNKNOWN("Unknown"),
    NO_LICENCE("No licence!");

    private final String name1;

    private CustomerTableColumns(String name1) {
        this.name1 = name1;
    }

    @Override
    public String toString() {
        return name1;
    }
}
