
package operation;

import connection.MySqlConnection;
import java.sql.*;
import java.util.Scanner;

public class MemberOperation {
    private final Scanner sc = new Scanner(System.in);

    public void addMember() {
        String sql = "INSERT INTO members(member_name,email,mobile,address) VALUES(?,?,?,?)";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Member name: ");
            ps.setString(1, sc.nextLine());
            System.out.print("Email: ");
            ps.setString(2, sc.nextLine());
            System.out.print("Mobile: ");
            ps.setString(3, sc.nextLine());
            System.out.print("Address: ");
            ps.setString(4, sc.nextLine());

            ps.executeUpdate();
            System.out.println("Member added successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayMember() {
        String sql = "SELECT * FROM members";

        try (Connection con = MySqlConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) printMember(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchMember() {
        System.out.print("Enter member ID: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "SELECT * FROM members WHERE member_id=?";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) printMember(rs);
                else System.out.println("Member not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateMember() {
        System.out.print("Enter member ID to update: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "UPDATE members SET member_name=?,email=?,mobile=?,address=? WHERE member_id=?";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("New name: ");
            ps.setString(1, sc.nextLine());
            System.out.print("New email: ");
            ps.setString(2, sc.nextLine());
            System.out.print("New mobile: ");
            ps.setString(3, sc.nextLine());
            System.out.print("New address: ");
            ps.setString(4, sc.nextLine());
            ps.setInt(5, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Member updated successfully!"
                    : "Member not found.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteMember() {
        System.out.print("Enter member ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM members WHERE member_id=?";

        try (Connection con = MySqlConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            System.out.println(ps.executeUpdate() > 0
                    ? "Member deleted successfully!"
                    : "Member not found.");
        } catch (SQLException e) {
            System.out.println("Cannot delete a member linked to issue records.");
        }
    }

    private void printMember(ResultSet rs) throws SQLException {
        System.out.println(
                "ID: " + rs.getInt("member_id")
                        + " | Name: " + rs.getString("member_name")
                        + " | Email: " + rs.getString("email")
                        + " | Mobile: " + rs.getString("mobile")
                        + " | Address: " + rs.getString("address")
        );
    }
}
