package db.interfaces;

import db.ent.Customer;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public interface IFSOController extends CRUDInterface<Owner> {

    List<FsProp> getAllFSProperties(Owner owner);

    List<Owner> getAllOwners(Fuelstation fuelstation);

    void addNew(Customer customer, Fuelstation fuelstation, Date dateFrom, Date dateTo, boolean active) throws Exception;

    void updateExisting(Long ID, Customer customer, Fuelstation fuelstation, Date dateFrom, Date dateTo, boolean active) throws Exception;

}
