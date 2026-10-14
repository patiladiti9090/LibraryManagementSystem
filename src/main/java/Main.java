
import operation.BookOperation;
import operation.MemberOperation;
import operation.IssueBookOperation;
import operation.ReturnBookOperation;
import operation.DisplayOperation;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BookOperation books = new BookOperation();
        MemberOperation members = new MemberOperation();
        IssueBookOperation issue = new IssueBookOperation();
        ReturnBookOperation returns = new ReturnBookOperation();
        DisplayOperation display = new DisplayOperation();

        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Add Member");
            System.out.println("7. Display Members");
            System.out.println("8. Search Member");
            System.out.println("9. Update Member");
            System.out.println("10. Delete Member");
            System.out.println("11. Issue Book");
            System.out.println("12. Return Book");
            System.out.println("13. Display Issued Books");
            System.out.println("14. Display Available Books");
            System.out.println("15. Display Returned Books");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> books.addBook();
                    case 2 -> books.displayBooks();
                    case 3 -> books.searchBooks();
                    case 4 -> books.updateBooks();
                    case 5 -> books.deleteBooks();
                    case 6 -> members.addMember();
                    case 7 -> members.displayMember();
                    case 8 -> members.searchMember();
                    case 9 -> members.updateMember();
                    case 10 -> members.deleteMember();
                    case 11 -> issue.issueBook();
                    case 12 -> returns.returnBook();
                    case 13 -> display.displayIssuedBooks();
                    case 14 -> display.displayAvailableBooks();
                    case 15 -> display.displayReturnedBooks();
                    case 0 -> {
                        System.out.println("Thank you for using the Library Management System!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid menu number.");
            }
        }
    }
}
