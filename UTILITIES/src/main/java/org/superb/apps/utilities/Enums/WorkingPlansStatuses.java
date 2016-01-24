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
public enum WorkingPlansStatuses {

    FINISHED("OK"),
    IN_PROGRESS("In progress.."),
    UNKNOWN("Unknown"),
    //
    FINISHED_COLOR("#2dd085"),
    IN_PROGRESS_COLOR("#F3A344"),
    UNKNOWN_COLOR("#aa66aa");

    private final String name;

    private WorkingPlansStatuses(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
