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
public enum CrudOperations {

    CREATE("Create"),
    READ("Read"),
    UPDATE("Update"),
    DELETE("Delete"),
    //
    BUTTON_CAPTION_UPDATE("Save modifications"),
    BUTTON_CAPTION_NEW("Insert new data");

    private final String name;

    private CrudOperations(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
