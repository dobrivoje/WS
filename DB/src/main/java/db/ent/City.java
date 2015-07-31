/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "CITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c"),
    @NamedQuery(name = "City.findByIdc", query = "SELECT c FROM City c WHERE c.idc = :idc"),
    @NamedQuery(name = "City.findByName", query = "SELECT c FROM City c WHERE c.name = :name"),
    @NamedQuery(name = "City.PartialName", query = "SELECT c FROM City c WHERE c.name LIKE :name"),
    @NamedQuery(name = "City.MunicipalityPartialName", query = "SELECT c FROM City c WHERE c.municipality LIKE :municipality"),
    @NamedQuery(name = "City.DistrictPartialName", query = "SELECT c FROM City c WHERE c.district LIKE :district"),
    @NamedQuery(name = "City.partialRegion", query = "SELECT c FROM City c WHERE c.region LIKE :zip"),
    @NamedQuery(name = "City.findByZip", query = "SELECT c FROM City c WHERE c.zip = :zip"),
    @NamedQuery(name = "City.findByRegion", query = "SELECT c FROM City c WHERE c.region = :region")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDC")
    private Long idc;
    @Column(name = "Name")
    private String name;
    @Column(name = "Municipality")
    private String municipality;
    @Column(name = "District")
    private String district;
    @Column(name = "Region")
    private String region;
    @Column(name = "ZIP")
    private String zip;
    @OneToMany(mappedBy = "FK_City")
    private List<Customer> customerList;
    @OneToMany(mappedBy = "FK_City")
    private List<Fuelstation> fuelstationList;

    public City() {
    }

    public City(Long idc) {
        this.idc = idc;
    }

    public Long getIdc() {
        return idc;
    }

    public void setIdc(Long idc) {
        this.idc = idc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @XmlTransient
    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @XmlTransient
    public List<Fuelstation> getFuelstationList() {
        return fuelstationList;
    }

    public void setFuelstationList(List<Fuelstation> fuelstationList) {
        this.fuelstationList = fuelstationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idc != null ? idc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        return !((this.idc == null && other.idc != null) || (this.idc != null && !this.idc.equals(other.idc)));
    }

    @Override
    public String toString() {
        return name + " - " + municipality;
    }

}
