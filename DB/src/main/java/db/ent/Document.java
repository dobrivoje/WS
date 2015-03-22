/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "DOCUMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByIdd", query = "SELECT d FROM Document d WHERE d.idd = :idd"),
    @NamedQuery(name = "Document.findByGallery", query = "SELECT d FROM Document d WHERE d.FK_Gallery = :idg"),
    @NamedQuery(name = "Document.findByUploadDate", query = "SELECT d FROM Document d WHERE d.uploadDate = :uploadDate")})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDD")
    private Long idd;
    @Column(name = "Name")
    private String name;
    @Lob
    @Column(name = "DocData")
    private Serializable docData;
    @Column(name = "DocLocation")
    private String docLocation;
    @Column(name = "UploadDate")
    @Temporal(TemporalType.DATE)
    private Date uploadDate;
    @OneToOne(mappedBy = "FK_DOCUMENT")
    private RelFSDocument relFSImage;
    @OneToOne(mappedBy = "FK_DOCUMENT")
    private RelSALESMANIMAGE relSALESMANIMAGE;
    @JoinColumn(name = "FK_IDG", referencedColumnName = "IDG")
    @ManyToOne
    private Gallery FK_Gallery;

    public Document() {
    }

    public Document(String name, Serializable docData, String docLocation, Date uploadDate, Gallery FK_Gallery) {
        this.name = name;
        this.docData = docData;
        this.docLocation = docLocation;
        this.uploadDate = uploadDate;
        this.FK_Gallery = FK_Gallery;
    }

    public Long getIdd() {
        return idd;
    }

    public void setIdd(Long idd) {
        this.idd = idd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Serializable getDocData() {
        return docData;
    }

    public void setDocData(Serializable docData) {
        this.docData = docData;
    }

    public String getDocLocation() {
        return docLocation;
    }

    public void setDocLocation(String docLocation) {
        this.docLocation = docLocation;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public RelFSDocument getRelFSImage() {
        return relFSImage;
    }

    public void setRelFSImage(RelFSDocument relFSImage) {
        this.relFSImage = relFSImage;
    }

    public RelSALESMANIMAGE getRelSALESMANIMAGE() {
        return relSALESMANIMAGE;
    }

    public void setRelSALESMANIMAGE(RelSALESMANIMAGE relSALESMANIMAGE) {
        this.relSALESMANIMAGE = relSALESMANIMAGE;
    }

    public Gallery getFK_Gallery() {
        return FK_Gallery;
    }

    public void setFK_Gallery(Gallery FK_Gallery) {
        this.FK_Gallery = FK_Gallery;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idd != null ? idd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        return !((this.idd == null && other.idd != null) || (this.idd != null && !this.idd.equals(other.idd)));
    }

    @Override
    public String toString() {
        return "db.Image[ idi=" + idd + " ]";
    }

}
