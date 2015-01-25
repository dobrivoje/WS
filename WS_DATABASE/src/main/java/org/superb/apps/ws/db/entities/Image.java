/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.entities;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "IMAGE", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"Name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findByIdi", query = "SELECT i FROM Image i WHERE i.idi = :idi"),
    @NamedQuery(name = "Image.findByName", query = "SELECT i FROM Image i WHERE i.name = :name")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDI", nullable = false)
    private Long idi;
    @Column(name = "Name", length = 100)
    private String name;
    @Lob
    @Column(name = "ImageData")
    private Serializable imageData;
    @Column(name = "UploadDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;
    @JoinColumn(name = "FK_IDG", referencedColumnName = "IDG")
    @ManyToOne
    private Gallery FK_IDGallery;
    @OneToMany(mappedBy = "FK_Image")
    private List<RelSALESMANIMAGE> SalesmanImageList;

    public Image() {
    }

    public Image(Long idi) {
        this.idi = idi;
    }

    public Long getIdi() {
        return idi;
    }

    public void setIdi(Long idi) {
        this.idi = idi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Serializable getImageData() {
        return imageData;
    }

    public void setImageData(Serializable imageData) {
        this.imageData = imageData;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Gallery getFK_IDGallery() {
        return FK_IDGallery;
    }

    public void setFK_IDGallery(Gallery FK_IDGallery) {
        this.FK_IDGallery = FK_IDGallery;
    }

    @XmlTransient
    public List<RelSALESMANIMAGE> getSalesmanImageList() {
        return SalesmanImageList;
    }

    public void setSalesmanImageList(List<RelSALESMANIMAGE> SalesmanImageList) {
        this.SalesmanImageList = SalesmanImageList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idi != null ? idi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        return !((this.idi == null && other.idi != null) || (this.idi != null && !this.idi.equals(other.idi)));
    }

    @Override
    public String toString() {
        return getName() + ", Gallery:" + getFK_IDGallery().getName();
    }
}
