/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.CrmCase;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.datum.Dates;

public class test4 {

    public static final DataService DS = DataService.getDefault();
    static ICRMController CC = DataService.getDefault().getCRMController();
    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {
        Dates d = new Dates();
        d.setFrom(1, 6, 2015);
        d.setFrom(31, 8, 2015);

        for (Salesman s : DS.getSalesmanController().getAll()) {
            List<CrmCase> L = DS.getCRMController().getCRM_Salesrep_Sales_Cases(s, d.getFrom(), d.getTo());

            if (!L.isEmpty()) {
                System.err.println("salesman : " + s);

                for (Map.Entry<Object, List> ES : dbh.getCRM_MD_CRM_Sales(DS.getCRMController().getCRM_Salesrep_Sales_Cases(s, d.getFrom(), d.getTo())).entrySet()) {
                    CrmCase key = (CrmCase) ES.getKey();
                    List<RelSALE> value = (List<RelSALE>) ES.getValue();

                    System.err.println("|____crm case : " + key);
                    System.err.println("|________ crm sales : " + value);
                    System.err.println("");
                }
            }
        }
    }
}
