package Entities;

/**
 * Created by krirs on 19.03.2017.
 */
public class Invoice {
    public Invoice(int id, double sum, String date, String customer, String address) {
        this.id = id;
        this.sum = sum;
        this.date = date;
        this.customer = customer;
        this.address = address;
    }

    private int id;
    private double sum;
    private String date;
    private String customer;
    private String address;
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
