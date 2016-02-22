/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dataservice.DataService;
import db.DBHandler;
import db.ent.CrmCase;
import db.ent.InfSysUser;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.ent.custom.CustomSearchData;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;
import java.util.List;
import org.superb.apps.utilities.datum.Dates;

public class Test1 {

    public static final DataService DS = DataService.getDefault();
    static ICRMController CC = DataService.getDefault().getCRMController();
    static ISalesmanController SC = DataService.getDefault().getSalesmanController();
    static IInfSysUserController IS = DataService.getDefault().getINFSYSUSERController();
    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {

        InfSysUser loggedISUser;
        Salesman loggedInSalesman;

        loggedISUser = DS.getINFSYSUSERController().getByID(1);
        loggedInSalesman = DS.getINFSYSUSERController().getSalesman(loggedISUser);

        System.err.println(loggedISUser);
        System.err.println(loggedInSalesman == null ? "n/a" : loggedInSalesman);

        Dates d = new Dates(-6);

        System.err.println("propali slučajevi:");

        for (Salesman S : SC.getAll()) {
            System.err.println(CC.getCRM_Cases(S, true, false, d.getFrom(), d.getTo()));
        }

        for (Salesman S : SC.getAll()) {
            System.err.println("#2, Salesrep : " + S + ", " + CC.getCRM_CasesStats(S, true, false, d.getFrom(), d.getTo()));
        }

        System.err.println("#3 : ");
        System.err.println(CC.getCRM_CasesStats(d.getFrom(), d.getTo(), true, false));

        System.err.println("BL : ");
        System.err.println(CC.getCRM_CasesStats(loggedInSalesman.getFkIdbl(), true, false));

        System.err.println("------------------------");
        System.err.println("not signed, interval :  ");

        for (Salesman S : DS.getSalesmanController().getAll()) {

            List<CrmCase> L = DS.getCRMController().getCRM_CasesStats(S, true, false, d.getFrom(), d.getTo());
            if (!L.isEmpty()) {
                System.err.println(L);
            }
        }

        CustomSearchData csd = new CustomSearchData();
        System.err.println("------------------------");
        System.err.println("test1 : getAllSales ");

        for (RelSALE s : dbh.getSales(csd)) {
            System.err.println(s
                    + ", " + s.getFK_IDCA().getDescription() + ", "
                    + s.getFK_IDCA().getFK_IDRSC().getFK_IDC() + ", "
                    + s.getFK_IDCA().getFK_IDRSC().getFK_IDS()
            );
        }

        d.setTo(31, 8, 2015);
        csd.setEndDate(d.getTo());
        System.err.println("------------------------");
        System.err.println("test2 : getAllSales until 31.8.2015");

        for (RelSALE s : dbh.getSales(csd)) {
            System.err.println(s);
        }

        CustomSearchData csd1 = new CustomSearchData();
        csd1.setSalesman(DS.getSalesmanController().getByID(1L));
        System.err.println("------------------------");
        System.err.println("test3 : getAllSales for salesman : " + csd1.getSalesman());

        for (RelSALE s : dbh.getSales(csd1)) {
            System.err.println(s.getFK_IDCA().getFK_IDRSC().getFK_IDS() + " - " + s.toString());
        }

        CustomSearchData csd2 = new CustomSearchData();
        csd2.setCustomer(DS.getCustomerController().getByID(878L));
        System.err.println("------------------------");
        System.err.println("test4 : getAllSales for customer : " + csd2.getCustomer());

        for (RelSALE s : dbh.getSales(csd2)) {
            System.err.println(s.getFK_IDCA().getFK_IDRSC().getFK_IDS() + " - " + s.toString());
        }

        System.err.println("------------------------------------------------------------------------");
        System.err.println("------------------------------------------------------------------------");
    }
}
