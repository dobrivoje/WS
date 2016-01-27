/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.RelSALE;
import db.ent.custom.CustomSearchData;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.superb.apps.utilities.datum.Dates;

public class test4 {

    public static final DataService DS = DataService.getDefault();
    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {
        Dates d = new Dates();
        d.setFrom(1, 6, 2015);
        d.setTo(31, 8, 2015);

        /*
         for (Salesman s : DS.getSalesmanController().getAll()) {
         Set setCRMCases = DS.getCRMController().getCRM_MD_CRM_Sales(s, d.getFrom(), d.getTo()).keySet();
        
         if (!setCRMCases.isEmpty()) {
         System.err.println("salesman : " + s);
        
         System.err.println("*******************");
         System.err.println("setCRMCases : " + setCRMCases);
         System.err.println("*******************");
        
         for (Map.Entry<Object, List> ES : DS.getCRMController().getCRM_MD_CRM_Sales(s, d.getFrom(), d.getTo()).entrySet()) {
         CrmCase key = (CrmCase) ES.getKey();
         List<RelSALE> value = (List<RelSALE>) ES.getValue();
        
         System.err.println("|____crm case : " + key);
         System.err.println("|________ crm sales : " + value);
         System.err.println("");
         }
        
         }
         }
         */
        Date df = null, dt = null;

        dbh.checkDates(df, dt);

        df = dbh.checkDates(df, dt).get(0);
        dt = dbh.checkDates(df, dt).get(1);

        Logger.getLogger("1").log(Level.SEVERE, "from : {0} to : {1}", new Object[]{df, dt});

        Dates dd = new Dates();
        dd.setFrom(1, 8, 2015);
        dd.setTo(31, 8, 2015);

        CustomSearchData csd = new CustomSearchData();
        csd.setStartDate(dd.getFrom());
        csd.setEndDate(dd.getTo());
        csd.setSalesman(DS.getSalesmanController().getByID(3L));

        System.err.println("salesman : " + csd.getSalesman());
        for (RelSALE s : dbh.getSales(csd)) {
            System.err.println("sales : " + s + ", sell date : " + s.getSellDate());
        }

        System.err.println("test 2 - getCRM_Sales(CustomSearchData csd) - salesman : " + csd.getSalesman());
        for (Map.Entry<Object, List> e : dbh.getCRM_MD_Sales(csd).entrySet()) {
            System.err.println("case : " + e.getKey());
            System.err.println("sales : " + e.getValue());
        }

        System.err.println("test 3 - getCRM_MD_CRM_SalesrepSales(CustomSearchData csd) - salesman : " + csd.getSalesman());
        for (Map.Entry<Object, List> e1 : dbh.getCRM_MD_SalesrepSales(csd).entrySet()) {
            System.err.println("salesrep : " + e1.getKey());
            System.err.println("sales : " + e1.getValue());
        }

    }
}
