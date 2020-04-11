
package hotel.management;
public class Room 
{
    private int roomId;
    private String roomType;
    private double rate;

    public int getNo_of_rooms() {
        return no_of_rooms;
    }

    public void setNo_of_rooms(int no_of_rooms) {
        this.no_of_rooms = no_of_rooms;
    }
    private int no_of_rooms;

    public Room(int roomId, String roomType, double rate,int no_of_rooms) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.rate = rate;
        this.no_of_rooms = no_of_rooms;
    }
   
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
}
