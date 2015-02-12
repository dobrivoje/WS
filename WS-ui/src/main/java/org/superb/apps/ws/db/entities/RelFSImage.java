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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "Rel_FS_Image")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelFSImage.findAll", query = "SELECT r FROM RelFSImage r"),
    @NamedQuery(name = "RelFSImage.findByIdfsi", query = "SELECT r FROM RelFSImage r WHERE r.idfsi = :idfsi"),
    @NamedQuery(name = "RelFSImage.findByImageTaken", query = "SELECT r FROM RelFSImage r WHERE r.imageTaken = :imageTaken")})
public class RelFSImage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDFSI")
    private Long idfsi;
    @Column(name = "ImageTaken")
    private String imageTaken;
    @JoinColumn(name = "FK_IDFS", referencedColumnName = "IDFS")
    @ManyToOne
    private Fuelstation FK_FS1;
    @JoinColumn(name = "FK_IDI", referencedColumnName = "IDI")
    @OneToOne
    private Image FK_Image;

    public RelFSImage() {
    }

    public RelFSImage(Long idfsi) {
        this.idfsi = idfsi;
    }

    public Long getIdfsi() {
        return idfsi;
    }

    public void setIdfsi(Long idfsi) {
        this.idfsi = idfsi;
    }

    public String getImageTaken() {
        return imageTaken;
    }

    public void setImageTaken(String imageTaken) {
        this.imageTaken = imageTaken;
    }

    public Fuelstation getFK_FS1() {
        return FK_FS1;
    }

    public void setFK_FS1(Fuelstation FK_FS1) {
        this.FK_FS1 = FK_FS1;
    }

    public Image getFK_Image() {
        return FK_Image;
    }

    public void setFK_Image(Image FK_Image) {
        this.FK_Image = FK_Image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfsi != null ? idfsi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelFSImage)) {
            return false;
        }
        RelFSImage other = (RelFSImage) object;
        return !((this.idfsi == null && other.idfsi != null) || (this.idfsi != null && !this.idfsi.equals(other.idfsi)));
    }

    @Override
    public String toString() {
        return "RelFSImage[ " + idfsi + " ]";
    }
    
}
