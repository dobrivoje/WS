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
 * @author dprtenjak
 */
@Entity
@Table(name = "LOG")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l"),
            @NamedQuery(name = "Log.findByIdl", query = "SELECT l FROM Log l WHERE l.idl = :idl"),
            @NamedQuery(name = "Log.findByLogDate", query = "SELECT l FROM Log l WHERE l.logDate = :logDate"),
            @NamedQuery(name = "Log.findByAction", query = "SELECT l FROM Log l WHERE l.actionCode = :actionCode"),

            @NamedQuery(name = "Log.findByInfSysUser", query = "SELECT l FROM Log l WHERE l.FK_IDUN = :IDUN"),

            @NamedQuery(name = "Log.findByInfSysUserAndDate",
                    query = "SELECT l FROM Log l WHERE l.FK_IDUN = :IDUN AND l.logDate BETWEEN :dateFrom AND :dateTo"),

            @NamedQuery(name = "Log.findByDescription", query = "SELECT l FROM Log l WHERE l.description = :description")
        }
)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDL")
    private Long idl;
    @Column(name = "LogDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    @Column(name = "ActionCode")
    private String actionCode;
    @Column(name = "Description")
    private String description;
    @JoinColumn(name = "FK_IDUN", referencedColumnName = "IDUN")
    @ManyToOne
    private InfSysUser FK_IDUN;

    public Log() {
    }

    public Log(Date logDate, String actionCode, String description, InfSysUser infSysUser) {
        this.logDate = logDate;
        this.actionCode = actionCode;
        this.description = description;
        this.FK_IDUN = infSysUser;
    }

    public Long getIdl() {
        return idl;
    }

    public void setIdl(Long idl) {
        this.idl = idl;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getAction() {
        return actionCode;
    }

    public void setAction(String action) {
        this.actionCode = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InfSysUser getFK_IDUN() {
        return FK_IDUN;
    }

    public void setFK_IDUN(InfSysUser FK_IDUN) {
        this.FK_IDUN = FK_IDUN;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idl != null ? idl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        return !((this.idl == null && other.idl != null) || (this.idl != null && !this.idl.equals(other.idl)));
    }

    @Override
    public String toString() {
        return (getLogDate() != null ? getLogDate() : "/") + ", "
                + (getAction() != null ? getAction() : "/") + ", "
                + (getDescription() != null ? getDescription() : "/") + ", "
                + (getFK_IDUN() != null ? getFK_IDUN().getUserName() : "/");
    }

}
