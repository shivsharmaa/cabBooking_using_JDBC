package railworld.booking.cab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Scanner;

public class CabBooking {

	private static final String url = "jdbc:mysql://localhost:3306/cab_booking";

	private static final String username = "root";

	private static final String password = "root";

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	

	try {
		Connection connection = DriverManager.getConnection(url, username, password);
		boolean loggedIn = false; // Add this line before the while loop

		while (true) {
		    System.out.println();
		    System.out.println("CAB BOOKING SYSTEM");

		    Scanner scanner = new Scanner(System.in);

		    if (!loggedIn) {
		        System.out.println("1. Registration for a Customer");
		        System.out.println("2. Registration for a Car Owner");
		        System.out.println("3. Login");
		    } else {
		        System.out.println("4. Show Cars");
		        System.out.println("5. Book Ride");
		        System.out.println("6. Show Booking");
		    }

		    System.out.println("0. Exit");
		    System.out.println("Choose an Option");

		    int choice = scanner.nextInt();

		    switch (choice) {

		        case 1:
		            customerRegistration(connection, scanner);
		            break;

		        case 2:
		            carOwnerRegistration(connection, scanner);
		            break;

		        case 3:
		            loggedIn = login(connection, scanner); // Assuming login() returns a boolean indicating successful login
		            break;

		        case 4:
		            if (loggedIn) {
		                cars(connection);
		            } else {
		                System.out.println("Please log in first.");
		            }
		            break;

		        case 5:
		            if (loggedIn) {
		                createBooking(connection, scanner);
		            } else {
		                System.out.println("Please log in first.");
		            }
		            break;

		        case 6:
		            if (loggedIn) {
		                showBooking(connection, scanner);
		            } else {
		                System.out.println("Please log in first.");
		            }
		            break;

		        case 0:
		            exit();
		            scanner.close();
		            return;

		        default:
		            System.out.println("Invalid choice. Try again later...");

		    }
		}

				
			
	}catch(SQLException e) {
		e.printStackTrace();
	}

//	}catch(InterruptedException e) {
//		throw new RuntimeException(e);
//	}
	
	
	}	
	
	//--------------------------------------Car Owner Registration ------------------------------------------------------------
	
	public static void carOwnerRegistration(Connection connection, Scanner scanner) {
		try {
			System.out.print("Enter Car Owner name: ");
			String name = scanner.next();
			scanner.nextLine();
			
			System.out.println("Enter Car Owner email id: ");
			String email = scanner.nextLine();
			
			System.out.println("Enter Car Owner password: ");
			String password = scanner.nextLine();
			
			
			System.out.println("Enter Customer Address: ");
			String address=scanner.nextLine();
			
			System.out.println("Enter Customer phone number: ");
			String phone_number=scanner.next();
			
			
			
//			-- Insert login details into LoginTable
			String loginQuery = "INSERT INTO LoginTable (password, email, role)"+ "VALUES( ?,?,? )";
			
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, password);
            loginStatement.setString(2, email);
            loginStatement.setString(3, "car_owner");
            loginStatement.executeUpdate();

	
//			-- Insert owner details
	  String carOwnerQuery = "INSERT INTO carowner(name, address, email,  phone) " +
              "SELECT ?, ?, ?, ? FROM dual WHERE EXISTS (SELECT 1 FROM LoginTable WHERE email = ?)";
	  
	  PreparedStatement customerStatement = connection.prepareStatement(carOwnerQuery);
      customerStatement.setString(1, name);
      customerStatement.setString(2, email);
      customerStatement.setString(3, address);
      customerStatement.setString(4, phone_number);
      customerStatement.setString(5, email);
      customerStatement.executeUpdate();
			
			
      	System.out.println("Car Owner registered successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			}
		}

			
			
	
//	----------------------- Customer Owner Reservation ----------------------------------


	public static void customerRegistration(Connection connection, Scanner scanner) {
		try {
			System.out.print("Enter Customer first name: ");
			String first_name = scanner.next();
			scanner.nextLine();
			System.out.print("Enter Customer last name: ");
			String last_name = scanner.next();
			scanner.nextLine();
			
			System.out.println("Enter email id: ");
			String email = scanner.nextLine();
			
			System.out.println("Enter Password: ");
			String password = scanner.nextLine();
			
			
			System.out.println("Enter Customer Address");
			String address=scanner.nextLine();
			
			System.out.println("Enter Customer phone number");
			String phone_number=scanner.next();
			
			
			
            // Insert login details into LoginTable


            String loginQuery = "INSERT INTO LoginTable (password, email, role) VALUES (?, ?, ?)";
            PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, password);
            loginStatement.setString(2, email);
            loginStatement.setString(3, "customer");
            loginStatement.executeUpdate();

            // Insert customer details
            String customerQuery = "INSERT INTO Customer (first_name, last_name, email, address, phone_number) " +
                    "SELECT ?, ?, ?, ?, ? FROM dual WHERE EXISTS (SELECT 1 FROM LoginTable WHERE email = ?)";
            PreparedStatement customerStatement = connection.prepareStatement(customerQuery);
            customerStatement.setString(1, first_name);
            customerStatement.setString(2, last_name);
            customerStatement.setString(3, email);
            customerStatement.setString(4, address);
            customerStatement.setString(5, phone_number);
            customerStatement.setString(6, email);
            customerStatement.executeUpdate();

            System.out.println("Customer registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
		}


	
	// -------------------------------------- login ---------------------------------------------------------------
	
	private static boolean login(Connection connection, Scanner scanner) {
		try {
	        System.out.print("Enter Email id: ");
	        String email = scanner.nextLine();
	        scanner.nextLine();
	        System.out.print("Enter Password: ");
	        String password = scanner.nextLine();
			
			
			
			String loginQuery = "SELECT * FROM logintable WHERE email=? AND password=?";
			PreparedStatement loginStatement = connection.prepareStatement(loginQuery);
			loginStatement.setString(1, email);
			loginStatement.setString(2, password);
			
			ResultSet resultSet = loginStatement.executeQuery();
			if(resultSet!=null) {
				 String message = "Login Successfull!! \n";
				 
			      try {
		            for (char c : message.toCharArray()) {
		                System.out.print(c);
		                Thread.sleep(50); // Adjust the delay time as needed
		            	
		            }
					System.out.println("WELCOME !!!!");

		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
				
			      return true;
			
			}else {
				System.out.println("Login Failed!!");
				System.out.println("Please Try again later");
				return false;
			}
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	
	// --------------------------------------Car ------------------------------------
	
	private static void cars(Connection connection) {
	    try {
	        String retrieveQuery = "SELECT * FROM Cars";
	        PreparedStatement preparedStatement = connection.prepareStatement(retrieveQuery);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        System.out.println("+----------+----------+----------------+------------------------+----------------------+");
            System.out.println("| CAR ID   | OWNER ID | CAR NUMBER     | CAR MODEL              | RUPEES PER KILOMETER |");
            System.out.println("+----------+----------+----------------+------------------------+----------------------+");

	        while (resultSet.next()) {
	            int carId = resultSet.getInt(1);
	            int ownerId = resultSet.getInt(2);
	            String carNumber = resultSet.getString(3);
	            String carModel = resultSet.getString(4);
	            double kilometerRate = resultSet.getDouble(5);

	            
	            System.out.printf("|%-11d | %-8d | %-15s | %-22s | %-20.2f |\n",
	                    carId, ownerId, carNumber, carModel, kilometerRate);

	            System.out.println("+----------+----------+----------------+------------------------+----------------------+");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	// ----------------------------------- create Booking ---------------------------------------
	
	
	
	
	public static void createBooking(Connection connection, Scanner scanner) {
	    Random random = new Random();

	    try {
	        int ownerId = 1;
	        int carId = 1;

	        System.out.println("Enter starting point: ");
	        String starting_point = scanner.next();
	        
	        scanner.nextLine();

	        System.out.println("Enter ending point: ");
	        String ending_point = scanner.nextLine();

	        Timestamp start_time = new Timestamp(System.currentTimeMillis());
	        
	        // Adding 1 hour to the current time to set end time
	        long oneHourInMillis = 60 * 60 * 1000; // 1 hour in milliseconds
	        Timestamp end_time = new Timestamp(start_time.getTime() + oneHourInMillis);

	        double price = (random.nextDouble() * 100 + 10) * 10.5;

	        try {
	            String insertQuery = "INSERT INTO Rides (owner_id, car_id, starting_point, ending_point, time_for_start, expected_time_for_reach_destination, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
	            preparedStatement.setInt(1, ownerId);
	            preparedStatement.setInt(2, carId);
	            preparedStatement.setString(3, starting_point);
	            preparedStatement.setString(4, ending_point);
	            preparedStatement.setTimestamp(5, start_time);
	            preparedStatement.setTimestamp(6, end_time);
	            preparedStatement.setDouble(7, price);

	            int rowsInserted = preparedStatement.executeUpdate();
	            if (rowsInserted > 0) {
	                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    int rideId = generatedKeys.getInt(1);
	                    System.out.println("Ride data inserted successfully with Ride ID: " + rideId);
	                }
	            } else {
	                System.out.println("Failed to insert ride data.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	
	//-----------------------------------------Show Booking -------------------------------------------------------------
	public static void showBooking(Connection connection, Scanner scanner) {
	    try {
	        System.out.println("Enter Ride id: ");
	        int ride_id = scanner.nextInt(); // Assuming ride_id is an integer
	        
	        // Consume the newline character
	        scanner.nextLine();

	        String query = "SELECT * FROM RIDES WHERE ride_id=?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, ride_id);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Printing booking details
	        while (resultSet.next()) {
	            int rideId = resultSet.getInt("ride_id");
	            String start = resultSet.getString("starting_point");
	            String end = resultSet.getString("ending_point");
	            double payment = resultSet.getDouble("price");
	            int car_id = resultSet.getInt("car_id");

	            // Print out the booking details
	            System.out.println("Ride ID: " + rideId);
	            System.out.println("Starting Point: " + start);
	            System.out.println("Ending Point: " + end);
	            System.out.println("Price: " + payment);
	            System.out.println("Car ID: " + car_id);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	    // ----------------------------- exit code -----------------------------------------
	    
	    
	    private static void exit() throws InterruptedException 
		{
			System.out.print("Exiting System");
			int i=5;
			while(i!=0) {
				System.out.print(".");
				Thread.sleep(300);
				i--;
			}
			System.out.println();
			 String message = "Thank you for Using Hotel Management System!!!";
		      try {
	            for (char c : message.toCharArray()) {
	                System.out.print(c);
	                Thread.sleep(50); // Adjust the delay time as needed
	            }
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
}
