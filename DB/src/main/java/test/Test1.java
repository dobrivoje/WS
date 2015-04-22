/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.InfSysUser;
import db.ent.Log;
import db.ent.Salesman;
import db.interfaces.IInfSysUserController;
import db.interfaces.ILOGController;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Test1 {

    static DataService DS = DataService.getDefault();
    static final IInfSysUserController UC = DS.getInfSysUserController();
    static final ILOGController LC = DS.getLogController();

    public static void main(String[] args) {

        Salesman s = DS.getSalesmanController().getByID(1L);
        System.err.println("Salesman : " + s.toString());

        InfSysUser u = DS.getInfSysUserController().getByID(12L);
        System.err.println("InfSysUser : " + u.toString());

        System.err.println("Salesman : " + s.toString() + ", InfSys Useer : " + UC.getInfSysUser(s).toString());
        System.err.println("InfSys User : " + u.toString() + ", IS User : " + u.toString());

        try {
            LC.addNew(new Date(), "logovanje", "Logovanje korisnika : " + u.toString(), u);
        } catch (Exception ex) {
            Logger.getLogger(Test1.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Log L : LC.getLogByInfSysUser(u)) {
            System.err.println(L.toString());
        }
    }
}
