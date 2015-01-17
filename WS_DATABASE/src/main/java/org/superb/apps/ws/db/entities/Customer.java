/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByID", query = "SELECT c FROM Customer c WHERE c.idc = :ID"),
    @NamedQuery(name = "Customer.findByPIB", query = "SELECT c FROM Customer c WHERE c.pib = :PIB"),
    @NamedQuery(name = "Customer.PartialName", query = "SELECT c FROM Customer c WHERE c.name LIKE :partialName"),
    @NamedQuery(name = "Customer.PartialAddress", query = "SELECT c FROM Customer c WHERE c.address LIKE :partialAddress"),
    @NamedQuery(name = "Customer.PartialCity", query = "SELECT c FROM Customer c WHERE c.city LIKE :partialCity"),
    @NamedQuery(name = "Customer.findByZip", query = "SELECT c FROM Customer c WHERE c.zip = :zip"),
    @NamedQuery(name = "Customer.PartialRegion", query = "SELECT c FROM Customer c WHERE c.region LIKE :partialRegion")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDC")
    private Long idc;
    @Column(name = "Name")
    @Basic(optional = false)
    private String name;
    @Column(name = "Address")
    @Basic(optional = false)
    private String address;
    @Column(name = "City")
    @Basic(optional = false)
    private String city;
    @Column(name = "ZIP")
    @Basic(optional = false)
    private String zip;
    @Column(name = "Region")
    @Basic(optional = false)
    private String region;
    @Column(name = "PIB")
    @Basic(optional = false)
    private String pib;

    public Customer() {
    }

    public Customer(Long idc) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return !((this.idc == null && other.idc != null) || (this.idc != null && !this.idc.equals(other.idc)));
    }

    @Override
    public String toString() {
        return "org.superb.apps.ws.db.entities.Customer[ idc=" + idc + " ]";
    }

}
