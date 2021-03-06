/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "Owner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Owner.findAll", query = "SELECT o FROM Owner o"),
    @NamedQuery(name = "Owner.findByIdo", query = "SELECT o FROM Owner o WHERE o.ido = :ido"),
    @NamedQuery(name = "Owner.findByCustomer", query = "SELECT o FROM Owner o WHERE o.fKIDCustomer = :customer"),

    @NamedQuery(name = "Owner.ByCustomerAndActive",
            query = "SELECT o FROM Owner o WHERE o.fKIDCustomer = :customer AND o.active = :active"),

    @NamedQuery(name = "Owner.updateAllFSActiveFalse",
            query = "UPDATE Owner o SET o.active = FALSE WHERE o.fkIdFs = :fs"),

    @NamedQuery(name = "Owner.findByFuelstation",
            query = "SELECT o FROM Owner o WHERE o.fkIdFs = :fuelstation AND o.active = TRUE"),

    @NamedQuery(name = "Owner.findByDateFrom", query = "SELECT o FROM Owner o WHERE o.dateFrom = :dateFrom"),
    @NamedQuery(name = "Owner.findByDateTo", query = "SELECT o FROM Owner o WHERE o.dateTo = :dateTo"),
    @NamedQuery(name = "Owner.findByActive", query = "SELECT o FROM Owner o WHERE o.active = :active")})
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDO")
    private Long ido;
    @Column(name = "DateFrom")
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    private Date dateFrom;
    @Column(name = "DateTo")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    @Column(name = "Active")
    private Boolean active;
    @JoinColumn(name = "FK_ID_Customer", referencedColumnName = "IDC")
    @ManyToOne(optional = false)
    private Customer fKIDCustomer;
    @JoinColumn(name = "FK_ID_FS", referencedColumnName = "IDFS")
    @ManyToOne(optional = false)
    private Fuelstation fkIdFs;
    @OneToMany(mappedBy = "fkIdo")
    private List<FsProp> fsPropList;

    public Owner() {
    }

    public Owner(Owner newOwner) {
        this();

        try {
            this.dateFrom = newOwner.getDateFrom();
        } catch (Exception ex) {

        }
        try {
            this.dateTo = newOwner.getDateTo();
        } catch (Exception ex) {
        }

        try {
            this.active = newOwner.getActive();
        } catch (Exception ex) {
        }

        try {
            this.fKIDCustomer = newOwner.getFKIDCustomer();
        } catch (Exception ex) {
        }

        try {
            this.fkIdFs = newOwner.getFkIdFs();
        } catch (Exception ex) {
        }

        try {
            this.fsPropList.addAll(newOwner.getFsPropList());
        } catch (Exception ex) {
        }
    }

    public Owner(Customer fKIDCustomer, Fuelstation fkIdFs, Date dateFrom, Date dateTo, Boolean active) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.active = active;
        this.fKIDCustomer = fKIDCustomer;
        this.fkIdFs = fkIdFs;
    }

    public Owner(Long ido) {
        this.ido = ido;
    }

    public Long getIdo() {
        return ido;
    }

    public void setIdo(Long ido) {
        this.ido = ido;
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

    public Customer getFKIDCustomer() {
        return fKIDCustomer;
    }

    public void setFKIDCustomer(Customer fKIDCustomer) {
        this.fKIDCustomer = fKIDCustomer;
    }

    public Fuelstation getFkIdFs() {
        return fkIdFs;
    }

    public void setFkIdFs(Fuelstation fkIdFs) {
        this.fkIdFs = fkIdFs;
    }

    @XmlTransient
    public List<FsProp> getFsPropList() {
        return fsPropList;
    }

    public void setFsPropList(List<FsProp> fsPropList) {
        this.fsPropList = fsPropList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ido != null ? ido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Owner)) {
            return false;
        }
        Owner other = (Owner) object;
        return !((this.ido == null && other.ido != null) || (this.ido != null && !this.ido.equals(other.ido)));
    }

    @Override
    public String toString() {
        return fkIdFs.getName() + " - "
                + fkIdFs.getFK_City().getName()
                + (active ? " (a)" : " (na!)");
    }

}
