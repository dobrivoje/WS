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
    public List<CrmStatus> getCRM_AllStatuses() {
        return dbh.getCRM_AllStatuses();
    }

    @Override
    public List<Customer> getCRM_Customers(Salesman salesman) {
        return dbh.getCRM_SalesmansCustomers(salesman);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(Customer customer, Date dateFrom, Date dateTo) {
        return dbh.getCRM_CustomerProcessesByDate(customer, dateFrom, dateTo);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(Salesman salesman, Date dateFrom, Date dateTo) {
        return dbh.getCRM_SalesmanProcessesByDate(salesman, dateFrom, dateTo);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(CrmStatus crmStatus) {
        return dbh.getCRMProcessesByStatus(crmStatus);
    }

    /**
     * Overdue proces je poslednji uneti proces od strane Prodavca za kog važi
     * da je broj dana koji je protekao od kreiranja tog procesa do posmatranog
     * dana (koji je u stvari new Date() ) prevazišao definisani broj dana za
     * taj proces !
     *
     * @param salesman
     * @return
     */
    @Override
    public List<CrmProcess> getCRM_OverdueProcesses(Salesman salesman) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CrmProcess> getCRM_OverdueProcesses(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CrmProcess> getCRM_Opportunities(Salesman salesman) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CrmProcess> getCRM_Leads(Salesman salesman) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RelSALESMANCUST getCRM_R_SalesmanCustomer(Salesman s, Customer c) throws Exception {
        RelSALESMANCUST r = dbh.getCRM_R_Salesman_Cust(s, c);

        if (r == null) {
            throw new Exception("Salesman not in relation with this customer !");
        }

        return r;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    @Override
    public void addNew_R_Salesman_Cust(RelSALESMANCUST newRel_Salesman_Customer) throws Exception {
        dbh.addNew_RelSalesman_Cust(newRel_Salesman_Customer);
    }

    @Override
    public void addNew_R_Salesman_Cust(Customer c, Salesman s, Date dateFrom, Date dateTo, boolean active) throws Exception {
        dbh.addNew_RelSalesman_Cust(c, s, dateFrom, dateTo, active);
    }

    @Override
    public void addNewCRM_Process(Salesman s, Customer c, CrmStatus crmStatus, String comment, Date actionDate) throws Exception {
        dbh.addNewCRM_Process(s, c, crmStatus, comment, actionDate);
    }

    @Override
    public void addNewCRM_Process(CrmProcess newCrmProcess) throws Exception {
        dbh.addNewCRM_Process(newCrmProcess);
    }

    @Override
    public void update_R_Salesman_Cust(RelSALESMANCUST R_SalesmanCustomer) throws Exception {
        dbh.update_R_Salesman_Cust(R_SalesmanCustomer);
    }
    //</editor-fold>

}
