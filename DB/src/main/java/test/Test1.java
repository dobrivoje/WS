/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.Customer;
import db.ent.InfSysUser;
import db.ent.Salesman;

/**
 *
 * @author root
 */
public class Test1 {

    public static void main(String[] args) {

        DataService DS = DataService.getDefault();

        InfSysUser isu = DS.getInfSysUserController().getByID("INTERMOL\\ADekic");
        Customer c = DS.getCustomerController().getByID(900L);
        Salesman s = DS.getSalesmanController().getByID(1L);

        System.err.println("Salesman : " + s.toString());
        System.err.println("salesman from infsysuser : " + DS.getInfSysUserController().getSalesman(isu));

        for (CrmCase cc : DS.getCrmController().getCRM_Cases(s, false)) {
            System.err.println("S : " + s.toString() + ", Case : " + cc.toString());
        }

        for (CrmProcess cp : DS.getCrmController().getCRM_Processes(s, false)) {
            System.err.println(cp.toString());
        }

        for (Customer cc : DS.getCrmController().getCRM_CustomerActiveCases(false)) {
            System.err.println(cc.toString());
        }
    }
}
