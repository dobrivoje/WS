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
 * @author dprtenjak
 */
@Entity
@Table(name = "Rel_BLP")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "RelBLP.findAll", query = "SELECT r FROM RelBLP r"),
            
            @NamedQuery(name = "RelBLP.BL_Products", 
                    query = "SELECT r.FK_IDP FROM RelBLP r WHERE r.FK_IDBL = :ID_BL"),
            
            @NamedQuery(name = "RelBLP.findByFkIdp", query = "SELECT r FROM RelBLP r WHERE r.FK_IDBL = :fkIdp"),
            @NamedQuery(name = "RelBLP.findByFkIdbl", query = "SELECT r FROM RelBLP r WHERE r.FK_IDBL = :fkIdbl")
        }
)
public class RelBLP implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDBLP")
    private Long idblp;
    @JoinColumn(name = "FK_IDBL", referencedColumnName = "IDBL")
    @ManyToOne(optional = false)
    private BussinesLine FK_IDBL;
    @JoinColumn(name = "FK_IDP", referencedColumnName = "IDP")
    @ManyToOne(optional = false)
    private Product FK_IDP;

    public RelBLP() {
    }

    public RelBLP(Long idblp) {
        this.idblp = idblp;
    }

    public Long getIdblp() {
        return idblp;
    }

    public void setIdblp(Long idblp) {
        this.idblp = idblp;
    }

    public BussinesLine getFK_IDBL() {
        return FK_IDBL;
    }

    public void setFK_IDBL(BussinesLine FK_IDBL) {
        this.FK_IDBL = FK_IDBL;
    }

    public Product getFK_IDP() {
        return FK_IDP;
    }

    public void setFK_IDP(Product FK_IDP) {
        this.FK_IDP = FK_IDP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idblp != null ? idblp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelBLP)) {
            return false;
        }
        RelBLP other = (RelBLP) object;
        if ((this.idblp == null && other.idblp != null) || (this.idblp != null && !this.idblp.equals(other.idblp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ent.RelBLP[ idblp=" + idblp + " ]";
    }
    
}
