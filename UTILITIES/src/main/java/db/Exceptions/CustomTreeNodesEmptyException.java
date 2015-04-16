/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.Exceptions;

/**
 *
 * @author root
 */
public class CustomTreeNodesEmptyException extends Exception {

    public CustomTreeNodesEmptyException() {
        super();
    }

    @Override
    public String getMessage() {
        return "No nodes for the tree !";
    }

}
