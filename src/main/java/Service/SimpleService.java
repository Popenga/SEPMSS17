package Service;

import Entities.Box;
import Entities.Reservation;
import Persistence.DAOBox;

import java.util.ArrayList;

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

}
