package com.carrentalmanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.carrentalmanagementsystem.entity.Customer;
//import com.realestate.entity.User;
//import com.realestate.entity.User;

public class CustomerDAO {
	private Session session;

	public CustomerDAO(Session session) {
		super();
		this.session = session;
	}
	
	
    //void addCustomer(Customer customer);
    //void updateCustomer(Customer customer);
    //void deleteCustomer(String customerId);
   // Customer getCustomerById(String customerId);
   // List<Customer> getAllCustomers();
    // Other methods related to Customer entity
    //Customer getCustomerByEmailAndPassword(String email, String password);
	public String fetchLastAddedId() {
		Object customerID = session.createQuery("select max(c.customerID) from Customer c").getSingleResult();
		return String.valueOf(customerID);
	}

	// method to create an account for Customer
	public Customer addcustomer(String customerName,String customerContact,String customerEmail,String password, String customerAddress) {
		Transaction transaction = null;
		Customer newCustomer = null;
		try {
			if (!session.getTransaction().isActive()) {
				transaction = session.beginTransaction();
			}

			// to generate the custom id
			String customerID = fetchLastAddedId();

			if (customerID.contains("null")) {
				customerID = "C100";
			}

			String prefix = customerID.substring(0, 1); // C
			int postfix = Integer.parseInt(customerID.substring(1)); // 102
			String CId = prefix + (postfix + 1); // C + (102+1) = C + 103 = C103

			newCustomer = new Customer(CId, customerName, customerContact, customerEmail, password, customerAddress);

			// Saving the new user to the database
			session.save(newCustomer);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return newCustomer;
	}
	public Customer findByEmail(String customerEmail) {
		try {
			Customer customer = session.createQuery("from User where email = :e", Customer.class).setParameter("e", customerEmail)
					.getSingleResult();

			return customer;
			
		} catch (Exception e) {
			System.out.println("Customer details not found!!");
		}

		return null;
	}
	 public Customer getCustomerById(int CustomerID) {
	        try {
	        	Customer customer = session.get(Customer.class, CustomerID);
	            return customer;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 public Customer getCustomerByEmailAndPassword(String customerEmail, String password) {
	        try {
	            Customer customer = session.createQuery("from Customer where customerEmail = :email and password = :password", Customer.class)
	                    .setParameter("email", customerEmail)
	                    .setParameter("password", password)
	                    .getSingleResult();

	            return customer;

	        } catch (NoResultException e) {
	            // Handle case where no customer is found
	            return null;
	        }
	    }
}
