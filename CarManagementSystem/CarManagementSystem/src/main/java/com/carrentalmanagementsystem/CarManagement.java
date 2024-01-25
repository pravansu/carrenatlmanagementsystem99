package com.carrentalmanagementsystem;

import java.util.Scanner;

import com.carrentalmanagementsystem.Crud.CarCrud;

public class CarManagement {
	
	Scanner scanner = new Scanner(System.in);
	CarCrud carcrud = new CarCrud();

     int ch;
     
    public void carMenu() {
      
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose an option:");
            System.out.println("1. Add Car:");
            System.out.println("2. Update Car:");
            System.out.println("3. Get Car by ID:");
            System.out.println("4. Get All Cars:");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    // Add Car
                	carcrud.addingofcars();
                    break;
                case 2:
                    // Update Car
                	carcrud.updateCarDetails();
                    break;
                case 3:
                    // Get Car by ID
                   carcrud.getCarById();
                    break;
                case 4:
                    // Get All Cars
                	carcrud.allCarDetails();
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }

    }
}
