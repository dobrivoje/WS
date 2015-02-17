/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByID", query = "SELECT c FROM Customer c WHERE c.idc = :idc"),
    @NamedQuery(name = "Customer.PartialName", query = "SELECT c FROM Customer c WHERE c.name LIKE :name"),
    @NamedQuery(name = "Customer.findByPib", query = "SELECT c FROM Customer c WHERE c.pib = :pib")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDC")
    private Long idc;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @Basic(optional = false)
    @Column(name = "PIB")
    private String pib;
    @OneToMany(mappedBy = "fKIDCustomer")
    private List<Owner> ownerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdc")
    private List<RelCBType> relCBTypeList;
    @JoinColumn(name = "FK_IDCity", referencedColumnName = "IDC")
    @ManyToOne
    private City fKIDCity;

    public Customer() {
    }

    public Customer(Long idc) {
        this.idc = idc;
    }

    public Customer(Long idc, String name, String address, String pib) {
        this.idc = idc;
        this.name = name;
        this.address = address;
        this.pib = pib;
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

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    @XmlTransient
    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }

    @XmlTransient
    public List<RelCBType> getRelCBTypeList() {
        return relCBTypeList;
    }

    public void setRelCBTypeList(List<RelCBType> relCBTypeList) {
        this.relCBTypeList = relCBTypeList;
    }

    public City getFKIDCity() {
        return fKIDCity;
    }

    public void setFKIDCity(City fKIDCity) {
        this.fKIDCity = fKIDCity;
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
        if ((this.idc == null && other.idc != null) || (this.idc != null && !this.idc.equals(other.idc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
