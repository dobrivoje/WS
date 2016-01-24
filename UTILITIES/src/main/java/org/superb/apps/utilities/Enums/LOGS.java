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
public enum LOGS {

    LOGIN("app login"),
    LOGIN_WRONG("app login wrong"),
    LOGOUT("app logout"),
    DATA_SEARCH("data search"),
    DB_INSERT("db table insert"),
    DB_UPDATE("db table update"),
    ACTION("action");

    private final String name;

    private LOGS(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
