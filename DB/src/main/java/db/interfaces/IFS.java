/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.City;
import db.ent.Fuelstation;

/**
 *
 * @author root
 */
public interface IFS extends CRUDInterface<Fuelstation> {

    public void addNew(String name, String address, City city, String coordinates) throws Exception;

    public void updateExisting(int ID, String name, String address, City city, String coordinates) throws Exception;

}
