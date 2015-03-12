/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataservice;

import db.DBHandler;
import db.controllers.CBT_Controller;
import db.controllers.City_Controller;
import db.controllers.Customer_Controller;
import db.controllers.FSOwner_Controller;
import db.controllers.FSPROP_Controller;
import db.controllers.FS_Controller;
import db.controllers.RelCBT_Controller;
import db.controllers.Salesman_Controller;
import db.interfaces.ICBTController;
import db.interfaces.ICityController;
import db.interfaces.ICustomerController;
import db.interfaces.IFSController;
import db.interfaces.IFSOController;
import db.interfaces.IFSPROPController;
import db.interfaces.IRELCBTController;
import db.interfaces.ISalesmanController;

/**
 *
 * @author root
 */
public class DataService {

    //<editor-fold defaultstate="collapsed" desc="System Defs">
    private static DataService instance;

    private static final DBHandler DBH = DBHandler.getDefault();

    private DataService() {
    }

    public static synchronized DataService getDefault() {
        return instance == null ? instance = new DataService() : instance;
    }
    //</editor-fold>

    private final ICBTController cBTController = new CBT_Controller(DBH);
    private final ICityController cityController = new City_Controller(DBH);
    private final ICustomerController customerController = new Customer_Controller(DBH);
    private final IFSController fSController = new FS_Controller(DBH);
    private final IFSOController fSOController = new FSOwner_Controller(DBH);
    private final IFSPROPController fSPROPController = new FSPROP_Controller(DBH);
    private final IRELCBTController rELCBTController = new RelCBT_Controller(DBH);
    private final ISalesmanController salesmanController = new Salesman_Controller(DBH);

    public ICBTController getCBTController() {
        return cBTController;
    }

    public ICityController getCityController() {
        return cityController;
    }

    public ICustomerController getCustomerController() {
        return customerController;
    }

    public IFSController getFSController() {
        return fSController;
    }

    public IFSOController getFSOController() {
        return fSOController;
    }

    public IFSPROPController getFSPROPController() {
        return fSPROPController;
    }

    public IRELCBTController getRELCBTController() {
        return rELCBTController;
    }

    public ISalesmanController getSalesmanController() {
        return salesmanController;
    }

}