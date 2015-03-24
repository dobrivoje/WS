/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

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
    private Long idsi;
    @JoinColumn(name = "FK_IDD", referencedColumnName = "IDD")
    @ManyToOne
    private Document FK_DOCUMENT;
    @JoinColumn(name = "FK_SALESMAN", referencedColumnName = "IDS")
    @ManyToOne
    private Salesman fkSalesman;

    public RelSALESMANIMAGE() {
    }

    public RelSALESMANIMAGE(Salesman salesman, Document document) {
        this.FK_DOCUMENT = document;
        this.fkSalesman = salesman;
    }

    public Long getIdsi() {
        return idsi;
    }

    public void setIdsi(Long idsi) {
        this.idsi = idsi;
    }

    public Document getFkDocument() {
        return FK_DOCUMENT;
    }

    public void setFkDocument(Document document) {
        this.FK_DOCUMENT = document;
    }

    public Salesman getFkSalesman() {
        return fkSalesman;
    }

    public void setFkSalesman(Salesman salesman) {
        this.fkSalesman = salesman;
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
        if ((this.idsi == null && other.idsi != null) || (this.idsi != null && !this.idsi.equals(other.idsi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.RelSALESMANIMAGE[ idsi=" + idsi + " ]";
    }

}
