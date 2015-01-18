/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;
import org.superb.apps.ws.db.entities.RelCBType;

/**
 *
 * @author root
 */
public class DBHandler {

    //<editor-fold defaultstate="collapsed" desc="System definitions">
    private static DBHandler instance;
    private static final String PERSISTENCE_UNIT_ID = "org.superb.apps.ws_PU";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_ID);
    private static final EntityManager em = emf.createEntityManager();

    public static synchronized EntityManager getEm() throws NullPointerException, Exception, java.net.UnknownHostException, java.sql.SQLException {
        return em;
    }

    private DBHandler() {
    }

    public static synchronized DBHandler getDefault() {
        return instance == null ? instance = new DBHandler() : instance;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Customer">
    //<editor-fold defaultstate="collapsed" desc="Customer Read Data">
    public List<Customer> getAllCustomers() {
        try {
            return (List<Customer>) getEm().createNamedQuery("Customer.findAll").getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Customer getCustomerByID(Long customerID) {
        try {
            return (Customer) getEm().createNamedQuery("Customer.findByID")
                    .setParameter("ID", customerID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCustomerByName(String partialName) {
        try {
            return getEm().createNamedQuery("Customer.PartialName")
                    .setParameter("partialName", partialName.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCustomerByCity(String partialCityName) {
        try {
            return getEm().createNamedQuery("Customer.PartialCity")
                    .setParameter("partialCity", partialCityName.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCustomerByAddress(String partialAddress) {
        try {
            return getEm().createNamedQuery("Customer.PartialAddress")
                    .setParameter("partialAddress", partialAddress.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCustomerByZIP(String zip) {
        try {
            return getEm().createNamedQuery("Customer.findByZip")
                    .setParameter("zip", zip.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCustomerByRegion(String partialRegion) {
        try {
            return getEm().createNamedQuery("Customer.PartialRegion")
                    .setParameter("partialRegion", partialRegion.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Customer Add/Update Data">
    public void addNewCustomer(String name, String address, String city, String zip, String region, String PIB) throws Exception {
        Customer newCustomer = new Customer();

        newCustomer.setName(name);
        newCustomer.setAddress(address);
        newCustomer.setCity(city);
        newCustomer.setZip(zip);
        newCustomer.setRegion(region);
        newCustomer.setPib(PIB);

        getEm().getTransaction().begin();
        em.persist(newCustomer);
        getEm().getTransaction().commit();
    }

    public void addNewCustomer(Customer newCustomer) throws Exception {
        getEm().getTransaction().begin();
        em.persist(newCustomer);
        getEm().getTransaction().commit();
    }

    public void updateCustomer(Long customerID, String name, String address, String city, String zip, String region, String PIB) throws Exception {
        Customer customer = getCustomerByID(customerID);

        customer.setName(name);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setZip(zip);
        customer.setRegion(region);
        customer.setPib(PIB);

        getEm().getTransaction().begin();
        em.merge(customer);
        getEm().getTransaction().commit();
    }

    public void updateCustomer(Customer customer) throws Exception {
        getEm().getTransaction().begin();
        em.merge(customer);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Customer Bussines Type">
    //<editor-fold defaultstate="collapsed" desc="Customer Read Data">
    public CustomerBussinesType getCustomerBussinesType(int IDCBT) {
        try {
            return (CustomerBussinesType) getEm().createNamedQuery("CustomerBussinesType.findByIdcbt")
                    .setParameter("idcbt", IDCBT)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CustomerBussinesType> getAllCustomerBussinesTypes() {
        try {
            return (List<CustomerBussinesType>) getEm().createNamedQuery("CustomerBussinesType.findAll").getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CustomerBussinesType> getAllCustomerBussinesTypes(String activityName) {
        try {
            return getEm().createNamedQuery("CustomerBussinesType.findByActivity")
                    .setParameter("customer_activity", activityName.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getAllCustomersForBussinesType(CustomerBussinesType bussinesType) {
        return bussinesType.getAllCustomersForBussinesType();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Customer Add/Update Data">
    public void addNewCustomerBussinesType(String newCustomerBussinesType) throws Exception {
        CustomerBussinesType newCBT = new CustomerBussinesType();
        newCBT.setCustomerActivity(newCustomerBussinesType);

        getEm().getTransaction().begin();
        em.persist(newCBT);
        getEm().getTransaction().commit();
    }

    public void updateCustomerBussinesType(int IDCBT, String newCustomerBussinesType) throws Exception {
        CustomerBussinesType newCBT = getCustomerBussinesType(IDCBT);
        newCBT.setCustomerActivity(newCustomerBussinesType);

        getEm().getTransaction().begin();
        em.merge(newCBT);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Relation: CB TYPE - CUSTOMER">
    public void addNew_CBT_CUSTOMER(CustomerBussinesType IDCBT, Customer IDC, String DateFrom, String DateTo, boolean active) throws Exception {
        RelCBType newRelCBType = new RelCBType();
        newRelCBType.setFK_IDC(IDC);
        newRelCBType.setFK_IDCBT(IDCBT);
        newRelCBType.setDateFrom(DateFrom);
        newRelCBType.setDateTo(DateTo);
        newRelCBType.setActive(active);

        getEm().getTransaction().begin();
        em.persist(newRelCBType);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
}
