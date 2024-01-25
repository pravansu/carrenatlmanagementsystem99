package com.carrentalmanagementsystem.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.carrentalmanagementsystem.dao.EmployeeDAO;
import com.carrentalmanagementsystem.entity.Cars;
import com.carrentalmanagementsystem.entity.Customer;
import com.carrentalmanagementsystem.entity.Reservation;

public class ReservationDAO {

	private Session session;
	EmployeeDAO employeeDao = new EmployeeDAO(session);
	
	public ReservationDAO(Session session){
		super();
		this.session = session;
	
	}

    public String fetchLastAddedId() {
        Object reservationId = session.createQuery("select max(r.reservationID) from Reservation r").getSingleResult();
        return String.valueOf(reservationId);
    }


    // SHOW RESERVATION METHOD
    public void showReservation(String carID) {
        try {
            Cars car = session.get(Cars.class, carID);
            List<Reservation> reservations = session.createQuery("from Reservation where reservedCar.carID = :c", Reservation.class)
                    .setParameter("c", carID)
                    .getResultList();

            if (reservations.isEmpty()) {
                System.out.println("No Reservations!!");
                return;
            }

            for (Reservation reservation : reservations) {
                System.out.println("+---------------- RESERVATIONS ------------------");
                System.out.println("|");
                System.out.println("+--- Car ID: " + car.getCarID());
                System.out.println("+--- Reservation ID: " + reservation.getReservationID());
                System.out.println("+--- Reservation Date: " + reservation.getReservationDate());
                System.out.println("+--- Status: " + reservation.getReservationStatus());
                System.out.println("+-----------------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECK RESERVATION STATUS METHOD
    public String checkReservationStatus(Customer loggedInCustomer) {
        try {
            List<Reservation> reservations = session
                    .createQuery("from Reservation where reservingCustomer.customerID = :customerId", Reservation.class)
                    .setParameter("customerId", loggedInCustomer.getCustomerID())
                    .getResultList();

            if (reservations.isEmpty()) {
                return "SORRY! You have no Confirmed Reservations";
            } else {
                StringBuilder statusInfo = new StringBuilder();
                for (Reservation reservation : reservations) {
                    statusInfo.append("|\n");

                    if ("confirmed".equals(reservation.getReservationStatus())) {
                        statusInfo.append("+-- YOUR RESERVATION HAS BEEN CONFIRMED!\n");
                        statusInfo.append("+-- Reservation ID: ").append(reservation.getReservationID()).append("\n");
                       // Employee confirmingEmployee = reservation.getConfirmingEmployee();
                        
                    } else {
                        // Reservation is pending
                        statusInfo.append("+-- Car ID: ").append(reservation.getReservedCar().getCarID()).append("\n");
                        statusInfo.append("+-- Status: ").append(reservation.getReservationStatus()).append("\n");
                        statusInfo.append("+-- PLEASE WAIT FOR CONFIRMATION FROM THE CAR OWNER.\n");
                    }
                }

                return statusInfo.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while fetching reservation status.";
        }
    }


    // CONFIRM RESERVATION METHOD
    public void confirmReservation(String reservationID) {
        Transaction transaction = null;
        
        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }

            // Retrieve the Reservation based on reservationID
            Reservation reservation = session.get(Reservation.class, reservationID);

            if (reservation == null) {
                System.out.println("Reservation not found with ID: " + reservationID);
                return;
            }

            // Update the reservation status to confirmed
            reservation.setReservationStatus("confirmed");

            // Update the reservation in the database
            session.update(reservation);

            transaction.commit();

            System.out.println("RESERVATION CONFIRMED SUCCESSFULLY!!");
            System.out.println();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // method to reserve a car
    public void reserveCar(String carId, Customer customer) {
        Transaction transaction = null;

        try {
            if (!session.getTransaction().isActive()) {
                transaction = session.beginTransaction();
            }

            // Retrieve the car based on carID
            Cars car = session.get(Cars.class, carId);

            if (car == null) {
                System.out.println("Car not found with ID: " + carId);
                return;
            }

            // Create a new Reservation
            Reservation reservation = new Reservation();
            
         // TO GENERATE THE CUSTOM ID
            String lastReservationId = fetchLastAddedId();

            int newPostfix;

            if (lastReservationId == null || lastReservationId.contains("null")) {
                newPostfix = 100; // Start from 100 if no reservations are found
            } else {
                String prefix = lastReservationId.substring(0, 1);
                int postfix = Integer.parseInt(lastReservationId.substring(1));
                newPostfix = postfix + 1;
            }

            String newReservationId = "R" + newPostfix;

            reservation.setReservationID(newReservationId);
            reservation.setReservedCar(car);
            reservation.setReservingCustomer(customer);
            reservation.setReservationDate(LocalDate.now());
            reservation.setReservationStatus("pending"); // Update to setReservationStatus

            // Save the reservation to the database
            session.save(reservation);

            transaction.commit();

            System.out.println("Car with ID " + carId + " has been sucessfully applied for reservation.");
            System.out.println();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } 
    }
    
}
