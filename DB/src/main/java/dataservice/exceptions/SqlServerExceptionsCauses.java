/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataservice.exceptions;

/**
 *
 * @author Dobri
 */
public enum SqlServerExceptionsCauses {

    NULL_VALUE_DETECTED("java.sql.SQLException: Cannot insert the value NULL into column"),
    ALREADY_EXISTS("Violation of UNIQUE KEY constraint"),
    PIB_ALREADY_EXISTS("Violation of UNIQUE KEY constraint \'CUSTOMER_PIB\'"),
    CUSTOMER_BUSSINES_TYPE_ALREADY_EXISTS(" Violation of UNIQUE KEY constraint 'UNIQUE_Rel_CBType'"),
    CUSTOMER_PROBABLY_ALREADY_EXISTS("java.sql.SQLException: Violation of UNIQUE KEY constraint \'CUSTOMER_Unique_Data\'");

    private final String name;

    private SqlServerExceptionsCauses(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
