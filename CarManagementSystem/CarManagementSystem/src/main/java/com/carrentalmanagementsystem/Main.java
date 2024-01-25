package com.carrentalmanagementsystem;

import java.util.Scanner;

import com.carrentalmanagementsystem.dao.CarDAO;
import com.carrentalmanagementsystem.dao.CustomerDAO;
import com.carrentalmanagementsystem.dao.EmployeeDAO;
//import com.carrentalmanagementsystem.daoImpl.CarsDAOImpl;
//import com.carrentalmanagementsystem.daoImpl.CustomerDAOImpl;
//import com.carrentalmanagementsystem.daoImpl.EmployeeDAOImpl;
//import com.carrentalmanagementsystem.daoImpl.ReservationDAOImpl;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
	
	// private static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public static void main(String[] args) {

        try {
            
            Scanner sc = new Scanner(System.in);

                 Menu menu = new Menu();
            int choice;
            int ch;

            // Using do-while loop
            do {
                System.out.println();
                System.out.println("WELCOME TO Car Rental Management System");
                System.out.println("1) Create an Account \n2) Log In \n3) Exit");
                System.out.println("Enter Your Choice:");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Press 1) Create customer A/C 2) Create Employee A/C 3) Back");
                        ch = sc.nextInt();
                        if (ch == 1) {
                            // Customer account
                              menu.createCustomerAccount();
                        } else if (ch == 2) {
                            // Employee account
                             menu.createEmployeeAccount();
                        } else if (ch == 3) {
                            break;
                        } else {
                            System.out.println("Wrong Input!!");
                        }
                        break;

                    case 2:
                       menu.loginn();
                        break;

                    case 3:
                        System.out.println("Thank you for visiting!!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Wrong Input!!");
                }
            } while (true);

        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
