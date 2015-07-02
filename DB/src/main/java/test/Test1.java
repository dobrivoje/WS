/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.InfSysUser;
import db.ent.RelSALE;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author root
 */
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
        System.err.println(loggedInSalesman);

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
        System.err.println(DS.getCRMController().getCRM_Cases(loggedInSalesman, true, from, to));

        for (RelSALE r : DS.getCRMController().getCRM_Sales(loggedInSalesman, from, to)) {
            System.err.println("prodaja : " + r.getAmmount());
        }
        
        for (RelSALE r : DS.getCRMController().getCRM_Sales(loggedInSalesman, null, null)) {
            System.err.println("prodaja : " + r.getAmmount());
        }
        
    }
}
