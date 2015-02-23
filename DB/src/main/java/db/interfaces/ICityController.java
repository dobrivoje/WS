/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.City;
import java.util.List;

/**
 *
 * @author root
 */
public interface ICityController extends CRUDInterface<City> {

    List<City> getCityByDistrict(String partialName);

    List<City> getCityByContainingName(String partialName);

    List<City> getCityByMunicipality(String partialName);

}
