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
@Table(name = "Rel_SALESMAN_IMAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelSALESMANIMAGE.findAll", query = "SELECT r FROM RelSALESMANIMAGE r"),
    @NamedQuery(name = "RelSALESMANIMAGE.findByIdsi", query = "SELECT r FROM RelSALESMANIMAGE r WHERE r.idsi = :idsi")})
public class RelSALESMANIMAGE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSI")
    private Integer idsi;
    @JoinColumn(name = "FK_IMAGE", referencedColumnName = "IDI")
    @OneToOne(optional = false)
    private Image FK_Image;
    @JoinColumn(name = "FK_SALESMAN", referencedColumnName = "IDS")
    @ManyToOne
    private Salesman FK_Salesman;

    public RelSALESMANIMAGE() {
    }

    public RelSALESMANIMAGE(Integer idsi) {
        this.idsi = idsi;
    }

    public Integer getIdsi() {
        return idsi;
    }

    public void setIdsi(Integer idsi) {
        this.idsi = idsi;
    }

    public Image getFK_Image() {
        return FK_Image;
    }

    public void setFK_Image(Image FK_Image) {
        this.FK_Image = FK_Image;
    }

    public Salesman getFK_Salesman() {
        return FK_Salesman;
    }

    public void setFK_Salesman(Salesman FK_Salesman) {
        this.FK_Salesman = FK_Salesman;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsi != null ? idsi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelSALESMANIMAGE)) {
            return false;
        }
        RelSALESMANIMAGE other = (RelSALESMANIMAGE) object;
        return !((this.idsi == null && other.idsi != null) || (this.idsi != null && !this.idsi.equals(other.idsi)));
    }

    @Override
    public String toString() {
        return getFK_Salesman().toString() + " ,Image:" + getFK_Image().toString();
    }

}
