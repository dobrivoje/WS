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

/**
 *
 * @author dprtenjak
 */
@Entity
@Table(name = "Rel_SALESMAN_CUST")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "RelSALESMANCUST.findByIdrblc", query = "SELECT r FROM RelSALESMANCUST r WHERE r.idrblc = :idrblc"),

            @NamedQuery(name = "RelSALESMANCUST.findByCust",
                    query = "SELECT r FROM RelSALESMANCUST r WHERE r.FK_IDC = :IDC"),

            @NamedQuery(name = "RelSALESMANCUST.findBySalesman",
                    query = "SELECT r FROM RelSALESMANCUST r WHERE r.FK_IDS = :IDS"),

            @NamedQuery(name = "RelSALESMANCUST.CustomersBySalesman",
                    query = "SELECT r.FK_IDC FROM RelSALESMANCUST r WHERE r.FK_IDS = :IDS"),

            @NamedQuery(name = "RelSALESMANCUST.RelSalesmanCustomer",
                    query = "SELECT r FROM RelSALESMANCUST r WHERE r.FK_IDC = :IDC AND r.FK_IDS = :IDS")
        })
public class RelSALESMANCUST implements Serializable {

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
    @ManyToOne(optional = false)
    private Customer FK_IDC;
    @JoinColumn(name = "FK_IDS", referencedColumnName = "IDS")
    @ManyToOne(optional = false)
    private Salesman FK_IDS;
    @OneToMany(mappedBy = "FK_IDRSMC")
    private List<CrmProcess> CRMProcessList;

    public RelSALESMANCUST() {
    }

    public RelSALESMANCUST(Customer c, Salesman s, Date dateFrom, Date dateTo, boolean active) {
        this.FK_IDC = c;
        this.FK_IDS = s;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.active = active;
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

    public Customer getFK_IDC() {
        return FK_IDC;
    }

    public void setFK_IDC(Customer FK_IDC) {
        this.FK_IDC = FK_IDC;
    }

    public Salesman getFK_IDS() {
        return FK_IDS;
    }

    public void setFK_IDS(Salesman FK_IDS) {
        this.FK_IDS = FK_IDS;
    }

    public List<CrmProcess> getCRMProcessList() {
        return CRMProcessList;
    }

    public void setCRMProcessList(List<CrmProcess> CRMProcessList) {
        this.CRMProcessList = CRMProcessList;
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
        if (!(object instanceof RelSALESMANCUST)) {
            return false;
        }
        RelSALESMANCUST other = (RelSALESMANCUST) object;
        return !((this.idrblc == null && other.idrblc != null) || (this.idrblc != null && !this.idrblc.equals(other.idrblc)));
    }

    @Override
    public String toString() {
        return "db.ent.RelSALESMANCUST[ idrblc=" + idrblc + " ]";
    }

}
