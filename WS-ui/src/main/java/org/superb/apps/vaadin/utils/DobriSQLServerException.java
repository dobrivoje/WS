/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.vaadin.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author root
 */
public class DobriSQLServerException extends Exception {

    private static final Map<String, String> NULL_VALUE_DETECTED = new HashMap<>();
    private static final Map<String, String> PIB_MUST_BE_UNIQUE = new HashMap<>();
    private static final Map<String, String> CUSTOMER_PROBABLY_ALREADY_EXIST = new HashMap<>();

    public DobriSQLServerException() {
        NULL_VALUE_DETECTED.put("java.sql.SQLException: Cannot insert the value NULL into column",
                "NULL Value detected! ");

        PIB_MUST_BE_UNIQUE.put("Violation of UNIQUE KEY constraint \'CUSTOMER_PIB\'",
                "PIB MUST BE UNIQUE ! ");

        CUSTOMER_PROBABLY_ALREADY_EXIST.put("java.sql.SQLException: Violation of UNIQUE KEY constraint \'CUSTOMER_Unique_Data\'",
                "CUSTOMER PROBABLY ALREADY EXIST !!!");
    }
}
