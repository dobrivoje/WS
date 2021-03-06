/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataservice;

import db.DBHandler;
import db.controllers.BLController;
import db.controllers.CBT_Controller;
import db.controllers.CRM_Controller;
import db.controllers.City_Controller;
import db.controllers.Customer_Controller;
import db.controllers.Document_Controller;
import db.controllers.FSOwner_Controller;
import db.controllers.FSPROP_Controller;
import db.controllers.FS_Controller;
import db.controllers.Gallery_Controller;
import db.controllers.RelCBT_Controller;
import db.controllers.Salesman_Controller;
import db.controllers.InfSysUser_Controller;
import db.controllers.LOG_Controller;
import db.controllers.Product_Controller;
import db.controllers.AdvancedSearch_Controller;
import db.controllers.TMarginWHS_Controller;
import db.ent.TMarginWHS;
import db.ent.custom.CustomSearchData;
import db.interfaces.IBLController;
import db.interfaces.ICBTController;
import db.interfaces.ICRMController;
import db.interfaces.ICityController;
import db.interfaces.ICustomerController;
import db.interfaces.IDocumentController;
import db.interfaces.IDocumentTypeController;
import db.interfaces.IFSController;
import db.interfaces.IFSOController;
import db.interfaces.IFSPROPController;
import db.interfaces.IGalleryController;
import db.interfaces.IRELCBTController;
import db.interfaces.ISalesmanController;
import db.interfaces.IInfSysUserController;
import db.interfaces.ILOGController;
import db.interfaces.IPRODUCTController;
import db.interfaces.IAdvancedSearchController;
import db.interfaces.ICRUD2;
import javax.persistence.EntityManager;

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

    public static synchronized EntityManager getEM() throws Exception {
        return DBHandler.getEm();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Interfaces Defs">
    private final ICBTController cBTController = new CBT_Controller(DBH);
    private final ICityController cityController = new City_Controller(DBH);
    private final ICustomerController customerController = new Customer_Controller(DBH);
    private final IFSController fSController = new FS_Controller(DBH);
    private final IFSOController fSOController = new FSOwner_Controller(DBH);
    private final IFSPROPController fSPROPController = new FSPROP_Controller(DBH);
    private final IRELCBTController rELCBTController = new RelCBT_Controller(DBH);
    private final ISalesmanController salesmanController = new Salesman_Controller(DBH);
    private final IGalleryController galleryController = new Gallery_Controller(DBH);
    private final IDocumentController documentController = new Document_Controller(DBH);
    private final IDocumentTypeController documentTypeController = new Document_Controller(DBH);
    private final ICRMController crmController = new CRM_Controller(DBH);
    private final IInfSysUserController infSysUserController = new InfSysUser_Controller(DBH);
    private final ILOGController iLogController = new LOG_Controller(DBH);
    private final IPRODUCTController iPRODUCTController = new Product_Controller(DBH);
    private final IBLController iBLC = new BLController(DBH);
    private final IAdvancedSearchController<CustomSearchData> iSC = new AdvancedSearch_Controller(DBH);
    private final ICRUD2<TMarginWHS> iTMC = new TMarginWHS_Controller(DBH);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get Interfaces">
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

    public IGalleryController getGalleryController() {
        return galleryController;
    }

    public IDocumentController getDocumentController() {
        return documentController;
    }

    public IDocumentTypeController getDocumentTypeController() {
        return documentTypeController;
    }

    public ICRMController getCRMController() {
        return crmController;
    }

    public IInfSysUserController getINFSYSUSERController() {
        return infSysUserController;
    }

    public ILOGController getLOGController() {
        return iLogController;
    }

    public IPRODUCTController getProductController() {
        return iPRODUCTController;
    }

    public IBLController getBLController() {
        return iBLC;
    }

    public IAdvancedSearchController<CustomSearchData> getSearchController() {
        return iSC;
    }

    public ICRUD2<TMarginWHS> getTMarginWHSController() {
        return iTMC;
    }

    //</editor-fold>
}
