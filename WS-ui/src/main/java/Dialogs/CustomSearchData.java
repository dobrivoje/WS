package Dialogs;

import db.ent.Customer;
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
    @Column(name = "Customer")
    private Customer customer;
    @Column(name = "Product")
    private Product product;
    @Column(name = "Amount")
    private Integer amount;
    @Column(name = "CaseFinished")
    private Boolean caseFinished;
    @Column(name = "SaleAgreeded")
    private Boolean saleAgreeded;

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public CustomSearchData() {
        this(null, new Date(), new Salesman(), new Customer(),
                new Product(), 0, false, false);
    }

    public CustomSearchData(CustomSearchData newCustomSearchData) {
        this(newCustomSearchData.getStartDate(),
                newCustomSearchData.getEndDate(),
                newCustomSearchData.getSalesman(),
                newCustomSearchData.getCustomer(),
                newCustomSearchData.getProduct(),
                newCustomSearchData.getAmount(),
                newCustomSearchData.getCaseFinished(),
                newCustomSearchData.getSaleAgreeded()
        );
    }

    public CustomSearchData(Date startDate, Date endDate, Salesman salesman, Customer customer,
            Product product, int amount, boolean caseFinished, boolean saleAgreeded) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.salesman = salesman;
        this.customer = customer;
        this.product = product;
        this.amount = amount;
        this.caseFinished = caseFinished;
        this.saleAgreeded = saleAgreeded;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getAmount() {
        return amount == null ? 0 : amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getCaseFinished() {
        return caseFinished;
    }

    public void setCaseFinished(Boolean caseFinished) {
        this.caseFinished = caseFinished;
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
    //</editor-fold>

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

    //<editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        String s = "";

        try {
            if (getStartDate() == null) {
                s += "[] - ";
            } else {
                s += "[" + getStartDate() + "] - ";
            }
        } catch (Exception e) {
        }

        try {
            if (getEndDate() == null) {
                s += "[]";
            } else {
                s += "[" + getEndDate() + "], ";
            }
        } catch (Exception e) {
        }

        try {
            if (!getSalesman().toString().trim().isEmpty()) {
                s += "[" + getSalesman().toString() + "], ";
            } else {
                s += "[no salesman], ";
            }
        } catch (Exception e) {
            s += "[no salesman], ";
        }

        try {
            if (!getCustomer().toString().isEmpty()) {
                s += "[" + getCustomer().toString() + "], ";
            } else {
                s += "[no customer], ";
            }
        } catch (Exception e) {
            s += "[no customer], ";
        }

        try {
            if (!getProduct().toString().isEmpty()) {
                s += "[" + getProduct().toString() + "], ";
            } else {
                s += "[no product], ";
            }
        } catch (Exception e) {
            s += "[no product], ";
        }
        try {
            s += "[ammount: " + getAmount().toString() + "], ";
        } catch (Exception e) {
            s += "[no amount], ";
        }

        try {
            s += "[finished ? : " + getCaseFinished() + "], ";
        } catch (Exception e) {
            s += "[finished ? : false ], ";
        }

        try {
            s += "[agreed ? : " + getSaleAgreeded() + "], ";
        } catch (Exception e) {
            s += "[agreed ? : false ]";
        }

        return s;
    }
    //</editor-fold>

}
