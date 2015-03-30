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
    @NamedQuery(name = "Customer.findByNavCode", query = "SELECT c FROM Customer c WHERE c.navCode = :navCode"),
    @NamedQuery(name = "Customer.findByLicence", query = "SELECT c FROM Customer c WHERE c.licence = :licence"),
    @NamedQuery(name = "Customer.PartialName", query = "SELECT c FROM Customer c WHERE c.name LIKE :name"),
    @NamedQuery(name = "Customer.findByMatBr", query = "SELECT c FROM Customer c WHERE c.matBr = :matBr"),
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
    @Column(name = "Address")
    private String address;
    @Column(name = "PIB")
    private String pib;
    @Column(name = "MATBR")
    private String matBr;
    @Column(name = "NavCode")
    private String navCode;
    @Column(name = "Licence")
    private boolean licence;
    @Column(name = "Zone")
    private String zone;
    @Column(name = "Comment")
    private String comment;
    @Column(name = "Tel1")
    private String tel1;
    @Column(name = "Tel2")
    private String tel2;
    @Column(name = "Fax")
    private String fax;
    @Column(name = "Mob")
    private String mob;
    @Column(name = "Email1")
    private String email1;
    @Column(name = "Email2")
    private String email2;
    @OneToMany(mappedBy = "fKIDCustomer")
    private List<Owner> ownerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdc")
    private List<RelCBType> relCBTypeList;
    @JoinColumn(name = "FK_IDCity", referencedColumnName = "IDC")
    @ManyToOne
    private City fKIDCity;
    @OneToMany(mappedBy = "fkIdc")
    private List<RelSALESMANCUST> relSALESMEANCUSTList;

    public Customer() {
    }

    public Customer(Long idc) {
        this.idc = idc;
    }

    public Customer(String name, String address, String pib, String matBr, String navCode, boolean licence, String zone, String comment, String tel1, String tel2, String fax, String mob, String email1, String email2, City fKIDCity) {
        this.name = name;
        this.address = address;
        this.pib = pib;
        this.matBr = matBr;
        this.navCode = navCode;
        this.licence = licence;
        this.zone = zone;
        this.comment = comment;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.fax = fax;
        this.mob = mob;
        this.email1 = email1;
        this.email2 = email2;
        this.fKIDCity = fKIDCity;
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

    public String getNavCode() {
        return navCode;
    }

    public void setNavCode(String navCode) {
        this.navCode = navCode;
    }

    public boolean getLicence() {
        return licence;
    }

    public void setLicence(boolean licence) {
        this.licence = licence;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMatBr() {
        return matBr;
    }

    public void setMatBr(String matBr) {
        this.matBr = matBr;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
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

    //<editor-fold defaultstate="collapsed" desc="Custom Customer City getters">
    public String getMyCity() {
        return fKIDCity != null
                ? fKIDCity.getName() : " - ";
    }

    public String getMyCityMunicipality() {
        return fKIDCity != null
                ? fKIDCity.getMunicipality() : " - ";
    }

    public String getMyCityDistrict() {
        return fKIDCity != null
                ? fKIDCity.getDistrict() : " - ";
    }

    public String getMyCityRegion() {
        return fKIDCity != null
                ? fKIDCity.getRegion() : " - ";
    }
    //</editor-fold>

    public void setFKIDCity(City fKIDCity) {
        this.fKIDCity = fKIDCity;
    }

    @XmlTransient
    public List<RelSALESMANCUST> getRelSALESMEANCUSTList() {
        return relSALESMEANCUSTList;
    }

    public void setRelSALESMEANCUSTList(List<RelSALESMANCUST> relSALESMEANCUSTList) {
        this.relSALESMEANCUSTList = relSALESMEANCUSTList;
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
