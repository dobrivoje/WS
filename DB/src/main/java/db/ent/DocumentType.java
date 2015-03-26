/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.ent;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dprtenjak
 */
@Entity
@Table(name = "DocumentType")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "DocumentType.findAll", query = "SELECT d FROM DocumentType d"),
            @NamedQuery(name = "DocumentType.findByIddt", query = "SELECT d FROM DocumentType d WHERE d.iddt = :iddt"),
            @NamedQuery(name = "DocumentType.findByDocType",
                    query = "SELECT d FROM DocumentType d WHERE d.docType LIKE :docType")
        }
)
public class DocumentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDDT")
    private Long iddt;
    @Column(name = "DocType")
    private String docType;

    public DocumentType() {
    }

    public DocumentType(Long iddt) {
        this.iddt = iddt;
    }

    public Long getIddt() {
        return iddt;
    }

    public void setIddt(Long iddt) {
        this.iddt = iddt;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddt != null ? iddt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DocumentType)) {
            return false;
        }
        DocumentType other = (DocumentType) object;
        return !((this.iddt == null && other.iddt != null) || (this.iddt != null && !this.iddt.equals(other.iddt)));
    }

    @Override
    public String toString() {
        return "DocumentType[" + iddt + "]";
    }

}
