/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.superb.apps.ws.db.entities.BussinesLine;
import org.superb.apps.ws.db.entities.Customer;
import org.superb.apps.ws.db.entities.CustomerBussinesType;
import org.superb.apps.ws.db.entities.Fuelstation;
import org.superb.apps.ws.db.entities.Gallery;
import org.superb.apps.ws.db.entities.Image;
import org.superb.apps.ws.db.entities.RelCBType;
import org.superb.apps.ws.db.entities.RelSALESMANIMAGE;
import org.superb.apps.ws.db.entities.Salesman;

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

    public static EntityManager getEm() throws NullPointerException, Exception, java.net.UnknownHostException, java.sql.SQLException {
        return em;
    }

    private DBHandler() {
    }

    public static DBHandler getDefault() {
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

    //<editor-fold defaultstate="collapsed" desc="Fuel station">
    //<editor-fold defaultstate="collapsed" desc="Customer Read Data">
    public List<Fuelstation> getAllFuelstation() {
        try {
            return (List<Fuelstation>) getEm().createNamedQuery("Fuelstation.findAll").getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Fuelstation getFuelstationByID(int fuelstationID) {
        try {
            return (Fuelstation) getEm().createNamedQuery("Fuelstation.findByIdfs")
                    .setParameter("idfs", fuelstationID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Fuelstation> getFuelstationByName(String partialName) {
        try {
            return getEm().createNamedQuery("Fuelstation.PartialName")
                    .setParameter("partialName", partialName.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Customer Add/Update Data">
    public void addNewFS(String name, String city, String address, String coordinates) throws Exception {
        Fuelstation newFuelstation = new Fuelstation();

        newFuelstation.setName(name);
        newFuelstation.setAddress(address);
        newFuelstation.setCity(city);
        newFuelstation.setCity(coordinates);

        try {
            getEm().getTransaction().begin();
            em.persist(newFuelstation);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            getEm().getTransaction().rollback();
        } finally {
            if (getEm().getTransaction().isActive()) {
                getEm().close();
            }
        }
    }

    public void addNewFS(Fuelstation newFuelstation) throws Exception {
        getEm().getTransaction().begin();
        em.persist(newFuelstation);
        getEm().getTransaction().commit();
    }

    public void updateFS(int fuelstationID, String name, String city, String address, String coordinates) throws Exception {
        Fuelstation customer = getFuelstationByID(fuelstationID);

        customer.setName(name);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setCoordinates(coordinates);

        getEm().getTransaction().begin();
        em.merge(customer);
        getEm().getTransaction().commit();
    }

    public void updateFS(Fuelstation fuelstation) throws Exception {
        getEm().getTransaction().begin();
        em.merge(fuelstation);
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
    public void addNewCustomerBussinesType(CustomerBussinesType newCustomerBussinesType) throws Exception {
        getEm().getTransaction().begin();
        em.persist(newCustomerBussinesType);
        getEm().getTransaction().commit();
    }

    public void updateCustomerBussinesType(CustomerBussinesType newCustomerBussinesType) throws Exception {
        getEm().getTransaction().begin();
        em.merge(newCustomerBussinesType);
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
    public void addNew_CBT_CUSTOMER(Customer IDC, CustomerBussinesType IDCBT, String DateFrom, String DateTo, boolean active) throws Exception {
        RelCBType newRelCBType = new RelCBType();
        newRelCBType.setFK_Customer(IDC);
        newRelCBType.setFK_CBT(IDCBT);
        newRelCBType.setDateFrom(DateFrom);
        newRelCBType.setDateTo(DateTo);
        newRelCBType.setActive(active);

        getEm().getTransaction().begin();
        em.persist(newRelCBType);
        getEm().getTransaction().commit();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SALESMAN">
    //<editor-fold defaultstate="collapsed" desc="SALESMAN READ">
    public Salesman getSalesman(int IDS) {
        try {
            return (Salesman) getEm().createNamedQuery("Salesman.findByIds")
                    .setParameter("ids", IDS)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Salesman> getSalesmanByName(String name) {
        try {
            return getEm().createNamedQuery("Salesman.findByName")
                    .setParameter("name", name)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Salesman> getSalesmanBySurname(String surname) {
        try {
            return getEm().createNamedQuery("Salesman.findBySurname")
                    .setParameter("surname", surname)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Salesman> getSalesmanByPosition(String position) {
        try {
            return getEm().createNamedQuery("Salesman.findByPosition")
                    .setParameter("position", position)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Salesman> getAllSalesmanByActivity(boolean active) {
        try {
            return getEm().createNamedQuery("Salesman.findByActive")
                    .setParameter("active", active)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SALESMAN Add/Update Data">
    public void addNewSalesman(String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        Salesman newSalesman = new Salesman();

        newSalesman.setName(name);
        newSalesman.setSurname(surname);
        newSalesman.setPosition(position);
        newSalesman.setActive(active);
        newSalesman.setDateFrom(dateFrom);
        newSalesman.setDateTo(dateTo);
        newSalesman.setFK_BussinesLine(BL);

        getEm().getTransaction().begin();
        em.persist(newSalesman);
        getEm().getTransaction().commit();
    }

    public void addNewSalesman(Salesman newSalesman) throws Exception {
        getEm().getTransaction().begin();
        em.persist(newSalesman);
        getEm().getTransaction().commit();
    }

    public void updateSalesman(int IDS, String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        Salesman newSalesman = getSalesman(IDS);

        newSalesman.setName(name);
        newSalesman.setSurname(surname);
        newSalesman.setPosition(position);
        newSalesman.setActive(active);
        newSalesman.setDateFrom(dateFrom);
        newSalesman.setDateTo(dateTo);
        newSalesman.setFK_BussinesLine(BL);

        getEm().getTransaction().begin();
        em.persist(newSalesman);
        getEm().getTransaction().commit();
    }

    public void updateSalesman(Salesman newSalesman) throws Exception {
        getEm().getTransaction().begin();
        em.persist(newSalesman);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GALLERY & IMAGES">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public Gallery getGallery(int IDG) {
        try {
            return (Gallery) getEm().createNamedQuery("Gallery.findByIdg")
                    .setParameter("idg", IDG)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Gallery> getAllGalleries() {
        try {
            return getEm().createNamedQuery("Gallery.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Image getImage(long IDI) {
        try {
            return (Image) getEm().createNamedQuery("Image.findByIdi")
                    .setParameter("idi", IDI)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Image> getAllGalleryImages(Gallery gallery) {
        return gallery.getImageList();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewGallery(String name) throws Exception {
        Gallery newGallery = new Gallery();

        newGallery.setName(name);

        getEm().getTransaction().begin();
        em.persist(newGallery);
        getEm().getTransaction().commit();
    }

    public void addNewImage(Gallery gallery, String imageName, Serializable imageData, Date imageUploadDate) throws Exception {
        Image newImage = new Image();

        newImage.setFK_IDGallery(gallery);
        newImage.setName(imageName);
        newImage.setImageData(imageData);
        newImage.setUploadDate(imageUploadDate);

        getEm().getTransaction().begin();
        em.persist(newImage);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SALESMAN_IMAGE">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public List<RelSALESMANIMAGE> getAllSalesmanImages() {
        try {
            return getEm().createNamedQuery("RelSALESMANIMAGE.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addSalesmanImage(Salesman salesman, Image image) throws Exception {
        RelSALESMANIMAGE newASALESMANIMAGE = new RelSALESMANIMAGE();

        newASALESMANIMAGE.setFK_Salesman(salesman);
        newASALESMANIMAGE.setFK_Image(image);

        getEm().getTransaction().begin();
        em.persist(newASALESMANIMAGE);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>
}
