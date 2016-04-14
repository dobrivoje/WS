/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

/**
 *
 * @author root
 */
public interface IDelete {

    void delete(long index) throws Exception;

    void deleteAll() throws Exception;
}
