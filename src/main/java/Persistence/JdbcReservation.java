package Persistence;

import Entities.Reservation;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by krirs on 20.03.2017.
 */
public class JdbcReservation implements DAOReservation {

    private Connection conn;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    @Override
    public boolean create(int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted) {
        if(find(orderId)!=null){
            return false;
        }
        conn = ConnectionFactory.getConnection();
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO RESERVATION VALUES (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, boxId);
            preparedStatement.setInt(3, invoiceId);
            preparedStatement.setString(4, customer);
            preparedStatement.setString(5, reservationFrom);
            preparedStatement.setString(6, reservationTo);
            preparedStatement.setInt(7, duration);
            preparedStatement.setDouble(8, price);
            preparedStatement.setString(9, horse);
            preparedStatement.setBoolean(10, isDeleted);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(int orderId, int boxId, int invoiceId, String customer, String reservationFrom, String reservationTo, int duration, double price, String horse, boolean isDeleted) {
        if(find(orderId)==null){
            return false;
        }
        conn = ConnectionFactory.getConnection();
        try {
            preparedStatement = conn.prepareStatement("UPDATE RESERVATION SET BOXID=?, INVOICEID=?, CUSTOMER=?, RESERVATIONFROM=?, RESERVATIONTO=?, DURATION=?, PRICE=?, HORSE=?, ISDELETED=? WHERE ORDERID = ?");

            preparedStatement.setInt(1, boxId);
            preparedStatement.setInt(2, invoiceId);
            preparedStatement.setString(3, customer);
            preparedStatement.setString(4, reservationFrom);
            preparedStatement.setString(5, reservationTo);
            preparedStatement.setInt(6, duration);
            preparedStatement.setDouble(7, price);
            preparedStatement.setString(8, horse);
            preparedStatement.setBoolean(9, isDeleted);
            preparedStatement.setInt(10, orderId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int orderId) {
        if(find(orderId)==null){
            return false;
        }
        conn = ConnectionFactory.getConnection();
        try {
            preparedStatement = conn.prepareStatement("UPDATE RESERVATION SET ISDELETED =? WHERE ORDERID=?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reservation find(int id) {
        Reservation findMe=null;
        conn = ConnectionFactory.getConnection();
        try {
            preparedStatement = conn.prepareStatement("Select * FROM RESERVATION WHERE ORDERID =?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                findMe= new Reservation(resultSet.getInt("ORDERID"), resultSet.getInt("BOXID"), resultSet.getInt("INVOICEID"),resultSet.getString("CUSTOMER"), resultSet.getString("RESERVATIONFROM"), resultSet.getString("RESERVATIONTO"), resultSet.getInt("DURATION"), resultSet.getDouble("PRICE"), resultSet.getString("HORSE"), resultSet.getBoolean("isDeleted"));
            }
            preparedStatement.close();
            return findMe;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return findMe;
    }

    @Override
    public ArrayList<Reservation> findAllReservations() {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        String query = "Select * FROM RESERVATION";
        conn = ConnectionFactory.getConnection();
        try {
            statement=conn.createStatement();
            resultSet=statement.executeQuery(query);

            while(resultSet.next()){
                reservations.add(new Reservation(resultSet.getInt("ORDERID"), resultSet.getInt("BOXID"), resultSet.getInt("INVOICEID"),resultSet.getString("CUSTOMER"), resultSet.getString("RESERVATIONFROM"), resultSet.getString("RESERVATIONTO"), resultSet.getInt("DURATION"), resultSet.getDouble("PRICE"), resultSet.getString("HORSE"), resultSet.getBoolean("isDeleted")));
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return reservations;
    }
}
