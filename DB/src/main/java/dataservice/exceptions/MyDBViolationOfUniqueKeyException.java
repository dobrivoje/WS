/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataservice.exceptions;

/**
 *
 * @author root
 */
public class MyDBViolationOfUniqueKeyException extends Exception {

    public MyDBViolationOfUniqueKeyException(String message) {
        super(message);
    }
}
