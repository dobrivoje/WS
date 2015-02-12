/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.functionalities;

import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public interface CRUDInterface<T> {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    public List<T> getAll();

    public T getByID(int ID);

    public List<T> getByName(String partialName);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    public void addNew(T newObject) throws Exception;

    public void updateExisting(T object) throws Exception;
    //</editor-fold>
}
