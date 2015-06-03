/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.BussinesLine;
import db.ent.Product;
import java.util.List;

/**
 *
 * @author root
 */
public interface IPRODUCTController {

    Product getByID(Long ID);

    List<Product> getAll();
    
    List<Product> getProductsForBussinesLine(BussinesLine bussinesLine);

}
