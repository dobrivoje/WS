/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.BussinesLine;
import java.util.List;
import db.ent.Customer;
import db.ent.Salesman;

/**
 *
 * @author dprtenjak
 */
public interface ISalesmanController {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    List<Salesman> getAll();

    Salesman getByID(Long customerID);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    void addNew(Salesman newSalesman) throws Exception;

    void addNewSalesman(String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception;

    void updateExisting(Salesman salesman) throws Exception;
    //</editor-fold>
}
