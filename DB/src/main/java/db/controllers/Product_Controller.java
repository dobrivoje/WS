/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.controllers;

import db.DBHandler;
import db.ent.BussinesLine;
import db.ent.Product;
import db.ent.Salesman;
import db.interfaces.IPRODUCTController;
import java.util.List;

/**
 *
 * @author root
 */
public class Product_Controller implements IPRODUCTController {

    private static DBHandler dbh;

    public Product_Controller(DBHandler dbh) {
        Product_Controller.dbh = dbh;
    }

    @Override
    public Product getByID(Long ID) {
        return dbh.getProductByID(ID);
    }

    @Override
    public List<Product> getAll() {
        return dbh.getAllProducts();
    }

    @Override
    public List<Product> getProductsForBussinesLine(BussinesLine bussinesLine) {
        return dbh.getProductsByBussinesLine(bussinesLine);
    }

    @Override
    public List<Product> getProductsForBussinesLine(Salesman salesman) {
        return getProductsForBussinesLine(salesman.getFkIdbl());
    }
}
