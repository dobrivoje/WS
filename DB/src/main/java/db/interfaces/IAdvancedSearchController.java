/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.Customer;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAdvancedSearchController<T> {

    /**
     * <b>getCRMCases</b> metod vraća CRM slučajeve sa svim CRM procesima za
     * svaki CRM case, kao i svim prodajama ukoliko postoje.
     *
     * @param criteria
     * @return
     */
    List getCRMCases(T criteria);

    /**
     * <b>getSalesrepSales</b> metod vraća sve prodaje prodavca za kriterijum
     * pretrage.
     *
     * @param criteria
     * @return
     */
    Map<Object, List> getSalesrepSales(T criteria);

    /**
     * <b>getSalesrepCRMCases</b> metod vraća sve crm case-ove prodavca za
     * kriterijum pretrage.
     *
     * @param criteria
     * @return
     */
    Map<Object, List> getSalesrepCRMCases(T criteria);

    /**
     * <b>getCustomerCRMCases</b> metod vraća sve crm case-ove prodavca za
     * kriterijum pretrage.
     *
     * @param criteria
     * @return
     */
    Map<Object, List> getCustomerCRMCases(T criteria);

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
