package com.carrentalmanagementsystem;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.carrentalmanagementsystem.Crud.CarCrud;
import com.carrentalmanagementsystem.dao.CarDAO;
import com.carrentalmanagementsystem.dao.CustomerDAO;
import com.carrentalmanagementsystem.dao.EmployeeDAO;
import com.carrentalmanagementsystem.dao.RentDAO;
import com.carrentalmanagementsystem.dao.ReservationDAO;
import com.carrentalmanagementsystem.entity.Cars;
import com.carrentalmanagementsystem.entity.Customer;
import com.carrentalmanagementsystem.entity.Employee;
import com.carrentalmanagementsystem.entity.Reservation;


public class Menu {
	SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	Session session = factory.openSession();
	Scanner scanner = new Scanner(System.in);
	
	CustomerDAO customerDao = new CustomerDAO(session);
	EmployeeDAO employeeDao = new EmployeeDAO(session);
	CarDAO carDao = new CarDAO(session);
	ReservationDAO reservationdao =new ReservationDAO(session);
	RentDAO rentDao = new RentDAO(session);
	CarManagement carManagement = new CarManagement();
	CarCrud carCrud = new CarCrud();
	
    
	public void createCustomerAccount() {
		System.out.println("************** Create New Customer Account ************");

		System.out.println("Enter customer details");
		
		System.out.print("Enter customer name: ");
		String name = scanner.nextLine();
		
		System.out.print("Enter customer contact: ");
		String contact = scanner.next();
		
		System.out.print("Enter customer email: ");
		String email = scanner.next();
		
		System.out.print("Enter your password: ");
		String password = scanner.next();
		
		System.out.print("Enter customer address: ");
		String address = scanner.next();

		Customer newCustomer = customerDao.addcustomer(name, contact, email, password, address);
		//customerDAO.addcustomer(newCustomer);

		if (newCustomer != null) {
			System.out.println("Customer account created successfully!");
		} else {
			System.out.println("Customer account creation failed. Please try again.");
		}
	}
	
	public void createEmployeeAccount() {
		System.out.println("************** Create New Employee Account ************");

		System.out.println("Enter Employee details");
		
		System.out.print("Enter employee name: ");
		String empName = scanner.nextLine();
		
		System.out.print("Enter employee contact: ");
		String empContact = scanner.next();
			
		
		System.out.print("Enter employee email: ");
		String empEmail = scanner.next();
		

		System.out.print("Enter employee password: ");
		String password = scanner.next();
		
		Employee emp = new Employee(empName, empEmail, empContact, password);
		

		Employee newEmployee = employeeDao.addEmployee(emp);
		

		if (newEmployee != null) {
			System.out.println("Employee account created successfully!");
		} else {
			System.out.println("Employee account creation failed. Please try again.");
		}
	}
	

	public void loginn() {
	    System.out.println("**************  LOGIN ************");
	    System.out.println("Enter Email:");
	    String emailLogin = scanner.next();
	    System.out.println("Enter Password:");
	    String passwordLogin = scanner.next();

	    // Attempt to log in as a customer
	    Customer loggedInCustomer = customerDao.getCustomerByEmailAndPassword(emailLogin, passwordLogin);

	    if (loggedInCustomer != null) {
	        
	        System.out.println("Customer login successful!");
	        customerMenu(loggedInCustomer);
	         
	    } else {
	        // If customer login fails, attempt to log in as an employee
	        Employee loggedInEmployee = employeeDao.getEmployeeByEmailAndPassword(emailLogin, passwordLogin);

	        if (loggedInEmployee != null) {
	            System.out.println("Employee login successful!");
	         // Call the employee menu method
	           employeeMenu(loggedInEmployee);
	        } else {
	            System.out.println("Login failed. Invalid email or password.");
	        }
	    }
	}

	// Customer menu method
	public void customerMenu(Customer loggedInCustomer) {
        int choice;
        do {
            System.out.println();
            System.out.println("==================[ Welcome to Customer MENU ]======================");
            System.out.println();
            System.out.println("1) View Cars \n2) Reserve a Car  \n3) Check Reservation Status  \n4)rent status \n5) Log Out");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Implement viewCars method
                	System.out.println("See all car Details");
                	carCrud.allCarDetails();
                    System.out.println("Note: note down the CAR ID to reserve a car");
                    System.out.println("Note: note down the type of car for future purpose");
                    break;
                case 2:
                    // Implement reserveCar method
                	   System.out.println(" =========Enter car id  to apply for Reserving a car =====");
                	   System.out.print("Enter Car ID to reserve: ");
                       String carID = scanner.nextLine();
                	   reservationdao.reserveCar(carID, loggedInCustomer);
                    break;
                case 3:
                    // Implement checkReservationStatus method
                	String msg = reservationdao.checkReservationStatus(loggedInCustomer);
                	System.out.println(msg) ;              
                	break;
                case 4:
                  /*  // Enter the car ID to get the type of car
                    System.out.print("Enter the car ID: ");
                    String CarID = scanner.nextLine();

                    // Get the type of car using the car ID
                    String carType = carCrud.getCarTypeById(CarID);

                   
                    Cars car = carDao.getCarById(CarID);
                        // Get the per day price of the car type
                        double perDayPrice = rentDao.getPerDayPriceByCarType(carType);

                        // Calculate the rent amount
                        double rentAmount = rentDao.calculateRentAmountWithUserInput(carType, loggedInCustomer,car);

                        System.out.println("======================================================");
                        System.out.println("Total rent amount: Rs" + rentAmount + " you have to pay ");
                        System.out.println("You can pick up the car from our company.");
                   
                    break;
       */           System.out.print("Enter the car ID: ");
                     String CarID = scanner.nextLine();
                	
                	Cars car = carDao.getCarById(CarID);
                    System.out.print("Enter the car type: ");
                    String carType = scanner.nextLine();
				     double rentAmount = rentDao.calculateRentAmountWithUserInput(carType,loggedInCustomer,car);
                    System.out.println("======================================================");
                    System.out.println("Total rent amount: Rs" + rentAmount +" you have to pay ");
                    System.out.println("You can pick up the car from our company.");
                    break;
                    
                case 5:
                    
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 5);
    }


	// Employee menu method
	public void employeeMenu(Employee loggedInEmployee) {
			int ch;
			do {
				System.out.println();
				System.out.println("==================[ Welcome to Employee MENU ]======================");
				System.out.println();
				System.out.println(
						"1) Car Managment  \n2) Show reservation \n3) confirm reservation   \n4) Log Out");
				System.out.print("Enter your choice: ");
				ch = scanner.nextInt();
				scanner.nextLine();
				switch (ch) {
				case 1:
					carCrud.addingofcars();
					break;

				case 2:
					// to see reservation if there is any reservation
					System.out.print("Enter Car ID to see reservation for a particular car: ");
                    String carID = scanner.nextLine();
					reservationdao.showReservation(carID);
					break;

				case 3:
					System.out.println("Enter Reservation ID to confirm  the Reservation:");
					String reservationID = scanner.nextLine();
				 	reservationdao.confirmReservation(reservationID);
					break;

				case 4:
					
					break;
				}
			} while (ch != 4);
		}
}

	



