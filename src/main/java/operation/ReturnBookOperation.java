
package operation;

import connection.MySqlConnection;
import java.sql.*;
import java.util.Scanner;

public class ReturnBookOperation {
    private final Scanner sc = new Scanner(System.in);

    public void returnBook() {
        System.out.print("Enter issue ID: ");
        int issueId = Integer.parseInt(sc.nextLine());

        String findIssue =
                "SELECT book_id FROM issue_book WHERE issue_id=? AND status='ISSUED' FOR UPDATE";
        String markReturned =
                "UPDATE issue_book SET status='RETURNED', return_date=CURRENT_DATE WHERE issue_id=? AND status='ISSUED'";
        String increaseQuantity =
                "UPDATE books SET quantity=quantity+1 WHERE book_id=?";

        try (Connection con = MySqlConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int bookId;

                try (PreparedStatement ps = con.prepareStatement(findIssue)) {
                    ps.setInt(1, issueId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Active issue not found or book already returned.");
                            con.rollback();
                            return;
                        }
                        bookId = rs.getInt("book_id");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(markReturned)) {
                    ps.setInt(1, issueId);
                    if (ps.executeUpdate() != 1) {
                        con.rollback();
                        System.out.println("Return could not be completed.");
                        return;
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(increaseQuantity)) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book returned successfully!");

            } catch (Exception e) {
                con.rollback();
                System.out.println("Return failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
