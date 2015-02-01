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

    COMING("Coming"),
    AVAILABLE("Available"),
    DISCONTINUED("Discontinued");

    private final String name;

    private Statuses(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
