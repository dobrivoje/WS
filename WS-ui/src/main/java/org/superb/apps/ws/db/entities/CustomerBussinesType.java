/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.superb.apps.ws.db.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "CUSTOMER_BUSSINES_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerBussinesType.findAll", query = "SELECT c FROM CustomerBussinesType c"),
    @NamedQuery(name = "CustomerBussinesType.findByIdcbt", query = "SELECT c FROM CustomerBussinesType c WHERE c.idcbt = :idcbt"),
    @NamedQuery(name = "CustomerBussinesType.AllBussinesTypesForActivity",
            query = "SELECT c FROM CustomerBussinesType c WHERE c.customerActivity = :customerActivity")})
public class CustomerBussinesType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCBT")
    private Integer idcbt;
    @Column(name = "Customer_Activity")
    private String customerActivity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "FK_CBT")
    private List<RelCBType> relCBTypeList;

    public CustomerBussinesType() {
    }

    public CustomerBussinesType(Integer idcbt) {
        this.idcbt = idcbt;
    }

    public Integer getIdcbt() {
        return idcbt;
    }

    public void setIdcbt(Integer idcbt) {
        this.idcbt = idcbt;
    }

    public String getCustomerActivity() {
        return customerActivity;
    }

    public void setCustomerActivity(String customerActivity) {
        this.customerActivity = customerActivity;
    }

    @XmlTransient
    public List<RelCBType> getRelCBTypeList() {
        return relCBTypeList;
    }

    public List<Customer> getAllCustomersForBussinesType() {
        List<Customer> customersOfRelCBT = new ArrayList<>();

        if (getRelCBTypeList().isEmpty()) {
            customersOfRelCBT = null;
        } else {
            for (RelCBType r : getRelCBTypeList()) {
                customersOfRelCBT.add(r.getFK_Customer());
            }
        }

        return customersOfRelCBT;
    }

    public void setRelCBTypeList(List<RelCBType> relCBTypeList) {
        this.relCBTypeList = relCBTypeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcbt != null ? idcbt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerBussinesType)) {
            return false;
        }
        CustomerBussinesType other = (CustomerBussinesType) object;
        return !((this.idcbt == null && other.idcbt != null) || (this.idcbt != null && !this.idcbt.equals(other.idcbt)));
    }

    @Override
    public String toString() {
        // OBAVEZNO definisati ovako, da bi kasnije u Combobox-u
        // izlazio samo naziv aktivnosti !
        return getCustomerActivity();
    }

}