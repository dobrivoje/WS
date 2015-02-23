package db.interfaces;

import db.ent.Customer;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.List;

/**
 *
 * @author root
 */
public interface IFSOController extends CRUDInterface<Owner> {

    List<FsProp> getAllFSProperties(Owner owner);

    void addNew(Customer customer, Fuelstation fuelstation, String dateFrom, String dateTo, boolean active) throws Exception;

    void updateExisting(Long ID, Customer customer, Fuelstation fuelstation, String dateFrom, String dateTo, boolean active) throws Exception;

}
