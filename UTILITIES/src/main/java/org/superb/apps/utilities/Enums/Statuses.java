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
    UNKNOWN("Unknown"),
    NO_LICENCE("No licence!"),
    //
    OK_COLOR("#2dd085"),
    BLACK_LIST_COLOR("#222222"),
    IN_PROGRESS_COLOR("#F3A344"),
    UNKNOWN_COLOR("#aa66aa"),
    NO_LICENCE_COLOR("#f54993");

    private final String name;

    private Statuses(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
