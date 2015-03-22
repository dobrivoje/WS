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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "Rel_FS_Document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelFSDocument.findAll", query = "SELECT r FROM RelFSDocument r"),
    @NamedQuery(name = "RelFSDocument.findByIdfsi", query = "SELECT r FROM RelFSDocument r WHERE r.idfsd = :idfsd"),
    
    @NamedQuery(name = "RelFSDocument.findByFS", query = "SELECT r FROM RelFSDocument r WHERE r.fkIdFs = :fkIdfs")
    
})
public class RelFSDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDFSD")
    private Long idfsd;
    @Column(name = "DocumentDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date documentDate;
    @JoinColumn(name = "FK_IDFS", referencedColumnName = "IDFS")
    @ManyToOne
    private Fuelstation fkIdFs;
    @JoinColumn(name = "FK_IDD", referencedColumnName = "IDD")
    @OneToOne
    private Document FK_DOCUMENT;

    public RelFSDocument() {
    }

    public RelFSDocument(Fuelstation fkIdfs, Document FK_DOCUMENT, Date documentDate) {
        this.documentDate = documentDate;
        this.fkIdFs = fkIdfs;
        this.FK_DOCUMENT = FK_DOCUMENT;
    }

    public Long getIdfsd() {
        return idfsd;
    }

    public void setIdfsd(Long idfsd) {
        this.idfsd = idfsd;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Fuelstation getFkIdFs() {
        return fkIdFs;
    }

    public void setFkIdFs(Fuelstation fkIdFs) {
        this.fkIdFs = fkIdFs;
    }

    public Document getFK_DOCUMENT() {
        return FK_DOCUMENT;
    }

    public void setFK_DOCUMENT(Document FK_DOCUMENT) {
        this.FK_DOCUMENT = FK_DOCUMENT;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfsd != null ? idfsd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelFSDocument)) {
            return false;
        }
        RelFSDocument other = (RelFSDocument) object;
        return !((this.idfsd == null && other.idfsd != null) || (this.idfsd != null && !this.idfsd.equals(other.idfsd)));
    }

    @Override
    public String toString() {
        return "db.RelFSDocument[" + idfsd + "]";
    }

}
