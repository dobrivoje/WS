/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "CRM_STATUS")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "CrmStatus.findAll", query = "SELECT c FROM CrmStatus c"),
            @NamedQuery(name = "CrmStatus.findByIdcs", query = "SELECT c FROM CrmStatus c WHERE c.idcs = :idcs"),
            @NamedQuery(name = "CrmStatus.findByStatusName", query = "SELECT c FROM CrmStatus c WHERE c.statusName = :statusName"),
            @NamedQuery(name = "CrmStatus.findByOverdueDays", query = "SELECT c FROM CrmStatus c WHERE c.daysForOverdue = :DFO")
        })
public class CrmStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCS")
    private Long idcs;
    @Column(name = "StatusName")
    private String statusName;
    @Column(name = "DaysForOverdue")
    private String daysForOverdue;
    @OneToMany(mappedBy = "FK_IDCS")
    private List<CrmProcess> crmProcessList;

    public CrmStatus() {
    }

    /**
     * <p>
     * Za Status gde važi daysForOverdue==null || daysForOverdue < 1 </p>
     * <p>
     * se ne računaju dani sa zakašnjenjem</p>
     *
     * @param statusName
     * @param daysForOverdue
     */
    public CrmStatus(String statusName, String daysForOverdue) {
        this.statusName = statusName;
        this.daysForOverdue = daysForOverdue;
    }

    public Long getIdcs() {
        return idcs;
    }

    public void setIdcs(Long idcs) {
        this.idcs = idcs;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDaysForOverdue() {
        return daysForOverdue;
    }

    public void setDaysForOverdue(String daysForOverdue) {
        this.daysForOverdue = daysForOverdue;
    }

    @XmlTransient
    public List<CrmProcess> getCrmProcessList() {
        return crmProcessList;
    }

    public void setCrmProcessList(List<CrmProcess> crmProcessList) {
        this.crmProcessList = crmProcessList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcs != null ? idcs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CrmStatus)) {
            return false;
        }
        CrmStatus other = (CrmStatus) object;
        return !((this.idcs == null && other.idcs != null) || (this.idcs != null && !this.idcs.equals(other.idcs)));
    }

    @Override
    public String toString() {
        return getStatusName();
    }

}
