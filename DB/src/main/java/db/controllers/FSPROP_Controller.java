package db.controllers;

import db.DBHandler;
import db.ent.Customer;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Owner;
import db.interfaces.IFSPROPController;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class FSPROP_Controller implements IFSPROPController {

    private static DBHandler dbh;

    public FSPROP_Controller(DBHandler dbh) {
        FSPROP_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read data">
    @Override
    public List<FsProp> getAll() {
        return dbh.getAllFSProps();
    }

    @Override
    public List<FsProp> getAllFSProperties(Fuelstation fuelstation, boolean active) {
        return dbh.getAllFSProperties(fuelstation, active);
    }

    @Override
    public FsProp getNewestFSPropForFS(Fuelstation fuelstation) {
        return dbh.getNewestFSPropForFS(fuelstation);
    }

    @Override
    public List<FsProp> getAllFSPropertiesByCustomer(Customer customer, Fuelstation fuelstation, boolean active) {
        return dbh.getAllFSPropertiesByCustomer(customer, fuelstation, active);
    }

    @Override
    public FsProp getByID(Long ID) {
        return dbh.getFSProp(ID);
    }

    @Override
    public List<FsProp> getByName(String partialName) {
        return dbh.getAllFSProperties(partialName);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    @Override
    public void addNewFSProp(Owner owner, Date propDate, int noOfTanks, boolean restaurant, int truckCapable, boolean carWash, String compliance, String licence, Date dateLicenceFrom, Date dateLicenceTo, boolean active) throws Exception {
        dbh.addNewFSProp(owner, propDate, noOfTanks, restaurant, truckCapable, carWash, compliance, licence, dateLicenceFrom, dateLicenceTo, active);
    }

    @Override
    public void updateExisting(FsProp fsProp, Owner owner, Date propDate, int noOfTanks, boolean restaurant, int truckCapable, boolean carWash, String compliance, String licence, Date dateLicenceFrom, Date dateLicenceTo, boolean active) throws Exception {
        dbh.updateExistingFSProp(fsProp, owner, propDate, noOfTanks, restaurant, truckCapable, carWash, compliance, licence, dateLicenceFrom, dateLicenceTo, active);
    }

    @Override
    public void addNew(FsProp newObject) throws Exception {
        dbh.addNewFSProp(newObject);
    }

    @Override
    public void updateExisting(FsProp object) throws Exception {
        dbh.updateExistingFSProp(object);
    }
    //</editor-fold>
}
