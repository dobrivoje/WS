/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.RelCBType;
import java.util.Date;

/**
 *
 * @author root
 */
public interface IREL_CBT extends CRUDInterface<RelCBType> {

    public void addNew(Customer customer, CustomerBussinesType customerBussinesType, Date dateFrom, Date dateTo, boolean active)
            throws Exception;

    public void updateExisting(Long ID, Customer customer, CustomerBussinesType customerBussinesType, Date dateFrom, Date dateTo, boolean active)
            throws Exception;
}
