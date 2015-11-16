/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import dataservice.exceptions.MyDBNullException;
import dataservice.exceptions.MyDBViolationOfUniqueKeyException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import db.ent.BussinesLine;
import db.ent.City;
import db.ent.CrmCase;
import db.ent.CrmProcess;
import db.ent.CrmStatus;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
import db.ent.FsProp;
import db.ent.Fuelstation;
import db.ent.Gallery;
import db.ent.Document;
import db.ent.DocumentType;
import db.ent.Owner;
import db.ent.RelCBType;
import db.ent.RelFSDocument;
import db.ent.RelSALESMANIMAGE;
import db.ent.RelSALESMANCUST;
import db.ent.Salesman;
import db.ent.InfSysUser;
import db.ent.Log;
import db.ent.Product;
import db.ent.RelSALE;
import db.ent.custom.CustomSearchData;
import enums.ISUserType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.Query;
import org.superb.apps.utilities.datum.Dates;

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

    private void rollBackTransaction(String message) throws Exception {
        if (getEm().getTransaction().isActive()) {
            getEm().getTransaction().rollback();
        }
        throw new Exception(message);
    }

    private void rollBackTransaction(Exception e) throws Exception {
        if (getEm().getTransaction().isActive()) {
            getEm().getTransaction().rollback();
        }
        throw new Exception(e);
    }
    //</editor-fold>

    private static final String DB_DUPLICATE = "the duplicate key value is";
    private static final String DB_VIOLATION_UNIQUE_KEY = "violation of unique key constraint";
    private static final String DB_NULLVALUES = "cannot insert the value null into column";

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
        } catch (Exception ex) {
            rollBackTransaction("New Customer Addition Failed");
        }
    }

    public void addNewCustomer(String name, String address, City city, String PIB) throws Exception {
        Customer newCustomer = new Customer();

        newCustomer.setName(name);
        newCustomer.setAddress(address);
        newCustomer.setFK_IDCity(city);
        newCustomer.setPib(PIB);

        addNewCustomer(newCustomer);
    }

    public void updateCustomer(Customer customer) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(customer);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Customer Update Failed");
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
        } catch (Exception ex) {
            rollBackTransaction("New Fuelstation Addition Failed.\nFuelstation name must be entered.");
        }
    }

    public void addNewFS(String name, City city, String address, String coordinates) throws Exception {
        Fuelstation newFuelstation = new Fuelstation();

        newFuelstation.setName(name);
        newFuelstation.setAddress(address);
        newFuelstation.setFK_City(city);
        newFuelstation.setCoordinates(coordinates);

        addNewFS(newFuelstation);
    }

    public void updateFS(Fuelstation fuelstation) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(fuelstation);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Fuelstation Update Failed");
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

        bussinesType.getRelCBTypeList().stream().forEach((rcb) -> {
            customers.add(rcb.getFkIdc());
        });

        return customers;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/update data">
    public void addNewCustomerBussinesType(CustomerBussinesType newCustomerBussinesType) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newCustomerBussinesType);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("New Customer Bussines Type Addition Failed");
        }
    }

    public void updateCustomerBussinesType(CustomerBussinesType newCustomerBussinesType) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(newCustomerBussinesType);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Customer Bussines Type Update Failed");
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
        } catch (Exception ex) {
            rollBackTransaction("New Customer Bussines Type Relation Addition Failed");
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
        } catch (Exception ex) {
            rollBackTransaction("Customer Bussines Type Relation Update Failed");
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

    public List<Salesman> getSalesman(BussinesLine bl) {
        try {
            return getEm().createNamedQuery("Salesman.SalesmenByBL")
                    .setParameter("FK_IDBL", bl)
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

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewSalesman(String name, String surname, String position, boolean active, String dateFrom, String dateTo, BussinesLine BL) throws Exception {
        Salesman newSalesman = new Salesman(name, surname, position, active, dateFrom, dateTo, BL);
        addNewSalesman(newSalesman);
    }

    public void addNewSalesman(Salesman newSalesman) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newSalesman);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Salesman Update Failed.");
        }
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

        updateSalesman(newSalesman);
    }

    public void updateSalesman(Salesman newSalesman) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(newSalesman);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Salesman Update Failed.");
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GALLERY, DOCUMENTS, DOCUMENT TYPE">
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

    public Document getDocument(long idd) {
        try {
            return (Document) getEm().createNamedQuery("Document.findByIdd")
                    .setParameter("idd", idd)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<DocumentType> getAllDocumentTypes() {
        try {
            return getEm().createNamedQuery("DocumentType.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public DocumentType getDocumentType(long ID) {
        try {
            return (DocumentType) getEm().createNamedQuery("DocumentType.findByIddt")
                    .setParameter("iddt", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public DocumentType getDocumentType(String docType) {
        try {
            return (DocumentType) getEm().createNamedQuery("DocumentType.findByDocType")
                    .setParameter("docType", docType)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Document> getAllFSDocuments(Fuelstation fuelstation) {
        try {
            return getEm().createNamedQuery("RelFSDocument.getAllFSDocuments")
                    .setParameter("IDFS", fuelstation)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Document> getAllDocumentsByGallery(Gallery g) {
        try {
            return getEm().createNamedQuery("Document.findByGallery")
                    .setParameter("idg", g)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewGallery(Gallery newGallery) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newGallery);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("New Gallery Not Added.");
        }
    }

    public void addNewGallery(String galleryName, String storeLocation) throws Exception {
        Gallery newGallery = new Gallery();

        newGallery.setName(galleryName);
        newGallery.setStoreLocation(storeLocation);

        addNewGallery(newGallery);
    }

    public Document addNewDocument(Document newDoc) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newDoc);
            getEm().flush();
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            newDoc = null;
            rollBackTransaction("New Document Not Added.");
        }

        return newDoc;
    }

    public Document addNewDocument(Gallery gallery, String name, Serializable docData, String docLocation, Date uploadDate, String docType) throws Exception {
        return addNewDocument(new Document(gallery, name, docData, docLocation, uploadDate, docType));
    }

    public void updateDocument(Document document) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(document);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Document Not updated.");
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Realation : FS DOCUMENT">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public Document getFSDefaultImage(Fuelstation fuelstation, boolean defaultDocument) {
        try {
            List<Document> docs = getEm().createNamedQuery("RelFSDocument.getFSDefaultImage")
                    .setParameter("IDFS", fuelstation)
                    .setParameter("DefaultDocument", defaultDocument)
                    .getResultList();

            return docs.iterator().next();
        } catch (Exception ex) {
            return null;
        }
    }

    public void setFSDefaultImage(Fuelstation fuelstation, Document defaultFSImage) throws Exception {
        try {
            getEm().getTransaction().begin();

            // sve slike stanice resetuj na 0.
            getEm().createNamedQuery("RelFSDocument.Reset")
                    .setParameter("fkIdfs", fuelstation)
                    .executeUpdate();

            // odabranu sliku postavi kao podrazumevanu
            getEm().createNamedQuery("RelFSDocument.SetDefaultImage")
                    .setParameter("FK_IDFS", fuelstation)
                    .setParameter("FK_IDD", defaultFSImage)
                    .executeUpdate();

            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Document Failed To Be Updated as Default !");
        }
    }

    public Document getLastFSDocument(Fuelstation f) {
        try {
            List<Document> L = getEm().createNamedQuery("RelFSDocument.getAllFSDocuments")
                    .setParameter("IDFS", f)
                    .getResultList();

            return L.get(L.size() - 1);

        } catch (Exception ex) {
            return null;
        }
    }

    public Document getHighPriorityFSImage(Fuelstation f) {
        try {
            List<Document> L = getEm().createNamedQuery("RelFSDocument.getHighPriorityFSImage")
                    .setParameter("IDFS", f)
                    .getResultList();
            return L.get(0);
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewFSDocument(RelFSDocument newFSDocument) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newFSDocument);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("New FS Document Not Added.");
        }
    }

    public void addNewFSDocument(Fuelstation fuelstation, Document document, Date docDate, boolean defaultDocument, int priority) throws Exception {
        RelFSDocument newFSDoc = new RelFSDocument(fuelstation, document, docDate, defaultDocument, priority);
        addNewFSDocument(newFSDoc);
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
    public void addSalesmanImage(Salesman salesman, Document image) throws Exception {
        RelSALESMANIMAGE newASALESMANIMAGE = new RelSALESMANIMAGE();

        newASALESMANIMAGE.setFkSalesman(salesman);
        newASALESMANIMAGE.setFkDocument(image);

        try {
            getEm().getTransaction().begin();
            em.persist(newASALESMANIMAGE);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Salesman Image Addition Failed.");
        }
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
        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("New Fuelstation Owner Addition Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
        }
    }

    public void updateOwner(Owner existingOwner) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(existingOwner);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("Existing Fuelstation Owner Update Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
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
        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("Fuelstation Property Update Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
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
        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("Fuelstation Property Update Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
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

        addNewCity(newCity);
    }

    public void addNewCity(City newCity) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newCity);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("New City Addition Failed.");
        }
    }

    public void updateExistingCity(City newCity) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(newCity);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("City Update Failed.");
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CRM">
    //<editor-fold defaultstate="collapsed" desc="RELATION SALESMAN CUSTOMER">
    public RelSALESMANCUST getCRM_R_Salesman_Cust(Salesman s, Customer c) throws Exception {
        try {
            return (RelSALESMANCUST) getEm().createNamedQuery("RelSALESMANCUST.RelSalesmanCustomer")
                    .setParameter("IDC", c)
                    .setParameter("IDS", s)
                    .getSingleResult();
        } catch (Exception ex) {
            throw new Exception("No Relation Between Salesman and Customer !");
        }
    }

    public RelSALESMANCUST getCRM_R_Salesman_Cust(Customer customer) {
        try {
            return (RelSALESMANCUST) getEm().createNamedQuery("RelSALESMANCUST.findByCust")
                    .setParameter("IDC", customer)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public RelSALESMANCUST getCRM_RSalesman_Cust(Salesman salesman) {
        try {
            return (RelSALESMANCUST) getEm().createNamedQuery("RelSALESMANCUST.findBySalesman")
                    .setParameter("IDS", salesman)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCRM_SalesmansCustomers(Salesman salesman) {
        try {
            return getEm().createNamedQuery("RelSALESMANCUST.CustomersBySalesman")
                    .setParameter("IDS", salesman)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CRM PROCESS">
    public List<CrmProcess> getCRM_CustomerProcessesByDate(Customer customer, boolean finished, Date dateFrom, Date dateTo) {
        dateTo = dateTo == null ? new Date() : dateTo;

        if (dateFrom == null) {
            // dateFrom = dateTo - 1 godina !
            long g = dateTo.getTime() - 1000 * 60 * 60 * 24 * 365 * 1;
            g = g < 0 ? 0 : g;

            dateFrom = new Date(dateTo.getTime() - g);
        }

        try {
            return getEm().createNamedQuery("CrmProcess.CustomerProcessesByDate")
                    .setParameter("IDC", customer)
                    .setParameter("finished", finished)
                    .setParameter("dateFrom", dateFrom)
                    .setParameter("dateTo", dateTo)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRM_AllCustomerProcesses(Customer customer) {
        try {
            return getEm().createNamedQuery("CrmProcess.AllCustomerProcesses")
                    .setParameter("IDC", customer)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRM_SalesmanProcesses(Salesman salesman, boolean finished) {
        try {
            return getEm().createNamedQuery("CrmProcess.SalesmanProcesses")
                    .setParameter("IDS", salesman)
                    .setParameter("finished", finished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRM_SalesmanProcessesByDate(Salesman salesman, boolean finished, Date dateFrom, Date dateTo) {
        dateTo = dateTo == null ? new Date() : dateTo;

        if (dateFrom == null) {
            // dateFrom = dateTo - 1 godina !
            long g = dateTo.getTime() - 1000 * 60 * 60 * 24 * 365 * 1;
            g = g < 0 ? 0 : g;

            dateFrom = new Date(dateTo.getTime() - g);
        }
        try {
            return getEm().createNamedQuery("CrmProcess.SalesmanProcessesByDate")
                    .setParameter("IDS", salesman)
                    .setParameter("finished", finished)
                    .setParameter("dateFrom", dateFrom)
                    .setParameter("dateTo", dateTo)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRMProcessesByStatus(CrmStatus crmStatus) {
        try {
            return getEm().createNamedQuery("CrmProcess.findByCRMStatus")
                    .setParameter("IDCS", crmStatus)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRMProcesses(CrmCase crmCase, boolean finished) {
        try {
            return getEm().createNamedQuery("CrmProcess.findByCRMCase")
                    .setParameter("FK_IDCA", crmCase)
                    .setParameter("finished", finished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRMProcesses(CrmCase crmCase) {
        try {
            return getEm().createNamedQuery("CrmProcess.ByCRMCase")
                    .setParameter("FK_IDCA", crmCase)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public void updateCRM_Process(CrmProcess crmProcess) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(crmProcess);
            getEm().getTransaction().commit();

        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("CRM Process Update Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
        }
    }

    public void addNewCRM_Process(CrmCase crmCase, CrmStatus crmStatus, String comment, Date actionDate) throws Exception {

        CrmProcess crmProcess = new CrmProcess(crmCase, crmStatus, comment, actionDate);
        addNewCRM_Process(crmProcess);
    }

    public void addNewCRM_Process(CrmProcess newCrmProcess) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newCrmProcess);
            getEm().getTransaction().commit();

        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("New CRM Process Addition Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CRM STATUS">
    public List<CrmStatus> getCRM_AllStatuses() {
        try {
            return getEm().createNamedQuery("CrmStatus.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public CrmStatus getCRM_Status(long ID) {
        try {
            return (CrmStatus) getEm().createNamedQuery("CrmStatus.findByIdcs")
                    .setParameter("idcs", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmStatus> getCRM_Status(String type) {
        try {
            return getEm().createNamedQuery("CrmStatus.findByType")
                    .setParameter("type", type)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmStatus> getCRM_Statuses(String... type) {
        List<CrmStatus> L = new ArrayList<>();

        for (String t : type) {
            L.addAll(getCRM_Status(t));
        }

        return L;
    }

    public List<CrmStatus> getCRM_CaseStatuses(CrmCase crmCase) {
        try {
            return getEm().createNamedQuery("CrmProcess.CRMCaseStatuses")
                    .setParameter("CRMCASE", crmCase)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param crmCase Metod koji ispravno uređuje sledeći CRM status u odnosu na
     * dosadašnje unete statuse.
     * <p>
     * Npr. ako je otvoren CRM slučaj, metod vraća isključivo jedan status -
     * "Negotiation Started".
     * <p>
     * Ako za CRM status već postoji "Negotiation Started", sledeći mogući
     * status može biti SAMO "Offer". Ako već postoji "Offer", sledeći unetii
     * status mogu biti "Offer" (jer može biti više ponuda", kao i statusi
     * "Contract Signed", ili "Contract Not Signed"
     * @return Lista ispravnih CRM statusa.
     */
    public List<CrmStatus> getCRM_AvailableStatuses(CrmCase crmCase) {
        Set<CrmStatus> s = new HashSet<>(getCRM_CaseStatuses(crmCase));

        Set<CrmStatus> START_ = new HashSet<>(getCRM_Statuses("start"));
        Set<CrmStatus> END_ = new HashSet<>(getCRM_Statuses("end"));
        Set<CrmStatus> CURRENT_ = new HashSet<>(getCRM_Statuses("current"));

        List<CrmStatus> L;

        if (s.contains(getCRM_Status(3)) || s.contains(getCRM_Status(4))) {
            L = null;
        } else if (s.containsAll(CURRENT_) && s.containsAll(START_)) {
            CURRENT_.addAll(END_);
            L = new ArrayList<>(CURRENT_);
        } else if (s.containsAll(START_)) {
            L = new ArrayList<>(CURRENT_);
        } else {
            L = new ArrayList<>(START_);
        }

        return L;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CRM CASE">
    public CrmCase getCRM_Case(long ID) {
        try {
            return (CrmCase) getEm().createNamedQuery("CrmCase.findByIdca")
                    .setParameter("idca", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public CrmCase getLastActive_CRMCase(Customer c, Salesman s) {
        try {
            return (CrmCase) getEm().createNamedQuery("CrmCase.findLastActiveCRM_Case")
                    .setParameter("IDC", c)
                    .setParameter("IDS", s)
                    .setMaxResults(1)
                    .getResultList()
                    .get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_AllActiveCases(boolean caseFinished) {
        try {
            return getEm().createNamedQuery("CrmCase.AllActiveCases")
                    .setParameter("Finished", caseFinished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRMCase(Customer c, Salesman s, boolean finished) {
        try {
            return getEm().createNamedQuery("CrmCase.SalesmanCustomers")
                    .setParameter("IDC", c)
                    .setParameter("IDS", s)
                    .setParameter("Finished", finished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_CasesStats(Salesman s, boolean finished, boolean saleAgreeded) {
        try {
            return getEm().createNamedQuery("CrmCase.SalesmanCompletedCases")
                    .setParameter("salesman", s)
                    .setParameter("finished", finished)
                    .setParameter("saleAgreeded", saleAgreeded)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_CasesStats(Date dateFrom, Date dateTo, boolean finished, boolean saleAgreeded) {
        Dates d = new Dates();
        Date from, to;

        from = dateFrom == null ? d.getFrom() : dateFrom;
        to = dateTo == null ? d.getTo() : dateTo;

        try {
            return getEm().createNamedQuery("CrmCase.SaleAgreededCases")
                    .setParameter("startDate", from)
                    .setParameter("endDate", to)
                    .setParameter("finished", finished)
                    .setParameter("saleAgreeded", saleAgreeded)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_CasesStats(Salesman salesman, Date dateFrom, Date dateTo, boolean finished, boolean saleAgreeded) {
        Dates d = new Dates();
        Date from, to;

        from = dateFrom == null ? d.getFrom() : dateFrom;
        to = dateTo == null ? d.getTo() : dateTo;

        try {
            return getEm().createNamedQuery("CrmCase.Salesrep_Cases")
                    .setParameter("IDS", salesman)
                    .setParameter("startDate", from)
                    .setParameter("endDate", to)
                    .setParameter("finished", finished)
                    .setParameter("saleAgreeded", saleAgreeded)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_CasesStats(BussinesLine bussinesLine, boolean finished, boolean saleAgreeded) {
        try {
            return getEm().createNamedQuery("CrmCase.BussinesLine_Cases")
                    .setParameter("IDBL", bussinesLine)
                    .setParameter("finished", finished)
                    .setParameter("saleAgreeded", saleAgreeded)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_Cases(Customer customer, boolean caseFinished) {
        try {
            return getEm().createNamedQuery("CrmCase.findByCustomer")
                    .setParameter("IDC", customer)
                    .setParameter("Finished", caseFinished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_Cases(Salesman salesman, boolean finished) {
        try {
            return getEm().createNamedQuery("CrmCase.findBySalesman")
                    .setParameter("FK_IDS", salesman)
                    .setParameter("finished", finished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_Cases(BussinesLine bussinesLine, boolean finished) {
        try {
            return getEm().createNamedQuery("CrmCase.ByBussinesLine")
                    .setParameter("FK_IDBL", bussinesLine)
                    .setParameter("finished", finished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_Cases(Salesman salesman, Date dateFrom, Date dateTo, boolean finished, boolean saleAgreeded, int ammount) {
        Dates d = new Dates();
        Date from, to;

        from = dateFrom == null ? d.getFrom() : dateFrom;
        to = dateTo == null ? d.getTo() : dateTo;

        try {
            return getEm().createNamedQuery("CrmCase.Salesrep_Cases")
                    .setParameter("IDS", salesman)
                    .setParameter("startDate", from)
                    .setParameter("endDate", to)
                    .setParameter("ammount", ammount)
                    .setParameter("finished", finished)
                    .setParameter("saleAgreeded", saleAgreeded)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCRM_CustomerActiveCases(boolean caseFinished) {
        try {
            return getEm().createNamedQuery("CrmCase.CustomerActiveCases")
                    .setParameter("Finished", caseFinished)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Customer> getCRM_CustomerActiveCases(boolean caseFinished, BussinesLine bl) {
        try {
            return getEm().createNamedQuery("CrmCase.BL_CustomerActiveCases")
                    .setParameter("finished", caseFinished)
                    .setParameter("FK_IDBL", bl)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public void addNewCRM_Case(CrmCase newCrmCase) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newCrmCase);
            getEm().getTransaction().commit();

        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("New CRM Case Addition Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
        }
    }

    public void updateCRM_Case(CrmCase existingCrmCase) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(existingCrmCase);
            getEm().getTransaction().commit();

        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("Existing CRM Case Update Failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
        }
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="RELATION SALESMAN CUSTOMER">
    public void addNew_RelSalesman_Cust(Customer c, Salesman s, Date dateFrom, Date dateTo, boolean active) throws Exception {
        RelSALESMANCUST r = new RelSALESMANCUST(c, s, dateFrom, dateTo, active);
        addNew_RelSalesman_Cust(r);
    }

    public void addNew_RelSalesman_Cust(RelSALESMANCUST newRelSALESMANCUST) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newRelSALESMANCUST);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("Salesman and Customer Must Be Selected !"));
            } else if (ex.toString().toLowerCase().contains(DB_DUPLICATE) || ex.toString().toLowerCase().contains(DB_VIOLATION_UNIQUE_KEY)) {
                rollBackTransaction(new MyDBViolationOfUniqueKeyException("Relation Between Salesman and Customer Already Exists !"));
            } else {
                rollBackTransaction(ex.getMessage());
            }
        }
    }

    public void update_R_Salesman_Cust(RelSALESMANCUST R_Salesman_Cust) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.merge(R_Salesman_Cust);
            getEm().getTransaction().commit();
        } catch (Exception ex) {
            rollBackTransaction("Relation Salesman-Customer Update Failed. " + ex.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SALE">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public List<RelSALE> getCRM_Sales(CrmCase c, Date dateFrom, Date dateTo) {
        Dates d = new Dates();
        Date from, to;

        from = dateFrom == null ? d.getFrom() : dateFrom;
        to = dateTo == null ? d.getTo() : dateTo;

        try {
            return getEm().createNamedQuery("RelSALE.CRMCases_Sales_ForPeriod")
                    .setParameter("FK_IDCA", c)
                    .setParameter("dateFrom", from)
                    .setParameter("dateTo", to)
                    .setParameter("ammount", 0)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<RelSALE> getCRM_Sales(CrmCase crmCase) {
        try {
            return getEm().createNamedQuery("RelSALE.CRMCases_Sales_ForACase")
                    .setParameter("FK_IDCA", crmCase)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<RelSALE> getCRM_Sales(Salesman s, Date dateFrom, Date dateTo) {
        Dates d = new Dates();
        Date from, to;

        from = dateFrom == null ? d.getFrom() : dateFrom;
        to = dateTo == null ? d.getTo() : dateTo;

        try {
            return getEm().createNamedQuery("RelSALE.Salesman_Sales_In_Period")
                    .setParameter("IDS", s)
                    .setParameter("dateFrom", from)
                    .setParameter("dateTo", to)
                    .setParameter("ammount", 0)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> CRM_Salesrep_Sales(Salesman s, Date dateFrom, Date dateTo) {
        Dates d = new Dates();
        Date from, to;

        from = dateFrom == null ? d.getFrom() : dateFrom;
        to = dateTo == null ? d.getTo() : dateTo;

        try {
            return getEm().createNamedQuery("RelSALE.CRM_Salesrep_Sales_Cases")
                    .setParameter("IDS", s)
                    .setParameter("dateFrom", from)
                    .setParameter("dateTo", to)
                    .setParameter("ammount", 0)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRM_Sales_Cases(Date dateFrom, Date dateTo) {
        Date from, to;

        Dates d = new Dates();

        from = dateFrom == null ? d.getFrom() : dateFrom;
        to = dateTo == null ? d.getTo() : dateTo;

        if (from.after(to)) {
            Date z = from;
            from = to;
            to = z;
        }

        try {
            return getEm().createNamedQuery("RelSALE.CRM_Sales_Cases")
                    .setParameter("dateFrom", from)
                    .setParameter("dateTo", to)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewSale(RelSALE sale) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(sale);
            getEm().getTransaction().commit();

        } catch (Exception ex) {
            rollBackTransaction("New Sale Addition Has Failed.\nReason : " + ex.getMessage());
        }
    }
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="USER">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public List<InfSysUser> getAllUsers() {
        try {
            return getEm().createNamedQuery("InfSysUser.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public InfSysUser getByID(long ID) {
        try {
            return (InfSysUser) getEm().createNamedQuery("InfSysUser.findByIDUN")
                    .setParameter("idun", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public InfSysUser getByID(String shiroUserPrincipal) {
        try {
            return (InfSysUser) getEm().createNamedQuery("InfSysUser.findByShiroPrincipal")
                    .setParameter("shiroUserPrincipal", shiroUserPrincipal)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<InfSysUser> getSectorManagerUsers(boolean sectorManager) {
        try {
            return getEm().createNamedQuery("InfSysUser.SectorManagers")
                    .setParameter("sectorManager", sectorManager)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<InfSysUser> getTopManagerUsers(boolean topManager) {
        try {
            return getEm().createNamedQuery("InfSysUser.TopManagers")
                    .setParameter("topManager", topManager)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<InfSysUser> getAdminUsers(boolean admin) {
        try {
            return getEm().createNamedQuery("InfSysUser.AdminUsers")
                    .setParameter("admin", admin)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public ISUserType getInfUserType(String shiroUserPrincipal) {
        try {
            if (getByID(shiroUserPrincipal).getAdmin()) {
                return ISUserType.ADMIN;
            }
        } catch (Exception e) {
        }

        try {
            if (getByID(shiroUserPrincipal).getTopManager()) {
                return ISUserType.TOP_MANAGER;
            }
        } catch (Exception e) {
        }

        try {
            if (getByID(shiroUserPrincipal).getSectorManager()) {
                return ISUserType.SECTOR_MANAGER;
            }
        } catch (Exception e) {
        }

        try {
            if (getSalesman(getByID(shiroUserPrincipal)) != null) {
                return ISUserType.SALESMAN;
            }
        } catch (Exception e) {
        }

        return null;
    }

    public ISUserType getInfSysUserType(InfSysUser infSysUser) {
        try {
            if (infSysUser.getAdmin()) {
                return ISUserType.ADMIN;
            }
        } catch (Exception e) {
        }

        try {
            if (infSysUser.getTopManager()) {
                return ISUserType.TOP_MANAGER;
            }
        } catch (Exception e) {
        }

        try {
            if (infSysUser.getSectorManager()) {
                return ISUserType.SECTOR_MANAGER;
            }
        } catch (Exception e) {
        }

        try {
            if (getSalesman(infSysUser) != null) {
                return ISUserType.SALESMAN;
            }
        } catch (Exception e) {
        }

        return ISUserType.ТЕST;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="REL INFSYSUSER SALESMAN">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public InfSysUser getUser(Salesman salesman) {
        return getUser(salesman, true);
    }

    public Salesman getSalesman(InfSysUser user) {
        return getSalesman(user, true);
    }

    public InfSysUser getUser(Salesman salesman, boolean active) {
        try {
            return (InfSysUser) getEm().createNamedQuery("RelUserSalesman.getInfSysUser")
                    .setParameter("FK_IDS", salesman)
                    .setParameter("active", active)
                    .setMaxResults(1)
                    .getResultList()
                    .get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public Salesman getSalesman(InfSysUser user, boolean active) {
        try {
            return (Salesman) getEm().createNamedQuery("RelUserSalesman.getSalesman")
                    .setParameter("FK_IDUN", user)
                    .setParameter("active", active)
                    .setMaxResults(1)
                    .getResultList()
                    .get(0);
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="LOG">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public Log getLogByID(Long ID) {
        try {
            return (Log) getEm().createNamedQuery("Log.findByIdl")
                    .setParameter("idl", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Log> getLogByInfSysUser(InfSysUser isu) {
        try {
            return getEm().createNamedQuery("Log.findByInfSysUser")
                    .setParameter("IDUN", isu)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Log> getLogByInfSysUser(InfSysUser isu, Date dateFrom, Date dateTo) {
        try {
            return getEm().createNamedQuery("Log.findByInfSysUserAndDate")
                    .setParameter("IDUN", isu)
                    .setParameter("dateFrom", dateFrom)
                    .setParameter("dateTo", dateTo)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
    public void addNewLog(Log log) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(log);
            getEm().getTransaction().commit();

        } catch (Exception ex) {
            rollBackTransaction("New LOG Addition Failed.\nReason : " + ex.getMessage());
        }
    }

    public void addNewLog(Date logDate, String actionCode, String description, InfSysUser infSysUser) throws Exception {
        addNewLog(new Log(logDate, actionCode, description, infSysUser));
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PRODUCT">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public List<Product> getAllProducts() {
        try {
            return getEm().createNamedQuery("Product.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Product getProductByID(long ID) {
        try {
            return (Product) getEm().createNamedQuery("Product.findByIdp")
                    .setParameter("idp", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Product> getProductsByBussinesLine(BussinesLine bussinesLine) {
        try {
            return getEm().createNamedQuery("RelBLP.BL_Products")
                    .setParameter("ID_BL", bussinesLine)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="BUSSINES LINE">
    //<editor-fold defaultstate="collapsed" desc="READ">
    public BussinesLine getBussinesLine(Long ID) {
        try {
            return (BussinesLine) getEm().createNamedQuery("BussinesLine.findByIdbl")
                    .setParameter("idbl", ID)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<BussinesLine> getBussinesLines() {
        try {
            return getEm().createNamedQuery("BussinesLine.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Custom Search Data Queries">
    public List<RelSALE> getSales(CustomSearchData csd) {
        Date from, to;
        Query query;

        from = csd.getStartDate() == null ? new Date(0) : csd.getStartDate();
        to = csd.getEndDate() == null ? new Date() : csd.getEndDate();

        String Q = "SELECT S from RelSALE S WHERE S.sellDate BETWEEN :dateFrom AND :dateTo ";

        try {

            if (csd.getCustomer() != null) {
                Q += " AND S.FK_IDCA.FK_IDRSC.FK_IDC = :IDC ";
            }

            if (csd.getCustomer() != null) {
                Q += " AND S.FK_IDCA.FK_IDRSC.FK_IDC = :IDC ";
            }

            if (csd.getSalesman() != null) {
                Q += " AND S.FK_IDCA.FK_IDRSC.FK_IDS = :IDS ";
            }

            if (csd.getBussinesLine() != null) {
                Q += " AND S.FK_IDRSC.FK_IDS.fkIdbl = :IDBL ";
            }

            if (csd.getProduct() != null) {
                Q += " AND S.FK_IDP = :IDP ";
            }

            query = getEm().createQuery(Q)
                    .setParameter("dateFrom", from)
                    .setParameter("dateTo", to);

            if (csd.getCustomer() != null) {
                query.setParameter("IDC", csd.getCustomer());
            }
            if (csd.getSalesman() != null) {
                query.setParameter("IDS", csd.getSalesman());
            }
            if (csd.getBussinesLine()!= null) {
                query.setParameter("IDBL", csd.getBussinesLine());
            }
            if (csd.getProduct() != null) {
                query.setParameter("IDP", csd.getProduct());
            }

            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmCase> getCRMCases(CustomSearchData csd) {
        Query query;
        String Q = "SELECT c FROM CrmCase c WHERE 1=1";

        try {

            //<editor-fold defaultstate="collapsed" desc="check parameters">
            if (csd.getStartDate() != null) {
                Q += " AND c.startDate >= :startDate ";
            }

            if (csd.getEndDate() != null) {
                Q += " AND c.endDate <= :endDate ";
            }

            if (csd.getCustomer() != null) {
                Q += " AND c.FK_IDRSC.FK_IDC = :IDC ";
            }

            if (csd.getSalesman() != null) {
                Q += " AND c.FK_IDRSC.FK_IDS = :IDS ";
            }

            if (csd.getBussinesLine() != null) {
                Q += " AND c.FK_IDRSC.FK_IDS.fkIdbl = :IDBL ";
            }

            if (csd.getCaseFinished() != null) {
                Q += " AND c.finished = :finished ";
            }

            if (csd.getSaleAgreeded() != null) {
                Q += " AND c.saleAgreeded = :saleAgreeded ";
            }
            //</editor-fold>

            query = getEm().createQuery(Q);

            //<editor-fold defaultstate="collapsed" desc="setup parameters">
            if (csd.getStartDate() != null) {
                query.setParameter("startDate", csd.getStartDate());
            }

            if (csd.getEndDate() != null) {
                query.setParameter("endDate", csd.getEndDate());
            }

            if (csd.getCustomer() != null) {
                query.setParameter("IDC", csd.getCustomer());
            }

            if (csd.getSalesman() != null) {
                query.setParameter("IDS", csd.getSalesman());
            }

            if (csd.getBussinesLine()!= null) {
                query.setParameter("IDBL", csd.getBussinesLine());
            }

            if (csd.getCaseFinished() != null) {
                query.setParameter("finished", csd.getCaseFinished());
            }

            if (csd.getSaleAgreeded() != null) {
                query.setParameter("saleAgreeded", csd.getSaleAgreeded());
            }
            //</editor-fold>

            List<CrmCase> L = new ArrayList<>();

            for (CrmCase c : (List<CrmCase>) query.getResultList()) {
                c.setCrmProcessList(getCRMProcesses(c));
                L.add(c);
            }

            return L;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRMProcesses(CustomSearchData csd) {
        Date from, to;
        Query query;

        from = csd.getStartDate() == null ? new Date(0) : csd.getStartDate();
        to = csd.getEndDate() == null ? new Date() : csd.getEndDate();

        String Q = "SELECT p from CrmProcess p WHERE p.actionDate BETWEEN :dateFrom AND :dateTo ";

        try {

            if (csd.getCustomer() != null) {
                Q += " AND p.FK_IDCA.FK_IDRSC.FK_IDC = :IDC ";
            }

            if (csd.getSalesman() != null) {
                Q += " AND p.FK_IDCA.FK_IDRSC.FK_IDS = :IDS ";
            }

            if (csd.getProduct() != null) {
                Q += " AND p.FK_IDP = :IDP ";
            }

            if (csd.getCaseFinished() != null) {
                Q += " AND p.FK_IDCA.finished = :finished ";
            }

            if (csd.getSaleAgreeded() != null) {
                Q += " AND p.FK_IDCA.saleAgreeded = :saleAgreeded";
            }

            query = getEm().createQuery(Q)
                    .setParameter("dateFrom", from == null ? new Date(0) : csd.getStartDate())
                    .setParameter("dateTo", to == null ? new Date() : csd.getEndDate());

            if (csd.getCustomer() != null) {
                query.setParameter("IDC", csd.getCustomer());
            }
            if (csd.getSalesman() != null) {
                query.setParameter("IDS", csd.getSalesman());
            }
            if (csd.getProduct() != null) {
                query.setParameter("IDP", csd.getProduct());
            }
            if (csd.getCaseFinished() != null) {
                query.setParameter("finished", csd.getCaseFinished());
            }
            if (csd.getSaleAgreeded() != null) {
                query.setParameter("saleAgreeded", csd.getSaleAgreeded());
            }

            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public Map<Salesman, List<RelSALE>> getSalesrepSales(CustomSearchData csd) {
        Map<Salesman, List<RelSALE>> MSS = new HashMap<>();
        List<RelSALE> LRS;

        for (RelSALE s : getSales(csd)) {
            Salesman salesRep = s.getFK_IDCA().getFK_IDRSC().getFK_IDS();

            if (MSS.containsKey(salesRep)) {
                LRS = new ArrayList<>(MSS.get(salesRep));
                LRS.add(s);
            } else {
                LRS = Arrays.asList(s);
            }

            MSS.put(salesRep, LRS);

        }

        return MSS;
    }

    public Set<Customer> getCustomers(CustomSearchData csd) {
        Set<Customer> L = new HashSet<>();

        getCRMCases(csd).stream().forEach((c) -> {
            L.add(c.getFK_IDRSC().getFK_IDC());
        });

        return L;
    }

    public Set<Salesman> getSalesreps(CustomSearchData csd) {
        Set<Salesman> L = new HashSet<>();

        getCRMCases(csd).stream().forEach((c) -> {
            L.add(c.getFK_IDRSC().getFK_IDS());
        });

        return L;
    }

    //</editor-fold>
}
