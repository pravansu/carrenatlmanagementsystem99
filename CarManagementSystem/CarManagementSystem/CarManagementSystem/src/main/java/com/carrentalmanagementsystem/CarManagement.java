package com.carrentalmanagementsystem;

import java.util.List;
import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.carrentalmanagementsystem.dao.CarsDAO;
import com.carrentalmanagementsystem.daoImpl.CarsDAOImpl;
import com.carrentalmanagementsystem.entity.Cars;

public class CarManagement {

	private static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
			.buildSessionFactory();

	public static void main(String[] args) {
		CarsDAO carsDAO = new CarsDAOImpl(sessionFactory);

		Scanner scanner = new Scanner(System.in);

		boolean exit = false;
		while (!exit) {
			System.out.println("Choose an option:");
			System.out.println("1. Add Car");
			System.out.println("2. Update Car");
			System.out.println("3. Delete Car");
			System.out.println("4. Get Car by ID");
			System.out.println("5. Get All Cars");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline character

			switch (choice) {
			case 1:
				// Add Car
				System.out.println("Enter car details:");
				System.out.print("Enter car ID: ");
				String carID = scanner.nextLine();
				System.out.print("Enter car make: ");
				String carMake = scanner.nextLine();
				System.out.print("Enter car model: ");
				String carModel = scanner.nextLine();
				System.out.print("Enter car type: ");
				String carType = scanner.nextLine();
				System.out.print("Is car available? (yes/no): ");
				String carAvailability = scanner.next();
				scanner.nextLine(); // Consume newline character

				Cars newCar = new Cars(carID, carMake, carModel, carType, carAvailability);
				carsDAO.addCar(newCar);
				System.out.println("Car added successfully.");
				break;
			case 2:
				// Update Car
				System.out.print("Enter car ID to update: ");
				String carIdToUpdate = scanner.nextLine();
				Cars existingCar = carsDAO.getCarByID(carIdToUpdate);
				if (existingCar != null) {
					System.out.println("Enter new make for the car:");
					String newMake = scanner.nextLine();
					existingCar.setCarMake(newMake);
					// Similarly, update other properties if needed
					carsDAO.updateCar(existingCar);
					System.out.println("Car updated successfully.");
				} else {
					System.out.println("Car not found.");
				}
				break;
			case 3:
				// Delete Car
				System.out.print("Enter car ID to delete: ");
				String carIdToDelete = scanner.nextLine();
				carsDAO.deleteCar(carIdToDelete);
				System.out.println("Car deleted successfully.");
				break;
			case 4:
				// Get Car by ID
				System.out.print("Enter car ID to retrieve: ");
				String carIdToRetrieve = scanner.nextLine();
				Cars retrievedCar = carsDAO.getCarByID(carIdToRetrieve);
				if (retrievedCar != null) {
					System.out.println("Car Details:");
					System.out.println("Car Id: "+retrievedCar.getCarID());
					System.out.println("Model: "+retrievedCar.getCarModel());
				} else {
					System.out.println("Car not found.");
				}
				break;
			case 5:
				// Get All Cars
				List<Cars> allCars = carsDAO.getAllCars();
				if (allCars != null && !allCars.isEmpty()) {
					System.out.println("All Cars:");
					for (Cars car : allCars) {
						System.out.println("Car Id: "+car.getCarID());
						System.out.println("Model: "+car.getCarModel());
						System.out.println("===========================");
					}
				} else {
					System.out.println("No cars found.");
				}
				break;
			case 6:
				exit = true;
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}

		}

		// Close resources
		sessionFactory.close();
		scanner.close();
	}
}
