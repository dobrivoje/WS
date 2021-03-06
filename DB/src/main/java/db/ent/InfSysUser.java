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
 * @author root
 */
@Entity
@Table(name = "INFSYSUSER")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "InfSysUser.findAll", query = "SELECT u FROM InfSysUser u"),
            @NamedQuery(name = "InfSysUser.findByIDUN", query = "SELECT u FROM InfSysUser u WHERE u.idun = :idun"),

            @NamedQuery(name = "InfSysUser.findByShiroPrincipal",
                    query = "SELECT u FROM InfSysUser u WHERE u.userName = :shiroUserPrincipal"),

            @NamedQuery(name = "InfSysUser.TopManagers",
                    query = "SELECT u FROM InfSysUser u WHERE u.topManager = :topManager"),

            @NamedQuery(name = "InfSysUser.SectorManagers",
                    query = "SELECT u FROM InfSysUser u WHERE u.sectorManager = :sectorManager"),

            @NamedQuery(name = "InfSysUser.AdminUsers",
                    query = "SELECT u FROM InfSysUser u WHERE u.admin = :admin"),

            @NamedQuery(name = "InfSysUser.findByActive", query = "SELECT u FROM InfSysUser u WHERE u.active = :active")
        }
)
public class InfSysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDUN")
    private Long idun;
    @Basic(optional = false)
    @Column(name = "UserName")
    private String userName;
    @Basic(optional = false)
    @Column(name = "Active")
    private Boolean active;
    @Basic(optional = false)
    @Column(name = "TopManager")
    private Boolean topManager;
    @Basic(optional = false)
    @Column(name = "SectorManager")
    private Boolean sectorManager;
    @Basic(optional = false)
    @Column(name = "Admin")
    private Boolean admin;
    @OneToMany(mappedBy = "FK_IDUN")
    private List<RelUserSalesman> RelUserSalesmanList;
    @OneToMany(mappedBy = "FK_IDUN")
    private List<Log> logList;

    public InfSysUser() {
    }

    public InfSysUser(String userName, Boolean active) {
        this.userName = userName;
        this.active = active;
    }

    public InfSysUser(String userName, Boolean active, boolean topManager, boolean sectorManager, boolean admin) {
        this(userName, active);

        this.topManager = topManager;
        this.sectorManager = sectorManager;
        this.admin = admin;
    }

    public Long getIdun() {
        return idun;
    }

    public void setIdun(Long idun) {
        this.idun = idun;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getTopManager() {
        return topManager;
    }

    public void setTopManager(Boolean topManager) {
        this.topManager = topManager;
    }

    public Boolean getSectorManager() {
        return sectorManager;
    }

    public void setSectorManager(Boolean sectorManager) {
        this.sectorManager = sectorManager;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @XmlTransient
    public List<RelUserSalesman> getRelUserSalesmanList() {
        return RelUserSalesmanList;
    }

    public void setRelUserSalesmanList(List<RelUserSalesman> relUserSalesmanList) {
        this.RelUserSalesmanList = relUserSalesmanList;
    }

    @XmlTransient
    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idun != null ? idun.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InfSysUser)) {
            return false;
        }
        InfSysUser other = (InfSysUser) object;
        return !((this.idun == null && other.idun != null) || (this.idun != null && !this.idun.equals(other.idun)));
    }

    @Override
    public String toString() {
        return userName != null ? userName : "n/a";
    }

}
