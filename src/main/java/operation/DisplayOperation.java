
package operation;

import connection.MySqlConnection;
import java.sql.*;

public class DisplayOperation {

    public void displayIssuedBooks() {
        displayByStatus("ISSUED");
    }

    public void displayReturnedBooks() {
        displayByStatus("RETURNED");
    }

    private void displayByStatus(String status) {
        String sql = """
            SELECT i.issue_id, b.book_name, m.member_name,
                   i.issue_date, i.return_date, i.status
            FROM issue_book i
            JOIN books b ON i.book_id = b.book_id
            JOIN members m ON i.member_id = m.member_id
            WHERE i.status = ?
            ORDER BY i.issue_id
            """;

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(
                            "Issue ID: " + rs.getInt("issue_id")
                                    + " | Book: " + rs.getString("book_name")
                                    + " | Member: " + rs.getString("member_name")
                                    + " | Issue date: " + rs.getDate("issue_date")
                                    + " | Return date: " + rs.getDate("return_date")
                                    + " | Status: " + rs.getString("status")
                    );
                }

                if (!found) System.out.println("No records found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayAvailableBooks() {
        String sql = "SELECT * FROM books WHERE quantity > 0 ORDER BY book_id";

        try (Connection con = MySqlConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        "ID: " + rs.getInt("book_id")
                                + " | Book: " + rs.getString("book_name")
                                + " | Author: " + rs.getString("author_name")
                                + " | Available copies: " + rs.getInt("quantity")
                );
            }

            if (!found) System.out.println("No available books.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
