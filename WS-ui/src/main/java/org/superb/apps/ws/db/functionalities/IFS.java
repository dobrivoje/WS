/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.functionalities;

import org.superb.apps.ws.db.entities.Fuelstation;

/**
 *
 * @author root
 */
public interface IFS extends CRUDInterface<Fuelstation> {

    public void addNew(String name, String address, String city, String coordinates) throws Exception;

    public void updateExisting(int ID, String name, String address, String city, String coordinates) throws Exception;

}
