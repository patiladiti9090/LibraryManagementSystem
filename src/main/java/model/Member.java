
package model;

public class Member {
    private int memberId;
    private String memberName;
    private String email;
    private String mobile;
    private String address;

    public Member(int memberId, String memberName, String email,
                  String mobile, String address) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public int getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getAddress() { return address; }
}
