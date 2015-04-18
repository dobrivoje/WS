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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "Rel_UserSalesman")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "RelUserSalesman.findAll", query = "SELECT r FROM RelUserSalesman r"),
            
            
            @NamedQuery(name = "RelUserSalesman.getInfSysUser",
                    query = "SELECT r.FK_IDUN FROM RelUserSalesman r WHERE r.FK_IDS = :FK_IDS AND r.active = :active ORDER BY r.idrus DESC"),
            
            @NamedQuery(name = "RelUserSalesman.getSalesman",
                    query = "SELECT r.FK_IDS FROM RelUserSalesman r WHERE r.FK_IDUN = :FK_IDUN AND r.active = :active ORDER BY r.idrus DESC")
        }
)
public class RelUserSalesman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRUS")
    private Long idrus;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "DateFrom")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFrom;
    @Column(name = "DateTo")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTo;
    @JoinColumn(name = "FK_IDS", referencedColumnName = "IDS")
    @ManyToOne
    private Salesman FK_IDS;
    @JoinColumn(name = "FK_IDUN", referencedColumnName = "IDUN")
    @ManyToOne
    private InfSysUser FK_IDUN;

    public RelUserSalesman() {
    }

    public RelUserSalesman(Long idrus) {
        this.idrus = idrus;
    }

    public Long getIdrus() {
        return idrus;
    }

    public void setIdrus(Long idrus) {
        this.idrus = idrus;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Salesman getFK_IDS() {
        return FK_IDS;
    }

    public void setFK_IDS(Salesman FK_IDS) {
        this.FK_IDS = FK_IDS;
    }

    public InfSysUser getFK_IDUN() {
        return FK_IDUN;
    }

    public void setFK_IDUN(InfSysUser FK_IDUN) {
        this.FK_IDUN = FK_IDUN;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrus != null ? idrus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RelUserSalesman)) {
            return false;
        }
        RelUserSalesman other = (RelUserSalesman) object;
        return !((this.idrus == null && other.idrus != null) || (this.idrus != null && !this.idrus.equals(other.idrus)));
    }

    @Override
    public String toString() {
        return (getFK_IDUN() != null ? getFK_IDUN() : "UN n/a")
                + "-"
                + (getFK_IDS() != null ? getFK_IDS() : "S n/a");
    }

}
