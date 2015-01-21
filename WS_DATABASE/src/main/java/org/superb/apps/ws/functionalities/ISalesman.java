/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.functionalities;

import java.util.List;
import static org.superb.apps.ws.db.DBHandler.getEm;
import org.superb.apps.ws.db.entities.BussinesLine;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.Salesman;

/**
 *
 * @author dprtenjak
 */
public interface ISalesman {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    public Salesman getSalesmanByID(int customerID);

    public List<Salesman> getSalesmanByName(String partialName);

    public List<Salesman> getSalesmanBySurname(String partialSurname);

    public List<Salesman> getSalesmanByPosition(String partialPosition);

    public List<Salesman> getAllActiveSalesman(boolean active);
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    public void addNewSalesman(Salesman newSalesman) throws Exception;

    public void addNewSalesman(String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception;

    public void updateSalesman(Salesman salesman) throws Exception;

    public void updateSalesman(int SalesmanID, String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception;
    //</editor-fold>
}
