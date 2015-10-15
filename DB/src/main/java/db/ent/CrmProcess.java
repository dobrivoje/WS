/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "CRM_PROCESS")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "CrmProcess.findAll", query = "SELECT c FROM CrmProcess c"),

            @NamedQuery(name = "CrmProcess.findByCustomer",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCA.FK_IDRSC.FK_IDC = :IDC"),

            @NamedQuery(name = "CrmProcess.CustomerProcessesByDate",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCA.FK_IDRSC.FK_IDC = :IDC AND c.FK_IDCA.finished = :finished AND c.actionDate BETWEEN :dateFrom AND :dateTo"),

            @NamedQuery(name = "CrmProcess.SalesmanProcesses",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCA.FK_IDRSC.FK_IDS = :IDS AND c.FK_IDCA.finished = :finished ORDER BY c.idp ASC"),

            @NamedQuery(name = "CrmProcess.SalesmanProcessesByDate",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCA.FK_IDRSC.FK_IDS = :IDS AND c.FK_IDCA.finished = :finished AND c.actionDate BETWEEN :dateFrom AND :dateTo ORDER BY c.idp ASC"),

            @NamedQuery(name = "CrmProcess.findByCRMStatus",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCS.idcs = :IDCS"),

            @NamedQuery(name = "CrmProcess.findByCRMCase",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCA = :FK_IDCA AND c.FK_IDCA.finished = :finished"),

            @NamedQuery(name = "CrmProcess.ByCRMCase",
                    query = "SELECT c FROM CrmProcess c WHERE c.FK_IDCA = :FK_IDCA"),

            @NamedQuery(name = "CrmProcess.findByDate", query = "SELECT c FROM CrmProcess c WHERE c.actionDate = :actionDate"),

            @NamedQuery(name = "CrmProcess.CRMCaseStatuses",
                    query = "SELECT DISTINCT c.FK_IDCS FROM CrmProcess c WHERE c.FK_IDCA = :CRMCASE")

        }
)

public class CrmProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDP")
    private Long idp;
    @Basic(optional = false)
    @Column(name = "ActionDate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date actionDate;
    @Column(name = "Comment")
    private String comment;
    @JoinColumn(name = "FK_IDCA", referencedColumnName = "IDCA")
    @ManyToOne(optional = false)
    private CrmCase FK_IDCA;
    @JoinColumn(name = "FK_IDCS", referencedColumnName = "IDCS")
    @ManyToOne(optional = false)
    private CrmStatus FK_IDCS;

    public CrmProcess() {
    }

    public CrmProcess(CrmCase crmCase, CrmStatus crmStatus, String comment, Date actionDate) {
        this.actionDate = actionDate;
        this.comment = comment;
        this.FK_IDCA = crmCase;
        this.FK_IDCS = crmStatus;
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

    public CrmCase getFK_IDCA() {
        return FK_IDCA;
    }

    public void setFK_IDCA(CrmCase FK_IDCA) {
        this.FK_IDCA = FK_IDCA;
    }

    public CrmStatus getFK_IDCS() {
        return FK_IDCS;
    }

    public void setFK_IDCS(CrmStatus FK_IDCS) {
        this.FK_IDCS = FK_IDCS;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idp != null ? idp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CrmProcess)) {
            return false;
        }
        CrmProcess other = (CrmProcess) object;
        return !((this.idp == null && other.idp != null) || (this.idp != null && !this.idp.equals(other.idp)));
    }

    @Override
    public String toString() {
        try {
            return getFK_IDCS().getStatusName()
                    + ", "
                    + new SimpleDateFormat("dd.MM.yyyy").format(getActionDate());
        } catch (Exception e) {
            return "";
        }
    }

}
