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
        return dbh.getOwner(ID);
    }

    @Override
    public List<Owner> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Owner getCurrentFSOwner(Fuelstation fuelstation) {
        return dbh.getCurrentFSOwner(fuelstation);
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
    public Owner changeFSOwner(Fuelstation fs, Customer newCustomer) throws Exception {
        Owner newOwner = new Owner();
        Owner currentOwner = getCurrentFSOwner(fs);

        newOwner.setFkIdFs(fs);
        newOwner.setFKIDCustomer(newCustomer);
        newOwner.setActive(true);
        newOwner.setDateFrom(new Date());

        if (currentOwner != null) {
            currentOwner.setActive(false);
            currentOwner.setDateTo(new Date());

            try {
                updateExisting(currentOwner);
            } catch (Exception ex) {
                throw new Exception("Old FS owner update not successful !");
            }
        }

        try {
            addNew(newOwner);
        } catch (Exception ex) {
            throw new Exception("New FS owner addition not successful !");
        }

        return newOwner;
    }
    //</editor-fold>

}
