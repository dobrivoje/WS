/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.InfSysUser;
import db.ent.Salesman;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;
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
    }
}
