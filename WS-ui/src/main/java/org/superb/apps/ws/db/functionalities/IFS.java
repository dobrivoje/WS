/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.functionalities;

import java.util.List;
import org.superb.apps.ws.db.entities.Fuelstation;

/**
 *
 * @author root
 */
public interface IFS extends CRUDInterface<Fuelstation> {

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<Fuelstation> getByName(String partialName);

    @Override
    public Fuelstation getByID(Long ID);

    @Override
    public List<Fuelstation> getAll();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Write data">
    public void addNew(String name, String address, String city, String coordinates) throws Exception;

    public void updateExisting(Long ID, String name, String address, String city, String coordinates) throws Exception;

    @Override
    public void updateExisting(Fuelstation object) throws Exception;

    @Override
    public void addNew(Fuelstation newObject) throws Exception;
    //</editor-fold>
}
