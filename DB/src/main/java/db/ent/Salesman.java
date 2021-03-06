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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SALESMAN")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Salesman.findAll", query = "SELECT s FROM Salesman s"),

            @NamedQuery(name = "Salesman.SalesmenByBL",
                    query = "SELECT s FROM Salesman s WHERE s.fkIdbl = :FK_IDBL"),

            @NamedQuery(name = "Salesman.findByIds", query = "SELECT s FROM Salesman s WHERE s.ids = :ids"),
            @NamedQuery(name = "Salesman.findByName", query = "SELECT s FROM Salesman s WHERE s.name = :name"),
            @NamedQuery(name = "Salesman.findBySurname", query = "SELECT s FROM Salesman s WHERE s.surname = :surname"),
            @NamedQuery(name = "Salesman.findByPosition", query = "SELECT s FROM Salesman s WHERE s.position = :position"),
            @NamedQuery(name = "Salesman.findByActive", query = "SELECT s FROM Salesman s WHERE s.active = :active"),}
)
public class Salesman implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDS")
    private Long ids;
    @Column(name = "Name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @Column(name = "Position")
    private String position;
    @Column(name = "Active")
    private Boolean active;
    @Column(name = "DateFrom")
    private String dateFrom;
    @Column(name = "DateTo")
    private String dateTo;
    @OneToMany(mappedBy = "fkSalesman")
    private List<RelSALESMANIMAGE> relSALESMANIMAGEList;
    @JoinColumn(name = "FK_IDBL", referencedColumnName = "IDBL")
    @ManyToOne
    private BussinesLine fkIdbl;
    @OneToMany(mappedBy = "FK_IDS")
    private List<RelSALESMANCUST> relSALESMEANCUSTList;
    @OneToMany(mappedBy = "FK_IDS")
    private List<RelUserSalesman> relUserSalesmanList;

    public Salesman() {
        this("", "", "", false, null, null, new BussinesLine());
    }

    public Salesman(Salesman s) {
        this(s.name, s.surname, s.position, s.active, s.dateFrom, s.dateTo, s.fkIdbl);
    }

    public Salesman(String name, String surname, String position, Boolean active, String dateFrom, String dateTo, BussinesLine bussinesLine) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.active = active;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.fkIdbl = bussinesLine;
    }

    public Long getIds() {
        return ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @XmlTransient
    public List<RelSALESMANIMAGE> getRelSALESMANIMAGEList() {
        return relSALESMANIMAGEList;
    }

    public void setRelSALESMANIMAGEList(List<RelSALESMANIMAGE> relSALESMANIMAGEList) {
        this.relSALESMANIMAGEList = relSALESMANIMAGEList;
    }

    public BussinesLine getFkIdbl() {
        return fkIdbl;
    }

    public void setFkIdbl(BussinesLine fkIdbl) {
        this.fkIdbl = fkIdbl;
    }

    @XmlTransient
    public List<RelSALESMANCUST> getRelSALESMEANCUSTList() {
        return relSALESMEANCUSTList;
    }

    public void setRelSALESMEANCUSTList(List<RelSALESMANCUST> relSALESMEANCUSTList) {
        this.relSALESMEANCUSTList = relSALESMEANCUSTList;
    }

    @XmlTransient
    public List<RelUserSalesman> getRelUserSalesmanList() {
        return relUserSalesmanList;
    }

    public void setRelUserSalesmanList(List<RelUserSalesman> relUserSalesmanList) {
        this.relUserSalesmanList = relUserSalesmanList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ids != null ? ids.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Salesman)) {
            return false;
        }
        Salesman other = (Salesman) object;
        return !((this.ids == null && other.ids != null) || (this.ids != null && !this.ids.equals(other.ids)));
    }

    @Override
    public String toString() {
        String s = "";

        try {
            s += getName();
        } catch (Exception e) {
        }

        try {
            s += " " + getSurname();
        } catch (Exception e) {
        }

        return s;
    }
}
