/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public interface ICRUD<T> {

    public List<T> getAll();

    public T getByID(Long ID);

    public List<T> getByName(String partialName);

    void addNew(T newObject) throws Exception;

    void updateExisting(T object) throws Exception;
}
