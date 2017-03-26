package Persistence;

import Entities.Box;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by krirs on 20.03.2017.
 */
public class JdbcBox implements DAOBox {

    private Connection conn;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;




    public boolean create(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment) {
        if(find(id)!=null){
            return false;
        }
        if(name.equals("")){
            return false;
        }
        conn = ConnectionFactory.getConnection();
        //System.out.println("Got connection");
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO BOX VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, pic);
            preparedStatement.setString(3, name);
            preparedStatement.setDouble(4, dailyRate);
            preparedStatement.setBoolean(5, window);
            preparedStatement.setBoolean(6, isDeleted);
            preparedStatement.setString(7, comment);
           // System.out.println("Trying to execute Update");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, String pic, String name, double dailyRate, boolean window, boolean isDeleted, String comment) {
       if(find(id)==null){
           return false;
       }
        conn = ConnectionFactory.getConnection();
        //System.out.println("Got connection");
        try {
            preparedStatement = conn.prepareStatement("UPDATE BOX SET name=?, PIC=?, DAILYRATE=?, WINDOW=?, ISDELETED=?, COMMENT=? WHERE ID = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pic);
            preparedStatement.setDouble(3, dailyRate);
            preparedStatement.setBoolean(4, window);
            preparedStatement.setBoolean(5,isDeleted);
            preparedStatement.setString(6,comment);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        if(find(id)==null){
            return false;
        }
        conn = ConnectionFactory.getConnection();
        try {
            preparedStatement = conn.prepareStatement("UPDATE BOX SET ISDELETED =? WHERE ID=?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Box find(int id) {
        Box findMe=null;
        conn = ConnectionFactory.getConnection();
        try {
            preparedStatement = conn.prepareStatement("Select * FROM BOX WHERE ID =?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                findMe= new Box(resultSet.getInt("ID"), resultSet.getString("PIC"),resultSet.getString("NAME"),resultSet.getDouble("dailyRate"), resultSet.getBoolean("Window"), resultSet.getBoolean("IsDeleted"), resultSet.getString("comment"));
            }
            preparedStatement.close();
            return findMe;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return findMe;
    }

    public ArrayList<Box> findAllBoxes() {
        ArrayList<Box> boxes = new ArrayList<Box>();
        String query = "Select * FROM BOX";
        conn = ConnectionFactory.getConnection();
        try {
            statement=conn.createStatement();
            resultSet=statement.executeQuery(query);

                while(resultSet.next()){
                    boxes.add(new Box(resultSet.getInt("ID"), resultSet.getString("PIC"), resultSet.getString("NAME"), resultSet.getDouble("DAILYRATE"), resultSet.getBoolean("WINDOW"), resultSet.getBoolean("ISDELETED"), resultSet.getString("comment")));
                }
                statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return boxes;
    }
}
