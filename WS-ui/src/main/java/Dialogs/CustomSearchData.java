package Dialogs;

import db.ent.Product;
import db.ent.Salesman;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class CustomSearchData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDCSD")
    private Long idca;
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "EndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "Salesman")
    private Salesman salesman;
    @Column(name = "Product")
    private Product product;
    @Column(name = "SaleAgreeded")
    private Boolean saleAgreeded;

    public CustomSearchData() {
        this(null, new Date(), new Salesman(), new Product(), false);
    }

    public CustomSearchData(Date startDate, Date endDate, Salesman salesman, Product product, Boolean saleAgreeded) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.salesman = salesman;
        this.product = product;
        this.saleAgreeded = saleAgreeded;
    }

    public Long getIdca() {
        return idca;
    }

    public void setIdca(Long idca) {
        this.idca = idca;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getSaleAgreeded() {
        return saleAgreeded == null ? false : saleAgreeded;
    }

    public void setSaleAgreeded(Boolean saleAgreeded) {
        this.saleAgreeded = saleAgreeded;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idca != null ? idca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CustomSearchData)) {
            return false;
        }
        CustomSearchData other = (CustomSearchData) object;
        return !((this.idca == null && other.idca != null) || (this.idca != null && !this.idca.equals(other.idca)));
    }

    @Override
    public String toString() {
        return "[" + startDate != null ? startDate + "], " : "]"
                + "[" + endDate != null ? endDate + "], " : "]"
                        + "[" + salesman != null ? salesman.toString() + "], " : "]"
                                + "[" + product != null ? product.toString() + "], " : "]"
                                        + "[" + saleAgreeded != null ? saleAgreeded.toString() + "], " : "]";
    }

}
