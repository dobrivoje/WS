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
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dprtenjak
 */
@Entity
@Table(name = "Rel_SALESMEAN_CUST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelSALESMEANCUST.findAll", query = "SELECT r FROM RelSALESMEANCUST r"),
    @NamedQuery(name = "RelSALESMEANCUST.findByIdrblc", query = "SELECT r FROM RelSALESMEANCUST r WHERE r.idrblc = :idrblc"),
    @NamedQuery(name = "RelSALESMEANCUST.findByDateFrom", query = "SELECT r FROM RelSALESMEANCUST r WHERE r.dateFrom = :dateFrom"),
    @NamedQuery(name = "RelSALESMEANCUST.findByDateTo", query = "SELECT r FROM RelSALESMEANCUST r WHERE r.dateTo = :dateTo"),
    @NamedQuery(name = "RelSALESMEANCUST.findByActive", query = "SELECT r FROM RelSALESMEANCUST r WHERE r.active = :active")})
public class RelSALESMEANCUST implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRBLC")
    private Long idrblc;
    @Column(name = "DateFrom")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFrom;
    @Column(name = "DateTo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTo;
    @Column(name = "Active")
    private Boolean active;
    @JoinColumn(name = "FK_IDC", referencedColumnName = "IDC")
    @ManyToOne
    private Customer fkIdc;
    @JoinColumn(name = "FK_IDS", referencedColumnName = "IDS")
    @ManyToOne
    private Salesman fkIds;

    public RelSALESMEANCUST() {
    }

    public RelSALESMEANCUST(Long idrblc) {
        this.idrblc = idrblc;
    }

    public Long getIdrblc() {
        return idrblc;
    }

    public void setIdrblc(Long idrblc) {
        this.idrblc = idrblc;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer getFkIdc() {
        return fkIdc;
    }

    public void setFkIdc(Customer fkIdc) {
        this.fkIdc = fkIdc;
    }

    public Salesman getFkIds() {
        return fkIds;
    }

    public void setFkIds(Salesman fkIds) {
        this.fkIds = fkIds;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrblc != null ? idrblc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelSALESMEANCUST)) {
            return false;
        }
        RelSALESMEANCUST other = (RelSALESMEANCUST) object;
        if ((this.idrblc == null && other.idrblc != null) || (this.idrblc != null && !this.idrblc.equals(other.idrblc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ent.RelSALESMEANCUST[ idrblc=" + idrblc + " ]";
    }

}
