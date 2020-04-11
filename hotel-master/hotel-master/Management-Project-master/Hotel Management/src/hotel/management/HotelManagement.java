package hotel.management;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotelManagement 
{
    Authentication a = new Authentication();
    Exception exp = new Exception();
    Scanner sc = new Scanner(System.in);
    static Socket socket;
    static DataOutputStream outStream;

    public static void main(String[] args) {
        try {
            socket = new Socket("127.0.0.2", 3030);
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "", serverMessage = "";
            while (!clientMessage.equals("bye")) {
                HotelManagement hotel = new HotelManagement();
                hotel.login();
            }
            outStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void login() 
    {

        System.out.println("Hotel Management System");
        String Id;
        int userId = 0;
        do 
        {
            System.out.print("Enter a Valid User Id: ");
            Id = sc.nextLine();
            try 
            {
                userId = Integer.parseInt(Id);
            } 
            catch (NumberFormatException e) 
            {
                Id = "invalid";
            }
        }
        while (Id.equals("invalid"));
        System.out.print("Enter Password:");
        String password = sc.nextLine();
        if (a.auhtenticate(userId, password)) 
        {
            mainMenu();
        } else 
        {
            System.out.println("Invalid User Name or Password!");
            login();
        }

    }

    public void bookRoom() 
    {
       int no_of_rooms;
        String no_of_Guests;
        int noOfGuest=0;
            do
            {
                System.out.println("Hotel Management System");
                System.out.print("Enter No of Guests:");
                no_of_Guests = sc.nextLine();
                try
                {
                   noOfGuest =Integer.parseInt(no_of_Guests);
                   if(noOfGuest<1)
                       throw new NumberFormatException("Invalid Input");
                }
                catch(NumberFormatException e)
                {
                   no_of_Guests="invalid";
                }
            }
            while(no_of_Guests.equals("invalid"));
            no_of_rooms = (noOfGuest/2)+noOfGuest%2;
            List<Room> room = new Query().getRooms(no_of_rooms,0);
            for(int i = 0;i<room.size();i++)
            {
                System.out.println((1+i)+")"+room.get(i).getRoomType());
            }
            String roomTypeString="";
            int room_type=0;
            do{
            System.out.print("Select ROOM TYPE:");
            roomTypeString=sc.nextLine();
            try
            {
            room_type=Integer.parseInt(roomTypeString);
            if(room_type>room.size() || room_type<1)
                throw new NumberFormatException("Invalid Input");
            }catch(NumberFormatException e)
            {
                roomTypeString="invalid";
            }
            }
            while(roomTypeString.equals("invalid"));
            String noOfDays="";
            int no_of_days=0;
            do{
            System.out.print("no of days to be booked for:");
            noOfDays=sc.nextLine();
            try
            {
                no_of_days =Integer.parseInt(noOfDays);
                if(no_of_days<1)
                    throw new NumberFormatException("Invlaid Input");
            }catch(NumberFormatException e)
            {
                noOfDays="invalid";
            }
            }while(noOfDays.equals("invalid"));
            double rate = no_of_rooms * room.get(room_type-1).getRate() * no_of_days;
            System.out.println("The Total Amount for Booking is: "+rate);
            System.out.print("Press Y to Confirm Booking and Other Key to Cancel: ");
            String confirm = sc.nextLine();
            if(confirm.equalsIgnoreCase("Y"))
            {
                String regex = "";
                String title,first_name,last_name,address,phoneNo,email;
                regex="^[A-Za-z]+\\s+[A-Za-z]+$";
                do{
                System.out.print("Title:");
                title = sc.nextLine();
                }
                while(!(title.matches(regex) ||  title.matches("^[A-Za-z]+$")));
                do
                {
                System.out.print("First Name:");
                first_name = sc.nextLine();
                }
                while(!(first_name.matches(regex) ||  first_name.matches("^[A-Za-z]+$")));
                do
                {
                System.out.print("Last Name:");
                last_name = sc.nextLine();
                }
                while(!(last_name.matches(regex) || last_name.matches("^[A-Za-z]+$")));
                System.out.print("Address:"); 
                address = sc.nextLine();
                do
                {
                regex="^[0-9]+$";    
                System.out.print("Phone No:");
                phoneNo=sc.nextLine();
                }
                while(!phoneNo.matches(regex));
                int phone=Integer.parseInt(phoneNo);
                do
                {
                regex="^[A-Za-z0-9+_.-]+@(.+)$";
                System.out.print("email:");
                email = sc.nextLine();
                }while(!email.matches(regex));
                int guest_id = new Query().insertGuest(title,first_name,last_name,address,phone,email);
                new Query().placeBooking(guest_id,room.get(room_type-1).getRoomType(),no_of_rooms,no_of_days,rate);
                System.out.println("\nRoom Booked Sucessfully!");
            }
            else
            {
                mainMenu();
            }
    }

    public void billService() {
        Scanner sc=new Scanner(System.in);
        String bookId;
        int book_id=0;
        do
        {
        System.out.println("Hotel Management System");
        System.out.print("Enter Booking Id:");
        bookId=sc.nextLine();
        try
        {
            book_id = Integer.parseInt(bookId);
            if(book_id<0)
                throw new NumberFormatException("Invalid Input!");
        }catch(NumberFormatException e)
        {
            bookId="invalid";
        }
        }
        while(bookId.equals("invalid"));
        Reservation reservation = new Query().getReservation(book_id);
        System.out.print("test:" + reservation.getGuest_id());
        String Guest = new Query().getGuest(reservation.getGuest_id());
        System.out.println("Guest name:" + Guest);
        System.out.println("no of rooms booked:" + reservation.getNo_of_rooms());
        System.out.println("rate per night" + reservation.getBill() / reservation.getNo_of_days());
        System.out.println("Type of room:" + reservation.getRoom_type());
        System.out.println("Total amount:" + reservation.getBill());
        if (reservation.getStatus().equalsIgnoreCase("checked out"))
        {
            System.out.print("person has checked out  enter any key to continue");
            sc.nextLine();
            mainMenu();
        }
        else 
        {
            System.out.println("Press Y to Check Out or any key to exit:");
            String checkout = sc.nextLine();
            if (checkout.equalsIgnoreCase("Y"))
            {
                String discountString;
                int discount=0;
                do
                {
                System.out.println("Discount:");
                discountString=sc.nextLine();
                try
                {
                    discount=Integer.parseInt(discountString);
                    if(discount>25)
                        throw new NumberFormatException("Invalid Input!");
                }catch(NumberFormatException e)
                {
                    discountString="invalid";
                }
                }while(discountString.equals("invalid"));
                double amountAfterDiscount=(reservation.getBill()-(reservation.getBill()*discount/100));
                System.out.println("Amount After Discount: "+amountAfterDiscount);
                
                new Query().updateCheckOut(book_id, reservation.getNo_of_rooms(), reservation.getRoom_type(),amountAfterDiscount);
                mainMenu();
            } 
            else 
            {
                mainMenu();
            }
        }
    }

    public void getBookings() {
        List<Reservation> reservations = new Query().getReservations();
        for (int i = 0; i < reservations.size(); i++) {
            String Guest = new Query().getGuest(reservations.get(i).getGuest_id());
            System.out.println("booking#    Customer name    Room Type   no of rooms    no of days");
            System.out.println("    " + i + "      " + "    " + Guest + "    " + reservations.get(i).getRoom_type() + "   " + "      " + reservations.get(i).getNo_of_rooms() + "      " + "    " + "      " + reservations.get(i).getNo_of_days());
        }
        System.out.print("enter any key to continue");
        sc.nextLine();
    }

    public void getRooms() {
        List<Room> room = new Query().getRooms(0, 1);
        for (int i = 0; i < room.size(); i++) {
            System.out.println("room id#     Room Type   no of rooms Availible");
            System.out.println("  " + room.get(i).getRoomId() + "           " + "" + room.get(i).getRoomType() + "          " + room.get(i).getNo_of_rooms());
        }
        System.out.print("enter any key to continue");
        sc.nextLine();
    }

    public void mainMenu() {

        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Hotel Management System");
            System.out.println("1. Book a Room");
            System.out.println("2. Bill Service");
            System.out.println("3. Current Bookings");
            System.out.println("4. Available Rooms");
            System.out.println("5. Exit");
            System.out.print("Select:");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    bookRoom();
                    mainMenu();
                    break;
                case "2":
                    billService();
                    mainMenu();
                    break;
                case "3":
                    getBookings();
                    mainMenu();
                    break;
                case "4":
                    getRooms();
                    mainMenu();
                    break;
                case "5": {
                    try {
                        System.exit(0);
                        outStream.close();
                        socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(HotelManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;

                default:
                    System.out.println("Invalid Choice!");
                    choice = "invalid";
                    break;
            }
        } while (choice.equals("invalid"));
    }
}
