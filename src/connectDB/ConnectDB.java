package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance = new ConnectDB();

    private ConnectDB() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=ShoeStore;encrypt=false;trustServerCertificate=true;";
        String user = "sa";
        String password = "sapassword";
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectDB getInstance() {
        return instance;
    }

    public static Connection getConnection() {
    	 try {
    		 if (con == null || con.isClosed()) {
	            new ConnectDB();
	         }
    	 } catch (SQLException e) {
    		 e.printStackTrace();
    	 }
        return con;
    }
}