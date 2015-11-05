package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;
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
        d.setFrom(1, 4, 2015);
        d.setTo(31, 8, 2015);

        csd.setStartDate(d.getFrom());
        csd.setEndDate(d.getTo());

        System.err.println("------------------------");
        System.err.println("test : getAllSales from :" + d.getFrom() + "-" + d.getTo());

        for (RelSALE s : dbh.getAllSales(csd)) {
            System.err.println(
                    s.getSellDate() + ", "
                    + s.getFK_IDCA().getFK_IDRSC().getFK_IDS() + ", "
                    + s.getFK_IDCA().getFK_IDRSC().getFK_IDC() + ", "
                    + s
            );
        }

        System.err.println("------------------------");
        System.err.println("test2 : getAllSales from :" + d.getFrom() + "-" + d.getTo());

        for (Map.Entry<Salesman, List<RelSALE>> entrySet : DS.getSearchController().getAllSalesrepSales(csd).entrySet()) {
            Salesman S = entrySet.getKey();
            List<RelSALE> LRS = entrySet.getValue();

            System.err.println(S + " -> " + LRS);
        }

        System.err.println("------------------------");
        System.err.println("test3 : getAllSales from :" + d.getFrom() + "-" + d.getTo());
        System.err.println("Salesrep(s) : " + DS.getSearchController().getAllSalesrepSales(csd).keySet());
        System.err.println("Salesrep(s) sale(s) : " + DS.getSearchController().getAllSalesrepSales(csd).values());
    }
}
