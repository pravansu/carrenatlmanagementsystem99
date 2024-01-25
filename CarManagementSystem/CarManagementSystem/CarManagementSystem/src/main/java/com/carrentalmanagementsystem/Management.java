package com.carrentalmanagementsystem;

import com.carrentalmanagementsystem.dao.CustomerDAO;
import com.carrentalmanagementsystem.daoImpl.CustomerDAOImpl;
import com.carrentalmanagementsystem.entity.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class Management {

    private static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAOImpl(sessionFactory);

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Choose an option:");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Get Customer by ID");
            System.out.println("5. Get All Customers");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
            case 1:
                System.out.println("Enter customer details:");
                System.out.print("Enter customer ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter customer name: ");
                String name = scanner.nextLine();
                System.out.print("Enter customer contact: ");
                String contact = scanner.next();
                scanner.nextLine();
                System.out.print("Enter customer email: ");
                String email = scanner.nextLine();
                System.out.print("Enter customer address: ");
                String add = scanner.nextLine();
                Customer newCustomer = new Customer(id, name, contact, email, add);
                // Add the customer with updated details
                customerDAO.addCustomer(newCustomer);
                System.out.println("Customer added successfully.");
                break;

                case 2:
                    System.out.print("Enter customer ID to update: ");
                    String customerIdToUpdate = scanner.nextLine();
                    Customer existingCustomer = customerDAO.getCustomerById(customerIdToUpdate);
                    if (existingCustomer != null) {
                        System.out.println("Enter new name for the customer:");
                        String newName = scanner.nextLine();
                        existingCustomer.setCustomerName(newName);
                        customerDAO.updateCustomer(existingCustomer);
                        System.out.println("Customer updated successfully.");
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter customer ID to delete: ");
                    String customerIdToDelete = scanner.nextLine();
                    customerDAO.deleteCustomer(customerIdToDelete);
                    System.out.println("Customer deleted successfully.");
                    break;
                case 4:
                    System.out.print("Enter customer ID to retrieve: ");
                    String customerId = scanner.nextLine();
                    Customer retrievedCustomer = customerDAO.getCustomerById(customerId);
                    if (retrievedCustomer != null) {
                        System.out.println("Customer Details:");
                        System.out.println(retrievedCustomer);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 5:
                    List<Customer> allCustomers = customerDAO.getAllCustomers();
                    if (allCustomers != null && !allCustomers.isEmpty()) {
                        System.out.println("All Customers:");
                        for (Customer customer : allCustomers) {
                            System.out.println(customer);
                        }
                    } else {
                        System.out.println("No customers found.");
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

