/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dataservice.DataService;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Customer;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import java.util.Date;

/**
 *
 * @author root
 */
public class Test1 {

    static DataService DS = DataService.getDefault();

    public static void main(String[] args) {

        //<editor-fold defaultstate="collapsed" desc="test FS, FSOWNER, i FSPROP">
        /*
         Fuelstation fs = DS.getFSController().getByID(1L);
         System.out.println("Fuelstation : " + fs.toString());
        
         Owner currentOwner = DS.getFSOController().getCurrentFSOwner(fs);
         System.err.println("currentOwner  : " + (currentOwner != null ? currentOwner.getFKIDCustomer().getName() : " n/a!"));
        
         FsProp currentFsProp = DS.getFSPROPController().getCurrentFSProp(currentOwner);
         System.err.println("currentFsProp  : " + (currentFsProp != null
         ? currentFsProp.getPropertiesDate() + ", "
         + (currentFsProp.getActive() ? "active" : "not active") + ", "
         + (currentFsProp.getCarWash() ? "car wash" : "car wash-na") + ", "
         + (currentFsProp.getNoOfTanks() != null ? "NoOfTanks=" + currentFsProp.getNoOfTanks() : "NoOfTanks n/a") + ", "
         + "TruckCapable=" + currentFsProp.getTruckCapable()
         : " n/a!"));
        
         Owner o1 = DS.getFSOController().getByID(52L);
         Owner newOwner = new Owner(o1);
         System.err.println("o1==newOwner ? " + (o1 == newOwner));
         System.err.println("");
        
         System.err.println("o1  : " + (o1 != null
         ? o1.getDateFrom() + ", "
         + (o1.getActive() ? " o1 active" : " o1 not active") + ", "
         + (o1.getFKIDCustomer() != null ? " o1 Cust:" + o1.getFKIDCustomer() : " o1 cust n/a")
         : " o1 n/a!"));
        
         System.err.println("newOwner  : " + (o1 != null
         ? newOwner.getDateFrom() + ", "
         + (newOwner.getActive() ? " newOwner active" : " newOwner not active") + ", "
         + (newOwner.getFKIDCustomer() != null ? " newOwner Cust:" + newOwner.getFKIDCustomer() : " newOwner cust n/a")
         : " newOwner n/a!"));
        
         FsProp fsProp = new FsProp();
        
         try {
         fsProp = DS.getFSPROPController().getCurrentFSProp(o1);
         } catch (Exception ex) {
         }
        
         fsProp.setActive(false);
         fsProp.setPropertiesDate(new Date());
        
         System.err.println("fsProp : " + fsProp.toString());
        
         FsProp fsProp1 = DBHandler.getDefault().getCurrentFSProp(currentOwner);
         System.err.println("feprop1 : "+fsProp1);
         */
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="test: MS manag. studio -> ručna izmena Customer.Name polja. ">
        // TEST USPEŠAN ! DS.getCustomerController().getAll() vraća ažuriranu vrednost !
        /*
         List<Customer> customers = DS.getCustomerController().getAll();
         Customer superbApps = DS.getCustomerController().getByID(9L);
        
         for (Customer c : customers) {
         if (c.equals(superbApps)) {
         System.err.println("Superb Apps : " + c.getName());
         }
         }
         */
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="test: upis novog fs owner-a. OK">
        /*
         Fuelstation f = DS.getFSController().getByID(2L);
         Owner co = DS.getFSOController().getCurrentFSOwner(f);
         System.err.println("fs : " + f.toString());
         System.err.println("currentOwner : " + (co != null ? co.toString() : "n/a"));
        
         Customer nc = DS.getCustomerController().getByID(3L);
         if (co == null) {
         try {
         DS.getFSOController().addNew(nc, f, new Date(), null, true);
         } catch (Exception ex) {
         }
         }
         */
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="test Galerija FS - ok">
        /*
         Fuelstation fs = DS.getFSController().getByID(1L);
         Gallery g = DS.getGalleryController().getDefaultImageGallery();
        
         // System.err.println(DS.getDocumentController().getDefaultFSImage(fs).getName());
        
         System.err.println(
         DBHandler.getDefault().getHighPriorityFSImage(fs));
         */
        //</editor-fold>
        Salesman s = DS.getSalesmanController().getByID(3L);
        Customer cu = DS.getCustomerController().getByID(3500L);
        CrmStatus st = DS.getCrmController().getCRM_AllStatuses().get(0);

        RelSALESMANCUST r = null;
        try {
            r = DS.getCrmController().getCRM_R_SalesmanCustomer(s, cu);
        } catch (Exception e) {
        }
        
        try {
            CrmProcess crmProcess = new CrmProcess(r, null, "komentar", new Date());

            DS.getCrmController().addNewCRM_Process(crmProcess);
        } catch (Exception ex) {
            System.err.println("GRESKA : ");
        }
    }
}
