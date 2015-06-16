/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.BussinesLine;
import db.ent.InfSysUser;
import db.ent.Product;
import db.ent.Salesman;

/**
 *
 * @author root
 */
public class Test1 {

    public static void main(String[] args) {

        DataService DS = DataService.getDefault();

        InfSysUser isu = DS.getINFSYSUSERController().getByID("INTERMOL\\RDragutinovic");
        Salesman s = DS.getINFSYSUSERController().getSalesman(isu);
        BussinesLine bl = s.getFkIdbl();
        
        System.err.println("salesman from infsysuser : " + s);
        
        for (Product p : DS.getProductController().getProductsForBussinesLine(bl)) {
            System.err.println(p);
        }
    }
}
