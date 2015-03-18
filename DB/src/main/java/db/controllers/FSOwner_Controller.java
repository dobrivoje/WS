package db.controllers;

import db.DBHandler;
import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;
import db.interfaces.IFSOController;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class FSOwner_Controller implements IFSOController {

    private static DBHandler dbh;

    public FSOwner_Controller(DBHandler dbh) {
        FSOwner_Controller.dbh = dbh;
    }

    //<editor-fold defaultstate="collapsed" desc="Read Data">
    @Override
    public List<Owner> getAll() {
        return dbh.getAllFSOwners();
    }

    @Override
    public Owner getByID(Long ID) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public List<Owner> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Owner getCurrentFSOwner(Fuelstation fuelstation) {
        return dbh.getFSOwner(fuelstation);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    @Override
    public void addNew(Owner newObject) throws Exception {
        dbh.addNewOwner(newObject);
    }

    @Override
    public void addNew(Customer customer, Fuelstation fuelstation, Date dateFrom, Date dateTo, boolean active) throws Exception {
        dbh.addNewOwner(customer, fuelstation, dateFrom, dateTo, active);
    }

    @Override
    public void updateExisting(Owner object) throws Exception {
        dbh.updateOwner(object);
    }

    @Override
    public void updateAllOwnerFSActiveFalse(Fuelstation fuelstation) throws Exception {
        dbh.updateAllOwnerFSActiveFalse(fuelstation);
    }

    @Override
    public Owner changeFSOwner(Fuelstation fs) throws Exception {
        Owner oldOwner = getCurrentFSOwner(fs);
        if (oldOwner != null) {
            oldOwner.setActive(false);
            oldOwner.setDateTo(new Date());

            try {
                updateExisting(oldOwner);
            } catch (Exception e) {
                throw new Exception("Old FS owner update not successful !");
            }
        }

        Owner newOwner = new Owner();
        newOwner.setFkIdFs(fs);
        newOwner.setActive(true);
        newOwner.setDateFrom(new Date());

        try {
            updateExisting(newOwner);
        } catch (Exception e) {
            throw new Exception("New FS owner update not successful !");
        }

        return newOwner;
    }
    //</editor-fold>

}
