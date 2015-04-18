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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "Rel_FS_Document")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "RelFSDocument.findAll", query = "SELECT r FROM RelFSDocument r"),
            @NamedQuery(name = "RelFSDocument.findByIdfsi", query = "SELECT r FROM RelFSDocument r WHERE r.idfsd = :idfsd"),

            @NamedQuery(name = "RelFSDocument.getAllFSDocuments", 
                    query = "SELECT r.FK_DOCUMENT FROM RelFSDocument r WHERE r.fkIdFs = :IDFS"),

            @NamedQuery(name = "RelFSDocument.getFSActiveDocuments",
                    query = "SELECT r.FK_DOCUMENT FROM RelFSDocument r WHERE r.fkIdFs = :IDFS AND r.defaultDocument = :defaultDocument"),

            @NamedQuery(name = "RelFSDocument.getHighPriorityFSImage",
                    query = "SELECT r.FK_DOCUMENT FROM RelFSDocument r WHERE r.fkIdFs = :IDFS ORDER BY R.priority DESC"),

            @NamedQuery(name = "RelFSDocument.getFSDefaultImage",
                    query = "SELECT r.FK_DOCUMENT FROM RelFSDocument r WHERE r.fkIdFs = :IDFS AND r.defaultDocument = :DefaultDocument"),

            @NamedQuery(name = "RelFSDocument.findByFS",
                    query = "SELECT r FROM RelFSDocument r WHERE r.fkIdFs = :fkIdfs"),
                
            @NamedQuery(name = "RelFSDocument.Reset",
                    query = "UPDATE RelFSDocument r SET r.defaultDocument = FALSE, r.priority = 0 WHERE r.fkIdFs = :fkIdfs AND r.FK_DOCUMENT.docType='img'"),
            
            @NamedQuery(name = "RelFSDocument.SetDefaultImage",
                    query = "UPDATE RelFSDocument r SET r.defaultDocument = TRUE, r.priority = 10 WHERE r.fkIdFs = :FK_IDFS AND r.FK_DOCUMENT = :FK_IDD")

        })
public class RelFSDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDFSD")
    private Long idfsd;
    @Column(name = "DocumentDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date documentDate;
    @Column(name = "DefaultDocument")
    private Boolean defaultDocument;
    @Column(name = "Priority")
    private int priority;
    @JoinColumn(name = "FK_IDFS", referencedColumnName = "IDFS")
    @ManyToOne
    private Fuelstation fkIdFs;
    @JoinColumn(name = "FK_IDD", referencedColumnName = "IDD")
    @ManyToOne
    private Document FK_DOCUMENT;

    public RelFSDocument() {
    }

    public RelFSDocument(Fuelstation fkIdfs, Document FK_DOCUMENT, Date documentDate, boolean defaultDocument, int priority) {
        this.documentDate = documentDate;
        this.fkIdFs = fkIdfs;
        this.FK_DOCUMENT = FK_DOCUMENT;
        this.defaultDocument = defaultDocument;
        this.priority = priority;
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

    public Boolean getDefaultDocument() {
        return defaultDocument;
    }

    public void setDefaultDocument(Boolean defaultDocument) {
        this.defaultDocument = defaultDocument;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
