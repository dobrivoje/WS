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
public interface IFSPROPController extends ICRUD<FsProp> {

    List<FsProp> getAllFSProperties(Fuelstation fuelstation, boolean active);

    /**
     * <b>Trenutno svojstvo stanice.</b>
     *
     * @param owner
     * @return
     */
    FsProp getCurrentFSProp(Owner owner);

    /**
     * <b>Trenutno svojstvo stanice.</b>
     *
     * @param fuelstation
     * @return
     */
    FsProp getCurrentFSProp(Fuelstation fuelstation);

    /**
     * <b>Lista svih svojstava.</b>
     * Za unetu stanicu i vlasnika, i aktivnosti, vraćanje liste svojstava
     * stanice.
     *
     * @param customer
     * @param fuelstation
     * @param active
     * @return List FsProp Lista svih svojstava.
     */
    List<FsProp> getAllFSPropertiesByCustomer(Customer customer, Fuelstation fuelstation, boolean active);

    /**
     * <b>Izmena vlasništva FS.</b>
     *
     * @param owner
     * @return
     * @throws java.lang.Exception
     */
    FsProp changeFSProp(Owner owner) throws Exception;

    void addNewFSProp(Owner owner, Date propDate, int noOfTanks, boolean restaurant,
            int truckCapable, boolean carWash, String compliance, String licence,
            Date dateLicenceFrom, Date dateLicenceTo, String comment, boolean active)
            throws Exception;

    void updateExisting(FsProp fsProp, Owner owner, Date propDate, int noOfTanks, boolean restaurant,
            int truckCapable, boolean carWash, String compliance, String licence,
            Date dateLicenceFrom, Date dateLicenceTo, String comment, boolean active)
            throws Exception;
}
