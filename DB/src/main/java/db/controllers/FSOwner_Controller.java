package db.controllers;

import db.DBHandler;
import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;
import db.interfaces.IFS_Owner;
import java.util.List;

/**
 *
 * @author root
 */
public class FSOwner_Controller implements IFS_Owner {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="Customer read data">@Override
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

    //<editor-fold defaultstate="collapsed" desc="Customer add/update data">
    @Override
    public void addNew(Owner newObject) throws Exception {
        dbh.addNewOwner(newObject);
    }

    @Override
    public void addNew(Customer customer, Fuelstation fuelstation, String dateFrom, String dateTo, boolean active) throws Exception {
        dbh.addNewOwner(customer, fuelstation, dateFrom, dateTo, active);
    }

    @Override
    public void updateExisting(Owner object) throws Exception {
        dbh.updateOwner(object);
    }

    @Override
    public void updateExisting(Long ID, Customer customer, Fuelstation fuelstation, String dateFrom, String dateTo, boolean active) throws Exception {
        dbh.updateOwner(ID, customer, fuelstation, dateFrom, dateTo, active);
    }

    //</editor-fold>
}
