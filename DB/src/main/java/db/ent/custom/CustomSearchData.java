package db.ent.custom;

import db.ent.BussinesLine;
import db.ent.CrmCase;
import db.ent.Customer;
import db.ent.CustomerBussinesType;
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
import org.superb.apps.utilities.datum.Dates;

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
    @Column(name = "CustomerBussinesType")
    private CustomerBussinesType customerBussinesType;
    @Column(name = "Salesman")
    private Salesman salesman;
    @Column(name = "BussinesLine")
    private BussinesLine bussinesLine;
    @Column(name = "CrmCase")
    private CrmCase crmCase;
    @Column(name = "Customer")
    private Customer customer;
    @Column(name = "Product")
    private Product product;
    @Column(name = "Quantity")
    private Double quantity;
    @Column(name = "MoneyAmount")
    private Double moneyAmount;
    @Column(name = "CaseFinished")
    private Boolean caseFinished;
    @Column(name = "SaleAgreeded")
    private Boolean saleAgreeded;

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public CustomSearchData() {
        // Dates d = new Dates();
        // d.setMonthsBackForth(-1);

        //startDate = startDate == null ? d.getFrom() : startDate;
        //endDate = endDate == null ? new Date() : endDate;
        //caseFinished = true;
        //saleAgreeded = true;
    }

    public CustomSearchData(CustomSearchData newCustomSearchData) {
        this(newCustomSearchData.getStartDate(),
                newCustomSearchData.getEndDate(),
                newCustomSearchData.getCustomerBussinesType(),
                newCustomSearchData.getSalesman(),
                newCustomSearchData.getBussinesLine(),
                newCustomSearchData.getCrmCase(),
                newCustomSearchData.getCustomer(),
                newCustomSearchData.getProduct(),
                newCustomSearchData.getQuantity(),
                newCustomSearchData.getMoneyAmount(),
                newCustomSearchData.getCaseFinished(),
                newCustomSearchData.getSaleAgreeded()
        );
    }

    public CustomSearchData(Date startDate, Date endDate, CustomerBussinesType cbt, Salesman salesman, BussinesLine bussinesLine, CrmCase crmCase, Customer customer,
            Product product, double quantity, double moneyAmount, boolean caseFinished, boolean saleAgreeded) {
        setUp(startDate, endDate, salesman, bussinesLine, crmCase, cbt, customer, product, quantity, moneyAmount, true, true);
    }

    public final void setUp(Date startDate, Date endDate, Salesman salesman, BussinesLine bussinesLine, CrmCase crmCase, CustomerBussinesType cbt, Customer customer,
            Product product, double quantity, double moneyAmount, boolean caseFinished, boolean saleAgreeded) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.salesman = salesman;
        this.bussinesLine = bussinesLine;
        this.crmCase = crmCase;
        this.customerBussinesType = cbt;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.moneyAmount = moneyAmount;
        this.caseFinished = caseFinished;
        this.saleAgreeded = saleAgreeded;
    }

    public final void setUp(CustomSearchData csd) {
        setUp(csd.getStartDate(), csd.getEndDate(), csd.getSalesman(), csd.getBussinesLine(), csd.getCrmCase(), csd.getCustomerBussinesType(), csd.getCustomer(), csd.getProduct(), csd.getQuantity(), csd.getMoneyAmount(), csd.getCaseFinished(), csd.getSaleAgreeded());
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

    public void setStartDate(int day, int month, int year) {
        Dates d = new Dates();
        d.setFrom(day, month, year);
        this.startDate = d.getFrom();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setEndDate(int day, int month, int year) {
        Dates d = new Dates();
        d.setTo(day, month, year);
        this.endDate = d.getTo();
    }

    /**
     *
     * @param months No. of months back/forth, to the current date. <br>
     * <p>
     * Positive value makes up an interval from today <br>
     * to the last day of the no. of months onward. <br>
     * <p>
     * Negative value makes up an interval <br>
     * from no. of months backward to this date.
     */
    public void setMonthsBackForth(int months) {
        Dates d = new Dates();
        d.setMonthsBackForth(months);

        setStartDate(d.getFrom());
        setEndDate(d.getTo());
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public BussinesLine getBussinesLine() {
        return bussinesLine;
    }

    public void setBussinesLine(BussinesLine bussinesLine) {
        this.bussinesLine = bussinesLine;
    }

    public CrmCase getCrmCase() {
        return crmCase;
    }

    public void setCrmCase(CrmCase crmCase) {
        this.crmCase = crmCase;
    }

    public CustomerBussinesType getCustomerBussinesType() {
        return customerBussinesType;
    }

    public void setCustomerBussinesType(CustomerBussinesType cbt) {
        this.customerBussinesType = cbt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
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
        return saleAgreeded;
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
            if (!getBussinesLine().toString().trim().isEmpty()) {
                s += "[" + getBussinesLine().toString() + "], ";
            } else {
                s += "[no bussines line], ";
            }
        } catch (Exception e) {
            s += "[no bussines line], ";
        }

        try {
            if (!getCustomerBussinesType().toString().trim().isEmpty()) {
                s += "[" + getCustomerBussinesType().toString() + "], ";
            } else {
                s += "[no customer bussines type], ";
            }
        } catch (Exception e) {
            s += "[no customer bussines type], ";
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
            s += "[quantity: " + getQuantity().toString() + "], ";
        } catch (Exception e) {
            s += "[no quantity], ";
        }
        try {
            s += "[money ammount: " + getMoneyAmount().toString() + "], ";
        } catch (Exception e) {
            s += "[no money amount], ";
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
