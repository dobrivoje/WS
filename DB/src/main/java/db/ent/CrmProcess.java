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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dprtenjak
 */
@Entity
@Table(name = "CRM_PROCESS")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "CrmProcess.findAll", query = "SELECT c FROM CrmProcess c"),

            @NamedQuery(name = "CrmProcess.findByCustomer",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDRSMC.FK_IDC = :IDC"),

            @NamedQuery(name = "CrmProcess.CustomerProcessesByDate",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDRSMC.FK_IDC = :IDC AND c.actionDate BETWEEN :dateFrom AND :dateTo"),

            @NamedQuery(name = "CrmProcess.SalesmanProcessesByDate",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDRSMC.FK_IDS = :IDS AND c.actionDate BETWEEN :dateFrom AND :dateTo"),

            @NamedQuery(name = "CrmProcess.findByCRMStatus",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCS = :IDCS"),

            @NamedQuery(name = "CrmProcess.findByDate", query = "SELECT c FROM CrmProcess c WHERE c.actionDate = :actionDate")
        })
public class CrmProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDP")
    private Long idp;
    @Column(name = "ActionDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date actionDate;
    @Lob
    @Column(name = "Comment")
    private String comment;
    @JoinColumn(name = "FK_IDCS", referencedColumnName = "IDCS")
    @ManyToOne(optional = false)
    private CrmStatus FK_IDCS;
    @JoinColumn(name = "FK_IDRBLC", referencedColumnName = "IDRBLC")
    @ManyToOne(optional = false)
    private RelSALESMANCUST FK_IDRSMC;

    public CrmProcess() {
    }

    public CrmProcess(RelSALESMANCUST RelSalesmanCustomer, CrmStatus crmStatus, String comment, Date actionDate) {
        this.FK_IDRSMC = RelSalesmanCustomer;
        this.FK_IDCS = crmStatus;
        this.comment = comment;
        this.actionDate = actionDate;
    }

    public Long getIdp() {
        return idp;
    }

    public void setIdp(Long idp) {
        this.idp = idp;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CrmStatus getFK_IDCS() {
        return FK_IDCS;
    }

    public void setFK_IDCS(CrmStatus FK_IDCS) {
        this.FK_IDCS = FK_IDCS;
    }

    public RelSALESMANCUST getFK_IDRSMC() {
        return FK_IDRSMC;
    }

    public void setFK_IDRSMC(RelSALESMANCUST FK_IDRSMC) {
        this.FK_IDRSMC = FK_IDRSMC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idp != null ? idp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CrmProcess)) {
            return false;
        }
        CrmProcess other = (CrmProcess) object;
        return !((this.idp == null && other.idp != null) || (this.idp != null && !this.idp.equals(other.idp)));
    }

    @Override
    public String toString() {
        return getFK_IDCS().getStatusName();
    }
}
