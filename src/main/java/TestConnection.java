
import connection.MySqlConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection con = MySqlConnection.getConnection()) {

            if (con != null) {
                System.out.println("Database Connected Successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}