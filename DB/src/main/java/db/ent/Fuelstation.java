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
@Table(name = "FUELSTATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fuelstation.findAll", query = "SELECT f FROM Fuelstation f"),
    @NamedQuery(name = "Fuelstation.findByIdfs", query = "SELECT f FROM Fuelstation f WHERE f.idfs = :idfs"),
    @NamedQuery(name = "Fuelstation.findByName", query = "SELECT f FROM Fuelstation f WHERE f.name = :name"),
    @NamedQuery(name = "Fuelstation.findByAddress", query = "SELECT f FROM Fuelstation f WHERE f.address = :address"),
    @NamedQuery(name = "Fuelstation.findByCoordinates", query = "SELECT f FROM Fuelstation f WHERE f.coordinates = :coordinates")})
public class Fuelstation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDFS")
    private Long idfs;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Column(name = "Address")
    private String address;
    @Column(name = "Coordinates")
    private String coordinates;
    @OneToMany(mappedBy = "fkIdFs")
    private List<RelFSDocument> relFSDocumentsList;
    @OneToMany(mappedBy = "fkIdFs")
    private List<Owner> ownerList;
    @JoinColumn(name = "FK_IDC", referencedColumnName = "IDC")
    @ManyToOne(optional = false)
    private City FK_City;

    public Fuelstation() {
    }

    public Fuelstation(Long idfs) {
        this.idfs = idfs;
    }

    public Long getIdfs() {
        return idfs;
    }

    public void setIdfs(Long idfs) {
        this.idfs = idfs;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @XmlTransient
    public List<RelFSDocument> getRelFSDocumentsList() {
        return relFSDocumentsList;
    }

    public void setRelFSDocumentsList(List<RelFSDocument> relFSDocumentsList) {
        this.relFSDocumentsList = relFSDocumentsList;
    }

    @XmlTransient
    public List<Owner> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList) {
        this.ownerList = ownerList;
    }

    public City getFK_City() {
        return FK_City;
    }

    public void setFK_City(City FK_City) {
        this.FK_City = FK_City;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfs != null ? idfs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fuelstation)) {
            return false;
        }
        Fuelstation other = (Fuelstation) object;
        return !((this.idfs == null && other.idfs != null) || (this.idfs != null && !this.idfs.equals(other.idfs)));
    }

    @Override
    public String toString() {
        return name + ", " + FK_City.getName();
    }

}
