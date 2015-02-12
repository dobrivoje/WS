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
@Table(name = "BUSSINES_LINE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BussinesLine.findAll", query = "SELECT b FROM BussinesLine b"),
    @NamedQuery(name = "BussinesLine.findByIDBL", query = "SELECT b FROM BussinesLine b WHERE b.idbl = :idbl"),
    @NamedQuery(name = "BussinesLine.findByName", query = "SELECT b FROM BussinesLine b WHERE b.name LIKE :name")})
public class BussinesLine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDBL")
    private Long idbl;
    @Column(name = "Name")
    private String name;
    @OneToMany(mappedBy = "FK_BussinesLine")
    private List<Salesman> salesmanList;

    public BussinesLine() {
    }

    public BussinesLine(Long idbl) {
        this.idbl = idbl;
    }

    public Long getIdbl() {
        return idbl;
    }

    public void setIdbl(Long idbl) {
        this.idbl = idbl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Salesman> getSalesmanList() {
        return salesmanList;
    }

    public void setSalesmanList(List<Salesman> salesmanList) {
        this.salesmanList = salesmanList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbl != null ? idbl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BussinesLine)) {
            return false;
        }
        BussinesLine other = (BussinesLine) object;
        return !((this.idbl == null && other.idbl != null) || (this.idbl != null && !this.idbl.equals(other.idbl)));
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
