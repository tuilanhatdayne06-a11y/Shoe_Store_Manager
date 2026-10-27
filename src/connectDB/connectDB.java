package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    public static Connection con = null;
    private static connectDB instance = new connectDB();

    private connectDB() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=ShoeStore;encrypt=false;trustServerCertificate=true;";
        String user = "sa";
        String password = "sapassword";
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static connectDB getInstance() {
        return instance;
    }

    public static Connection getConnection() throws SQLException {
    	String url = "jdbc:sqlserver://localhost:1433;"
                + "databaseName=ShoeStore;"
                + "encrypt=false;"
                + "trustServerCertificate=true;";

     return DriverManager.getConnection(url, "sa", "sapassword");
    }
}