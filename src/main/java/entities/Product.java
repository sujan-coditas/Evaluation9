package entities;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pid;
    private String pname;
    private int pprice;
    private int quantity;
    private int selectQty;

    public int getSelectQty() {
        return selectQty;
    }

    public void setSelectQty(int selectQty) {
        this.selectQty = selectQty;
    }
    @ManyToOne
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public Product(String pname, int pprice, int quantity, int selectQty) {
        this.pname = pname;
        this.pprice = pprice;
        this.quantity = quantity;
        this.selectQty = selectQty;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPprice() {
        return pprice;
    }

    public void setPprice(int pprice) {
        this.pprice = pprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Product() {
    }
}
