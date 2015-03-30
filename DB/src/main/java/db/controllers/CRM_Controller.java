/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Customer;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class CRM_Controller implements ICRMController {

    private static DBHandler dbh;

    public CRM_Controller(DBHandler dbh) {
        CRM_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<CrmProcess> getAllCRMProcesses() {
        return dbh.getAllCRMProcesses();
    }

    @Override
    public List<CrmProcess> getCRMProcessesByCustomer(Customer customer) {
        return dbh.getCRMProcessesByCustomer(customer);
    }

    @Override
    public List<CrmProcess> getCRMProcessesBySalesman(Salesman salesman) {
        return dbh.getCRMProcessesBySalesman(salesman);
    }

    @Override
    public List<CrmProcess> getCRMProcessesBySalesman(CrmStatus crmStatus) {
        return dbh.getCRMProcessesByStatus(crmStatus);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Add/update data">

    @Override
    public void addNew_RelSalesman_Cust(RelSALESMANCUST newRel_Salesman_Customer) throws Exception {
        dbh.addNew_RelSalesman_Cust(newRel_Salesman_Customer);
    }

    @Override
    public void addNew_RelSalesman_Cust(Customer c, Salesman s, Date dateFrom, Date dateTo, boolean active) throws Exception {
        dbh.addNew_RelSalesman_Cust(c, s, dateFrom, dateTo, active);
    }

    @Override
    public void update_SalesmanCustomer(RelSALESMANCUST newRel_Salesman_Customer) throws Exception {
        dbh.update_RelSalesman_Cust(newRel_Salesman_Customer);
    }

    @Override
    public void addNew_CRMProcess(RelSALESMANCUST RelSalesmanCustomer, CrmStatus crmStatus, String comment, Date actionDate) throws Exception {
        dbh.addNew_CRMProcess(RelSalesmanCustomer, crmStatus, comment, actionDate);
    }

    @Override
    public void addNew_CRMProcess(CrmProcess newCrmProcess) throws Exception {
        dbh.addNew_CRMProcess(newCrmProcess);
    }

    @Override
    public void update_CRMProcess(CrmProcess crmProcess) throws Exception {
        dbh.update_CRMProcess(crmProcess);
    }
    //</editor-fold>

}
