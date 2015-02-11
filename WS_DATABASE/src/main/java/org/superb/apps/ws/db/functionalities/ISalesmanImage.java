/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.functionalities;

import java.util.List;
import org.superb.apps.ws.db.entities.Image;
import org.superb.apps.ws.db.entities.RelSALESMANIMAGE;
import org.superb.apps.ws.db.entities.Salesman;

/**
 *
 * @author root
 */
public interface ISalesmanImage {

    //<editor-fold defaultstate="collapsed" desc="data to read">
    public List<RelSALESMANIMAGE> getAllSalesmanImages();
    
    public RelSALESMANIMAGE getSalesmanImage(Salesman salesman);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="data to create, and update">

    public void addNewSalesmanImage(Salesman salesman, Image image) throws Exception;

    //</editor-fold>
}
