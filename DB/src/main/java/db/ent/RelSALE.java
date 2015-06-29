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
@Table(name = "Rel_SALE")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "RelSALE.findAll", query = "SELECT r FROM RelSALE r"),
            @NamedQuery(name = "RelSALE.findByIdrs", query = "SELECT r FROM RelSALE r WHERE r.idrs = :idrs"),
            @NamedQuery(name = "RelSALE.findBySellDate", query = "SELECT r FROM RelSALE r WHERE r.sellDate = :sellDate"),

            @NamedQuery(name = "RelSALE.ForPeriod",
                    query = "SELECT r FROM RelSALE r WHERE r.sellDate BETWEEN :SellDateFrom AND :SellDateTo"),

            @NamedQuery(name = "RelSALE.findByAmmount", query = "SELECT r FROM RelSALE r WHERE r.ammount = :ammount"),
            @NamedQuery(name = "RelSALE.findByPaymentMethod", query = "SELECT r FROM RelSALE r WHERE r.paymentMethod = :paymentMethod")
        }
)
public class RelSALE implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRS")
    private Long idrs;
    @Basic(optional = false)
    @Column(name = "SellDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sellDate;
    @Basic(optional = false)
    @Column(name = "Ammount")
    private double ammount;
    @Basic(optional = false)
    @Column(name = "PaymentMethod")
    private String paymentMethod;
    @JoinColumn(name = "FK_IDCA", referencedColumnName = "IDCA")
    @ManyToOne(optional = false)
    private CrmCase FK_IDCA;
    @JoinColumn(name = "FK_IDP", referencedColumnName = "IDP")
    @ManyToOne(optional = false)
    private Product FK_IDP;

    public RelSALE() {
        this(new Date(), 0, "", null, null);
    }

    public RelSALE(Date sellDate, double ammount, String paymentMethod, CrmCase crmCase, Product product) {
        this.sellDate = sellDate;
        this.ammount = ammount > 0 ? ammount : 0;
        this.paymentMethod = paymentMethod;
        this.FK_IDCA = crmCase;
        this.FK_IDP = product;
    }

    public Long getIdrs() {
        return idrs;
    }

    public void setIdrs(Long idrs) {
        this.idrs = idrs;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CrmCase getFK_IDCA() {
        return FK_IDCA;
    }

    public void setFK_IDCA(CrmCase FK_IDCA) {
        this.FK_IDCA = FK_IDCA;
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
        hash += (idrs != null ? idrs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelSALE)) {
            return false;
        }
        RelSALE other = (RelSALE) object;
        return !((this.idrs == null && other.idrs != null) || (this.idrs != null && !this.idrs.equals(other.idrs)));
    }

    @Override
    public String toString() {
        return "[idrs=" + idrs + " ]";
    }

}
