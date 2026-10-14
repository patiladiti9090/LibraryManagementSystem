
package model;

public class Book {
    private int bookId;
    private String bookName;
    private String authorName;
    private String publisher;
    private double price;
    private int quantity;

    public Book(int bookId, String bookName, String authorName,
                String publisher, double price, int quantity) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
    }

    public int getBookId() { return bookId; }
    public String getBookName() { return bookName; }
    public String getAuthorName() { return authorName; }
    public String getPublisher() { return publisher; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
