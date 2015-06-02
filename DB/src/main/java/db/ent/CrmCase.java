/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "CRM_CASE")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "CrmCase.findAll", query = "SELECT c FROM CrmCase c"),

            @NamedQuery(name = "CrmCase.AllActiveCases", query = "SELECT c FROM CrmCase c WHERE c.finished = :Finished"),

            @NamedQuery(name = "CrmCase.findByIdca", query = "SELECT c FROM CrmCase c WHERE c.idca = :idca"),

            @NamedQuery(name = "CrmCase.findLastActiveCRM_Case",
                    query = "SELECT c FROM CrmCase c WHERE c.FK_IDRSC.FK_IDC = :IDC AND c.FK_IDRSC.FK_IDS = :IDS AND c.finished = TRUE ORDER BY c.idca DESC"),

            @NamedQuery(name = "CrmCase.findByCustomer",
                    query = "SELECT c FROM CrmCase c WHERE c.FK_IDRSC.FK_IDC = :IDC AND c.finished = :Finished ORDER BY c.idca DESC"),

            @NamedQuery(name = "CrmCase.findBySalesman",
                    query = "SELECT c FROM CrmCase c WHERE c.FK_IDRSC.FK_IDS = :IDS AND c.finished = :Finished ORDER BY c.idca DESC"),

            @NamedQuery(name = "CrmCase.SalesmanCustomers",
                    query = "SELECT c FROM CrmCase c WHERE c.FK_IDRSC.FK_IDS = :IDS AND c.FK_IDRSC.FK_IDC =:IDC AND c.finished = :Finished ORDER BY c.idca DESC"),

            @NamedQuery(name = "CrmCase.CustomerActiveCases",
                    query = "SELECT c.FK_IDRSC.FK_IDC FROM CrmCase c WHERE c.finished = :Finished GROUP BY C.FK_IDRSC.FK_IDC"),

            @NamedQuery(name = "CrmCase.findByStartDate", query = "SELECT c FROM CrmCase c WHERE c.startDate = :startDate"),
            @NamedQuery(name = "CrmCase.findByDescription", query = "SELECT c FROM CrmCase c WHERE c.description = :description")
        }
)

public class CrmCase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCA")
    private Long idca;
    @Basic(optional = false)
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "EndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "Description")
    private String description;
    @Column(name = "Finished")
    private boolean finished;
    @JoinColumn(name = "FK_IDRBLC", referencedColumnName = "IDRBLC")
    @ManyToOne
    private RelSALESMANCUST FK_IDRSC;
    @OneToMany(mappedBy = "FK_IDCA")
    private List<CrmProcess> crmProcessList;

    public CrmCase() {
    }

    public CrmCase(Date startDate, String description, RelSALESMANCUST FK_IDRSC) {
        this.startDate = startDate;
        this.description = description;
        this.FK_IDRSC = FK_IDRSC;
        this.finished = false;
    }

    public Long getIdca() {
        return idca;
    }

    public void setIdca(Long idca) {
        this.idca = idca;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public RelSALESMANCUST getFK_IDRSC() {
        return FK_IDRSC;
    }

    public void setFK_IDRSC(RelSALESMANCUST FK_IDRSC) {
        this.FK_IDRSC = FK_IDRSC;
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
        hash += (idca != null ? idca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CrmCase)) {
            return false;
        }
        CrmCase other = (CrmCase) object;
        return !((this.idca == null && other.idca != null) || (this.idca != null && !this.idca.equals(other.idca)));
    }

    @Override
    public String toString() {
        /*
         return (description == null ? "CRM Case" : getDescription()) + ", "
         + new SimpleDateFormat("dd.MM.yyyy").format(getStartDate())
         + (isFinished() ? " - Finished." : "");
         */
        return getFK_IDRSC().getFK_IDC().getName() + ", "
                + new SimpleDateFormat("dd.MM.yyyy").format(getStartDate());
    }

}
