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
import javax.persistence.Table;
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
    @NamedQuery(name = "RelCBType.findByDateFrom", query = "SELECT r FROM RelCBType r WHERE r.dateFrom = :dateFrom"),
    @NamedQuery(name = "RelCBType.findByDateTo", query = "SELECT r FROM RelCBType r WHERE r.dateTo = :dateTo"),
    @NamedQuery(name = "RelCBType.findByActive", query = "SELECT r FROM RelCBType r WHERE r.active = :active")})
public class RelCBType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRCBT")
    private Integer idrcbt;
    @Column(name = "Date_From")
    private String dateFrom;
    @Column(name = "Date_To")
    private String dateTo;
    @Column(name = "Active")
    private Boolean active;
    @JoinColumn(name = "FK_IDCBT", referencedColumnName = "IDCBT")
    @ManyToOne(optional = false)
    private CustomerBussinesType FK_CBT;
    @JoinColumn(name = "FK_IDC", referencedColumnName = "IDC")
    @ManyToOne(optional = false)
    private Customer FK_Customer;

    public RelCBType() {
    }

    public RelCBType(Integer idrcbt) {
        this.idrcbt = idrcbt;
    }

    public Integer getIdrcbt() {
        return idrcbt;
    }

    public void setIdrcbt(Integer idrcbt) {
        this.idrcbt = idrcbt;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CustomerBussinesType getFK_CBT() {
        return FK_CBT;
    }

    public void setFK_CBT(CustomerBussinesType FK_CBT) {
        this.FK_CBT = FK_CBT;
    }

    public Customer getFK_Customer() {
        return FK_Customer;
    }

    public void setFK_Customer(Customer FK_Customer) {
        this.FK_Customer = FK_Customer;
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
        return getFK_Customer() + "->" + getFK_CBT().getCustomerActivity();
    }

}
