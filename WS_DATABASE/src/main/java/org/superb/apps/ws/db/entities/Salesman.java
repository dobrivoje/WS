/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.entities;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dprtenjak
 */
@Entity
@Table(name = "SALESMAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salesman.findAll", query = "SELECT s FROM Salesman s"),
    @NamedQuery(name = "Salesman.findByIds", query = "SELECT s FROM Salesman s WHERE s.ids = :ids"),
    @NamedQuery(name = "Salesman.findByName", query = "SELECT s FROM Salesman s WHERE s.name LIKE :name"),
    @NamedQuery(name = "Salesman.findBySurname", query = "SELECT s FROM Salesman s WHERE s.surname LIKE :surname"),
    @NamedQuery(name = "Salesman.findByPosition", query = "SELECT s FROM Salesman s WHERE s.position LIKE :position"),
    @NamedQuery(name = "Salesman.findByActive", query = "SELECT s FROM Salesman s WHERE s.active = :active"),
    @NamedQuery(name = "Salesman.findByDateFrom", query = "SELECT s FROM Salesman s WHERE s.dateFrom = :dateFrom"),
    @NamedQuery(name = "Salesman.findByDateTo", query = "SELECT s FROM Salesman s WHERE s.dateTo = :dateTo")})
public class Salesman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDS")
    private Integer ids;
    @Column(name = "Name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @Column(name = "Position")
    private String position;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "DateFrom")
    private String dateFrom;
    @Column(name = "DateTo")
    private String dateTo;
    @JoinColumn(name = "FK_IDBL", referencedColumnName = "IDBL")
    @ManyToOne
    private BussinesLine fkIdbl;
    @OneToMany(mappedBy = "FK_Salesman")
    private List<RelSALESMANIMAGE> SalesmanImageList;

    public Salesman() {
    }

    public Salesman(Integer ids) {
        this.ids = ids;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public BussinesLine getFkIdbl() {
        return fkIdbl;
    }

    public void setFkIdbl(BussinesLine fkIdbl) {
        this.fkIdbl = fkIdbl;
    }

    @XmlTransient
    public List<RelSALESMANIMAGE> getSalesmanImageList() {
        return SalesmanImageList;
    }

    public void setSalesmanImageList(List<RelSALESMANIMAGE> SalesmanImageList) {
        this.SalesmanImageList = SalesmanImageList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ids != null ? ids.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salesman)) {
            return false;
        }
        Salesman other = (Salesman) object;
        return !((this.ids == null && other.ids != null) || (this.ids != null && !this.ids.equals(other.ids)));
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname() + " " + getPosition();
    }

}
