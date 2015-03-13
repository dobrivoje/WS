package db.interfaces;

import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.Date;

/**
 *
 * @author root
 */
public interface IFSOController extends CRUDInterface<Owner> {

    /**
     * Za FS, vrati trenutnog Owner-a. 
     * Trenutni vlasnik je uvek u statusu Owner.active == true !
     * @param fuelstation
     * @return Owner
     */
    Owner getFSOwner(Fuelstation fuelstation);

    void addNew(Customer customer, Fuelstation fuelstation, Date dateFrom, Date dateTo, boolean active) throws Exception;

    /**
     * Posle definisanja vlasnika FS, radi se update svih Active polja na False
     * u Owner-u za tu FS, pošto dodavanje novog Owner-a znači da je samo
     * poslednji aktivan, (a svi eventualni prethodni moraju biti neaktivni).
     *
     * @param fuelstation
     * @throws java.lang.Exception
     */
    void updateAllOwnerFSActiveFalse(Fuelstation fuelstation) throws Exception;

}
