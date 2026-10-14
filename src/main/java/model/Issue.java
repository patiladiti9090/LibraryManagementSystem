
package model;

import java.sql.Date;

public class Issue {
    private int issueId;
    private int bookId;
    private int memberId;
    private Date issueDate;
    private Date returnDate;
    private String status;

    public Issue(int issueId, int bookId, int memberId,
                 Date issueDate, Date returnDate, String status) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public int getIssueId() { return issueId; }
    public int getBookId() { return bookId; }
    public int getMemberId() { return memberId; }
    public Date getIssueDate() { return issueDate; }
    public Date getReturnDate() { return returnDate; }
    public String getStatus() { return status; }
}
