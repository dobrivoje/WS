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
@Table(name = "Rel_FS_PROP_PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelFSPROPPRODUCT.findAll", query = "SELECT r FROM RelFSPROPPRODUCT r"),
    @NamedQuery(name = "RelFSPROPPRODUCT.findByIdrfp", query = "SELECT r FROM RelFSPROPPRODUCT r WHERE r.idrfp = :idrfp"),
    @NamedQuery(name = "RelFSPROPPRODUCT.findByPotential", query = "SELECT r FROM RelFSPROPPRODUCT r WHERE r.potential = :potential")})
public class RelFSPROPPRODUCT implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRFP")
    private Long idrfp;
    @Column(name = "Potential")
    private Integer potential;
    @JoinColumn(name = "FK_IDFSP", referencedColumnName = "IDFSP")
    @ManyToOne
    private FsProp fkIdfsp;
    @JoinColumn(name = "FK_IDP", referencedColumnName = "IDP")
    @ManyToOne
    private Product fkIdp;

    public RelFSPROPPRODUCT() {
    }

    public RelFSPROPPRODUCT(Long idrfp) {
        this.idrfp = idrfp;
    }

    public Long getIdrfp() {
        return idrfp;
    }

    public void setIdrfp(Long idrfp) {
        this.idrfp = idrfp;
    }

    public Integer getPotential() {
        return potential;
    }

    public void setPotential(Integer potential) {
        this.potential = potential;
    }

    public FsProp getFkIdfsp() {
        return fkIdfsp;
    }

    public void setFkIdfsp(FsProp fkIdfsp) {
        this.fkIdfsp = fkIdfsp;
    }

    public Product getFkIdp() {
        return fkIdp;
    }

    public void setFkIdp(Product fkIdp) {
        this.fkIdp = fkIdp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrfp != null ? idrfp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelFSPROPPRODUCT)) {
            return false;
        }
        RelFSPROPPRODUCT other = (RelFSPROPPRODUCT) object;
        if ((this.idrfp == null && other.idrfp != null) || (this.idrfp != null && !this.idrfp.equals(other.idrfp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.RelFSPROPPRODUCT[ idrfp=" + idrfp + " ]";
    }
    
}
