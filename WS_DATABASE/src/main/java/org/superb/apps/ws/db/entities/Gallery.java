/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.entities;

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
    @Column(name = "IDG", nullable = false)
    private Integer idg;
    @Basic(optional = false)
    @Column(name = "Name", nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "FK_IDGallery")
    private List<Image> imageList;

    public Gallery() {
    }

    public Gallery(Integer idg) {
        this.idg = idg;
    }

    public Gallery(Integer idg, String name) {
        this.idg = idg;
        this.name = name;
    }

    public Integer getIdg() {
        return idg;
    }

    public void setIdg(Integer idg) {
        this.idg = idg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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
        return getName();
    }
    
}
