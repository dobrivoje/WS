/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;
import db.ent.BussinesLine;
import db.ent.Salesman;

/**
 *
 * @author dprtenjak
 */
public interface ISalesmanController extends CRUDInterface<Salesman>{

    //<editor-fold defaultstate="collapsed" desc="data to read">
    public List<Salesman> getSalesmanBySurname(String partialSurname);

    public List<Salesman> getAllActiveSalesman(boolean active);
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    void addNewSalesman(String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception;

    void updateSalesman(Long SalesmanID, String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception;
    //</editor-fold>
}
