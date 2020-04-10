package hotel.management;
import java.util.Scanner;
public class HotelManagement 
{
    private static DAO dbs=new DAOImpl();
    public static void main(String[] args) 
    {
        
    }
    public void login()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Hotel Management System");
        String Id;
        do
        {
            System.out.print("Enter a Valid User Id: ");
            Id=sc.nextLine();
            int userId=0;
            try
            {
                userId=Integer.parseInt(Id);
            }
            catch(NumberFormatException e)
            {
                Id="invalid";
            }
        }
        while(Id.equals("invalid"));
        System.out.println("Enter Password");
        String password=sc.nextLine();
        if(dbs.isValidUser(0, password))
        {
            mainMenu();
        }
        else
        {
            System.out.println("Invalid User Name or Password!");
        }
        
    }
    public void mainMenu()
    {
        Scanner sc=new Scanner(System.in);
        String choice;
        do
        {
            System.out.println("Hotel Management System");
            System.out.println("1. Book a Room");
            System.out.println("2. Bill Service");
            System.out.println("3. Current Bookings");
            System.out.println("4. Available Rooms");
            System.out.println("5. Exit");
            System.out.println("Select a number");
            choice=sc.nextLine();
            switch(choice)
            {
                case "1":
                  //  bookRoom();
                break;
                case "2":
                    //BillService();
                break;
                case "3":
                    
                break;
                case "4":
            
                break;
                case "5":
                    System.exit(0);
                break;
                default:
                    System.out.println("Invalid Choice!");
                    choice="invalid";
                break;
            }
        }while(choice.equals("invalid"));
    }
    public void bookRoom()
    {
        System.out.println("Hotel Reservaion System");
        System.out.print("Enter number of guests");
        
    }
}
