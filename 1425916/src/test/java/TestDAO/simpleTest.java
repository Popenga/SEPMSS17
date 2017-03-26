package TestDAO;

import Entities.Box;
import Persistence.DAOBox;
import Persistence.JdbcBox;
import Persistence.JdbcReservation;
import Service.SimpleService;
import Service.WendySimpleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by krirs on 24.03.2017.
 */
public class simpleTest {
    public static void main(String[] args) {
       System.out.println(new JdbcReservation().getDayDiff(9));
    canFindAllFridaysInRange();

    }

    public static void canFindAllFridaysInRange(){
        LocalDate start;
        LocalDate end;
        start = LocalDate.of(2017, 3, 1);
        end = LocalDate.of(2017, 3,30);

        DayOfWeek dowOfStart = start.getDayOfWeek();
        int difference = DayOfWeek.FRIDAY.getValue() - dowOfStart.getValue();
        if (difference < 0) difference += 6;

        List<LocalDate> fridaysInRange = new ArrayList<LocalDate>();

        LocalDate currentFriday = start.plusDays(difference);
        do {
            fridaysInRange.add(currentFriday);
            currentFriday = currentFriday.plusDays(6);
        } while (currentFriday.isBefore(end));

        System.out.println("Fridays in range: " + fridaysInRange);
    }
}