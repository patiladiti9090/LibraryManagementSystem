
package operation;

import connection.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BookOperation {

    private final Scanner sc = new Scanner(System.in);

    // 1. Add Book
    public void addBook() {

        String sql = "INSERT INTO books " +
                "(book_name, author_name, publisher, price, quantity) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter book name: ");
            String bookName = sc.nextLine();

            System.out.print("Enter author name: ");
            String authorName = sc.nextLine();

            System.out.print("Enter publisher: ");
            String publisher = sc.nextLine();

            System.out.print("Enter price: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());

            if (bookName.isBlank() || authorName.isBlank()) {
                System.out.println("Book name and author cannot be empty.");
                return;
            }

            if (price < 0 || quantity < 0) {
                System.out.println("Price and quantity cannot be negative.");
                return;
            }

            ps.setString(1, bookName);
            ps.setString(2, authorName);
            ps.setString(3, publisher);
            ps.setDouble(4, price);
            ps.setInt(5, quantity);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book added successfully!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for price and quantity.");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // 2. Display All Books
    public void displayBooks() {

        String sql = "SELECT * FROM books";

        try (Connection con = MySqlConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                printBook(rs);
            }

            if (!found) {
                System.out.println("No books found.");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying books: " + e.getMessage());
        }
    }

    // 3. Search Book by ID
    public void searchBooks() {

        System.out.print("Enter book ID to search: ");

        try {
            int id = Integer.parseInt(sc.nextLine());

            String sql = "SELECT * FROM books WHERE book_id = ?";

            try (Connection con = MySqlConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        printBook(rs);
                    } else {
                        System.out.println("Book not found.");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid book ID.");
        } catch (SQLException e) {
            System.out.println("Error searching book: " + e.getMessage());
        }
    }

    // 4. Update Book
    public void updateBooks() {

        System.out.print("Enter book ID to update: ");

        try {
            int id = Integer.parseInt(sc.nextLine());

            String sql = "UPDATE books SET book_name = ?, " +
                    "author_name = ?, publisher = ?, price = ?, " +
                    "quantity = ? WHERE book_id = ?";

            try (Connection con = MySqlConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                System.out.print("Enter new book name: ");
                String bookName = sc.nextLine();

                System.out.print("Enter new author name: ");
                String authorName = sc.nextLine();

                System.out.print("Enter new publisher: ");
                String publisher = sc.nextLine();

                System.out.print("Enter new price: ");
                double price = Double.parseDouble(sc.nextLine());

                System.out.print("Enter new quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());

                if (bookName.isBlank() || authorName.isBlank()) {
                    System.out.println("Book name and author cannot be empty.");
                    return;
                }

                if (price < 0 || quantity < 0) {
                    System.out.println("Price and quantity cannot be negative.");
                    return;
                }

                ps.setString(1, bookName);
                ps.setString(2, authorName);
                ps.setString(3, publisher);
                ps.setDouble(4, price);
                ps.setInt(5, quantity);
                ps.setInt(6, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Book updated successfully!");
                } else {
                    System.out.println("Book ID not found.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values.");
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    // 5. Delete Book
    public void deleteBooks() {

        System.out.print("Enter book ID to delete: ");

        try {
            int id = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM books WHERE book_id = ?";

            try (Connection con = MySqlConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Book deleted successfully!");
                } else {
                    System.out.println("Book ID not found.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid book ID.");
        } catch (SQLException e) {
            System.out.println(
                    "Cannot delete this book. It may have issue records. "
                            + e.getMessage()
            );
        }
    }

    // Helper method to print book details
    private void printBook(ResultSet rs) throws SQLException {

        System.out.println("----------------------------------");
        System.out.println("Book ID     : " + rs.getInt("book_id"));
        System.out.println("Book Name   : " + rs.getString("book_name"));
        System.out.println("Author Name : " + rs.getString("author_name"));
        System.out.println("Publisher   : " + rs.getString("publisher"));
        System.out.println("Price       : " + rs.getDouble("price"));
        System.out.println("Quantity    : " + rs.getInt("quantity"));
        System.out.println("----------------------------------");
    }
}
