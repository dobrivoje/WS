/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.BussinesLine;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Customer;
import db.ent.RelSALE;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author root
 */
public interface ICRMController {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    List<CrmStatus> getCRM_AllStatuses();

    CrmStatus getCRM_Status(long ID);

    List<CrmStatus> getCRM_Status(String type);

    List<CrmStatus> getCRM_CaseStatuses(CrmCase crmCase);

    List<Customer> getCRM_CustomerActiveCases(boolean finished);

    List<Customer> getCRM_CustomerActiveCases(boolean finished, BussinesLine bl);

    List<CrmCase> getCRM_AllActiveCases(boolean finished);

    List<CrmCase> getCRM_Cases(Customer customer, boolean finished);

    List<CrmCase> getCRM_Cases(BussinesLine bussinesLine, boolean finished);

    List<CrmCase> getCRM_Cases(Salesman salesman, boolean finished);

    List<CrmCase> getCRM_Cases(Salesman salesman, boolean finished, boolean saleAgreeded, Date dateFrom, Date dateTo);

    /**
     * Ako su oba datuma null, onda se traži prodaja <br>
     * za prethodni i sadašnji mesec.
     *
     * @param crmCase
     * @param dateFrom
     * @param dateTo
     * @return
     */
    List<RelSALE> getCRM_Sales(CrmCase crmCase, Date dateFrom, Date dateTo);

    /**
     * Za posmatrani CRM case vrati sve prodaje.<br>
     * Master/detail.
     *
     * @param crmCase
     * @return
     */
    List<RelSALE> getCRM_Sales(CrmCase crmCase);

    /**
     * Stablo CRM case-ova sa svim prodajama za svaki CRM case.<br>
     * Master/detail -> crm case : sales
     *
     * @param salesRep
     * @param salesFrom
     * @param SalesTo
     * @return
     */
    Map<Object, List> getCRM_MD_CRM_Sales(Salesman salesRep, Date salesFrom, Date SalesTo);

    /**
     * Stablo CRM case-ova sa svim prodajama za svaki CRM case.<br>
     * Master/detail -> crm case : sales
     *
     * @param csd
     * @return
     */
    Map<Object, List> getCRM_MD_CRM_Sales(CustomSearchData csd);

    /**
     * Stablo CRM case-ova sa svim prodajama za svakog prodavca.<br>
     * Master/detail -> salesrep : sales
     *
     * @param csd
     * @return
     */
    Map<Object, List> getCRM_MD_CRM_SalesrepSales(CustomSearchData csd);

    /**
     * Ako su oba datuma null, onda se traži prodaja <br>
     * za prethodni i sadašnji mesec.
     *
     * @param s Sales rep
     * @param dateFrom
     * @param dateTo
     * @return
     */
    List<RelSALE> getCRM_Sales(Salesman s, Date dateFrom, Date dateTo);

    /**
     * Ako su oba datuma null, onda se traži prodaja <br>
     * za prethodni i sadašnji mesec.
     *
     * @param s Sales rep
     * @param dateFrom
     * @param dateTo
     * @return
     */
    List<CrmCase> getCRM_Salesrep_Sales_Cases(Salesman s, Date dateFrom, Date dateTo);

    /**
     * Svi CRM case-ovi koji IMAJU OSTVARENE prodaje, u posmatranom periodu.
     * <p>
     * Ako su oba datuma null, onda se traži prodaja<br>
     * od 1. danaa prethodnog meseca, do sadašnjeg dana.
     *
     * @param dateFrom
     * @param dateTo
     * @return
     */
    List<CrmCase> getCRM_Sales_Cases(Date dateFrom, Date dateTo);

    List<CrmCase> getCRM_Cases(Customer c, Salesman s, boolean finished);

    CrmCase getCRM_LastActive_CRMCase(Customer c, Salesman s);

    List<Customer> getCRM_Customers(Salesman salesman);

    List<CrmProcess> getCRM_Processes(CrmCase crmCase);

