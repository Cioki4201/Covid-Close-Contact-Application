package sample;

public class Contact {

    //Variables

    private String fName;
    private String lName;
    private String id;
    private String phoneNo;

    //Constructor

    public Contact(String fName, String lName, String id, String phoneNo) {
        this.fName = fName;
        this.lName = lName;
        this.id = id;
        this.phoneNo = phoneNo;
    }

    //Get Methods

    public String getfName() { return fName; }

    public String getlName() { return lName; }

    public String getId() { return id; }

    public String getPhoneNo() { return phoneNo; }

    //Set Methods

    public void setfName(String fName) { this.fName = fName; }

    public void setlName(String lName) { this.lName = lName; }

    public void setId(String id) { this.id = id; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    //ToString Method

    @Override
    public String toString() {
        return "Full Name: " + this.fName + " " + this.lName + "    ||    ID: " + this.id + "    ||    Phone Number: " + phoneNo;
    }
}
