package db.controllers;

import db.DBHandler;
import db.ent.Customer;
import db.ent.FsProp;
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

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Read Data">
    @Override
    public List<FsProp> getAllFSProperties(Owner owner) {
        return dbh.getAllFSProperties(owner);
    }

    @Override
    public List<Owner> getAll() {
        return dbh.getAllFSOwners();
    }

    @Override
    public Owner getByID(Long ID) {
        return dbh.getFSOwner(ID);
    }

    @Override
    public List<Owner> getByName(String partialName) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public void updateExisting(Long ID, Customer customer, Fuelstation fuelstation, Date dateFrom, Date dateTo, boolean active) throws Exception {
        dbh.updateOwner(ID, customer, fuelstation, dateFrom, dateTo, active);
    }

    //</editor-fold>
}
