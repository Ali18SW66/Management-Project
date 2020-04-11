package hotel.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Query
{

    private Connection con;
    private String query;
    private Statement stat = null;
    private PreparedStatement ps = null;
    private final Connection CONNECTION = DBConnection.getConnection();
    private ResultSet rs = null;

    public List<Room> getRooms(int no_of_rooms,int check) {
        List<Room> rooms = new ArrayList();
        try {
            con = DBConnection.getConnection();
            if(check==0){
            query = "select * from room where no_of_rooms > '" + no_of_rooms + "'";
            }
            if(check==1){
            query = "select * from room";
            }
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id"), rs.getString("type"),rs.getDouble("rate"), rs.getInt("no_of_rooms")));
            }

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rooms;
    }

    public int insertGuest(String title, String first_name, String last_name, String address, int phone, String email) {
        int id = 0;
        try {
            con = DBConnection.getConnection();

            query = " INSERT INTO `guest_details`(`title`, `first_name`, `last_name`, `address`, `phone`,`email`) VALUES (?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, first_name);
            ps.setString(3, last_name);
            ps.setString(4, address);
            ps.setInt(5, phone);
            ps.setString(6, email);
            ps.executeUpdate();
            query = "select id from guest_details where email='" + email + "'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            System.out.print(email);

            while (rs != null && rs.next()) {
                id = rs.getInt("id");
            }
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void placeBooking(int guest_id, String RoomType, int no_of_rooms, int no_of_days, double rate) {

        try {
            con = DBConnection.getConnection();
            query = " INSERT INTO `cu_reservations`(`Guest_id`, `room_type`, `no_of_rooms`, `no_of_days`, `bill`) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, guest_id);
            ps.setString(2, RoomType);
            ps.setInt(3, no_of_rooms);
            ps.setInt(4, no_of_days);
            ps.setDouble(5, rate);
            ps.executeUpdate();
            query = "select no_of_rooms from room where type='" + RoomType + "'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs != null && rs.next()) {
                no_of_rooms = rs.getInt("no_of_rooms") - no_of_rooms;
            }
            query = "UPDATE `room` SET `no_of_rooms`='" + no_of_rooms + "' where type='" + RoomType + "'";
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Reservation getReservation(int book_id) {
        Reservation reservation = null;
        try {
            con = DBConnection.getConnection();
            query = "select * from cu_reservations where id = '" + book_id + "'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while (rs.next()) {
                
                reservation = new Reservation(rs.getInt("Guest_id"), rs.getString("room_type"), rs.getInt("no_of_rooms"), rs.getInt("no_of_days"), rs.getDate("check_in"), rs.getDouble("bill"),rs.getString("status"));
            }

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reservation;
    }

    public String getGuest(int guest_id) {
        String guest = "";
        try {
            
            con = DBConnection.getConnection();
            query = "select first_name,last_name from guest_details where id ='" + guest_id + "'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs != null && rs.next()) {
                guest = rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return guest;
    }

    public void updateCheckOut(int bookid,int no_of_rooms,String RoomType,double bill) {
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        try {
            con = DBConnection.getConnection();
            query = "UPDATE `cu_reservations` SET `check_out`='" + ts + "' where id='" + bookid + "'";
            ps = con.prepareStatement(query);
            ps.executeUpdate();
            query = "select no_of_rooms from room where type='" + RoomType + "'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);

            while (rs != null && rs.next()) {
                no_of_rooms = rs.getInt("no_of_rooms") + no_of_rooms;
            }
            query = "UPDATE `room` SET `no_of_rooms`='" + no_of_rooms + "' where type='" + RoomType + "'";
            ps = con.prepareStatement(query);
            ps.executeUpdate();
            query = "UPDATE `cu_reservations` SET `bill`='" + bill + "' where id='" + bookid + "'";
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList();
        try {
            con = DBConnection.getConnection();
            query = "select * from cu_reservations";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery(query);
            while (rs.next()) {
                if(rs.getString("status").equalsIgnoreCase("checked in")){
               reservations.add(new Reservation(rs.getInt("Guest_id"), rs.getString("room_type"), rs.getInt("no_of_rooms"), rs.getInt("no_of_days"), rs.getDate("check_in"), rs.getDouble("bill"),rs.getString("status")));
                }
                }

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reservations;
    }
}
