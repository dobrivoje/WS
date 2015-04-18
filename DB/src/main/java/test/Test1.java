/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.InfSysUser;
import db.ent.Salesman;
import db.interfaces.IInfSysUserController;

/**
 *
 * @author root
 */
public class Test1 {

    static DataService DS = DataService.getDefault();
    static final IInfSysUserController UC = DS.getInfSysUserController();

    public static void main(String[] args) {

        Salesman s = DS.getSalesmanController().getByID(1L);
        System.err.println("Salesman : " + s.toString());

        InfSysUser u = DS.getInfSysUserController().getByID(1L);
        System.err.println("InfSysUser : " + u.toString());

        System.err.println("Salesman : " + s.toString() + ", InfSys Useer : " + UC.getInfSysUser(s).toString());
        System.err.println("InfSys User : " + u.toString() + ", Salesman : " + UC.getSalesman(u).toString());

        /*
         Fuelstation f = DS.getFSController().getByID(1L);
         Document d = DS.getDocumentController().getByID(111L);
        
         try {
         DS.getDocumentController().setDefaultFSImage(f, d);
         } catch (Exception ex) {
         System.err.println("Greška ! Poruka : " + ex.getMessage());
         }
         */
    }
}
