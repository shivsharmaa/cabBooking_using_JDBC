import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

        private static final String url="jdbc:mysql://localhost:3306/cab_booking";
        private static final String username="root";
        private  static final String password="Suman@123";

        public static void main(String[] args) {

            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            Scanner scanner=new Scanner(System.in);
            try{
                Connection connection= DriverManager.getConnection(url,username,password);
                Customer customer=new Customer(connection,scanner);
                Cab cab=new Cab(connection);
                while (true){
                    System.out.println(" Online Cab Booking  ");
                    System.out.println("1. Exit");
                    System.out.println("2. Register");
                    System.out.println("3. Login");
                    System.out.println("Enter Your Choice: ");
                    int choice= scanner.nextInt();


                    }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        }
