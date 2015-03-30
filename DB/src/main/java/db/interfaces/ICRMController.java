/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

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

    public List<CrmProcess> getAllCRMProcesses();

    public List<CrmProcess> getCRMProcessesByCustomer(Customer customer);

    public List<CrmProcess> getCRMProcessesBySalesman(Salesman salesman);

    public List<CrmProcess> getCRMProcessesBySalesman(CrmStatus crmStatus);

    void addNew_RelSalesman_Cust(RelSALESMANCUST newRel_Salesman_Customer) throws Exception;

    void addNew_RelSalesman_Cust(Customer c, Salesman s, Date dateFrom, Date dateTo, boolean active) throws Exception;

    void update_SalesmanCustomer(RelSALESMANCUST newRel_Salesman_Customer) throws Exception;

    void addNew_CRMProcess(RelSALESMANCUST RelSalesmanCustomer, CrmStatus crmStatus, String comment, Date actionDate) throws Exception;

    void addNew_CRMProcess(CrmProcess newCrmProcess) throws Exception;

    void update_CRMProcess(CrmProcess crmProcess) throws Exception;
}
