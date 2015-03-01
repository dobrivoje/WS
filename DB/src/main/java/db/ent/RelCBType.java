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
 * @author root
 */
@Entity
@Table(name = "Rel_CBType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelCBType.findAll", query = "SELECT r FROM RelCBType r"),
    @NamedQuery(name = "RelCBType.findByIdrcbt", query = "SELECT r FROM RelCBType r WHERE r.idrcbt = :idrcbt"),
    @NamedQuery(name = "RelCBType.findByCustomer", query = "SELECT r FROM RelCBType r WHERE r.fkIdc = :customer"),
    @NamedQuery(name = "RelCBType.findByDateFrom", query = "SELECT r FROM RelCBType r WHERE r.dateFrom = :dateFrom"),
    @NamedQuery(name = "RelCBType.findByDateTo", query = "SELECT r FROM RelCBType r WHERE r.dateTo = :dateTo"),
    @NamedQuery(name = "RelCBType.findByActive", query = "SELECT r FROM RelCBType r WHERE r.active = :active")})
public class RelCBType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRCBT")
    private Long idrcbt;
    @Column(name = "Date_From")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Column(name = "Date_To")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    @Column(name = "Active")
    private Boolean active;
    @JoinColumn(name = "FK_IDC", referencedColumnName = "IDC")
    @ManyToOne(optional = false)
    private Customer fkIdc;
    @JoinColumn(name = "FK_IDCBT", referencedColumnName = "IDCBT")
    @ManyToOne(optional = false)
    private CustomerBussinesType fkIdcbt;

    public RelCBType() {
    }

    public RelCBType(Long idrcbt) {
        this.idrcbt = idrcbt;
    }

    public Long getIdrcbt() {
        return idrcbt;
    }

    public void setIdrcbt(Long idrcbt) {
        this.idrcbt = idrcbt;
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

    public CustomerBussinesType getFkIdcbt() {
        return fkIdcbt;
    }

    public void setFkIdcbt(CustomerBussinesType fkIdcbt) {
        this.fkIdcbt = fkIdcbt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrcbt != null ? idrcbt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelCBType)) {
            return false;
        }
        RelCBType other = (RelCBType) object;
        return !((this.idrcbt == null && other.idrcbt != null) || (this.idrcbt != null && !this.idrcbt.equals(other.idrcbt)));
    }

    @Override
    public String toString() {
        return getFkIdcbt().getCustomerActivity()
                + ", "
                + (active ? " (a)" : " (n/a)");
    }

}
