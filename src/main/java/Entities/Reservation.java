package Entities;

/**
 * Created by krirs on 19.03.2017.
 */
public class Reservation {

    private int orderId;
    private int boxId;
    private int invoiceId;
    private String customer;
    private String reservationTo;
    private int duration;
    private double price;
    private String horse;
    private String reservationFrom;
    private boolean isDeleted;


    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getReservationFrom() {
        return reservationFrom;
    }

    public void setReservationFrom(String reservationFrom) {
        this.reservationFrom = reservationFrom;
    }

    public String getReservationTo() {
        return reservationTo;
    }

    public void setReservationTo(String reservationTo) {
        this.reservationTo = reservationTo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHorse() {
        return horse;
    }

    public void setHorse(String horse) {
        this.horse = horse;
    }

    public Reservation(int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted) {
        this.orderId = orderId;
        this.boxId = boxId;
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.reservationFrom = reservationFrom;
        this.reservationTo = reservationTo;
        this.duration = duration;
        this.price = price;
        this.horse = horse;
        this.isDeleted=isDeleted;
    }


}
