/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.CrmCase;
import db.ent.InfSysUser;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        Date from, to;

        Calendar now = Calendar.getInstance();

        now.add(Calendar.MONTH, -1);
        now.set(Calendar.DAY_OF_MONTH, 1);
        from = new Date(now.getTimeInMillis());

        to = new Date();

        System.err.println("salesman cases between "
                + new SimpleDateFormat("dd.MM.yyyy").format(from) + " - "
                + new SimpleDateFormat("dd.MM.yyyy").format(to)
        );
        System.err.println(DS.getCRMController().getCRM_Cases(loggedInSalesman, true, true, null, null));

        for (CrmCase cc : DS.getCRMController().getCRM_CompletedCases(loggedInSalesman, true, true)) {
            System.err.println("case : " + cc.getDescription());

            for (RelSALE r : DS.getCRMController().getCRM_Sales(cc, from, to)) {
                System.err.println("prodaja : " + r.getAmmount());
            }
        }

        System.err.println("-----salesman sales cases-----");
        for (Salesman S : DS.getSalesmanController().getAll()) {
            System.err.println("salesman : " + S);
            System.err.println(dbh.getCRM_CompletedCases(S, true, true));
        }

        System.err.println("-----salesman getCRM_Cases-----");
        for (Salesman S : DS.getSalesmanController().getAll()) {
            System.err.println("salesman : " + S);
            System.err.println(DS.getCRMController().getCRM_Cases(S, true, true, null, null));
        }

        System.err.println("-----salesman active cases-----");
        for (Salesman S : DS.getSalesmanController().getAll()) {
            System.err.println("salesman : " + S);
            System.err.println(dbh.getCRM_Cases(S, false));
        }

        System.err.println("-----salesman active cases 2-----");
        for (Salesman S : DS.getSalesmanController().getAll()) {
            System.err.println("salesman : " + S);

            List<CrmCase> L = DS.getCRMController().getCRM_Cases(S, false);
            if (!L.isEmpty()) {
                System.err.println(L);
            }
        }

        System.err.println("-----prodaje prodavca iz sektora-----");

        now.set(Calendar.MONTH, 5);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        from = new Date(now.getTimeInMillis());

        now.set(Calendar.MONTH, 5);
        now.set(Calendar.DAY_OF_MONTH, 30);
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);

        to = new Date(now.getTimeInMillis());

        System.err.println("prodaje između : "
                + new SimpleDateFormat("dd.MM.yyyy").format(from) + " - "
                + new SimpleDateFormat("dd.MM.yyyy").format(to)
        );

        // now.set(Calendar.MONTH, -1);
        System.err.println(">>>>>>>>>>>>>>>>> PRODAJE jun : ");

        for (CrmCase CC : DS.getCRMController().getCRM_Sales_Cases(from, to)) {

            System.err.println(
                    "Sell Case : " + CC.getDescription()
                    + ", " + CC.getStartDate() + " - "
                    + ", " + CC.getEndDate()
            );

            for (RelSALE RS : DS.getCRMController().getCRM_Sales(CC, from, to)) {
                System.err.println("sale : " + RS.getAmmount() + ", " + RS.getSellDate());
            }
        }

        System.err.println(">>>>>>>>>>>>>>>>> PRODAJE 2 meseca: ");

        now.set(Calendar.MONTH, 7);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        from = new Date(now.getTimeInMillis());

        to = new Date();

        for (CrmCase CC : DS.getCRMController().getCRM_Sales_Cases(from, to)) {

            System.err.println(
                    "Sell Case : " + CC.getDescription()
                    + ", " + CC.getStartDate() + " - "
                    + ", " + CC.getEndDate()
            );

            for (RelSALE RS : DS.getCRMController().getCRM_Sales(CC, from, to)) {
                System.err.println("sale : " + RS.getAmmount() + ", " + RS.getSellDate());
            }
        }

        System.err.println(">>>>>>>>>>>>>>>>> PRODAJE prodavaca 2 meseca : ");

        now.set(Calendar.MONTH, 7);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        from = new Date(now.getTimeInMillis());

        to = new Date();

        for (Salesman S : DS.getSalesmanController().getAll()) {

            List<RelSALE> L = DS.getCRMController().getCRM_Sales(S, from, to);

            if (!L.isEmpty()) {
                for (RelSALE CC : DS.getCRMController().getCRM_Sales(S, from, to)) {
                    System.err.println(
                            "Sales rep : " + CC.getFK_IDCA().getFK_IDRSC().getFK_IDS()
                            + ", Sell Case : " + CC.getFK_IDCA().getDescription()
                            + ", " + CC.getFK_IDCA().getStartDate() + " - "
                            + ", " + CC.getFK_IDCA().getEndDate()
                    );

                    System.err.println("sale : " + CC.getAmmount() + ", " + CC.getSellDate());
                }
            }
        }
    }
}
