package lab09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
    private static final String DB_NAME = "MusicAlbums";
    private static final int PORT = 3306;
    // connect first without SSL param if invalid key error
    private static final String URL = "jdbc:mysql://localhost:" + PORT + "/" + DB_NAME + "?useSSL=false&relaxAutoCommit=true";
    private static final String USER = "dba";
    private static final String PASSWORD = "sql";
    private static Connection connection = null;
    private Database() { }
    static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    //Implement the method createConnection()
    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    //Implement the method closeConnection()
    static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //Implement the method commit()
    static void commit() {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //Implement the method rollback()
    static void rollback() {
        if (connection != null ) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}