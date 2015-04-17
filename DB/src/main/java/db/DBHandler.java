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
        newCustomer.setFKIDCity(city);
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
                rollBackTransaction(new MyDBNullException("New fuelstation owner addition failed.\nCheck fileds that must not be empty."));
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
                rollBackTransaction(new MyDBNullException("Existing fuelstation ownerupdate failed.\nCheck fileds that must not be empty."));
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
                rollBackTransaction(new MyDBNullException("Fuelstation property update failed.\nCheck fileds that must not be empty."));
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
                rollBackTransaction(new MyDBNullException("Fuelstation property update failed.\nCheck fileds that must not be empty."));
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
    //<editor-fold defaultstate="collapsed" desc="READ">
    //<editor-fold defaultstate="collapsed" desc="RELATION SALESMAN CUSTOMER">
    public RelSALESMANCUST getCRM_R_Salesman_Cust(Salesman s, Customer c) throws Exception {
        try {
            return (RelSALESMANCUST) getEm().createNamedQuery("RelSALESMANCUST.RelSalesmanCustomer")
                    .setParameter("IDC", c)
                    .setParameter("IDS", s)
                    .getSingleResult();
        } catch (Exception ex) {
            throw new Exception("No relation between Salesman and Customer !");
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
    public List<CrmStatus> getCRM_AllStatuses() {
        try {
            return getEm().createNamedQuery("CrmStatus.findAll")
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRM_CustomerProcessesByDate(Customer customer, Date dateFrom, Date dateTo) {
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
                    .setParameter("dateFrom", dateFrom)
                    .setParameter("dateTo", dateTo)
                    .getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CrmProcess> getCRM_SalesmanProcessesByDate(Salesman salesman, Date dateFrom, Date dateTo) {
        try {
            return getEm().createNamedQuery("CrmProcess.SalesmanProcessesByDate")
                    .setParameter("IDS", salesman)
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
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add/Update Data">
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
            } else if (ex.toString().toLowerCase().contains(DB_DUPLICATE)) {
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

    //<editor-fold defaultstate="collapsed" desc="CRM PROCESS">
    public void addNewCRM_Process(Salesman s, Customer c, CrmStatus crmStatus, String comment, Date actionDate) throws Exception {
        RelSALESMANCUST r = getCRM_R_Salesman_Cust(s, c);

        CrmProcess crmProcess = new CrmProcess(r, crmStatus, comment, actionDate);
        addNewCRM_Process(crmProcess);
    }

    public void addNewCRM_Process(RelSALESMANCUST RelSalesmanCustomer, CrmStatus crmStatus, String comment, Date actionDate) throws Exception {
        CrmProcess crmProcess = new CrmProcess(RelSalesmanCustomer, crmStatus, comment, actionDate);
        addNewCRM_Process(crmProcess);
    }

    public void addNewCRM_Process(CrmProcess newCrmProcess) throws Exception {
        try {
            getEm().getTransaction().begin();
            em.persist(newCrmProcess);
            getEm().getTransaction().commit();

        } catch (Exception ex) {
            if (ex.toString().toLowerCase().contains(DB_NULLVALUES)) {
                rollBackTransaction(new MyDBNullException("New CRM process addition failed.\nCheck fileds that must not be empty."));
            } else {
                rollBackTransaction(ex.getMessage());
            }
        }
    }
    //</editor-fold>
    //</editor-fold>
    //</editor-fold>
}
