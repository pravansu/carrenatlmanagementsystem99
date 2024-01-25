package com.carrentalmanagementsystem.Crud;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.carrentalmanagementsystem.dao.CarDAO;

import com.carrentalmanagementsystem.entity.Cars;

public class CarCrud {
	
	SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	Session session = factory.openSession();
	Scanner scanner = new Scanner(System.in);
	
	CarDAO carDao = new CarDAO(session);

	
	public void addingofcars() {
		
		System.out.println("==Enter car details==");
		scanner.nextLine();
        System.out.print("Enter car company: ");
        String carMake = scanner.nextLine();
        
        System.out.print("Enter car Name: ");
        String carName = scanner.nextLine();
       
        System.out.print("Enter car model: ");
        String carModel = scanner.nextLine();
        
        System.out.print("Enter car type: ");
        String carType = scanner.nextLine();
        
        System.out.print("Is car available? (yes/no): ");
        String carAvailability = scanner.next();
        
        Cars car = new Cars(carMake, carName, carModel, carType, carAvailability);

        carDao.addCar(car);
        
	}
	
	public void allCarDetails() 
	{
        List<Cars> allCars = carDao.getAllCars();

        if (allCars != null && !allCars.isEmpty())
        {
            System.out.println("All Car Details:");

            for (Cars car : allCars) 
            {
                System.out.println("Car ID: " + car.getCarID());
                System.out.println("Car Make: " + car.getCarMake());
                System.out.println("Car Name: " + car.getCarName());
                System.out.println("Car Model: " + car.getCarModel());
                System.out.println("Car Type: " + car.getCarType());
                System.out.println("Car Availability: " + car.getCarAvailability());
                System.out.println("-----------------------------");
            }
        } else 
        {
            System.out.println("No cars found.");
        }
	}
	public void getCarById() {
        System.out.println("Enter the Car ID to retrieve details:");
        String carIDToRetrieve = scanner.nextLine();

        Cars car = carDao.getCarById(carIDToRetrieve);

        if (car != null) {
            System.out.println("Car Details for ID " + carIDToRetrieve + ":");
            System.out.println("Car ID: " + car.getCarID());
            System.out.println("Car Make: " + car.getCarMake());
            System.out.println("Car Name: " + car.getCarName());
            System.out.println("Car Model: " + car.getCarModel());
            System.out.println("Car Type: " + car.getCarType());
            System.out.println("Car Availability: " + car.getCarAvailability());
        } else {
            System.out.println("Car with ID " + carIDToRetrieve + " not found.");
        }
    }
	public void updateCarDetails() {
        System.out.println("Enter the Car ID to update details:");
        String carIDToUpdate = scanner.nextLine();

        // Check if the car with the given ID exists
        Cars existingCar = carDao.getCarById(carIDToUpdate);

        if (existingCar != null) {
            System.out.println("Enter updated car details:");
            System.out.print("Enter car make: ");
            String updatedCarMake = scanner.nextLine();
            System.out.print("Enter car Name: ");
            String updatedCarName = scanner.nextLine();
            System.out.print("Enter car model: ");
            String updatedCarModel = scanner.nextLine();
            System.out.print("Enter car type: ");
            String updatedCarType = scanner.nextLine();
            System.out.print("Is car available? (yes/no): ");
            String updatedCarAvailability = scanner.next();
            scanner.nextLine(); // Consume newline character

            // Call updateCarDetails method from CarDAO
            carDao.updateCarDetails(carIDToUpdate, updatedCarMake, updatedCarName, updatedCarModel, updatedCarType, updatedCarAvailability);

            System.out.println("Car details updated successfully.");
        } else {
            System.out.println("Car with ID " + carIDToUpdate + " not found.");
        }
    }
	 public String getCarTypeById(String carID) {
	        // Assuming carDao is an instance of CarDAO
	        Cars car = carDao.getCarByType(carID);

	        // Check if the car was found
	        if (car != null) {
	            return car.getCarType();
	        } else {
	            return null; // Car not found
	        }
	    }

}	
	

