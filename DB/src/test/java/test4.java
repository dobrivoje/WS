/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dataservice.DataService;
import db.DBHandler;
import db.ent.CrmCase;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.datum.Dates;

public class test4 {

    public static final DataService DS = DataService.getDefault();
    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {

        Dates dd = new Dates(-1, true);
        // dd.setFrom(1, 8, 2015);
        // dd.setTo(31, 8, 2015);

        CustomSearchData csd = new CustomSearchData();
        // csd.setStartDate(dd.getFrom());
        // csd.setEndDate(dd.getTo());
        // csd.setProduct(DS.getProductController().getByID(2L));
        csd.setSalesman(DS.getSalesmanController().getByID(1L));
        csd.setCaseFinished(false);
        csd.setSaleAgreeded(false);

        System.err.println("test1 : getSalesrepSales");
        for (Map.Entry<Object, List> E : DS.getSearchController().getSalesrepSales(csd).entrySet()) {
            System.err.println("salesrep : " + (Salesman) E.getKey());

            List<RelSALE> L = (List<RelSALE>) E.getValue();
            for (RelSALE r : L) {
                System.err.println("sell date : " + r.getSellDate());
                System.err.println("sell : " + r.toString());
            }
        }

        System.err.println("");
        System.err.println("");
        System.err.println("test2 : ");

        for (Map.Entry<Object, List> E : DS.getSearchController().getSalesrepCRMCases(csd).entrySet()) {
            System.err.println("salesrep : " + (Salesman) E.getKey());
            System.err.println("cases : " + (List<CrmCase>) E.getValue());
        }

        System.err.println("");
        System.err.println("");
        System.err.println("test4 : crm case, crm processes !");

        for (Object cc : DS.getSearchController().getCRMCases(csd)) {
            System.err.println("crm case : " + cc);
            System.err.println("processes : " + ((CrmCase) cc).getCrmProcessList());
            System.err.println("sales : " + ((CrmCase) cc).getRelSALEList());
        }

    }
}
