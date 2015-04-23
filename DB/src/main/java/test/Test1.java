/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.CrmProcess;
import db.ent.Customer;
import db.ent.InfSysUser;
import db.ent.Salesman;
import db.interfaces.IInfSysUserController;
import db.interfaces.ILOGController;
import java.util.Date;

/**
 *
 * @author root
 */
public class Test1 {

    public static void main(String[] args) {

        DataService DS = DataService.getDefault();
        final IInfSysUserController UC = DS.getInfSysUserController();
        final ILOGController LC = DS.getLogController();

        Customer c = DS.getCustomerController().getByID(592L);

        Salesman s = DS.getSalesmanController().getByID(1L);
        System.err.println("Salesman : " + s.toString());

        InfSysUser u = DS.getInfSysUserController().getByID(12L);
        System.err.println("InfSysUser : " + u.toString());

        System.err.println("Salesman : " + s.toString() + ", InfSys Useer : " + UC.getInfSysUser(s).toString());
        System.err.println("InfSys User : " + u.toString() + ", IS User : " + u.toString());

        int count = DS.getCrmController().getCRM_Processes(c, new Date(), new Date()).size();

        System.err.println("customer active processes : " + count);

        for (CrmProcess cp : DS.getCrmController().getCRM_Processes(c, null, null)) {
            System.err.println("*** : " + cp.toString());
        }
    }
}
