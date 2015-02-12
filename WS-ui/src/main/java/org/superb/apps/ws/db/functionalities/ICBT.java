/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.functionalities;

import java.util.List;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;

/**
 *
 * @author root
 */
public interface ICBT extends CRUDInterface<CustomerBussinesType> {

    //<editor-fold defaultstate="collapsed" desc="Read data">
    List<Customer> getAllCustomersForCBT(long IDCustomerBussinesType);

    List<Customer> getAllCustomersForBussinesType(CustomerBussinesType customerBussinesType);

    @Override
    public List<CustomerBussinesType> getByName(String partialName);

    @Override
    public CustomerBussinesType getByID(long ID);

    @Override
    public List<CustomerBussinesType> getAll();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Write data">
    void addNewCBT(Customer customer, CustomerBussinesType CBType, String dateFrom, String dateTo, boolean active) throws Exception;

    @Override
    public void updateExisting(CustomerBussinesType object) throws Exception;

    @Override
    public void addNew(CustomerBussinesType newObject) throws Exception;
    //</editor-fold>

}
