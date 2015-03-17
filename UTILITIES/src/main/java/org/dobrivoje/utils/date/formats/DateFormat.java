/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.date.formats;

/**
 *
 * @author root
 */
public enum DateFormat {

    DATE_FORMAT_ENG("yyyy-MM-dd"),
    DATETIME_FORMAT_ENG("yyyy-MM-dd hh:mm:ss"),
    DATE_FORMAT_SRB("dd.MM.yyyy"),
    DATETIME_FORMAT_SRB("dd.MM.yyyy hh:mm:ss");

    private final String name;

    private DateFormat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
