package Persistence;

import Entities.Reservation;

import java.util.ArrayList;

/**
 * Created by krirs on 20.03.2017.
 */
public interface DAOReservation {

    /*creates an invoice according to arguments passed
  returns true in case of success, false otherwise
   */
    public boolean create (int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted);

    /*updates the invoice if input and its key match
     returns true in case of success, false otherwise
      */
    public boolean update(int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted);

    /* deletes the invoice if input and its key match
     returns true in case of success, false otherwise
     */
    public boolean delete( int orderId);

    /*finds the reservation if input and id match
  returns the reservation if found, null otherwise
   */
    public Reservation find(int orderId);

    /*
    finds all reservations
    returns all reservation, or an empty arrayList if nothing is found
     */
    public ArrayList<Reservation> findAllReservations();

}
