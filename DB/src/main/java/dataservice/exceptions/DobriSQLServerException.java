/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataservice.exceptions;

import static dataservice.exceptions.SqlServerExceptionsCauses.ALREADY_EXISTS;

/**
 *
 * @author root
 */
public class DobriSQLServerException extends Exception {

    public DobriSQLServerException() {
        super();
    }

    @Override
    public String getMessage() {
        if (super.getMessage().contains(ALREADY_EXISTS.toString())) {
            return ALREADY_EXISTS.name();
        }
        return super.getMessage();
    }

}
