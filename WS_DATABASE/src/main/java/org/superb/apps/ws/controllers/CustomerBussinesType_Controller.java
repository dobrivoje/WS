/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.controllers;

import org.superb.apps.ws.functionalities.ICustomerBussinesType;
import java.util.List;
import org.superb.apps.ws.db.DBHandler;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;

/**
 *
 * @author root
 */
public class CustomerBussinesType_Controller implements ICustomerBussinesType {

    private static final DBHandler dbh = DBHandler.getDefault();

    //<editor-fold defaultstate="collapsed" desc="REL_CBType Read Data">
    @Override
    public List<Customer> getAllCustomersForBussinesType(int IDCustomerBussinesType) {
        return dbh.getCustomerBussinesType(IDCustomerBussinesType)
                .getAllCustomersForBussinesType();
    }

    @Override
    public List<CustomerBussinesType> getAllBussinesTypes() {
        return dbh.getAllCustomerBussinesTypes();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="REL_CBType add/update data">
    //</editor-fold>
}
