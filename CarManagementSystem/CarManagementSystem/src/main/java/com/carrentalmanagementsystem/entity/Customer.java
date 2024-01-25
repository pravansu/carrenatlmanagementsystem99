package com.carrentalmanagementsystem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {

	@Id
	@Column(length=10)
	private String customerID;
	
	@Column(length = 20, nullable= false)
    private String customerName;
	
	@Column(length = 10, nullable= false, unique=true)
    private String customerContact;
	
	@Column(length = 50, nullable= false, unique=true)
    private String customerEmail;
	
	@Column(length = 10, nullable= false, unique=true)
    private String password;
	
	@Column(length = 150, nullable= false)
    private String customerAddress;
    
    @OneToMany(mappedBy = "rentingCustomer")
    private List<Rent> rents;

    @OneToMany(mappedBy = "reservingCustomer")
    private List<Reservation> reservations;
    
    public Customer(String customerID,String customerName,String customerContact,String customerEmail,String password, String customerAddress)
    {
    	super();
    	this.customerID = customerID;
    	this.customerName = customerName;
    	this.customerContact = customerContact;
    	this.customerEmail = customerEmail;
    	this.password = password;
    	this.customerAddress = customerAddress;
    }

	
}
