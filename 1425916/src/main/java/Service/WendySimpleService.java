package Service;

import Entities.Box;
import Entities.Reservation;
import Persistence.DAOBox;
import Persistence.DAOReservation;
import Persistence.JdbcBox;
import Persistence.JdbcReservation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public long getDayDiff( int orderId) {return reservationDao.getDayDiff(orderId);}

    public String generateInvoice(String customer){
        String invoice="";
        long invoiceId=10000;
        long total=0;
        ArrayList<Reservation> reservations = reservationDao.findAllReservations();
        ArrayList<Box> boxes = boxDao.findAllBoxes();

        ArrayList<Reservation> customerReservations= new ArrayList<>();

        for(Reservation reservation: reservations){
            if(!reservation.isDeleted() && reservation.getCustomer().toLowerCase().equals(customer.toLowerCase())){
                customerReservations.add(reservation);
                invoiceId+=reservation.getOrderId();
                total+=reservation.getPrice();
            }
        }
        invoice="[INVOICEID:"+invoiceId+"|Customer:"+customer+"|Total:"+total+"EUR]"+"\n";
        for(Reservation reservation :customerReservations){
            invoice+=reservation.toString()+"\n";
        }
        if(customerReservations.isEmpty()){
            invoice="";
        }

    return invoice;
    }

    @Override
    public ArrayList<Reservation> findAllIntersectReservations(String dateFrom, String dateTo) {
        return reservationDao.findAllIntersectReservations(dateFrom,dateTo);
    }



    public int[] getDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int mondays=0;
        int tuesdays=0;
        int wednesdays=0;
        int thursdays=0;
        int fridays=0;
        int saturdays=0;
        int sundays=0;

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
            switch (startCal.get(Calendar.DAY_OF_WEEK)) {

                case Calendar.MONDAY:
                    mondays += 1;
                    break;
                case Calendar.TUESDAY:
                    tuesdays += 1;
                    break;
                case Calendar.WEDNESDAY:
                    wednesdays += 1;
                    break;
                case Calendar.THURSDAY:
                    thursdays += 1;
                    break;
                case Calendar.FRIDAY:
                    fridays += 1;
                    break;
                case Calendar.SATURDAY:
                    saturdays += 1;
                    break;
                case Calendar.SUNDAY:
                    sundays += 1;
                    break;
            }

                startCal.add(Calendar.DAY_OF_MONTH, 1);

        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        int[] dayCounts= {mondays, tuesdays, wednesdays, thursdays, fridays, saturdays, sundays};
        return dayCounts;
    }
}
