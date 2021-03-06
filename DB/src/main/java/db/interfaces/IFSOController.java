package db.interfaces;

import db.ent.Customer;
import db.ent.Fuelstation;
import db.ent.Owner;
import java.util.Date;

/**
 *
 * @author root
 */
public interface IFSOController extends ICRUD<Owner> {

    /**
     * <b>Trenutni vlasnik (Owner) stanice (FS).</b>
     * <p>
     * Trenutni vlasnik je uvek u statusu Owner.active == true ! Ukoliko je
     * Owner null, onda ne postoji Owner tako da je Owner.Active==true. Ovo
     * znači ili da ne postoji ni jedan Owner ili da postoji više Owner-a za tu
     * FS, tako da su svi zapisi Owner.Active == false.</p>
     *
     * @param fuelstation
     * @return Owner
     */
    Owner getCurrentFSOwner(Fuelstation fuelstation);

    void addNew(Customer customer, Fuelstation fuelstation, Date dateFrom, Date dateTo, boolean active) throws Exception;

    /**
     * <b>Izmena vlasništva FS.</b>
     *
     * @param fs
     * @param newCustomer
     * @return
     * @throws java.lang.Exception
     */
    Owner changeFSOwner(Fuelstation fs, Customer newCustomer) throws Exception;
}
