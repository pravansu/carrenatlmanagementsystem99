package com.carrentalmanagementsystem.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rent {

	@Id
	@Column(length=10)
	private String rentID;
	
	@Column(nullable = false)
    private Date rentStartDate;
	
	@Column(nullable = false)
    private Date rentEndDate;
    
    @Column(nullable = false, precision = 2)
    private double amount;
    
    @ManyToOne
    @JoinColumn(name = "carID")
    private Cars rentedCar;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer rentingCustomer;

    @ManyToOne
    @JoinColumn(name = "employeeID") // Assuming there's an employee ID field
    private Employee employee; // Adding the reference to Employee
    

}
