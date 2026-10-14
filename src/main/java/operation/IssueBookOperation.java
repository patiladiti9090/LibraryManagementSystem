
package operation;

import connection.MySqlConnection;
import java.sql.*;
import java.util.Scanner;

public class IssueBookOperation {
    private final Scanner sc = new Scanner(System.in);

    public void issueBook() {
        System.out.print("Enter book ID: ");
        int bookId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter member ID: ");
        int memberId = Integer.parseInt(sc.nextLine());

        String checkBook = "SELECT quantity FROM books WHERE book_id=? FOR UPDATE";
        String checkMember = "SELECT member_id FROM members WHERE member_id=?";
        String insertIssue = "INSERT INTO issue_book(book_id,member_id,issue_date,status) VALUES(?,?,CURRENT_DATE,'ISSUED')";
        String updateQuantity = "UPDATE books SET quantity=quantity-1 WHERE book_id=? AND quantity>0";

        try (Connection con = MySqlConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int quantity;

                try (PreparedStatement ps = con.prepareStatement(checkBook)) {
                    ps.setInt(1, bookId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Book not found.");
                            con.rollback();
                            return;
                        }
                        quantity = rs.getInt("quantity");
                    }
                }

                if (quantity <= 0) {
                    System.out.println("No copies available.");
                    con.rollback();
                    return;
                }

                try (PreparedStatement ps = con.prepareStatement(checkMember)) {
                    ps.setInt(1, memberId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Member not found.");
                            con.rollback();
                            return;
                        }
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(updateQuantity)) {
                    ps.setInt(1, bookId);
                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        System.out.println("No copies available.");
                        return;
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(insertIssue)) {
                    ps.setInt(1, bookId);
                    ps.setInt(2, memberId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book issued successfully!");

            } catch (Exception e) {
                con.rollback();
                System.out.println("Issue failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