    List<CrmProcess> getCRM_Processes(CrmCase crmCase, boolean finished);

    List<CrmProcess> getCRM_Processes(Customer customer, boolean finished, Date dateFrom, Date dateTo);

    List<CrmProcess> getCRM_Processes(Salesman salesman, boolean finished, Date dateFrom, Date dateTo);

    List<CrmProcess> getCRM_Processes(Salesman salesman, boolean finished);

    CrmCase getCRM_Case(long ID);

    List<CrmProcess> getCRM_OverdueProcesses(Salesman salesman);

    List<CrmProcess> getCRM_OverdueProcesses(Customer customer);

    List<CrmProcess> getCRM_Opportunities(Salesman salesman);

    List<CrmProcess> getCRM_Leads(Salesman salesman);

    List<CrmProcess> getCRM_Processes(CrmStatus crmStatus);

    RelSALESMANCUST getCRM_R_SalesmanCustomer(Salesman s, Customer c) throws Exception;

    /*
     * Metod koji ispravno uređuje sledeći CRM status <br>
     * u odnosu na dosadašnje  unete statuse.
     * <p>
     * @param crmCase Za CRM slučaj, metod vraća isključivo
     * jedan status - npr. za tek otvoren, vraća "Negotiation Started". <br>
     * Ako za CRM status već postoji "Negotiation Started", sledeći mogući
     * status može biti SAMO "Offer". Ako već postoji "Offer", sledeći unetii
     * status mogu biti "Offer" (jer može biti više ponuda", kao i statusi
     * "Contract Signed", ili "Contract Not Signed"
     * @return Lista ispravnih CRM statusa.
     */
    List<CrmStatus> getCRM_AvailableStatuses(CrmCase crmCase);

    List<CrmStatus> getCRM_Statuses(String... type);

    /**
     * Svi CRM slučajevi za prodavca koji su završeni ili ne<br>
     * i gde je ugovor o prodaji sklopljen ili ne!
     *
     * @param salesman
     * @param caseFinished
     * @param saleAgreeded Ako je true, Ugovor zaključen, <br>
     * a ako je false - CRM aktivan - pregovori u toku.
     * @return Lista sCRM slučajeva.
     */
    public List<CrmCase> getCRM_CasesStats(Salesman salesman, boolean caseFinished, boolean saleAgreeded);

    public List<CrmCase> getCRM_CasesStats(BussinesLine bussinesLine, boolean caseFinished, boolean saleAgreeded);

    public List<CrmCase> getCRM_CasesStats(Salesman salesman, boolean caseFinished, boolean saleAgreeded, Date from, Date to);

    /**
     * Svi CRM slučajevi za prodavca koji su završeni, i gde je ugovor o prodaji
     * sklopljen !
     *
     * @param dateFrom
     * @param dateTo
     * @param finished
     * @param saleAgreeded true: Prodaja zaključena.
     * @return
     */
    public List<CrmCase> getCRM_CasesStats(Date dateFrom, Date dateTo, boolean finished, boolean saleAgreeded);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">
    void addNew_R_Salesman_Cust(RelSALESMANCUST newRel_Salesman_Customer) throws Exception;

    void addNew_R_Salesman_Cust(Customer c, Salesman s, Date dateFrom, Date dateTo, boolean active) throws Exception;

    void update_R_Salesman_Cust(RelSALESMANCUST R_SalesmanCustomer) throws Exception;

    void addNewCRM_Case(CrmCase crmCase) throws Exception;

    void addNewCRM_Case(RelSALESMANCUST relSC, Date startDate, String description) throws Exception;

    void addNewCRM_Process(CrmCase crmCase, CrmStatus crmStatus, String comment, Date actionDate) throws Exception;

    void updateCRM_Case(CrmCase crmCase) throws Exception;

    void updateCRM_Process(CrmProcess crmProcess) throws Exception;

    void addNewCRM_Process(CrmProcess newCrmProcess) throws Exception;

    void addNewSale(RelSALE sale) throws Exception;
    //</editor-fold>
}
