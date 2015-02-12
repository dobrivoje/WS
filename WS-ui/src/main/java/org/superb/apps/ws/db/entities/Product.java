/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByIdp", query = "SELECT p FROM Product p WHERE p.idp = :idp"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name")})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDP")
    private Long idp;
    @Column(name = "Name")
    private String name;
    @OneToOne(mappedBy = "FK_Product")
    private RelFSPROPPRODUCT relFSPROPPRODUCT;

    public Product() {
    }

    public Product(Long idp) {
        this.idp = idp;
    }

    public Long getIdp() {
        return idp;
    }

    public void setIdp(Long idp) {
        this.idp = idp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelFSPROPPRODUCT getRelFSPROPPRODUCT() {
        return relFSPROPPRODUCT;
    }

    public void setRelFSPROPPRODUCT(RelFSPROPPRODUCT relFSPROPPRODUCT) {
        this.relFSPROPPRODUCT = relFSPROPPRODUCT;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        return !((this.idp == null && other.idp != null) || (this.idp != null && !this.idp.equals(other.idp)));
    }

    @Override
    public String toString() {
        return "Product[ " + idp + " ]";
    }
    
}
