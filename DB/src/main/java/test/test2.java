package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.CrmCase;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.superb.apps.utilities.datum.Dates;

public class test2 {

    public static final DataService DS = DataService.getDefault();
    static ICRMController CC = DataService.getDefault().getCRMController();
    static ISalesmanController SC = DataService.getDefault().getSalesmanController();
    static IInfSysUserController IS = DataService.getDefault().getINFSYSUSERController();
    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {
        CustomSearchData csd = new CustomSearchData();
        Dates d = new Dates();

        System.err.println("------------------------");
        System.err.println("test2 : getAllSales from :" + d.getFrom() + "-" + d.getTo());

        for (Map.Entry<Salesman, List<RelSALE>> entrySet : DS.getSearchController().getSalesrepSales(csd).entrySet()) {
            Salesman S = entrySet.getKey();
            List<RelSALE> LRS = entrySet.getValue();

            System.err.println(S + " -> " + LRS);
        }

        System.err.println("_______________________________________________________________");
        System.err.println("");
        System.err.println("test7");
        System.err.println("");

        CustomSearchData csd2 = new CustomSearchData();
        d.setFrom(1, 8, 2015);
        d.setTo(10, 11, 2015);
        csd2.setStartDate(d.getFrom());
        csd2.setEndDate(d.getTo());

        for (CrmCase cc : DS.getSearchController().getCRMCases(csd2)) {
            System.err.println(cc.toString());
            System.err.println("|____" + Arrays.toString(cc.getCrmProcessList().toArray()));
        }
    }
}
