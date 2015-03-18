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
public interface IFSPROPController extends CRUDInterface<FsProp> {

    List<FsProp> getAllFSProperties(Fuelstation fuelstation, boolean active);

    /**
     * Za unetog vlasnika, vrati poslednje svojstvo stanice.
     *
     * @param owner
     * @return
     */
    FsProp getCurrentFSProp(Owner owner);

    /**
     * Za unetu stanicu vrati poslednje svojstvo stanice.
     *
     * @param fuelstation
     * @return
     */
    FsProp getCurrentFSProp(Fuelstation fuelstation);

    /**
     * @param customer
     * @param fuelstation
     * @param active
     * @return List FsProp
     *
     * Za unetu stanicu i vlasnika, vrati listu svih svojstava.
     */
    List<FsProp> getAllFSPropertiesByCustomer(Customer customer, Fuelstation fuelstation, boolean active);

    /**
     * Izmeni vlasništvo FS-a.
     *
     * @param owner
     * @return
     * @throws java.lang.Exception
     */
    FsProp changeFSProp(Owner owner) throws Exception;

    void addNewFSProp(Owner owner, Date propDate, int noOfTanks, boolean restaurant,
            int truckCapable, boolean carWash, String compliance, String licence,
            Date dateLicenceFrom, Date dateLicenceTo, boolean active)
            throws Exception;

    void updateExisting(FsProp fsProp, Owner owner, Date propDate, int noOfTanks, boolean restaurant,
            int truckCapable, boolean carWash, String compliance, String licence,
            Date dateLicenceFrom, Date dateLicenceTo, boolean active)
            throws Exception;
}
