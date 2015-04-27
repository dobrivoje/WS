/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Customer;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public interface ICRMController {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    List<CrmStatus> getCRM_AllStatuses();

    CrmStatus getCRM_Status(long ID);

    List<CrmCase> getCRM_Cases(Customer customer, boolean caseFinished);

    List<CrmCase> getCRM_Cases(Salesman salesman, boolean caseFinished);

    CrmCase getCRM_LastActive_CRMCase(Customer c, Salesman s);

    List<Customer> getCRM_Customers(Salesman salesman);

    List<CrmProcess> getCRM_Processes(Customer customer, Date dateFrom, Date dateTo);

    List<CrmProcess> getCRM_Processes(Salesman salesman, boolean finished, Date dateFrom, Date dateTo);

    CrmCase getCRM_Case(long ID);

    List<CrmProcess> getCRM_OverdueProcesses(Salesman salesman);

    List<CrmProcess> getCRM_OverdueProcesses(Customer customer);

    List<CrmProcess> getCRM_Opportunities(Salesman salesman);

    List<CrmProcess> getCRM_Leads(Salesman salesman);

    List<CrmProcess> getCRM_Processes(CrmStatus crmStatus);

    RelSALESMANCUST getCRM_R_SalesmanCustomer(Salesman s, Customer c) throws Exception;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    void addNew_R_Salesman_Cust(RelSALESMANCUST newRel_Salesman_Customer) throws Exception;

    void addNew_R_Salesman_Cust(Customer c, Salesman s, Date dateFrom, Date dateTo, boolean active) throws Exception;

    void update_R_Salesman_Cust(RelSALESMANCUST R_SalesmanCustomer) throws Exception;

    void addNewCRM_Case(CrmCase crmCase) throws Exception;

    void addNewCRM_Case(RelSALESMANCUST relSC, Date startDate, String description) throws Exception;

    void addNewCRM_Process(CrmCase crmCase, CrmStatus crmStatus, String comment, Date actionDate) throws Exception;

    void addNewCRM_Process(CrmProcess newCrmProcess) throws Exception;
    //</editor-fold>
}
