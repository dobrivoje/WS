/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import db.ent.BussinesLine;
import db.ent.City;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Gallery;
import db.ent.Image;
import db.ent.Owner;
import db.ent.RelCBType;
import db.ent.RelSALESMANIMAGE;
import db.ent.Salesman;
import java.util.ArrayList;

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
                    .setParameter("idc", customerID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public Customer getCustomerByNavCode(String navCode) {
        try {
            return (Customer) getEm().createNamedQuery("Customer.findByNavCode")
                    .setParameter("navCode", navCode)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCustomerByLicence(boolean licence) {
        try {
            return getEm().createNamedQuery("Customer.findByLicence")
                    .setParameter("licence", licence)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Customer getCustomerByMatBr(String matBr) {
        try {
            return (Customer) getEm().createNamedQuery("Customer.findByMatBr")
                    .setParameter("matBr", matBr)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCustomerByName(String partialName) {
        try {
            return getEm().createNamedQuery("Customer.PartialName")
                    .setParameter("name", partialName.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    public void addNewCustomer(Customer newCustomer) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newCustomer);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("New Customer Addition Failed ! Error message : " + e.toString());
        }
    }

    public void addNewCustomer(String name, String address, City city, String PIB) throws Exception {
        Customer newCustomer = new Customer();

        newCustomer.setName(name);
        newCustomer.setAddress(address);
        newCustomer.setFKIDCity(city);
        newCustomer.setPib(PIB);

        addNewCustomer(newCustomer);
    }

    public void updateCustomer(Customer customer) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(customer);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Customer Update Failed ! Error message : " + e.toString());
        }
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

    public Fuelstation getFuelstationByID(Long fuelstationID) {
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

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    public void addNewFS(Fuelstation newFuelstation) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newFuelstation);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("New Fuelstation Addition Failed ! Error message : " + e.toString());
        }
    }

    public void addNewFS(String name, City city, String address, String coordinates) throws Exception {
        Fuelstation newFuelstation = new Fuelstation();

        newFuelstation.setName(name);
        newFuelstation.setAddress(address);
        newFuelstation.setFkIdc(city);
        newFuelstation.setCoordinates(coordinates);

        addNewFS(newFuelstation);
    }

    public void updateFS(Fuelstation fuelstation) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(fuelstation);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Fuelstation Update Failed ! Error message : " + e.toString());
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Customer Bussines Type">
    //<editor-fold defaultstate="collapsed" desc="Customer Read Data">
    public CustomerBussinesType getCustomerBussinesType(Long IDCBT) {
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
        List<Customer> customers = new ArrayList<>();

        for (RelCBType rcb : bussinesType.getRelCBTypeList()) {
            customers.add(rcb.getFkIdc());
        }

        return customers;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    public void addNewCustomerBussinesType(CustomerBussinesType newCustomerBussinesType) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newCustomerBussinesType);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("New Customer Bussines Type Addition Failed ! Error message : " + e.toString());
        }
    }

    public void updateCustomerBussinesType(CustomerBussinesType newCustomerBussinesType) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(newCustomerBussinesType);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Customer Bussines Type Update Failed ! Error message : " + e.toString());
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Relation: REL CB TYPE">
    public List<RelCBType> getAllRelCBT() {
        try {
            return getEm().createNamedQuery("RelCBType.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<RelCBType> getAllCustomerBussinesTypes(Customer customer) {
        try {
            return getEm().createNamedQuery("RelCBType.findByCustomer")
                    .setParameter("customer", customer)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public RelCBType getRelCBType(Long ID) {
        try {
            return (RelCBType) getEm().createNamedQuery("RelCBType.findByIdrcbt")
                    .setParameter("idrcbt", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public void addNewRelCBT(RelCBType newRelCBType) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newRelCBType);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("New Customer Bussines Type Relation Addition Failed ! Error message : " + e.toString());
        }
    }

    public void addNewRelCBT(Customer IDC, CustomerBussinesType IDCBT, Date dateFrom, Date dateTo, boolean active) throws Exception {
        RelCBType newRelCBType = new RelCBType();
        newRelCBType.setFkIdc(IDC);
        newRelCBType.setFkIdcbt(IDCBT);
        newRelCBType.setDateFrom(dateFrom);
        newRelCBType.setDateFrom(dateTo);
        newRelCBType.setActive(active);

        addNewRelCBT(newRelCBType);
    }

    public void updateRelCBT(RelCBType newRelCBType) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(newRelCBType);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Customer Bussines Type Relation Update Failed ! Error message : " + e.toString());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SALESMAN">
    //<editor-fold defaultstate="collapsed" desc="SALESMAN READ">
    public List<Salesman> getAllSalesman() {
        try {
            return getEm().createNamedQuery("Salesman.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Salesman getSalesman(Long IDS) {
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
        newSalesman.setFkIdbl(BL);

        getEm().getTransaction().begin();
        em.persist(newSalesman);
        getEm().getTransaction().commit();
    }

    public void addNewSalesman(Salesman newSalesman) throws Exception {
        getEm().getTransaction().begin();
        em.persist(newSalesman);
        getEm().getTransaction().commit();
    }

    public void updateSalesman(Long IDS, String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        Salesman newSalesman = getSalesman(IDS);

        newSalesman.setName(name);
        newSalesman.setSurname(surname);
        newSalesman.setPosition(position);
        newSalesman.setActive(active);
        newSalesman.setDateFrom(dateFrom);
        newSalesman.setDateTo(dateTo);
        newSalesman.setFkIdbl(BL);

        getEm().getTransaction().begin();
        em.merge(newSalesman);
        getEm().getTransaction().commit();
    }

    public void updateSalesman(Salesman newSalesman) throws Exception {
        getEm().getTransaction().begin();
        em.merge(newSalesman);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GALLERY & IMAGES">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public Gallery getGallery(Long IDG) {
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

        newImage.setFkIdg(gallery);
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

        newASALESMANIMAGE.setFkSalesman(salesman);
        newASALESMANIMAGE.setFkImage(image);

        getEm().getTransaction().begin();
        em.persist(newASALESMANIMAGE);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="OWNER">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public List<Owner> getAllFSOwners() {
        try {
            return getEm().createNamedQuery("Owner.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Owner getOwner(long ID) {
        try {
            return (Owner) getEm().createNamedQuery("Owner.findByIdo")
                    .setParameter("ido", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Owner> getAllFSOwnedByCustomer(Customer customer) {
        try {
            return getEm().createNamedQuery("Owner.findByCustomer")
                    .setParameter("customer", customer)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Owner> getAllFSOwnedByCustomer(Customer customer, boolean justActive) {
        try {
            return getEm().createNamedQuery("Owner.ByCustomerAndActive")
                    .setParameter("customer", customer)
                    .setParameter("active", justActive)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Owner getCurrentFSOwner(Fuelstation fuelstation) {
        try {
            return (Owner) getEm().createNamedQuery("Owner.findByFuelstation")
                    .setParameter("fuelstation", fuelstation)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewOwner(Customer customer, Fuelstation fuelstation, Date dateFrom, Date dateTo, boolean active) throws Exception {
        Owner newOwner = new Owner();

        newOwner.setFKIDCustomer(customer);
        newOwner.setFkIdFs(fuelstation);
        newOwner.setDateFrom(dateFrom);
        newOwner.setDateTo(dateTo);
        newOwner.setActive(active);

        addNewOwner(newOwner);
    }

    public void addNewOwner(Owner newOwner) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newOwner);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("New Owner Addition Failed ! Error message : " + e.toString());
        }
    }

    public void updateOwner(Owner existingOwner) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(existingOwner);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Owner Update Failed ! Error message : " + e.toString());
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FS PROPERTIES">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public FsProp getFSProp(FsProp fsProp) {
        try {
            return (FsProp) getEm().createNamedQuery("FsProp.findByFSPROP")
                    .setParameter("FSPROP", fsProp)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public FsProp getFSProp(long ID) {
        try {
            return (FsProp) getEm().createNamedQuery("FsProp.findByID")
                    .setParameter("ID", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<FsProp> getAllFSProps() {
        try {
            return getEm().createNamedQuery("FsProp.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<FsProp> getAllFSProperties(Fuelstation fuelstation, boolean active) {
        try {
            return getEm().createNamedQuery("FsProp.FSPropByFS")
                    .setParameter("fuelstation", fuelstation)
                    .setParameter("active", active)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    // Ovaj metod NE radi !!!
    /*
     public FsProp getCurrentFSProp(Fuelstation fuelstation) {
     try {
     return (FsProp) getEm().createNamedQuery("FsProp.CurrentFSPropForFS")
     .setParameter("fuelstation", fuelstation)
     .getSingleResult();
     } catch (Exception ex) {
     return null;
     }
     }
     */
    public FsProp getCurrentFSProp(Owner owner) {
        try {
            return (FsProp) getEm().createNamedQuery("FsProp.CurrentFSPropForOwner")
                    .setParameter("owner", owner)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<FsProp> getAllFSProperties(Owner owner) {
        try {
            return getEm().createNamedQuery("FsProp.findByOwner")
                    .setParameter("owner", owner)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<FsProp> getAllFSPropertiesByCustomer(Customer customer, Fuelstation fuelstation, boolean active) {
        try {
            return getEm().createNamedQuery("FsProp.FSPropByCustomer")
                    .setParameter("customer", customer)
                    .setParameter("fuelstation", fuelstation)
                    .setParameter("active", active)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    public void addNewFSProp(FsProp newFsProp) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newFsProp);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("New Fuelstation property Addition Failed ! Error message : " + e.toString());
        }
    }

    public void addNewFSProp(Owner owner, Date propDate, int noOfTanks, boolean restaurant,
            int truckCapable, boolean carWash, String compliance, String licence,
            Date dateLicenceFrom, Date dateLicenceTo, String comment, boolean active) throws Exception {

        FsProp newFsProp = new FsProp();

        newFsProp.setFkIdo(owner);
        newFsProp.setPropertiesDate(propDate);
        newFsProp.setNoOfTanks(noOfTanks);
        newFsProp.setRestaurant(restaurant);
        newFsProp.setTruckCapable(truckCapable);
        newFsProp.setCarWash(carWash);
        newFsProp.setCompliance(compliance);
        newFsProp.setLicence(licence);
        newFsProp.setLicDateFrom(dateLicenceFrom);
        newFsProp.setLicDateTo(dateLicenceTo);
        newFsProp.setComment(comment);
        newFsProp.setActive(active);

        addNewFSProp(newFsProp);
    }

    public void updateExistingFSProp(FsProp existingFsProp) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(existingFsProp);
            getEm().getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Fuelstation property Update Failed ! Error message : " + e.toString());
        }
    }

    public void updateExistingFSProp(FsProp existingFsProp, Owner owner, Date propDate, int noOfTanks, boolean restaurant,
            int truckCapable, boolean carWash, String compliance, String licence,
            Date dateLicenceFrom, Date dateLicenceTo, String comment, boolean active) throws Exception {

        existingFsProp.setFkIdo(owner);
        existingFsProp.setPropertiesDate(propDate);
        existingFsProp.setNoOfTanks(noOfTanks);
        existingFsProp.setRestaurant(restaurant);
        existingFsProp.setTruckCapable(truckCapable);
        existingFsProp.setCarWash(carWash);
        existingFsProp.setCompliance(compliance);
        existingFsProp.setLicence(licence);
        existingFsProp.setLicDateFrom(dateLicenceFrom);
        existingFsProp.setLicDateTo(dateLicenceTo);
        existingFsProp.setComment(comment);
        existingFsProp.setActive(active);

        updateExistingFSProp(existingFsProp);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CITY">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public List<City> getAllCities() {
        try {
            return getEm().createNamedQuery("City.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public City getCity(Long ID) {
        try {
            return (City) getEm().createNamedQuery("City.findByIdc")
                    .setParameter("idc", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<City> getCityByName(String partialNameWithBeggining) {
        try {
            return getEm().createNamedQuery("City.PartialName")
                    .setParameter("name", partialNameWithBeggining.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<City> getCityByContainingName(String partialName) {
        try {
            return getEm().createNamedQuery("City.PartialName")
                    .setParameter("name", "%" + partialName + "%")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<City> getCityByMunicipality(String partialName) {
        try {
            return getEm().createNamedQuery("City.MunicipalityPartialName")
                    .setParameter("municipality", partialName.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<City> getCityByDistrict(String partialName) {
        try {
            return getEm().createNamedQuery("City.DistrictPartialName")
                    .setParameter("district", partialName.concat("%"))
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewCity(String name, String zip, String region) throws Exception {
        City newCity = new City();

        newCity.setName(name);
        newCity.setZip(zip);
        newCity.setRegion(region);

        getEm().getTransaction().begin();
        em.persist(newCity);
        getEm().getTransaction().commit();
    }

    public void addNewCity(City newCity) throws Exception {
        getEm().getTransaction().begin();
        em.persist(newCity);
        getEm().getTransaction().commit();
    }

    public void updateExistingCity(City newCity) throws Exception {
        getEm().getTransaction().begin();
        em.merge(newCity);
        getEm().getTransaction().commit();
    }
    //</editor-fold>
    //</editor-fold>
}
