package Service;

import Entities.Box;
import Entities.Reservation;
import Persistence.DAOBox;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by krirs on 23.03.2017.
 */
public interface SimpleService {

     boolean createBox(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment);
     boolean deleteBox(int id);
     boolean updateBox(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment);
     ArrayList<Box> findAllBoxes();
     void setBoxDao();
     Box findBox(int id);

     boolean createReservation (int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted);
     boolean updateReservation(int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted);
     boolean deleteReservation( int orderId);
     Reservation findReservation(int orderId);
     ArrayList<Reservation> findAllReservations();
     void setReservationDao();

     /*returns the duration of a reservation in days
     since the input is checked this will always return an Integer (Assumption: no one will rent for over 15 years straight)
      */
     long getDayDiff( int orderId);

     /*returns Invoice if Reservations for the Customer can be found
     if no reservations can be found an empty String is returned
     */
     String generateInvoice(String customer);

     /*
     returns all Reservations that are in the given Period
      */
     ArrayList<Reservation> findAllIntersectReservations(String dateFrom, String dateTo);

     /*
     returns an integer-array containing the Count of Weekdays (MON-SUN),(0-6)
     returns 1 if startDate and endDate are the same
      */
     int[] getDaysBetweenTwoDates(Date startDate, Date endDate);


}
