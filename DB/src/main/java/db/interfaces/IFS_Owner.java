package db.interfaces;

import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;

/**
 *
 * @author root
 */
public interface IFS_Owner extends CRUDInterface<Owner> {

    public void addNew(Customer customer, Fuelstation fuelstation, String dateFrom, String dateTo, boolean active) throws Exception;

    public void updateExisting(Long ID, Customer customer, Fuelstation fuelstation, String dateFrom, String dateTo, boolean active) throws Exception;

}
