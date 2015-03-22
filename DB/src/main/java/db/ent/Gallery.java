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
@Table(name = "GALLERY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gallery.findAll", query = "SELECT g FROM Gallery g"),
    @NamedQuery(name = "Gallery.findByIdg", query = "SELECT g FROM Gallery g WHERE g.idg = :idg"),
    @NamedQuery(name = "Gallery.findByName", query = "SELECT g FROM Gallery g WHERE g.name = :name")})
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDG")
    private Long idg;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "StoreLocation")
    private String storeLocation;
    @OneToMany(mappedBy = "FK_Gallery")
    private List<Document> documentsList;

    public Gallery() {
    }

    public Gallery(String name, String storeLocation, List<Document> documentsList) {
        this.name = name;
        this.storeLocation = storeLocation;
        this.documentsList = documentsList;
    }

    public Long getIdg() {
        return idg;
    }

    public void setIdg(Long idg) {
        this.idg = idg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    @XmlTransient
    public List<Document> getDocumentsList() {
        return documentsList;
    }

    public void setDocumentsList(List<Document> documentsList) {
        this.documentsList = documentsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idg != null ? idg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gallery)) {
            return false;
        }
        Gallery other = (Gallery) object;
        return !((this.idg == null && other.idg != null) || (this.idg != null && !this.idg.equals(other.idg)));
    }

    @Override
    public String toString() {
        return "Gallery : "
                + getName() + ", path: "
                + getStoreLocation();
    }

}
