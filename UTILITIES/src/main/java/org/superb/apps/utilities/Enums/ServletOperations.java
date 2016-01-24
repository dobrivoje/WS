/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.utilities.Enums;

public enum ServletOperations {

    SERVLET_CREATION("SERVLET_SESSION_CREATION___SHIRO_ACCESS_CONTROL"),
    SERVLET_DESTRUCTION("SERVLET_SESSION_DESTRUCTION___SHIRO_ACCESS_CONTROL");

    private final String name;

    private ServletOperations(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
