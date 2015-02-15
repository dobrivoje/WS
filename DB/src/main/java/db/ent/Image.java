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
@Table(name = "IMAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findByIdi", query = "SELECT i FROM Image i WHERE i.idi = :idi"),
    @NamedQuery(name = "Image.findByName", query = "SELECT i FROM Image i WHERE i.name = :name"),
    @NamedQuery(name = "Image.findByUploadDate", query = "SELECT i FROM Image i WHERE i.uploadDate = :uploadDate")})
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDI")
    private Long idi;
    @Column(name = "Name")
    private String name;
    @Lob
    @Column(name = "ImageData")
    private Serializable imageData;
    @Column(name = "UploadDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;
    @OneToOne(mappedBy = "fkIdi")
    private RelFSImage relFSImage;
    @OneToOne(mappedBy = "fkImage")
    private RelSALESMANIMAGE relSALESMANIMAGE;
    @JoinColumn(name = "FK_IDG", referencedColumnName = "IDG")
    @ManyToOne
    private Gallery fkIdg;

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

    public RelFSImage getRelFSImage() {
        return relFSImage;
    }

    public void setRelFSImage(RelFSImage relFSImage) {
        this.relFSImage = relFSImage;
    }

    public RelSALESMANIMAGE getRelSALESMANIMAGE() {
        return relSALESMANIMAGE;
    }

    public void setRelSALESMANIMAGE(RelSALESMANIMAGE relSALESMANIMAGE) {
        this.relSALESMANIMAGE = relSALESMANIMAGE;
    }

    public Gallery getFkIdg() {
        return fkIdg;
    }

    public void setFkIdg(Gallery fkIdg) {
        this.fkIdg = fkIdg;
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
        if ((this.idi == null && other.idi != null) || (this.idi != null && !this.idi.equals(other.idi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Image[ idi=" + idi + " ]";
    }
    
}
