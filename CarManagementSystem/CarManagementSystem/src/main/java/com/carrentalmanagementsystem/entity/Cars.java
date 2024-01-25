package com.carrentalmanagementsystem.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Cars {

	@Id
	@Column(length=10)
	private String carID;
	
	@Column(length = 20, nullable= false)
    private String carMake;
	
	@Column(length = 20, nullable= false)
	private String carName;
	
	@Column(length = 20, nullable= false)
    private String carModel;
	
	@Column(length = 20, nullable= false)
    private String carType;
	
	@Column(nullable=false)
    private String carAvailability;
	   
    @OneToMany(mappedBy = "rentedCar")
    private List<Rent> rents;

    @OneToMany(mappedBy = "reservingCustomer")
    private List<Reservation> reservations;

	public Cars(String carMake, String carName, String carModel, String carType, String carAvailability) {
		super();
		this.carMake = carMake;
		this.carName = carName;
		this.carModel = carModel;
		this.carType = carType;
		this.carAvailability = carAvailability;
	}
    
    

    
}
