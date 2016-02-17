/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dataservice.DataService;
import db.DBHandler;
import db.ent.RelSALE;
import db.ent.Salesman;
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

        Dates dd = new Dates();
        dd.setFrom(1, 8, 2015);
        dd.setTo(31, 8, 2015);

        CustomSearchData csd = new CustomSearchData();
        csd.setProduct(DS.getProductController().getByID(2L));

        for (Map.Entry<Salesman, List<RelSALE>> E : DS.getSearchController().getSalesrepSales(csd).entrySet()) {
            System.err.println("salesrep : " + E.getKey());
            System.err.println("sales : " + E.getValue());
        }
        
        System.err.println("-------------------------------------------");
        System.err.println("");
        
        for (RelSALE rs : dbh.getSales(csd)) {
            System.err.println(rs);
        }

    }
}
