/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.DBHandler;
import db.ent.InfSysUser;
import db.ent.RelUserSalesman;
import db.interfaces.ICRMController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ISalesmanController;

/**
 *
 * @author root
 */
public class Test1 {

    static ICRMController CC = DataService.getDefault().getCRMController();
    static ISalesmanController SC = DataService.getDefault().getSalesmanController();
    static IInfSysUserController IS = DataService.getDefault().getINFSYSUSERController();
    static DBHandler dbh = DBHandler.getDefault();

    public static void main(String[] args) {
        for (InfSysUser I : IS.getAll()) {
            System.err.println(I.getUserName() + ", SectorManager : " + I.getSectorManager() + ", TopManager : " + I.getTopManager());
        }

        for (InfSysUser I : IS.getAll()) {
            for (RelUserSalesman R : I.getRelUserSalesmanList()) {
                System.err.println(R.getFK_IDS().toString() + " -> " + I.getUserName());
            }
        }

        System.err.println("INTERMOL\\sjankovic Top Manager -> " + IS.getByID("INTERMOL\\sjankovic").getTopManager());

        for (InfSysUser I : IS.getAdminUsers(true)) {
            System.err.println("Admin users : " + I.getUserName());
        }

        for (InfSysUser I : IS.getTopManagerUsers(true)) {
            System.err.println("TopManagerUsers users : " + I.getUserName());
        }

        for (InfSysUser I : IS.getSectorManagerUsers(true)) {
            System.err.println("SectorManagerUsers users : " + I.getUserName());
        }

        InfSysUser IU = IS.getByID("INTERMOL\\SJankovic");
        System.err.println();
        System.err.println(IU + " ,admin user ? " + IS.getAdminUsers(true).contains(IU));
        System.err.println(IU + " ,sector manager ? " + IS.getSectorManagerUsers(true).contains(IU));
        System.err.println(IU + " ,top manager ? " + IS.getTopManagerUsers(true).contains(IU));

        System.err.println("User type : " + IS.getInfSysUserType(IU));

        System.err.println("---------------------------------------------------");

        for (InfSysUser u : IS.getAll()) {
            System.err.println("User : " + u.getUserName() + ", type : " + IS.getInfSysUserType(u));
        }
    }
}
