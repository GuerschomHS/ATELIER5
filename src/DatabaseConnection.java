import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_DBMagasin" ;
    private static final String USER = "freedb_ngandu" ;
    private static final String PASSWORD = "6gRJDj4QaMBw$Ha" ;

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

