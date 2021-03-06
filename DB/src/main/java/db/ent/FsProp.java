/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "FS_PROP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FsProp.findAll", query = "SELECT f FROM FsProp f"),

    @NamedQuery(name = "FsProp.findByFSPROP", query = "SELECT f FROM FsProp f WHERE f = :FSPROP"),
    @NamedQuery(name = "FsProp.findByID", query = "SELECT f FROM FsProp f WHERE f.idfsp = :ID"),
    @NamedQuery(name = "FsProp.findByOwner", query = "SELECT f FROM FsProp f WHERE f.fkIdo = :owner"),

    @NamedQuery(name = "FsProp.FSPropByCustomer",
            query = "SELECT f FROM FsProp f WHERE f.fkIdo.fKIDCustomer = :customer AND f.fkIdo.fkIdFs = :fuelstation AND f.active = :active"),

    @NamedQuery(name = "FsProp.FSPropByFS",
            query = "SELECT f FROM FsProp f WHERE f.fkIdo.fkIdFs = :fuelstation AND f.active = :active"),

    @NamedQuery(name = "FsProp.CurrentFSPropForFS",
            query = "SELECT f FROM FsProp f WHERE f.fkIdo.fkIdFs = :fuelstation AND f.active = TRUE"),

    @NamedQuery(name = "FsProp.CurrentFSPropForOwner",
            query = "SELECT fsp FROM FsProp fsp WHERE fsp.fkIdo = :owner AND fsp.active = TRUE"),

    @NamedQuery(name = "FsProp.findByNoOfTanks", query = "SELECT f FROM FsProp f WHERE f.noOfTanks = :noOfTanks"),
    @NamedQuery(name = "FsProp.findByRestaurant", query = "SELECT f FROM FsProp f WHERE f.restaurant = :restaurant"),
    @NamedQuery(name = "FsProp.findByTruckCapable", query = "SELECT f FROM FsProp f WHERE f.truckCapable = :truckCapable"),
    @NamedQuery(name = "FsProp.findByCarWash", query = "SELECT f FROM FsProp f WHERE f.carWash = :carWash"),
    @NamedQuery(name = "FsProp.findByLicDateFrom", query = "SELECT f FROM FsProp f WHERE f.licDateFrom = :licDateFrom"),
    @NamedQuery(name = "FsProp.findByLicDateTo", query = "SELECT f FROM FsProp f WHERE f.licDateTo = :licDateTo"),
    @NamedQuery(name = "FsProp.findByActive", query = "SELECT f FROM FsProp f WHERE f.active = :active")})

public class FsProp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDFSP")
    private Long idfsp;
    @Column(name = "PropertiesDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date propertiesDate;
    @Column(name = "NoOfTanks")
    private Integer noOfTanks;
    @Column(name = "TruckCapable")
    private int truckCapable;
    @Column(name = "Restaurant")
    private Boolean restaurant;
    @Column(name = "CarWash")
    private Boolean carWash;
    @Column(name = "Compliance")
    private String compliance;
    @Column(name = "Licence")
    private String licence;
    @Column(name = "LicDateFrom")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date licDateFrom;
    @Column(name = "LicDateTo")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date licDateTo;
    @Column(name = "Comment")
    private String comment;
    @Column(name = "Active")
    private Boolean active;
    @JoinColumn(name = "FK_IDO", referencedColumnName = "IDO")
    @ManyToOne(optional = false)
    private Owner fkIdo;
    @OneToMany(mappedBy = "FK_FSP")
    private List<RelFSPROPPRODUCT> relFSPROPPRODUCTList;

    public FsProp() {
    }

    public FsProp(FsProp existingFsProp) {
        initFsProp(existingFsProp);
    }

    private void initFsProp(FsProp existingFsProp) {

        try {
            this.propertiesDate = existingFsProp.getPropertiesDate();
        } catch (Exception ex) {
        }

        try {
            this.noOfTanks = existingFsProp.getNoOfTanks();
        } catch (Exception ex) {
        }

        try {
            this.restaurant = existingFsProp.getRestaurant();
        } catch (Exception ex) {
        }

        try {
            this.truckCapable = existingFsProp.getTruckCapable();
        } catch (Exception ex) {
        }

        try {
            this.carWash = existingFsProp.getCarWash();
        } catch (Exception ex) {
        }

        try {
            this.compliance = existingFsProp.getCompliance();
        } catch (Exception ex) {
        }

        try {
            this.licence = existingFsProp.getLicence();
        } catch (Exception ex) {
        }

        try {
            this.licDateFrom = existingFsProp.getLicDateFrom();
        } catch (Exception ex) {
        }

        try {
            this.licDateTo = existingFsProp.getLicDateTo();
        } catch (Exception ex) {
        }

        try {
            this.comment = existingFsProp.getComment();
        } catch (Exception ex) {
        }

        try {
            this.active = existingFsProp.getActive();
        } catch (Exception ex) {
        }

        try {
            this.fkIdo = existingFsProp.getFkIdo();
        } catch (Exception ex) {
        }

        try {
            this.relFSPROPPRODUCTList.addAll(existingFsProp.getRelFSPROPPRODUCTList());
        } catch (Exception ex) {
        }
    }

    public void setNewFsProp(FsProp existingFsProp) {
        initFsProp(existingFsProp);
    }

    public FsProp(Long idfsp) {
        this.idfsp = idfsp;
    }

    public Long getIdfsp() {
        return idfsp;
    }

    public void setIdfsp(Long idfsp) {
        this.idfsp = idfsp;
    }

    public Date getPropertiesDate() {
        return propertiesDate;
    }

    public void setPropertiesDate(Date propertiesDate) {
        this.propertiesDate = propertiesDate;
    }

    public Integer getNoOfTanks() {
        return noOfTanks;
    }

    public void setNoOfTanks(Integer noOfTanks) {
        this.noOfTanks = noOfTanks;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        this.restaurant = restaurant;
    }

    public int getTruckCapable() {
        return truckCapable;
    }

    public void setTruckCapable(int truckCapable) {
        this.truckCapable = truckCapable;
    }

    public Boolean getCarWash() {
        return carWash;
    }

    public void setCarWash(Boolean carWash) {
        this.carWash = carWash;
    }

    public String getCompliance() {
        return compliance;
    }

    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public Date getLicDateFrom() {
        return licDateFrom;
    }

    public void setLicDateFrom(Date licDateFrom) {
        this.licDateFrom = licDateFrom;
    }

    public Date getLicDateTo() {
        return licDateTo;
    }

    public void setLicDateTo(Date licDateTo) {
        this.licDateTo = licDateTo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Owner getFkIdo() {
        return fkIdo;
    }

    public void setFkIdo(Owner fkIdo) {
        this.fkIdo = fkIdo;
    }

    @XmlTransient
    public List<RelFSPROPPRODUCT> getRelFSPROPPRODUCTList() {
        return relFSPROPPRODUCTList;
    }

    public void setRelFSPROPPRODUCTList(List<RelFSPROPPRODUCT> relFSPROPPRODUCTList) {
        this.relFSPROPPRODUCTList = relFSPROPPRODUCTList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfsp != null ? idfsp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FsProp)) {
            return false;
        }
        FsProp other = (FsProp) object;
        return !((this.idfsp == null && other.idfsp != null) || (this.idfsp != null && !this.idfsp.equals(other.idfsp)));
    }

    @Override
    public String toString() {
        return getFkIdo() != null
                ? getFkIdo().getFKIDCustomer()
                + ", "
                + getFkIdo().getFkIdFs()
                + ", "
                + (getActive() ? "(a)" : "(na)")
                : "no owner!";
    }
}
