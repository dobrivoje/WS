/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Customer;
import db.ent.InfSysUser;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import java.util.Date;

/**
 *
 * @author root
 */
public class Test1 {

    public static void main(String[] args) {

    DataService DS = DataService.getDefault();

    InfSysUser isu = DS.getInfSysUserController().getByID("ws");
    Customer c = DS.getCustomerController().getByID(900L);
    Salesman s = DS.getSalesmanController().getByID(1L);

    System.err.println (

    "Salesman : " + s.toString());
    System.err.println (
    "salesman from infsysuser : "
                + DS.getInfSysUserController().getSalesman(isu));

        int count = DS.getCrmController().getCRM_Processes(c, new Date(), new Date()).size();

    System.err.println (

    
        "customer active processes : " + count);

        try {
            RelSALESMANCUST rsc = DS.getCrmController().getCRM_R_SalesmanCustomer(s, c);
        System.err.println("relation : " + c.getName() + ", " + s.toString() + " -> exists.");

        CrmCase newCrmCase = new CrmCase(new Date(), "Novi CRM slučaj", rsc);

            //DS.getCrmController().addNewCRM_Case(newCrmCase);
        //CrmCase newCrmCase = DS.getCrmController().getCRM_Case(2L);
        System.err.println("CRM case : " + newCrmCase.toString());
        CrmStatus crmStatus = DS.getCrmController().getCRM_Status((long) (1 + (int) (3 * Math.random())));
        System.err.println("Status : " + crmStatus.toString());

        //DS.getCrmController().addNewCRM_Process(newCrmCase, crmStatus, "komentar", new Date());
        for (CrmCase cc : DS.getCrmController().getCRM_Cases(s, false)) {
            System.err.println(cc.toString());
        }

        for (CrmProcess cp : DS.getCrmController().getCRM_Processes(s, null, null)) {
            System.err.println(cp.toString());
        }

    }
    catch (Exception ex

    
        ) {
            System.err.println("Error : " + ex.getMessage());
    }
}
}
