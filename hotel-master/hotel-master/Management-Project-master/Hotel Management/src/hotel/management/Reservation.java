
package hotel.management;

import java.util.Date;

public class Reservation 
{
    private int bookId;
    private int guest_id;
    private String room_type;
    private int no_of_rooms;
    private int no_of_days;
    private Date bookDate;
    private Date checkIn;
    private Date checkOut;
    private double bill;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    private String status;
    public int getBookId() {
        return bookId;
    }

    public Reservation(int guest_id, String room_type, int no_of_rooms, int no_of_days, Date checkIn, double bill,String status) {
        this.guest_id = guest_id;
        this.room_type = room_type;
        this.no_of_rooms = no_of_rooms;
        this.no_of_days = no_of_days;
        this.bookDate = bookDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bill = bill;
        this.status = status;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getNo_of_rooms() {
        return no_of_rooms;
    }

    public void setNo_of_rooms(int no_of_rooms) {
        this.no_of_rooms = no_of_rooms;
    }

    public int getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(int no_of_days) {
        this.no_of_days = no_of_days;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }
    
}
