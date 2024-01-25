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
public class Employee {
	
	@Id
	@Column(length = 10)
	private String empID;
	
	@Column(length = 30, nullable= false)
    private String empName;
	
	@Column(length = 30, nullable= false)
    private String empEmail;
	
	@Column(length = 10, nullable= false)
    private String empContact;
	
	@Column(length = 10, nullable= false)
    private String password;
    
    @OneToMany(mappedBy = "employee")
    private List<Rent> rentsManaged;
    
    

	public Employee(String empID, String empName, String empEmail, String empContact, String password) 
	{
		super();
		this.empID = empID;
		this.empName = empName;
		this.empEmail = empEmail;
		this.empContact = empContact;
		this.password = password;
	}



	public Employee(String empName, String empEmail, String empContact, String password) {
		super();
		this.empName = empName;
		this.empEmail = empEmail;
		this.empContact = empContact;
		this.password = password;
	}
}
