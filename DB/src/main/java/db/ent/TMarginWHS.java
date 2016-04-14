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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DPrtenjak
 */
@Entity
@Table(name = "T_MarginWHS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TMarginWHS.findAll", query = "SELECT t FROM TMarginWHS t"),
    @NamedQuery(name = "TMarginWHS.deleteAll", query = "DELETE FROM TMarginWHS t"),
    @NamedQuery(name = "TMarginWHS.deleteByID", query = "DELETE FROM TMarginWHS t WHERE t.id = :id"),
    @NamedQuery(name = "TMarginWHS.findByCustomerNo", query = "SELECT t FROM TMarginWHS t WHERE t.customerNo = :customerNo"),
    @NamedQuery(name = "TMarginWHS.findByCustomerName", query = "SELECT t FROM TMarginWHS t WHERE t.customerName = :customerName"),
    @NamedQuery(name = "TMarginWHS.findByItemNo", query = "SELECT t FROM TMarginWHS t WHERE t.itemNo = :itemNo"),
    @NamedQuery(name = "TMarginWHS.findByItemName", query = "SELECT t FROM TMarginWHS t WHERE t.itemName = :itemName"),
    @NamedQuery(name = "TMarginWHS.findByDivision", query = "SELECT t FROM TMarginWHS t WHERE t.division = :division"),
    @NamedQuery(name = "TMarginWHS.findByItemGroup", query = "SELECT t FROM TMarginWHS t WHERE t.itemGroup = :itemGroup"),
    @NamedQuery(name = "TMarginWHS.findByDocumentNo", query = "SELECT t FROM TMarginWHS t WHERE t.documentNo = :documentNo"),
    @NamedQuery(name = "TMarginWHS.findByPostingDate", query = "SELECT t FROM TMarginWHS t WHERE t.postingDate = :postingDate"),
    @NamedQuery(name = "TMarginWHS.findByLocationCode", query = "SELECT t FROM TMarginWHS t WHERE t.locationCode = :locationCode"),
    @NamedQuery(name = "TMarginWHS.findBySalesPersonCode", query = "SELECT t FROM TMarginWHS t WHERE t.salesPersonCode = :salesPersonCode"),
    @NamedQuery(name = "TMarginWHS.findByQuantity", query = "SELECT t FROM TMarginWHS t WHERE t.quantity = :quantity"),
    @NamedQuery(name = "TMarginWHS.findByUnitOfMeasureCode", query = "SELECT t FROM TMarginWHS t WHERE t.unitOfMeasureCode = :unitOfMeasureCode"),
    @NamedQuery(name = "TMarginWHS.findBySalesAmount", query = "SELECT t FROM TMarginWHS t WHERE t.salesAmount = :salesAmount"),
    @NamedQuery(name = "TMarginWHS.findByCostAmount", query = "SELECT t FROM TMarginWHS t WHERE t.costAmount = :costAmount"),
    @NamedQuery(name = "TMarginWHS.findByCoverage", query = "SELECT t FROM TMarginWHS t WHERE t.coverage = :coverage"),
    @NamedQuery(name = "TMarginWHS.findByMargin", query = "SELECT t FROM TMarginWHS t WHERE t.margin = :margin"),
    @NamedQuery(name = "TMarginWHS.findByShipmentmethodcode", query = "SELECT t FROM TMarginWHS t WHERE t.shipmentmethodcode = :shipmentmethodcode"),
    @NamedQuery(name = "TMarginWHS.findByCustomerSegment", query = "SELECT t FROM TMarginWHS t WHERE t.customerSegment = :customerSegment"),
    @NamedQuery(name = "TMarginWHS.findByDocumentType", query = "SELECT t FROM TMarginWHS t WHERE t.documentType = :documentType")})
public class TMarginWHS implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "CustomerNo")
    private String customerNo;
    @Size(max = 255)
    @Column(name = "CustomerName")
    private String customerName;
    @Size(max = 255)
    @Column(name = "ItemNo")
    private String itemNo;
    @Size(max = 255)
    @Column(name = "ItemName")
    private String itemName;
    @Size(max = 255)
    @Column(name = "Division")
    private String division;
    @Size(max = 255)
    @Column(name = "ItemGroup")
    private String itemGroup;
    @Size(max = 255)
    @Column(name = "DocumentNo")
    private String documentNo;
    @Column(name = "PostingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postingDate;
    @Size(max = 255)
    @Column(name = "LocationCode")
    private String locationCode;
    @Size(max = 255)
    @Column(name = "SalesPersonCode")
    private String salesPersonCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Quantity")
    private Double quantity;
    @Size(max = 255)
    @Column(name = "UnitOfMeasureCode")
    private String unitOfMeasureCode;
    @Column(name = "SalesAmount")
    private Double salesAmount;
    @Column(name = "CostAmount")
    private Double costAmount;
    @Column(name = "Coverage")
    private Double coverage;
    @Column(name = "Margin")
    private Double margin;
    @Size(max = 255)
    @Column(name = "ShipmentMethodCode")
    private String shipmentmethodcode;
    @Size(max = 255)
    @Column(name = "CustomerSegment")
    private String customerSegment;
    @Size(max = 255)
    @Column(name = "DocumentType")
    private String documentType;

    public TMarginWHS() {
    }

    public TMarginWHS(String customerNo, String customerName, String itemNo, String itemName, String division,
            String itemGroup, String documentNo, Date postingDate, String locationCode,
            String salesPersonCode, Double quantity, String unitOfMeasureCode, Double salesAmount,
            Double costAmount, Double coverage, Double margin, String shipmentmethodcode, String customerSegment, String documentType) {

        this.customerNo = customerNo;
        this.customerName = customerName;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.division = division;
        this.itemGroup = itemGroup;
        this.documentNo = documentNo;
        this.postingDate = postingDate;
        this.locationCode = locationCode;
        this.salesPersonCode = salesPersonCode;
        this.quantity = quantity;
        this.unitOfMeasureCode = unitOfMeasureCode;
        this.salesAmount = salesAmount;
        this.costAmount = costAmount;
        this.coverage = coverage;
        this.margin = margin;
        this.shipmentmethodcode = shipmentmethodcode;
        this.customerSegment = customerSegment;
        this.documentType = documentType;
    }

    public TMarginWHS(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getSalesPersonCode() {
        return salesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        this.salesPersonCode = salesPersonCode;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasureCode() {
        return unitOfMeasureCode;
    }

    public void setUnitOfMeasureCode(String unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Double getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }

    public Double getCoverage() {
        return coverage;
    }

    public void setCoverage(Double coverage) {
        this.coverage = coverage;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public String getShipmentmethodcode() {
        return shipmentmethodcode;
    }

    public void setShipmentmethodcode(String shipmentmethodcode) {
        this.shipmentmethodcode = shipmentmethodcode;
    }

    public String getCustomerSegment() {
        return customerSegment;
    }

    public void setCustomerSegment(String customerSegment) {
        this.customerSegment = customerSegment;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TMarginWHS)) {
            return false;
        }
        TMarginWHS other = (TMarginWHS) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return id + " | "
                + customerNo + " | "
                + customerName + " | "
                + itemNo + " | "
                + itemName + " | "
                + division + " | "
                + itemGroup + " | "
                + documentNo + " | "
                + postingDate + " | "
                + locationCode + " | "
                + salesPersonCode + " | "
                + quantity + " | "
                + unitOfMeasureCode + " | "
                + salesAmount + " | "
                + costAmount + " | "
                + coverage + " | "
                + shipmentmethodcode + " | "
                + customerSegment + " | "
                + documentType;
    }

}
