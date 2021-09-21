package sample;

public class CloseContact{


    //Variables
    private Contact contact1;
    private Contact contact2;
    private String date;
    private String time;


    //Constructor
    public CloseContact(Contact contact1, Contact contact2, String date, String time) {
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.date = date;
        this.time = time;
    }

    //Get Methods
    public Contact getContact1() { return contact1; }

    public Contact getContact2() { return contact2; }

    public String getDate() { return date; }

    public String getTime() { return time; }


    //Set Methods
    public void setContact1(Contact contact1) { this.contact1 = contact1; }

    public void setContact2(Contact contact2) { this.contact2 = contact2; }

    public void setDate(String date) { this.date = date; }

    public void setTime(String time) { this.time = time; }


    //ToString Method
    @Override
    public String toString() {
        return this.contact1.getfName() +" "+ this.contact1.getlName() + " was in contact with " + this.contact2.getfName() +" "+ this.contact2.getlName() + " on " + this.date + " at " + this.time;
    }
}
