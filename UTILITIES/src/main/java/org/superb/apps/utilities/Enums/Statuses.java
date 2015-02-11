/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.Enums;

/**
 *
 * @author root
 */
public enum Statuses {

    OK("OK"),
    BLACK_LIST("Black List!"),
    IN_PROGRESS("In progress.."),
    NO_LICENCE("No licence!");

    private final String name;

    private Statuses(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
