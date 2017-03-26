package Service;

import Entities.Box;
import Entities.Reservation;
import Persistence.DAOBox;
import Persistence.DAOReservation;
import Persistence.JdbcBox;
import Persistence.JdbcReservation;

import java.util.ArrayList;

/**
 * Created by krirs on 23.03.2017.
 */
public class WendySimpleService implements SimpleService {

    private DAOBox boxDao;
    private DAOReservation reservationDao;

    public Box findBox(int id) {

        return boxDao.find(id);
    }

    @Override
    public boolean createReservation(int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted) {
        return reservationDao.create(orderId, boxId, invoiceId, customer, reservationFrom, reservationTo, duration, price, horse, isDeleted);
    }

    @Override
    public boolean updateReservation(int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted) {
        return reservationDao.update(orderId, boxId, invoiceId, customer, reservationFrom, reservationTo, duration, price, horse, isDeleted);
    }

    @Override
    public boolean deleteReservation(int orderId) {
        return reservationDao.delete(orderId);
    }

    @Override
    public Reservation findReservation(int orderId) {
        return reservationDao.find(orderId);
    }

    @Override
    public ArrayList<Reservation> findAllReservations() {
        return reservationDao.findAllReservations();
    }

    public void setReservationDao() {
        this.reservationDao = new JdbcReservation();
    }
    public void setBoxDao() {
        this.boxDao = new JdbcBox();
    }

    public boolean createBox(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment){
        return boxDao.create( id,  pic,  name,  dailyRate,  window,  isDeleted, comment);
    }

    public boolean deleteBox(int id) throws IllegalArgumentException {
        return boxDao.delete(id);
    }

    public boolean updateBox(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment )  {

        return boxDao.update(id, pic, name, dailyRate, window, isDeleted, comment);
    }

    public ArrayList<Box> findAllBoxes() {
        return boxDao.findAllBoxes();
    }
}
