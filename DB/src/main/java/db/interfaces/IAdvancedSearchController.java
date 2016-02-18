/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Customer;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAdvancedSearchController<T> {

    /**
     * <b>getSales</b> metod vraća sve prodaje za kriterijum pretrage.
     *
     * @param criteria
     * @return
     */
    List<RelSALE> getSales(T criteria);

    /**
     * <b>getCRMCases</b> metod vraća CRM slučajeve sa svim CRM procesima za
     * svaki CRM case, kao i svim prodajama ukoliko postoje.
     *
     * @param criteria
     * @return
     */
    List<CrmCase> getCRMCases(T criteria);

    /**
     * <b>getCRMProcesses</b> metod vraća CRM procese za kriterijum pretrage.
     *
     * @param criteria
     * @return
     */
    List<CrmProcess> getCRMProcesses(T criteria);

    /**
     * <b>getSalesrepSales</b> metod vraća sve prodaje prodavca za kriterijum
     * pretrage.
     *
     * @param criteria
     * @return
     */
    Map<Salesman, List<RelSALE>> getSalesrepSales(T criteria);

    /**
     * <b>getCustomers</b> metod vraća sve kupce <b>bez ponavljanja</b> iz CRM
     * slučajeva za kriterijum pretrage.
     *
     * @param criteria
     * @return
     */
    Set<Customer> getCustomers(CustomSearchData criteria);

    /**
     * <b>getSalesreps</b> metod vraća sve prodavce <b>bez ponavljanja</b> iz
     * CRM slučajeva za kriterijum pretrage.
     *
     * @param criteria
     * @return
     */
    Set<Salesman> getSalesreps(CustomSearchData criteria);

}
