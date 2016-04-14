/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dobrivoje.utils.excel.exceptions;

/**
 *
 * @author DPrtenjak
 */
public class NoExcelFileException extends Exception {

    public NoExcelFileException() {
        super("File is not Excel 2003 or greater version !");
    }

    public NoExcelFileException(String message) {
        super(message);
    }

}
