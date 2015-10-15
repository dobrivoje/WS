/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.BussinesLine;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Customer;
import db.ent.RelSALE;
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
    public List<CrmCase> getCRM_AllActiveCases(boolean finished) {
        return dbh.getCRM_AllActiveCases(finished);
    }

    @Override
    public CrmCase getCRM_LastActive_CRMCase(Customer c, Salesman s) {
        return dbh.getLastActive_CRMCase(c, s);
    }

    @Override
    public List<CrmCase> getCRM_Cases(Customer c, Salesman s, boolean finished) {
        return dbh.getCRMCase(c, s, finished);
    }

    @Override
    public List<CrmCase> getCRM_Cases(Customer customer, boolean finished) {
        return dbh.getCRM_Cases(customer, finished);
    }

    @Override
    public List<CrmCase> getCRM_Cases(Salesman salesman, boolean finished) {
        return dbh.getCRM_Cases(salesman, finished);
    }

    @Override
    public List<CrmCase> getCRM_Cases(BussinesLine bussinesLine, boolean finished) {
        return dbh.getCRM_Cases(bussinesLine, finished);
    }

    @Override
    public CrmStatus getCRM_Status(long ID) {
        return dbh.getCRM_Status(ID);
    }

    @Override
    public CrmCase getCRM_Case(long ID) {
        return dbh.getCRM_Case(ID);
    }

    @Override
    public List<Customer> getCRM_Customers(Salesman salesman) {
        return dbh.getCRM_SalesmansCustomers(salesman);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(CrmCase crmCase, boolean finished) {
        return dbh.getCRMProcesses(crmCase, finished);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(CrmCase crmCase) {
        return dbh.getCRMProcesses(crmCase);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(Customer customer, boolean finished, Date dateFrom, Date dateTo) {
        return dbh.getCRM_CustomerProcessesByDate(customer, finished, dateFrom, dateTo);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(Salesman salesman, boolean finished, Date dateFrom, Date dateTo) {
        return dbh.getCRM_SalesmanProcessesByDate(salesman, finished, dateFrom, dateTo);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(Salesman salesman, boolean finished) {
        return dbh.getCRM_SalesmanProcesses(salesman, finished);
    }

    @Override
    public List<CrmProcess> getCRM_Processes(CrmStatus crmStatus) {
        return dbh.getCRMProcessesByStatus(crmStatus);
    }

    /**
     *
     * @param salesman
     * @param finished - Ako su oba datuma null, vraćaju se CRM case-ovi za
     * prethodni i tekući mesec.
     * @param saleAgreeded
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @Override
    public List<CrmCase> getCRM_Cases(Salesman salesman, boolean finished, boolean saleAgreeded, Date dateFrom, Date dateTo) {
        return dbh.getCRM_Cases(salesman, dateFrom, dateTo, finished, saleAgreeded, 0);
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
        return dbh.getCRM_R_Salesman_Cust(s, c);
    }

    /**
     * Lista svih kupaca sa otvorenim slučajevima.
     *
     * @param finished
     * @return
     */
    @Override
    public List<Customer> getCRM_CustomerActiveCases(boolean finished) {
        return dbh.getCRM_CustomerActiveCases(finished);
    }

    @Override
    public List<Customer> getCRM_CustomerActiveCases(boolean finished, BussinesLine bl) {
        return dbh.getCRM_CustomerActiveCases(finished, bl);
    }

    @Override
    public List<CrmCase> getCRM_CasesStats(Salesman salesman, boolean finished, boolean caseAggreed) {
        return dbh.getCRM_CasesStats(salesman, finished, caseAggreed);
    }

    @Override
    public List<CrmCase> getCRM_CasesStats(Date dateFrom, Date dateTo, boolean finished, boolean saleAgreeded) {
        return dbh.getCRM_CasesStats(dateFrom, dateTo, finished, saleAgreeded);
    }

    @Override
    public List<CrmCase> getCRM_CasesStats(Salesman salesman, boolean finished, boolean saleAgreeded, Date from, Date to) {
        return dbh.getCRM_CasesStats(salesman, from, to, finished, saleAgreeded);
    }

    @Override
    public List<CrmCase> getCRM_CasesStats(BussinesLine bussinesLine, boolean finished, boolean saleAgreeded) {
        return dbh.getCRM_CasesStats(bussinesLine, finished, saleAgreeded);
    }

    @Override
    public List<CrmStatus> getCRM_AvailableStatuses(CrmCase crmCase) {
        return dbh.getCRM_AvailableStatuses(crmCase);
    }

    @Override
    public List<CrmStatus> getCRM_Statuses(String... type) {
        return dbh.getCRM_Statuses(type);
    }

    @Override
    public List<CrmStatus> getCRM_Status(String type) {
        return dbh.getCRM_Status(type);
    }

    @Override
    public List<CrmStatus> getCRM_CaseStatuses(CrmCase crmCase) {
        return dbh.getCRM_CaseStatuses(crmCase);
    }

    @Override
    public List<RelSALE> getCRM_Sales(CrmCase c, Date dateFrom, Date dateTo) {
        return dbh.getCRM_Sales(c, dateFrom, dateTo);
    }

    @Override
    public List<RelSALE> getCRM_Sales(Salesman s, Date dateFrom, Date dateTo) {
        return dbh.getCRM_Sales(s, dateFrom, dateTo);
    }

    @Override
    public List<CrmCase> getCRM_Salesrep_Sales_Cases(Salesman s, Date dateFrom, Date dateTo) {
        return dbh.CRM_Salesrep_Sales(s, dateFrom, dateTo);
    }

    @Override
    public List<CrmCase> getCRM_Sales_Cases(Date dateFrom, Date dateTo) {
        return dbh.getCRM_Sales_Cases(dateFrom, dateTo);
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
    public void addNewCRM_Process(CrmCase crmCase, CrmStatus crmStatus, String comment, Date actionDate) throws Exception {
        dbh.addNewCRM_Process(crmCase, crmStatus, comment, actionDate);
    }

    @Override
    public void addNewCRM_Process(CrmProcess newCrmProcess) throws Exception {
        dbh.addNewCRM_Process(newCrmProcess);
    }

    @Override
    public void updateCRM_Process(CrmProcess crmProcess) throws Exception {
        dbh.updateCRM_Process(crmProcess);
    }

    @Override
    public void update_R_Salesman_Cust(RelSALESMANCUST R_SalesmanCustomer) throws Exception {
        dbh.update_R_Salesman_Cust(R_SalesmanCustomer);
    }

    @Override
    public void addNewCRM_Case(CrmCase crmCase) throws Exception {
        dbh.addNewCRM_Case(crmCase);
    }

    @Override
    public void addNewCRM_Case(RelSALESMANCUST relSC, Date startDate, String description) throws Exception {
        dbh.addNewCRM_Case(new CrmCase(startDate, description, relSC));
    }

    @Override
    public void updateCRM_Case(CrmCase crmCase) throws Exception {
        dbh.updateCRM_Case(crmCase);
    }

    @Override
    public void addNewSale(RelSALE sale) throws Exception {
        dbh.addNewSale(sale);
    }
    //</editor-fold>

}
