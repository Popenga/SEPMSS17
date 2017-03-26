package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by krirs on 19.03.2017.
 */
public class ConnectionFactory {


    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USER = "sa";
    public static final String PW = "";
    public static final String DRIVER = "org.h2.Driver";
    public static Connection connection;

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load H2-JDBC driver:" + "\n" + e);
        }
    }

    private Connection createConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PW);
            } catch (SQLException e) {
                System.out.println("Unable to connect to Database:" + "\n" + e);
            }
            return connection;
        }
        else {
            try {
            connection.close();
            connection=null;
            } catch (SQLException e) {
               System.err.println("Could not close DB-Connection:"+"\n");
               e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(URL, USER, PW);
            } catch (SQLException e) {
                System.out.println("Unable to connect to Database:" + "\n" + e);
            }
            return connection;
        }
    }

    public static Connection getConnection() {

        return instance.createConnection();
    }


}
