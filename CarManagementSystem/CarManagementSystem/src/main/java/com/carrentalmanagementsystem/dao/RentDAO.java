package com.carrentalmanagementsystem.dao;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.carrentalmanagementsystem.entity.Cars;
import com.carrentalmanagementsystem.entity.Customer;
import com.carrentalmanagementsystem.entity.Rent;

public class RentDAO {
	
private Session session;
Scanner scanner = new Scanner(System.in);
	public RentDAO(Session session){
		super();
		this.session = session;
	
	}
	public String fetchLastAddedRentId() {
        Object rentID = session.createQuery("select max(r.rentID) from Rent r").getSingleResult();
        return String.valueOf(rentID);
    }
	public double calculateRentAmountWithUserInput(String carType,Customer customer, Cars car) {
	   // Scanner scanner = new Scanner(System.in);

	    try {
	        Transaction transaction = session.beginTransaction();

	        // Generate the custom rent ID
	        String rentID = fetchLastAddedRentId();
	        if (rentID.contains("null")) {
	            rentID = "RENT100";
	        }
	        String prefix = rentID.substring(0, 4); // RENT
	        int postfix = Integer.parseInt(rentID.substring(4)); // 100
	        String newRentID = prefix + (postfix + 1); // RENT + (100+1) = RENT + 101 = RENT101

	        // Parse the user input to Date objects
	        System.out.print("Enter rent start date (yyyy-MM-dd): ");
	        String startDateInput = scanner.nextLine();
	        Date rentStartDate = Date.valueOf(startDateInput);

	        System.out.print("Enter rent end date (yyyy-MM-dd): ");
	        String endDateInput = scanner.nextLine();
	        Date rentEndDate = Date.valueOf(endDateInput);

	        // Fetch the per day price for the given car type
	        double perDayPrice = getPerDayPriceByCarType(carType);

	        // Calculate the number of days between rentStartDate and rentEndDate
	        long numberOfDays = ChronoUnit.DAYS.between(rentStartDate.toLocalDate(), rentEndDate.toLocalDate());

	        // Calculate the total amount
	        double totalAmount = perDayPrice * numberOfDays;

	        // Create a Rent object with the calculated values
	        Rent rent = new Rent();
	        rent.setRentID(newRentID);
	        rent.setRentStartDate(rentStartDate);
	        rent.setRentEndDate(rentEndDate);
	        rent.setAmount(totalAmount);

	      //Assuming you have the car, customer, and employee objects available
	        rent.setRentedCar(car);
	        rent.setRentingCustomer(customer);
	        

	        // Save the Rent object to the database
	        session.save(rent);

	        transaction.commit();

	        System.out.println("Rent added successfully.");

	        return totalAmount;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0.0;
	    }
	}

    public double getPerDayPriceByCarType(String carType) {
        if ("Hatchback".equalsIgnoreCase(carType)) {
            return 3500.0; 
        } else if ("Sedan".equalsIgnoreCase(carType)) {
            return 4500.0; 
        } else if ("SUV".equalsIgnoreCase(carType)) {
            return 5500.0; 
        } else if ("Coupe".equalsIgnoreCase(carType)) {
            return 5000.0; 
        } else {
            return 0.0; 
        }
    }

}
